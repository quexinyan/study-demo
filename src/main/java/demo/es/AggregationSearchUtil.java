package demo.es;/*
 * Time : 2018/7/2 14:15
 * Author : gaox
 * Description :
 */

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;

/**
 * @Time           2018/7/2 14:15
 * @Author          gaox
 * @Description     聚合查询工具类
*/
public class AggregationSearchUtil {

    private String index="index";
    private TransportClient client = ESUtils.getClient();

    public AggregationSearchUtil(String index){
        this.index=index;
    }

    /**
     * 最大值统计
     * @param field
     * @return
     */
    public double max(String field){
        AggregationBuilder agg= AggregationBuilders.max("aggMax").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Max max=response.getAggregations().get("aggMax");
        return max.getValue();
    }

    /**
     * 最小值统计
     * @param field
     * @return
     */
    public double min(String field){
        AggregationBuilder agg=AggregationBuilders.min("aggMin").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Min min=response.getAggregations().get("aggMin");
        return min.getValue();
    }

    /**
     * 合计统计
     * @param field
     * @return
     */
    public double sum(String field){
        AggregationBuilder agg=AggregationBuilders.sum("aggSum").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Sum sum=response.getAggregations().get("aggSum");
        return sum.getValue();
    }

    /**
     * 平均值统计
     * @param field
     * @return
     */
    public double avg(String field){
        AggregationBuilder agg=AggregationBuilders.avg("aggAvg").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Avg avg=response.getAggregations().get("aggAvg");
        return avg.getValue();
    }

    /**
     * 基本统计
     * @param field
     * @return
     */
    public Stats stats(String field){
        AggregationBuilder agg=AggregationBuilders.stats("aggStats").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("aggStats");
    }

    /**
     * 高级统计
     * @param field
     * @return
     */
    public ExtendedStats extendedStats(String field){
        AggregationBuilder agg=AggregationBuilders.extendedStats("exStats").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("exStats");
    }

    /**
     * 基数统计
     * @param field
     * @return
     */
    public double cardinality(String field){
        AggregationBuilder agg=AggregationBuilders.cardinality("cardinality").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).get();
        Cardinality c=response.getAggregations().get("cardinality");
        return c.getValue();
    }

    /**
     * 百分位统计
     * @param field
     * @return
     */
    public Percentiles percentiles(String field){
        AggregationBuilder agg=AggregationBuilders.percentiles("percentiles").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("percentiles");
    }

    /**
     * 文档数量统计
     * @param field
     * @return
     */
    public double valueCount(String field){
        AggregationBuilder agg=AggregationBuilders.count("valueCount").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        ValueCount count=response.getAggregations().get("valueCount");
        return count.getValue();
    }

    /**
     * 分组聚合
     * @param field
     * @return
     */
    public Terms terms(String field){
        AggregationBuilder agg = AggregationBuilders.terms("terms").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("terms");
    }

    /**
     * 过滤器聚合
     * @param field
     * @return
     */
    public Filter filter(String field, String key){
        QueryBuilder query= QueryBuilders.termQuery(field,key);
        AggregationBuilder agg=AggregationBuilders.filter("filter",query);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("filter");
    }

    /**
     * 多过滤器聚合
     * @return
     */
    public Filters filters(String field1, String key1, String field2, String key2){
        AggregationBuilder agg=AggregationBuilders.filters("filters",
                new FiltersAggregator.KeyedFilter(key1,QueryBuilders.termQuery(field1,key1)),
                new FiltersAggregator.KeyedFilter(key2,QueryBuilders.termQuery(field2,key2)));
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("filters");
    }

    /**
     * 区间聚合
     * @param field
     * @return
     */
    public Range range(String field, double to, double from){
        AggregationBuilder agg=AggregationBuilders
                .range("range")
                .field(field)
                .addUnboundedTo(to)//第1个范围 ( ,to)
                .addRange(to,from)//第2个范围[to,from)
                .addUnboundedFrom(from);//第3个范围[from,)
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("range");
    }

    /**
     * 日期区间聚合
     * @param field
     * @return
     */
    public Range dateRange(String field,String to,String from){
        AggregationBuilder agg=AggregationBuilders
                .dateRange("dateRange")
                .field(field)
                .format("yyyy-MM-dd")
                .addUnboundedTo(to)
                .addUnboundedFrom(from);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("dateRange");
    }

    /**
     * Missing聚合
     * @param field
     * @return
     */
    public Missing missing(String field){
        AggregationBuilder agg=AggregationBuilders.missing("missing").field(field);
        SearchResponse response=client.prepareSearch(index).addAggregation(agg).execute().actionGet();
        return response.getAggregations().get("missing");
    }
}
