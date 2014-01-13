package searchingtextinhtml;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
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
	public static String BASE_URL = "http://career.stonybrook.edu";
	public static Queue PAGE_QUEUE = new LinkedList();
	public static String CURRENT_PAGE = "";

	public static void main(String[] args) throws IOException {
		SearchingTextInHTML app = new SearchingTextInHTML();
		PAGE_QUEUE.add(BASE_URL);
		app.collectPage();
	}

	public void collectPage() throws IOException {
		String targetPage = (String) PAGE_QUEUE.poll();
		if(null == targetPage) { return; }

		Elements a = extractATag(targetPage);
		System.out.println(a);
		saveValidPage(a);
	}
	
	public void saveValidPage(Elements a) {

	}

	public Elements extractATag(String targetPage) throws IOException {
		Document doc = Jsoup.connect(targetPage).get();
		return doc.getElementsByTag("a");
	}

	/**
	 * This is for testing Jsoup.
	 * @throws IOException 
	 */
	public static void testJsoup() throws IOException {
		Document doc = Jsoup.connect(BASE_URL).get();
		Elements body = doc.select("body");
		System.out.println(body);
	}
}
