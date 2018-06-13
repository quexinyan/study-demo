package demo.lucene.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 
* @ClassName: DeleteIndex  
* @Description: 索引库删除数据  
* @author gaox  
* @date 2018年6月13日 下午5:41:50
 */
public class DeleteIndex {

	public static void main(String[] args) throws IOException {
		
		// 1.指定索引库目录
		File dir = new File("lucence");
		Directory directory = FSDirectory.open(dir);
		
		// 2.指定规则
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, conf);
		
		// 3.删除
		//indexWriter.deleteAll(); // 删除全部
		indexWriter.deleteDocuments(new Term("author", "张三0"));
		
		indexWriter.close();
	}
}
