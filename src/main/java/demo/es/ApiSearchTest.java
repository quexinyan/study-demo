package demo.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import java.util.Map;

/**
 * @Time           2018/6/28 16:50
 * @Author          gaox
 * @Description     JAVA Api 查询操作
*/
public class ApiSearchTest {

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
}
