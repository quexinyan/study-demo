package demo.lucene.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

/**
 * 
* @ClassName: SearchAPIHighLever  
* @Description: luceneAPI查询
* 	注意：尽可能的保证IndexWriter是单例的(用类级内部类实现代理)
* @author gaox  
* @date 2018年6月15日 上午10:50:42
 */
public class SearchAPIHighLever {

	private static final int[] ids = {1, 2, 3, 4, 5};
	private static final String[] names={"Struts2 in Action","Hibernate in Action",
			"Spring in Action","Lucene in Action","Hadoop in Action"};
	private static final String[] contents={
			"the book is designed in mvc model,good book",
			"the book act as a persistent framework,it is very good",
			"it acts as a container,we should learn it well .",
			"it is just a jar,but it is very powerful,it can support fulltext search",
			"it is good at bigdata store and analyzer"};
	private static final double[] prices={10,15,20,8,16};
	
	/**
	 * 
	 * @throws IOException 
	 * @Title: multipleSearch   
	 * @Description: 多条件查询   
	 */
	@Test
	public void BooleanQuerySearch() throws IOException {
		
		Query query1 = NumericRangeQuery.newDoubleRange("price", 16.0, 20.0, true, true);
		Query query2 = new WildcardQuery(new Term("content", "containe?")); // ？标识一位，*标识多位
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, Occur.MUST);
		booleanQuery.add(query2, Occur.SHOULD);
		
		search(booleanQuery);
	}
	
	/**
	 * 
	 * @Title: phraseQuerySearch   
	 * @Description: 短语查询   
	 */
	@Test
	public void phraseQuerySearch() throws IOException {
		
		Term term1=new Term("content","acts");
		Term term2=new Term("content","learn");
		PhraseQuery query=new PhraseQuery();
		query.add(term1);
		query.add(term2);
		query.setSlop(5); // 第一个词到第二个词的距离
		
		search(query);
	}
	
	/**
	 * 
	 * @Title: numericRangeQuerySearch   
	 * @Description: 范围查询   
	 */
	@Test
	public void numericRangeQuerySearch() throws IOException {
		
		Query query = NumericRangeQuery.newDoubleRange("price", 10.0, 16.0, true, true);
		search(query);
	}
	
	/**
	 * 
	 * @Title: wildcardQuerySearch   
	 * @Description: 通配符查询  
	 */
	@Test
	public void wildcardQuerySearch() throws IOException {
		
		Query query = new WildcardQuery(new Term("content", "fulltex?")); // ？标识一位，*标识多位
		search(query);
	}
	
	/**
	 * 
	 * @Title: fuzzyQuerySearch   
	 * @Description: 模糊查询   
	 */
	@Test
	public void fuzzyQuerySearch() throws IOException {
		
		Query query = new FuzzyQuery(new Term("content", "baok"), 0.749f); // 模糊度
		search(query);
	}
	
	/**
	 * 
	 * @Title: prefixQuerySearch   
	 * @Description: PrefixQuery 前缀查询   
	 */
	@Test
	public void prefixQuerySearch() throws IOException {
		
		Query query = new PrefixQuery(new Term("name", "Lu"));
		search(query);
	}
	
	/**
	 * 
	 * @throws IOException 
	 * @Title: TermSearch   
	 * @Description: termQuery   
	 */
	@Test
	public void TermQuerySearch() throws IOException {
		
		Query query = new TermQuery(new Term("content", "book"));
		search(query);
	}
	
	/**
	 * 
	 * @Title: search   
	 * @Description: 查询公用方法  
	 * @param: @param query 查询条件
	 */
	public void search(Query query) throws IOException {
		
		// 1.指定索引库目录
		Directory directory = FSDirectory.open(new File("lucence"));
		// 2.创建indexSearcher
		IndexSearcher indexSearcher = new IndexSearcher(IndexReader.open(directory));
		// 3.创建查询条件 --入参
		
		// 4.查询
		TopDocs search = indexSearcher.search(query, Integer.MAX_VALUE);
		int totalHits = search.totalHits; // 返回条数
		ScoreDoc[] scoreDocs = search.scoreDocs; // 返回数据（文档id已经相似度）
		System.out.println("返回数据条数："+totalHits);
		for(ScoreDoc doc : scoreDocs) {
			float score = doc.score; // 文档相似度
			int docId = doc.doc; // 文档id
			System.out.println("文档相似度："+score);
			System.out.println("文档id："+docId);
			Document document = indexSearcher.doc(docId);
			List<Fieldable> fields = document.getFields();
			for(Fieldable field : fields) {
				System.out.println(field.name() + " --> " + field.stringValue());
			}
			System.out.println("==========================");
		}
		indexSearcher.close();
	}
	
	/**
	 * 
	* @ClassName: CreateIndexWriter  
	* @Description: 类级内部类单例创建indexWriter  
	 */
	public static class CreateIndexWriter{
		
		private static IndexWriter indexWriter;
		
		// 静态初始化器，由JVM来保证线程安全
		static {
			System.out.println("类级内部类单例创建indexWriter......");
			Directory directory;
			try {
				directory = FSDirectory.open(new File("lucence"));
				Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
				indexWriter = new IndexWriter(directory, config);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @throws IOException 
	 * @Title: create   
	 * @Description: 创建索引库   
	 */
	@Test
	public void create() throws IOException {
		
		// 单例获取indexWriter
		IndexWriter indexWriter = CreateIndexWriter.indexWriter;
		//IndexWriter indexWriter2 = CreateIndexWriter.indexWriter;
		
		// 创建索引
		for(int i=0; i<ids.length; i++) {
			Document document = new Document();
			
			// 组装field
			NumericField idField = new NumericField("id", Store.YES, true);
			idField.setIntValue(ids[i]);
			Field nameField = new Field("name", names[i], Store.YES, Index.NOT_ANALYZED);
			Field contentField = new Field("content", contents[i], Store.YES, Index.ANALYZED);
			NumericField priceField = new NumericField("price", Store.YES, true);
			priceField.setDoubleValue(prices[i]);
			
			document.add(idField);
			document.add(nameField);
			document.add(contentField);
			document.add(priceField);
			
			indexWriter.addDocument(document);
		}
		
		//indexWriter.commit(); //调用了close()方法会自动调用commit提交事务
		indexWriter.close();
	}
}
