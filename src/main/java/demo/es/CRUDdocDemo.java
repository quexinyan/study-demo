package demo.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

/**
 * 
* @ClassName: CRUDdocDemo  
* @Description: 文档CRUD操作  
* @author gaox  
* @date 2018年6月25日 下午6:13:15
 */
public class CRUDdocDemo {

	// 文档添加
	@Test
	public void putDoc() throws IOException {
		TransportClient client=ESUtils.getClient();
        String json = "{" +
                "\"id\":\"1\"," +
                "\"title\":\"Java设计模式之装饰模式\"," +
                "\"content\":\"在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。\"," +
                "\"postdate\":\"2018-02-03 14:38:00\"," +
                "\"url\":\"csdn.net/79239072\"" +
                "}";
        System.out.println(json);
        IndexResponse response =client.prepareIndex("index1", "blog","10")
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println(response.status());

        XContentBuilder doc1 = jsonBuilder()
                    .startObject()
                    .field("id","2")
                    .field("title","Java设计模式之单例模式")
                    .field("content","枚举单例模式可以防反射攻击。")
                    .field("postdate","2018-02-03")
                    .field("url","csdn.net/79247746")
                    .endObject();
        System.out.println(doc1.toString());
        response = client.prepareIndex("index1", "blog", "11")
                .setSource(doc1)
                .get();
        System.out.println(response.status());
	}
	
	// 文档获取
	@Test
	public void getDoc() {
		TransportClient client=ESUtils.getClient();
        GetResponse response =client.prepareGet("index1","blog","1").get();
        System.out.println(response.isExists());
        System.out.println(response.getIndex());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getVersion());
        String source=response.getSourceAsString();
        System.out.println(source);
	}
	
	// 文档删除
	@Test
	public void deleteDoc() {
		TransportClient client=ESUtils.getClient();
        DeleteResponse response=client.prepareDelete("index1","blog","1").get();
        //删除成功返回OK，否则返回NOT_FOUND
        System.out.println(response.status());
        //返回被删除文档的类型
        System.out.println(response.getType());
        //返回被删除文档的ID
        System.out.println(response.getId());
        //返回被删除文档的版本信息
        System.out.println(response.getVersion());
	}
	
	// 文档更新
	@Test
	public void updateDoc() throws InterruptedException, ExecutionException, IOException {
		TransportClient client=ESUtils.getClient();
        UpdateRequest request=new UpdateRequest();
        request.index("index1")
                .type("blog")
                .id("2")
                .doc(
                        jsonBuilder().startObject()
                        .field("title","单例模式解读")
                        .endObject()
                );
        UpdateResponse response=client.update(request).get();
        //更新成功返回OK，否则返回NOT_FOUND
        System.out.println(response.status());
        //返回被更新文档的类型
        System.out.println(response.getType());
        //返回被更新文档的ID
        System.out.println(response.getId());
        //返回被更新文档的版本信息
        System.out.println(response.getVersion());
	}
	
	// 文档upsert操作:如果文档存在则执行更新操作，否则执行添加操作
	@Test
	public void upsert() throws IOException, InterruptedException, ExecutionException {
		TransportClient client=ESUtils.getClient();
        IndexRequest request1 =new IndexRequest("index1","blog","3")
                .source(
                        jsonBuilder().startObject()
                                .field("id","1")
                                .field("title","装饰模式")
                                .field("content","动态地扩展一个对象的功能")
                                .field("postdate","2018-02-03 14:38:10")
                                .field("url","csdn.net/79239072")
                                .endObject()
                );
        UpdateRequest request2=new UpdateRequest("index1","blog","4")
                .doc(
                        jsonBuilder().startObject()
                        .field("title","装饰模式解读")
                        .endObject()
                ).upsert(request1);
        UpdateResponse response=client.update(request2).get();
        //upsert操作成功返回OK，否则返回NOT_FOUND
        System.out.println(response.status());
        //返回被操作文档的类型
        System.out.println(response.getType());
        //返回被操作文档的ID
        System.out.println(response.getId());
        //返回被操作文档的版本信息
        System.out.println(response.getVersion());
	}
}
