/*
package demo.lucene.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

*/
/**
 * 
* @ClassName: CreateIndex  
* @Description: 创建索引库  
* @author gaox  
* @date 2018年6月13日 下午2:02:47
 *//*

public class CreateIndex {

	public static void main(String[] args) throws IOException {
		
		// 1.创建索引库目录
		File dir = new File("lucence");
		Directory directory = FSDirectory.open(dir);
		
		for(int i=0; i<5; i++) {
			// 2.创建一个Document
			Document document = new Document();
			
			// 3.构造Field
			Field authorField = new Field("author", "张三"+i, Store.YES, Index.NOT_ANALYZED);
			Field titleField = new Field("title", "lucene", Store.YES, Index.NOT_ANALYZED);
			Field pathField = new Field("path", "E:/document/springmvc.pdf", Store.YES, Index.ANALYZED);
			Field contentField = new Field("content", "this is a good book ,designed my MVC mode. it is a good framework.", Store.YES, Index.ANALYZED);
			
			document.add(authorField);
			document.add(titleField);
			document.add(pathField);
			document.add(contentField);
			
			// 4.将Document添加到索引库
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);	// 确定分词策略（当期为默认）
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			IndexWriter indexWriter = new IndexWriter(directory, config);
			
			indexWriter.addDocument(document);
			
			indexWriter.close();
		}
		
	}
}
*/
