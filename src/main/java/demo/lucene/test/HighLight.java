/*
package demo.lucene.test;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.wltea.analyzer.lucene.IKAnalyzer;

*/
/**
 * 
* @ClassName: HighLight  
* @Description: 高亮处理
* @author gaox  
* @date 2018年6月16日 下午5:11:46
 *//*

public class HighLight {

	public static void main(String[] args) throws IOException, InvalidTokenOffsetsException {
		String msg="欢迎百知大家来中关村软件园学习java,百知训练营欢迎你~";
		Query query=new TermQuery(new Term("text","百知"));
		QueryScorer fragmentScorer=new QueryScorer(query);
		
		Formatter formatter=new SimpleHTMLFormatter("<span class='highter'>", "</span>");
		Highlighter highlighter=new Highlighter(formatter, fragmentScorer);
		
		Fragmenter fragmenter=new SimpleSpanFragmenter(fragmentScorer, 18);
		//设置摘要
		highlighter.setTextFragmenter(fragmenter);
		//摘要 
		String bestFragment = highlighter.getBestFragment(new IKAnalyzer(), "text", msg);
		
		
		System.out.println(bestFragment);
	}
	public void hightlighter() throws IOException, InvalidTokenOffsetsException{
		String msg="欢迎大家来中关村软件园学习java,百知训练营欢迎你~";
		Query query=new TermQuery(new Term("text","百知"));
		Scorer fragmentScorer=new QueryScorer(query);
		Formatter formatter=new SimpleHTMLFormatter("<span class='highter'>", "</span>");
		Highlighter highlighter=new Highlighter(formatter, fragmentScorer);
		String bestFragment = highlighter.getBestFragment(new IKAnalyzer(), "text", msg);
		System.out.println(bestFragment);
	}
	public void higlighter01() throws IOException, InvalidTokenOffsetsException{
		String msg="欢迎大家来中关村软件园学习java,百知训练营欢迎你~";
		Query query=new TermQuery(new Term("text","百知"));
		Scorer fragmentScorer=new QueryScorer(query);
		Highlighter highlighter=new Highlighter(fragmentScorer);
		String bestFragment = highlighter.getBestFragment(new IKAnalyzer(), "text", msg);
		System.out.println(bestFragment);
	}
}
*/
