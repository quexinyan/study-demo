package demo.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.junit.Test;

import java.util.Map;

/**
 * @Time           2018/6/28 16:50
 * @Author          gaox
 * @Description     JAVA Api 查询操作
*/
public class ApiSearchTest {

    /**
    * 分页查询-from-size
     * from定义了目标数据的偏移值，size定义当前返回的数目。
     * 默认from为0，size为10，即所有的查询默认仅仅返回前10条数据。
     * 这种查询方式的缺点是越往后的分页，执行效率越低。随着from的增加，消耗时间也会增加。
     * 而且数据量越大，效果越明显！也就是说，分页的偏移值越大，执行分页查询时间就会越长！
    * @author      gaox
    * @date        2018/7/3 11:46
    */
    @Test
    public void page_from_size(){
        TransportClient client = ESUtils.getClient();
        SearchResponse response = client.prepareSearch("aggregations")
                .setTypes("aggregation").setFrom(1)
                .setSize(2).setQuery(QueryBuilders.matchAllQuery())
                .execute().actionGet();
        SearchHits hits=response.getHits();
        for(SearchHit hit:hits){
            System.out.println("source:"+hit.getSourceAsString());
            System.out.println("--------------------");
        }
    }

    /**
     * 分页查询-scroll
     *
     * scroll:
     * Scroll API像传统数据库里的cursors（游标），可以允许我们检索大量数据（甚至全部数据），它允许我们做一个初始阶段搜索并且持续批量从Elasticsearch里拉取结果直到没有结果剩下。
     * 相对于from-size的分页来说，使用scroll可以模拟一个传统数据的游标，记录当前读取的文档信息位置。这个分页的用法，不是为了实时查询数据，而是为了一次性查询大量的数据（甚至是全部的数据）。
     * 因为这个scroll相当于维护了一份当前索引段的快照信息，这个快照信息是你执行这个scroll查询时的快照。在这个查询后的任何新索引进来的数据，都不会在这个快照中查询到。
     * 但是它相对于from-size，不是查询所有数据然后剔除不要的部分，而是记录一个读取的位置，保证下一次快速继续读取
     *
     * SearchType详解:
     * 1、query and fetch
     向索引的所有分片（shard）都发出查询请求，各分片返回的时候把元素文档（document）和计算后的排名信息一起返回。这种搜索方式是最快的。因为相比下面的几种搜索方式，这种查询方法只需要去shard查询一次。但是各个shard返回的结果的数量之和可能是用户要求的size的n倍。

     2、query then fetch（默认的搜索方式）
     如果你搜索时，没有指定搜索方式，就是使用的这种搜索方式。这种搜索方式，大概分两个步骤，第一步，先向所有的shard发出请求，各分片只返回排序和排名相关的信息（注意，不包括文档document)，然后按照各分片返回的分数进行重新排序和排名，取前size个文档。然后进行第二步，去相关的shard取document。这种方式返回的document可能是用户要求的size的n倍

     3、DFS query and fetch
     这种方式比第一种方式多了一个初始化散发(initial scatter)步骤，有这一步，据说可以更精确控制搜索打分和排名。这种方式返回的document与用户要求的size是相等的。

     4、DFS query then fetch
     比第2种方式多了一个初始化散发(initial scatter)步骤。这种方式返回的document与用户要求的size是相等的。

     总结一下，从性能考虑QUERY_AND_FETCH是最快的，DFS_QUERY_THEN_FETCH是最慢的。从搜索的准确度来说，DFS要比非DFS的准确度更高。
     * @author      gaox
     * @date        2018/7/3 14:10
     */
    @Test
    public void page_scrol(){
        TransportClient client = ESUtils.getClient();
        SearchResponse searchResponse = client.prepareSearch("aggregations")
                .setTypes("aggregation")
                .setScroll(TimeValue.timeValueMinutes(1)) // 游标维持时间
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .execute()
                .actionGet();

        int i = 1;
        String scrollId = searchResponse.getScrollId();
        System.out.println("----------第"+i+"次查询-------------");
        System.out.println("searchByScroll scrollID: "+scrollId);
        i++;
        for (SearchHit hit : searchResponse.getHits().getHits()){
            String source = hit.getSourceAsString();
            System.out.println("searchByScroll source: "+source);
        }


        while (true){
            System.out.println("----------第"+i+"次查询-------------");
            System.out.println("searchByScroll scrollID: "+scrollId);
            i++;
            // 使用上次的scrollId继续访问,这个 ID 可以传递给 scroll API 来检索下一个批次的结果.
            searchResponse = client.prepareSearchScroll(scrollId).setScroll(new TimeValue(80000)).execute().actionGet();
            // 每次返回下一个批次结果 直到没有结果返回时停止 即hits数组空时
            if(searchResponse.getHits().getHits().length == 0){
                break;
            }
            // 这一批查询结果
            for (SearchHit hit : searchResponse.getHits().getHits()){
                String source = hit.getSourceAsString();
                System.out.println("searchByScroll source: "+source);
            }
            // 只有最近的滚动ID才能被使用
            scrollId = searchResponse.getScrollId();
        }
    }

    /**
    * 分页查询-scroll(代码改进)
    * @author      gaox
    * @date        2018/7/3 16:08
    */
    @Test
    public void page_scro2(){
        TransportClient client = ESUtils.getClient();
        SearchResponse searchResponse = client.prepareSearch("aggregations")
                .setTypes("aggregation")
                .setScroll(TimeValue.timeValueMinutes(1)) // 游标维持时间
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(3)
                .execute()
                .actionGet();

        int i = 0;
        do {
            i++;
            String scrollId = searchResponse.getScrollId();

            System.out.println("----------第"+i+"次查询-------------");
            System.out.println("searchByScroll scrollID: "+scrollId);
            // 这一批查询结果
            for (SearchHit hit : searchResponse.getHits().getHits()){
                String source = hit.getSourceAsString();
                System.out.println("searchByScroll source: "+source);
            }

            // 使用上次的scrollId继续访问,这个 ID 可以传递给 scroll API 来检索下一个批次的结果.
            searchResponse = client.prepareSearchScroll(scrollId).setScroll(new TimeValue(80000)).execute().actionGet();
        }while (searchResponse.getHits().getHits().length != 0);

    }

    /**
    * Java API之Match All Query
     * Elasticsearch以类似于REST Query DSL的方式提供完整的Java查询dsl。 查询构建器的工厂是QueryBuilders。
     * 一旦您的查询准备就绪，您可以使用搜索API。
    * @author      gaox
    * @date        2018/6/28 16:53
    */
    @Test
    public void MatchAllQuery(){
        TransportClient client = ESUtils.getClient();
        //构造查询对象
        QueryBuilder query= QueryBuilders.matchAllQuery();
        //搜索结果存入SearchResponse
        SearchResponse response=client.prepareSearch("twitter", "twitter2")
                .setQuery(query) //设置查询器
                .setSize(3)      //一次查询文档数
                .get();
        SearchHits hits=response.getHits();
        for(SearchHit hit:hits){
            System.out.println("source:"+hit.getSourceAsString());
            System.out.println("index:"+hit.getIndex());
            System.out.println("type:"+hit.getType());
            System.out.println("id:"+hit.getId());
            //遍历文档的每个字段
            Map<String,Object> map=hit.getSourceAsMap();
            for(String key:map.keySet()){
                System.out.println(key+"="+map.get(key));
            }
            System.out.println("--------------------");
        }
    }

    /**
    * Match
     * 模糊匹配，比如"宝马多少马力"会被分词为"宝马 多少 马力", 所有有关"宝马 多少 马力",
     * 那么所有包含这三个词中的一个或多个的文档就会被搜索出来。比如一个文档"我的保时捷马力不错"也会被搜索出来
     *
     * 查询结果：
     * source:{"user":"kimchy","postDate":"2018-06-28T09:43:51.878Z","message":"我的保时捷马力不错"}
     index:twitter2
     type:tweet
     id:2
     postDate=2018-06-28T09:43:51.878Z
     message=我的保时捷马力不错
     user=kimchy
     source:{"user":"kimchy","postDate":"2018-06-28T09:43:51.775Z","message":"我的宝马多少马力"}
     index:twitter2
     type:tweet
     id:1
     postDate=2018-06-28T09:43:51.775Z
     message=我的宝马多少马力
     user=kimchy

     Process finished with exit code 0

     * @author      gaox
    * @date        2018/6/28 17:26
    */
    @Test
    public void MatchQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.matchQuery(
                "message",
                "我的保时捷马力不错");
        util.query(qb).print();
    }

    /**
    * MatchPhrase
     * 短语匹配：精确匹配包含所有短语的文档
    * @author      gaox
    * @date        2018/6/28 18:03
    */
    @Test
    public void MatchPhraseQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.matchPhraseQuery(
                "message",
                "我的保时捷马力不错");
        util.query(qb).print();
    }

    /**
    * 希望两个字段进行匹配，其中一个字段有这个文档就满足的话
    * @author      gaox
    * @date        2018/6/29 9:28
    */
    @Test
    public void MultiMatchQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        QueryBuilder qb=QueryBuilders.multiMatchQuery("kimchy","message","user");
        util.query(qb).print();
    }

    /**
    * 查询用于匹配数值型、日期型或字符串型字段在某一范围内的文档
    * @author      gaox
    * @date        2018/6/29 9:49
    */
    @Test
    public void RangeQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.rangeQuery("postDate").from("2018-06-28").to("2018-12-31").format("yyyy-MM-dd");
        util.query(qb).print();
    }

    /**
    * 返回原始字段中至少包含一个非空值的文档
    * @author      gaox
    * @date        2018/6/29 9:51
    */
    @Test
    public void ExistsQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.existsQuery("postDate");
        util.query(qb).print();
    }

    /**
    * 查询以什么开头的文档
    * @author      gaox
    * @date        2018/6/29 9:53
    */
    @Test
    public void prefixQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.prefixQuery("name","kim");
        util.query(qb).print();
    }

    /**
    * 通配符查询
    * @author      gaox
    * @date        2018/6/29 9:54
    */
    @Test
    public void wildcard(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.wildcardQuery("name","*kim*");
        util.query(qb).print();
    }

    /**
    * 正则表达式查询
    * @author      gaox
    * @date        2018/6/29 9:57
    */
    @Test
    public void regexpQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.regexpQuery("name","ki.*");
        util.query(qb).print();
    }

    /**
    * 模糊查询
    * @author      gaox
    * @date        2018/6/29 10:09
    */
    @Test
    public void fuzzyQuery(){
        QueryUtil util=new QueryUtil("twitter2",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.fuzzyQuery("name","mch");
        util.query(qb).print();
    }

    /**
    * type查询
    * @author      gaox
    * @date        2018/6/29 10:10
    */
    @Test
    public void typeQuery(){
        QueryUtil util=new QueryUtil("twitter2",2);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.typeQuery("tweet");
        util.query(qb).print();
    }

    /**
    * ids查询
    * @author      gaox
    * @date        2018/6/29 10:11
    */
    @Test
    public void idsQuery(){
        QueryUtil util=new QueryUtil("twitter2",2);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.idsQuery().addIds("1","3");
        util.query(qb).print();
    }

    /*********************************聚合查询 begin***********************************/

    private AggregationSearchUtil aggregationUtil = new AggregationSearchUtil("aggregations");
    /**
    * 最大值统计
    * @author      gaox
    * @date        2018/7/2 14:09
    */
    @Test
    public void aggregationMaxSearch(){

        double max = aggregationUtil.max("age");
        System.out.println("max age="+max);
    }

    /**
    * 最小值统计
    * @author      gaox
    * @date        2018/7/2 14:58
    */
    @Test
    public void aggregationMinSearch(){
        double min = aggregationUtil.min("age");
        System.out.println("min age="+min);
    }

    /**
    * 平均值统计
    * @author      gaox
    * @date        2018/7/2 15:18
    */
    @Test
    public void aggregationAvgSearch(){
        double salary = aggregationUtil.avg("salary");
        System.out.println("avg salary="+salary);
    }

    /**
    * 合计统计
    * @author      gaox
    * @date        2018/7/2 15:19
    */
    @Test
    public void aggregationSumSearch(){
        double salary = aggregationUtil.sum("salary");
        System.out.println("sum salary="+salary);
    }

    /**
     * 基本统计
     * @author      gaox
     * @date        2018/7/2 15:19
     */
    @Test
    public void aggregationStatsSearch(){
        Stats stats = aggregationUtil.stats("salary");
        System.out.println("min="+stats.getMin());
        System.out.println("max="+stats.getMax());
        System.out.println("avg="+stats.getAvg());
        System.out.println("sum="+stats.getSum());
        System.out.println("count="+stats.getCount());
    }

    /**
     * 高级统计
     * @author      gaox
     * @date        2018/7/2 15:19
     */
    @Test
    public void aggregationExtendedStatsSearch(){
        ExtendedStats stats = aggregationUtil.extendedStats("salary");
        System.out.println("min="+stats.getMin());
        System.out.println("max="+stats.getMax());
        System.out.println("avg="+stats.getAvg());
        System.out.println("sum="+stats.getSum());
        System.out.println("count="+stats.getCount());
        System.out.println("stdDeviation="+stats.getStdDeviation());
        System.out.println("sumOfSquares="+stats.getSumOfSquares());
        System.out.println("variance="+stats.getVariance());
    }

    /**
    * 基数统计
    * @author      gaox
    * @date        2018/7/2 15:27
    */
    @Test
    public void aggregationCardinalitySearch(){
        double cardinality = aggregationUtil.cardinality("salary");
        System.out.println("cardinality="+cardinality);
    }

    /**
     * 百分位统计
     * @author      gaox
     * @date        2018/7/2 15:27
     */
    @Test
    public void aggregationPercentilesSearch(){
        Percentiles percent = aggregationUtil.percentiles("salary");
        for(Percentile p:percent) {
            System.out.printf("percent [%f],value [%f]\n",p.getPercent(),p.getValue());
        }
    }

    /**
     * 文档数量统计
     * @author      gaox
     * @date        2018/7/2 15:27
     */
    @Test
    public void aggregationValueCountSearch(){
        double count = aggregationUtil.valueCount("salary");
        System.out.println("count="+count);
    }

    /**
     * 分组聚合
     * @author      gaox
     * @date        2018/7/2 15:27
     */
    @Test
    public void aggregationTermsSearch(){
        Terms terms = aggregationUtil.terms("salary");
        for(Terms.Bucket entry:terms.getBuckets()){
            System.out.println(entry.getKey()+":"+entry.getDocCount());
        }
    }

    /**
    * 过滤器聚合
    * @author      gaox
    * @date        2018/7/2 18:16
    */
    @Test
    public void aggregationFilterSearch(){
        Filter filter = aggregationUtil.filter("age", "26");
        System.out.println(filter.getDocCount());
    }

    /**
     * 多过滤器聚合
     * @author      gaox
     * @date        2018/7/2 18:16
     */
    @Test
    public void aggregationFiltersSearch(){
        Filters filters = aggregationUtil.filters("age", "26", "user", "gaox");
        for(Filters.Bucket entry:filters.getBuckets()){
            System.out.println(entry.getKey()+":"+entry.getDocCount());
        }
    }

    /**
     * 区间聚合
     * @author      gaox
     * @date        2018/7/2 18:16
     */
    @Test
    public void aggregationRangeSearch(){
        Range range = aggregationUtil.range("salary", 4600, 12000);
        for(Range.Bucket entry:range.getBuckets()){
            System.out.println(entry.getKey()+":"+entry.getDocCount());
        }
    }

    /**
    * 日期区间聚合
    * @author      gaox
    * @date        2018/7/2 18:51
    */
    @Test
    public void aggregationDateRangeSearch(){
        Range dateRange = aggregationUtil.dateRange("postdate", "now-12M/M", "now-12M/M");
        for(Range.Bucket entry:dateRange.getBuckets()){
            System.out.println(entry.getKey()+":"+entry.getDocCount());
        }
    }

    /**
    * Missing聚合
    * @author      gaox
    * @date        2018/7/2 18:56
    */
    @Test
    public void aggregationMissingSearch(){
        Missing missing = aggregationUtil.missing("salary1");
        System.out.println(missing.getDocCount());
    }

    /*********************************聚合查询 end***********************************/
}
