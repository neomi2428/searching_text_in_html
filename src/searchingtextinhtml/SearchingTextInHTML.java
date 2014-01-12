package searchingtextinhtml;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * The public access point to search text in html.
 * This uses Jsoup library.
 * 
 * @author Junhee Park
 */
public class SearchingTextInHTML {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Hello, World!");
		testJsoup();
	}

	/**
	 * This is for testing Jsoup.
	 * 
	 * @throws IOException 
	 */
	public static void testJsoup() throws IOException {
		Document doc = Jsoup.connect("http://career.stonybrook.edu").get();
		Elements body = doc.select("body");
		
		System.out.println(body);
	}
}
