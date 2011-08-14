
package hackernews;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Post[] array = Page.get(1);

		for (Post post : array) {
			System.out.println(post.toString());
		}

	}

}
