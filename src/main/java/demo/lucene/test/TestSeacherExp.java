/*
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
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;


public class TestSeacherExp {
	private int[] ids={1,2,3,4,5};
	private String[] names={"Struts2 in Action","Hibernate in Action",
			"Spring in Action","Lucene in Action","Hadoop in Action"};
	private String[] contents={
			"the book is designed in mvc model,good book",
			"the book act as a persistent framework,it is very good",
			"it acts as a container,we should learn it well .",
			"it is just a jar,but it is very powerful,it can support fulltext search",
			"it is good at bigdata store and analyzer"};
	private double[] prices={10,15,20,8,16};
	
	
	
	@Test
	public void testTermQuery() throws IOException, ParseException, InvalidTokenOffsetsException{
		//1.指定索引库目录
		File dir=new File("index");
		Directory directory=FSDirectory.open(dir);
		//2.创建IndexReader
		IndexReader indexReader=IndexReader.open(directory);
		//3.创建IndexSearcher
		IndexSearcher indexSearcher=new IndexSearcher(indexReader);
		
		Analyzer a=new StandardAnalyzer(Version.LUCENE_35);
	    QueryParser parser=new QueryParser(Version.LUCENE_35, "text", a);
	    parser.setAllowLeadingWildcard(true);
	   // parser.setDefaultOperator(Operator.AND);
		String exp="*";
		
		Query query = parser.parse(exp);
		
		//6.获取返回结果 总共返回多少条,文档ID
		//TopDocs tds = indexSearcher.search(query, Integer.MAX_VALUE);
		SortField sortField=new SortField("price", SortField.DOUBLE, false);
		Sort sort=new Sort(sortField);
		TopDocs tds = indexSearcher.search(query,Integer.MAX_VALUE, sort);
		
		int totalHits = tds.totalHits;//总记录数
		System.out.println("查询总记录数:"+totalHits);
		ScoreDoc[] scoreDocs = tds.scoreDocs;//返回文档ID以及 文档的得分(相似度)
		
		QueryScorer fragmentScorer=new QueryScorer(query);
		Formatter formatter=new SimpleHTMLFormatter("<span class='highter'>", "</span>");
		Highlighter highlighter=new Highlighter(formatter, fragmentScorer);
		int pageNow=2;
		int pageSize=2;
		int begin=(pageNow-1)*pageSize;
		for (int i=begin;i<pageNow*pageSize;i++) {
			float score=scoreDocs[i].score;//文档得分
			int docID = scoreDocs[i].doc;//文档ID
			Document document=indexSearcher.doc(docID);
			List<Fieldable> fields = document.getFields();
			System.out.println("docId:"+docID+",Score:"+score);
			for (Fieldable fieldable : fields) {
				//System.out.println(fieldable.name()+"  ->  "+fieldable.stringValue());
				String name=fieldable.name();
				String hightValue=highlighter.getBestFragment(a, name, fieldable.stringValue());
				String value=(hightValue!=null)?hightValue:fieldable.stringValue();
				System.out.println(name+":"+value);
				
			}
			System.out.println();
			System.out.println("===============");
			System.out.println();
		}
		indexSearcher.close();
	}
	@Test
	public void testCreate() throws IOException{
		//1.指定索引库目录
		File dir=new File("index");
		Directory directory=FSDirectory.open(dir);
		Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_35);
		IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter indexWriter=new IndexWriter(directory, config);
	
		for (int i = 0; i < ids.length; i++) {
			Document document=new Document();
			NumericField idField=new NumericField("id", Store.YES, true);
			idField.setIntValue(ids[i]);
			
			Field nameField=new Field("name", names[i], Store.YES, Index.ANALYZED);
			Field contentField=new Field("content", contents[i], Store.YES, Index.ANALYZED);
			NumericField priceField=new NumericField("price", Store.YES, true);
			priceField.setDoubleValue(prices[i]);
			
			Field text=new Field("text", ids[i]+" "+names[i]+" "+contents[i]+" "+prices[i], Store.NO, Index.ANALYZED);
			document.add(text);
			document.add(idField);
			document.add(nameField);
			document.add(contentField);
			document.add(priceField);
			if(i==1){
				//document.setBoost(10);
				contentField.setBoost(10);
			}
			indexWriter.addDocument(document);
		}
		indexWriter.commit();
		indexWriter.close();
	}
}
*/
