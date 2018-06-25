package demo.es;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
/**
 * 
* @ClassName: ESUtilsDemo  
* @Description: ESUtils测试  
* @author gaox  
* @date 2018年6月25日 下午4:41:00
 */
public class ESUtilsDemo {

	public static void main(String[] args) {
		//1.判定索引是否存在
        boolean flag=ESUtils.isExists("index3");
        System.out.println("isExists:"+flag);
        //2.创建索引
        flag=ESUtils.createIndex("index3", 3, 0);
        System.out.println("createIndex:"+flag);
        //3.设置Mapping
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("id")
                    .field("type", "long")
                    .endObject()
                    /* es服务暂时没有安装分词插件，analyzer会报错
                    .startObject("title")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("search_analyzer", "ik_max_word")
                    .field("boost", 2)
                    .endObject()
                    .startObject("content")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("search_analyzer", "ik_max_word")
                    .endObject()*/
                    .startObject("postdate")
                    .field("type", "date")
                    .field("format", "yyyy-MM-dd HH:mm:ss")
                    .endObject()
                    .startObject("url")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject();
            System.out.println(builder.string());
            ESUtils.setMapping("index3", "blog", builder.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
