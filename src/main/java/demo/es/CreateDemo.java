package demo.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 
* @ClassName: CreateDemo  
* @Description: 创建索引 
* @author gaox  
* @date 2018年6月25日 下午4:11:08
 */
public class CreateDemo {

	public static void main(String[] args) throws UnknownHostException {
		
		//1.设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        //2.创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.2.2"), 9300));
        //3.获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        //4.创建索引
        CreateIndexResponse ciReponse=indicesAdminClient.prepareCreate("index1").get();
        System.out.println(ciReponse.isAcknowledged());
	}
}
