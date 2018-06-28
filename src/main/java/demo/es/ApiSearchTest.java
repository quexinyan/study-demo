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
}
