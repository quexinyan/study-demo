package demo.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 
* @ClassName: ClientDemo  
* @Description: Java API连接Elasticsearch 
* @author gaox  
* @date 2018年6月25日 下午2:41:50
 */
public class ClientDemo {

	public static void main(String[] args) throws UnknownHostException {
		
		// 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.2.2"), 9300));
        // 搜索数据
        GetResponse response = client.prepareGet("megacorp", "employee", "1").execute().actionGet();
        // 输出结果
        System.out.println(response.getSourceAsString());
        // 关闭client
        client.close();
	}
}
