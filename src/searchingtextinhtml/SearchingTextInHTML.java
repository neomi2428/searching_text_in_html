package searchingtextinhtml;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		saveValidPage(a);
	}

	/**
	 * validate link address
	 * @param link link address
	 * @return false: "http://web.address", "https://web.address", "mailto:mail@address", "/", "#blah-blah"
	 * 			true: "/page/address"
	 */
	public boolean validatePage(String link) {
		if(link.equals("/")) { return false; }
		if(!link.startsWith("/")) { return false; }

		return true;
	}
	
	/**
	 * save valid page into queue
	 * @param a a tag elements
	 */
	public void saveValidPage(Elements a) {
		ListIterator<Element> iterator = a.listIterator();
		while(iterator.hasNext()) {
			Element el = iterator.next();
			String href = el.attr("href");

			if(validatePage(href)) {
				System.out.println("[true] " + href);
			} else {
				System.out.println("[false] " + href);
			}
		}
	}

	/**
	 * extract a tags in html page(s)
	 * @param targetPage target html page
	 * @return a tag elements
	 * @throws IOException 
	 */
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
