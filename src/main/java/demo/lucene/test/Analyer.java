/*
package demo.lucene.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

*/
/**
 * 
* @ClassName: Analyer  
* @Description: 分析器 
* @author gaox  
* @date 2018年6月14日 上午10:13:34
 *//*

public class Analyer {

	public static void main(String[] args) throws IOException {
		
		Analyzer analyzer = new IKAnalyzer(false);//new SmartChineseAnalyzer(Version.LUCENE_35);//new CJKAnalyzer(Version.LUCENE_35);//new StandardAnalyzer(Version.LUCENE_35);
		String str = "高祥是高配吴彦祖吗？是的";
		TokenStream tokenStream = analyzer.tokenStream("text", new StringReader(str));
		CharTermAttribute termAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		while(tokenStream.incrementToken()){
			System.out.print("["+termAttribute+"]");
		}
		analyzer.close();
	}
}
*/
