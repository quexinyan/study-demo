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
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 
* @ClassName: UpdateIndex  
* @Description: 索引库修改  
* @author gaox  
* @date 2018年6月13日 下午5:56:38
 */
public class UpdateIndex {

	public static void main(String[] args) throws IOException {
		
		// 1.指定索引库目录
		File file = new File("lucence");
		Directory directory = FSDirectory.open(file);
		
		// 2.指定规则
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		
		// 3.删除
		Document doc = new Document();
		Field field = new Field("author", "李四", Store.YES, Index.NOT_ANALYZED);
		doc.add(field);
		indexWriter.updateDocument(new Term("author", "张三1"), doc);
		
		indexWriter.close();
	}
}
