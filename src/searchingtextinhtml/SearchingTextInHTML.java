package searchingtextinhtml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
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
	public static ArrayList PAGE_LIST = new ArrayList<String>();
	public static String CURRENT_PAGE = "";
	public static int INDEX = 0;

	public static void main(String[] args) throws IOException {
		SearchingTextInHTML app = new SearchingTextInHTML();
		PAGE_LIST.add("/");
		app.collectPage();
	}

	public void collectPage() throws IOException {
		while(INDEX < PAGE_LIST.size()) {
			CURRENT_PAGE = (String) PAGE_LIST.get(INDEX++);
			if(null == CURRENT_PAGE) { return; }
			CURRENT_PAGE = BASE_URL + CURRENT_PAGE;

			Elements a = extractATag(CURRENT_PAGE);
			saveValidPage(a);
		}

		System.out.println(PAGE_LIST.size());
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
		if(PAGE_LIST.contains(link) && PAGE_LIST.contains(CURRENT_PAGE)) { return false; }

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
				PAGE_LIST.add(href);
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
