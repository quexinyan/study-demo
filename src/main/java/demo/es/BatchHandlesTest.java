package demo.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @Time           2018/6/28 15:38
 * @Author          gaox
 * @Description     文档批量操作
*/
public class BatchHandlesTest {

    /**
    * 批量查询
    * @author      gaox
    * @date        2018/6/28 15:46
    */
    @Test
    public void batchSearch(){

        TransportClient client= ESUtils.getClient();
        MultiGetResponse mgResponse = client.prepareMultiGet()
                .add("index1","blog","1","2")
                .add("megacorp","employee","1","123","h__uJmQB7-T5bJhq5kg-")
                .get();
        for(MultiGetItemResponse response:mgResponse){
            GetResponse rp=response.getResponse();
            if(rp!=null && rp.isExists()){
                System.out.println(rp.getSourceAsString());
            }
        }
    }

    /**
    * 批量插入
    * @author      gaox
    * @date        2018/6/28 15:56
    */
    @Test
    public void batchPut() throws IOException {

        TransportClient client= ESUtils.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        bulkRequest.add(client.prepareIndex("aggregations", "aggregation", "3")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("age", 19)
                        .field("salary", 4500.00)
                        .field("message", "我的宝马多少马力")
                        .endObject()
                )
        );
        bulkRequest.add(client.prepareIndex("aggregations", "aggregation")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("age", 26)
                        .field("salary", 11000.60)
                        .field("message", "我的保时捷马力不错")
                        .endObject()
                )
        );
        //批量执行
        BulkResponse bulkResponse = bulkRequest.get();
        System.out.println(bulkResponse.status());
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("存在失败操作");
        }
    }

    /**
    * 批量处理器
     * BulkProcessor类提供了一个简单接口，可以根据请求的数量或大小自动刷新批量操作，也可以在给定的时间段之后自动刷新批量操作。
    * @author      gaox
    * @date        2018/6/28 16:07
    */
    @Test
    public void batchProcessor(){
        TransportClient client= ESUtils.getClient();
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId,BulkRequest request) {
                        //设置bulk批处理的预备工作
                        System.out.println("请求数:"+request.numberOfActions());
                    }
                    @Override
                    public void afterBulk(long executionId,BulkRequest request,BulkResponse response) {
                        //设置bulk批处理的善后工作
                        if(!response.hasFailures()) {
                            System.out.println("执行成功！");
                        }else {
                            System.out.println("执行失败！");
                        }
                    }
                    @Override
                    public void afterBulk(long executionId,BulkRequest request,Throwable failure) {
                        //设置bulk批处理的异常处理工作
                        System.out.println(failure);
                    }
                })
                .setBulkActions(1000)//设置提交批处理操作的请求阀值数
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))//设置提交批处理操作的请求大小阀值
                .setFlushInterval(TimeValue.timeValueSeconds(5))//设置刷新索引时间间隔
                .setConcurrentRequests(1)//设置并发处理线程个数
                //设置回滚策略，等待时间100ms,retry次数为3次
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        // Add your requests
        bulkProcessor.add(new DeleteRequest("twitter", "tweet", "1"));
        bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));
        // 刷新所有请求
        bulkProcessor.flush();
        // 关闭bulkProcessor
        bulkProcessor.close();
        // 刷新索引
        client.admin().indices().prepareRefresh().get();
        // Now you can start searching!
        client.prepareSearch().get();
    }

    /**
    * 查询并删除
    * @author      gaox
    * @date        2018/6/28 16:17
    */
    @Test
    public void batchDeleteQuery(){

        TransportClient client= ESUtils.getClient();
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("intersts", "sports"))
                .source("megacorp")//设置索引名称
                .get();
        //被删除文档数目
        long deleted = response.getDeleted();
        System.out.println(deleted);
    }
}
