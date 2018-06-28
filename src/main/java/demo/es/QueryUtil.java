package demo.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Map;

/**
 * @Time           2018/6/28 17:10
 * @Author          gaox
 * @Description     公共查询工具类
*/
public class QueryUtil {

    /**
     * 索引名
     */
    private String index = "index";
    /**
     * 一次查询文档数
     */
    private int size = 3;
    private SearchHits hits;
    private TransportClient client = ESUtils.getClient();

    public QueryUtil(String index, int size) {
        this.index = index;
        this.size = size;
    }

    /**
     *  构建指定查询器的查询工具
     * @param query 查询器
     * @return QueryUtil
     */
    public QueryUtil query(QueryBuilder query){
        //搜索结果存入SearchResponse
        SearchResponse response=client.prepareSearch(index)
                .setQuery(query) //设置查询器
                .setSize(size)      //一次查询文档数
                .get();
        this.hits=response.getHits();
        return this;
    }

    /**
     * 输出
     */
    public void print(){
        if(hits==null){
            return ;
        }
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
        }
    }
}
