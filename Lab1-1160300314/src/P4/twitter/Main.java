/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the main program.
 * 
 * You may change this class if you wish, but you don't have to.
 */
public class Main {

	/**
	 * URL of a server that produces a list of tweets sampled from Twitter
	 * within the last hour. This server may take up to a minute to respond, if
	 * it has to refresh its cached sample of tweets.
	 */
	public static final URL SAMPLE_SERVER = makeURLAssertWellFormatted("https://raw.githubusercontent.com/rainywang/Spring2018_HITCS_SC_Lab1/master/P4/src/tweetPoll.py");

	private static URL makeURLAssertWellFormatted(String urlString) {
		try {
			return new URL(urlString);
		} catch (MalformedURLException murle) {
			throw new AssertionError(murle);
		}
	}

	/**
	 * Main method of the program. Fetches a sample of tweets and prints some
	 * facts about it.
	 * 
	 * @param args
	 *            command-line arguments (not used)
	 */
	public static void main(String[] args) {
		try {
			assert false;
			throw new Error("Always run main and tests with assertions enabled");
		} catch (AssertionError ae) {
		}

		final List<Tweet> tweets;
		try {
			tweets = TweetReader.readTweetsFromWeb(SAMPLE_SERVER);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		// display some characteristics about the tweets
		System.err.println("fetched " + tweets.size() + " tweets");

		final Timespan span = Extract.getTimespan(tweets);
		System.err.println("ranging from " + span.getStart() + " to " + span.getEnd());

		final Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
		System.err.println("covers " + mentionedUsers.size() + " Twitter users");

		// infer the follows graph
		final Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
		System.err.println("follows graph has " + followsGraph.size() + " nodes");

		// To test how many people has @-mentioned someone
//		Map<String, Integer> mmMap = new HashMap<>();
//		for (int i = 0; i < tweets.size(); i++) {
//			if (tweets.get(i).getText().toLowerCase().contains("@judicialwatch")) {
//				if (mmMap.containsKey(tweets.get(i).getAuthor().toLowerCase()))
//					mmMap.put(tweets.get(i).getAuthor().toLowerCase(),
//							mmMap.get(tweets.get(i).getAuthor().toLowerCase()) + 1);
//				else {
//					mmMap.put(tweets.get(i).getAuthor().toLowerCase(), 1);
//				}
//			}
//		}
//		for (Map.Entry<String, Integer> entry : mmMap.entrySet()) {
//				System.out.println(entry.getKey() + "\t" + entry.getValue());
//		}
//		System.err.println(mmMap.size() + " persons have @judicialwatch");
//		System.out.println();
		//@mitsmr 直接查找会有85个匹配，但是包括@mitsmr的推特共有81条（有些推特多次@mitsmr）
		//其中81条推特只有58个人真正发的，有人多次发推特并且多次@mitsmr
		
		// print the top-N influencers
		final int count = 10;
		final List<String> influencers = SocialNetwork.influencers(followsGraph);
		for (String username : influencers.subList(0, Math.min(count, influencers.size()))) {
			System.out.println(username);
//			List<Tweet> con = Filter.containing(tweets, Arrays.asList("@" + username));
//			System.out.println(" " + con.size());
		}

	}

}
