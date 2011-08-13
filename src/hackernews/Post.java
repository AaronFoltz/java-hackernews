
package hackernews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Post {

	int	rank, score, comments;

	String	title, url, user, userURL, commentsURL;

	public int getComments() {

		return comments;
	}

	public String getCommentsURL() {

		return commentsURL;
	}

	public int getRank() {

		return rank;
	}

	public int getScore() {

		return score;
	}

	public String getTitle() {

		return title;
	}

	public String getUrl() {

		return url;
	}

	public String getUser() {

		return user;
	}

	public String getUserURL() {

		return userURL;
	}

	public void setComments(String commentString) {

		// Pattern to grab the number
		Pattern p = Pattern.compile("(\\d+).+");
		Matcher m = p.matcher(commentString);

		if (m.find()) {
			// Parse the score from the string
			this.comments = Integer.parseInt(m.group(1));
		}
	}

	public void setCommentsURL(String commentsURL) {

		commentsURL = "http://news.ycombinator.com/".concat(commentsURL);
		this.commentsURL = commentsURL;
	}

	public void setRank(String rankString) {

		// Replace the period in the rank
		rankString = rankString.replace(".", "");

		// Parse the rank from the string
		rank = Integer.parseInt(rankString);

	}

	public void setScore(String scoreString) {

		// Pattern to grab the number
		Pattern p = Pattern.compile("(\\d+).+");
		Matcher m = p.matcher(scoreString);

		if (m.find()) {
			// Parse the score from the string
			this.score = Integer.parseInt(m.group(1));
		}

	}

	public void setTitle(String title) {

		this.title = title;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public void setUser(String user) {

		this.user = user;
	}

	public void setUserURL(String userURL) {

		// Append the prefix onto the URL
		userURL = "http://news.ycombinator.com/".concat(userURL);
		this.userURL = userURL;
	}

	@Override
	public String toString() {

		return getRank() + "\t" + getTitle() + "\t" + getUrl() + "\t"
				+ getScore() + "\t" + getUser() + "\t" + getUserURL() + "\t"
				+ getComments() + "\t" + getCommentsURL();
	}
}
