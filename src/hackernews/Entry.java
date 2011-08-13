
package hackernews;

import java.io.IOException;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Entry {

	public static void main(String[] args) {

		Entry entry = new Entry();

	}

	public Entry() {

		// Document which will be created from the URL request
		Document doc = null;

		try {
			doc = Jsoup.connect("http://news.ycombinator.com").get();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements posts = doc.select("td.title");
		Elements subPosts = doc.select("td.subtext");

		ListIterator<Element> postIterator = posts.listIterator();
		ListIterator<Element> subPostIterator = subPosts.listIterator();

		// If there is another table data with class title, get it. Be aware,
		// the "MORE" button
		// at the bottom of the page is class title
		while (postIterator.hasNext()) {

			// Create a new post
			Post post = new Post();

			// The first td has the title only
			post.setRank(postIterator.next().text());

			// Grab the next td, this has the title and URL
			Element titleInformation = postIterator.next();

			// Grab the first anchor in this td
			Element title = titleInformation.select("a").first();

			// The title is just the text of this item
			post.setTitle(title.text());

			// The url is the attribute of this item
			post.setUrl(title.attr("href"));

			// Grab the subtext row
			Element subPostInformation = subPostIterator.next();

			// Grab the score of the post
			Element score = subPostInformation.select("span").first();

			// The score is held in the text of the span item
			post.setScore(score.text());

			// Grab the user's information
			Element user = subPostInformation.select("a[href^=user]").first();

			post.setUser(user.text());
			post.setUserURL(user.attr("href"));

			// Grab the comment information
			Element comment = subPostInformation.select("a[href^=item]")
					.first();

			post.setComments(comment.text());
			post.setCommentsURL(comment.attr("href"));

			System.out.println(post.toString());

		}
	}

}
