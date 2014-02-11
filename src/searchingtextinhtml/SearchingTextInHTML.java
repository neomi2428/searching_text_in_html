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
		Writer.writeToFile("./whole_pages.txt", PAGE_LIST);

		// test for finding certain characters
		for(int i = 0; i < PAGE_LIST.size(); ++i) {
			String targetURL = BASE_URL + PAGE_LIST.get(i);
			Document doc = Jsoup.connect(targetURL).get();
			Elements body = doc.select("body");
			String text = body.text();
			
			// Think of capital letter or small case
			if (text.contains("Junhee Park")) {
				System.out.println(targetURL);
			}
		}
	}

	public void collectPage() throws IOException {
		while(INDEX < PAGE_LIST.size()) {
			CURRENT_PAGE = (String) PAGE_LIST.get(INDEX++);
			if(null == CURRENT_PAGE) { return; }
			String targetPage = BASE_URL + CURRENT_PAGE;

			Elements a = extractATag(targetPage);
			saveValidPage(a);
		}

		System.out.println("Page list size: " + PAGE_LIST.size());
		System.out.println("Index: " + INDEX);
	}

	/**
	 * validate link address
	 * @param link link address
	 * @return false: "http://web.address", "https://web.address", "mailto:mail@address", "/", "#blah-blah", 
	 * 				   infinite loop, image file, document file
	 * 			true: "/page/address"
	 */
	public boolean validatePage(String link) {
		if(link.equals("/")) { return false; }
		if(!link.startsWith("/")) { return false; }
		if(PAGE_LIST.contains(link) && PAGE_LIST.contains(CURRENT_PAGE)) { 
			//System.out.println("avoiding infinite loop"); 
			return false; 
		}
		if(link.contains(".pdf") || link.contains(".doc") || link.contains(".pub")) { return false; }
		if(link.contains(".JPG#overlay-context") || link.endsWith(".JPG")) { return false; }

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
				//System.out.println("[true] " + href);
				PAGE_LIST.add(href);
			} else {
				//System.out.println("[false] " + href);
			}
		}
	}

	/**
	 * extract a tags in html page(s)
	 * @param targetPage target html page
	 * @return a tag elements
	 * @throws IOException 
	 */
	public Elements extractATag(String targetPage) {
		try {
			Document doc = Jsoup.connect(targetPage).get();
			return doc.getElementsByTag("a");
		} catch (IOException ex) {
			System.out.println("Broken link: " + targetPage);
			System.out.println("Page list size: " + PAGE_LIST.size());
			System.out.println("Index: " + INDEX);
			System.exit(0);
			return null;
		}
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
