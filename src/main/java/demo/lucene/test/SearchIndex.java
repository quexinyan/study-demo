/*
package demo.lucene.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

*/
/**
 * 
* @ClassName: SearchIndex  
* @Description: 索引库查询  
* @author gaox  
* @date 2018年6月13日 下午5:18:34
 *//*

public class SearchIndex {

	public static void main(String[] args) throws IOException {
		
		// 1.指定索引库目录
		File dir = new File("lucence");
		Directory directory = FSDirectory.open(dir);
		
		// 2.创建IndexReader
		IndexReader indexReader = IndexReader.open(directory);
		
		// 3.创建IndexSearcher
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		// 4.构造查询项-词项
		Term term = new Term("author", "李四");
		
		// 5.构造查询条件
		Query query = new TermQuery(term);
		
		// 6.获取返回结果，总共返回多少条，文档ID
		TopDocs search = indexSearcher.search(query, Integer.MAX_VALUE); // 第二个参数表述返回多少条符合条件的数据（MAX_VALUE表示所有）
		int totalHits = search.totalHits;
		System.out.println("查询总记录数："+totalHits);
		ScoreDoc[] scoreDocs = search.scoreDocs; // 返回文档id以及文档的得分（相似度）
		for(ScoreDoc scoreDoc : scoreDocs) {
			float score = scoreDoc.score; // 文档相似度
			int docID = scoreDoc.doc; // 文档ID
			System.out.println("文档相似度："+score+"  文档ID"+docID);
			Document document = indexSearcher.doc(docID);
			// 遍历document中的Field
			List<Fieldable> fields = document.getFields();
			for(Fieldable fieldable : fields) {
				System.out.println(fieldable.name()+"  ->  "+fieldable.stringValue());
			}
			//System.out.println(document.get("author"));
		}
		
		indexSearcher.close();
	}
}
*/
