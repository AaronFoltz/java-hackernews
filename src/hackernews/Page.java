
package hackernews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page {

	private static String	postRank;

	// Empty string to start
	private static String	pageParam	= "";

	public static Post[] get(int page) {

		// The page number will start at 1, but page 1 is just the base URL
		// without the pageParam
		for (int i = 1; i < page; i++) {

			// Document which will be created from the URL request
			Document doc = null;

			try {
				doc = Jsoup.connect("http://news.ycombinator.com" + pageParam)
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Element pageElement = doc.select("a[href^=/x?fnid]").last();

			pageParam = pageElement.attr("href");
		}

		System.out.println("http://news.ycombinator.com" + pageParam);
		return fetchPage("http://news.ycombinator.com" + pageParam);
	}

	// Get the desired page number
	private static Post[] fetchPage(String URL) {

		// Create an array of the posts
		ArrayList<Post> postArrayList = new ArrayList<Post>();

		// Document which will be created from the URL request
		Document doc = null;

		try {

			doc = Jsoup.connect(URL).get();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// All td elements with class="title". this correlates to the rank and
		// main link information
		Elements posts = doc.select("td.title");

		// All td elements with class="subtext". This correlates to the
		// information under the main link
		Elements subPosts = doc.select("td.subtext");

		ListIterator<Element> postIterator = posts.listIterator();
		ListIterator<Element> subPostIterator = subPosts.listIterator();

		// If there is another table data with class title, get it. Be aware,
		// the "MORE" button
		// at the bottom of the page is class title
		while (postIterator.hasNext()) {

			// Create a new post
			Post post = new Post();

			// If the Post only has the text "More", then it is the link to the
			// next page
			postRank = postIterator.next().text();
			if (!postRank.equals("More")) {

				// The first td has the rank only
				post.setRank(postRank);
			} else {
				break;
			}

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

			post.setTimeAgo(subPostInformation.ownText());

			// Grab the score of the post
			Element score = subPostInformation.select("span").first();

			// If there is a score, then add it to the post
			if (score != null) {
				// The score is held in the text of the span item
				post.setScore(score.text());
			}

			// Grab the user's information
			Element user = subPostInformation.select("a[href^=user]").first();

			// If there is a user, then add them to the post with their profile
			// url
			if (user != null) {
				post.setUser(user.text());
				post.setUserURL(user.attr("href"));
			}

			// Grab the comment information
			Element comment = subPostInformation.select("a[href^=item]")
					.first();

			// If there are some comments, then add the number and url to the
			// post
			if (comment != null) {
				post.setComments(comment.text());
				post.setCommentsURL(comment.attr("href"));
			}

			postArrayList.add(post);
		}

		// Create a simple post array
		Post[] postArray = new Post[postArrayList.size()];

		// Convert to a simple array and return
		return postArrayList.toArray(postArray);
	}
}
