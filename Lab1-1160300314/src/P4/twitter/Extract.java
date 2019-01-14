/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of
	 *         every tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		Instant start = tweets.get(0).getTimestamp();
		Instant end = tweets.get(0).getTimestamp();
		for(int i = 1;i<tweets.size();i++){
			if(start.isAfter(tweets.get(i).getTimestamp()))
				start = tweets.get(i).getTimestamp();
			if(end.isBefore(tweets.get(i).getTimestamp()))
				end=tweets.get(i).getTimestamp();
		}
//		Collections.sort(tweets, new Comparator<Tweet>() {
//
//			@Override
//			public int compare(Tweet o1, Tweet o2) {
//				// TODO Auto-generated method stub
//				return o1.getTimestamp().compareTo(o2.getTimestamp());
//			}
//
//		});
		// long minSpan = 0x3f3f3f3f;
		Timespan ans = new Timespan(start, end);
//		Timespan ans = new Timespan(tweets.get(0).getTimestamp(), tweets.get(tweets.size() - 1).getTimestamp());
		// for(int i = 1;i<tweets.size();i++){
		// if(tweets.get(i).getTimestamp().getEpochSecond() -
		// tweets.get(i-1).getTimestamp().getEpochSecond() < minSpan){
		// minSpan = tweets.get(i).getTimestamp().getEpochSecond() -
		// tweets.get(i-1).getTimestamp().getEpochSecond();
		// ans = new Timespan(tweets.get(i-1).getTimestamp(),
		// tweets.get(i).getTimestamp());
		// }
		// }
		return ans;
	}

	public static boolean isConSpeCharacters(String string) {
		if (string.replaceAll("\\d*[a-z]*[A-Z]*_*-*:*", "").length() == 0) {
			// 如果不包含除了\t \d \n 之外的字符
			return true;
		}
		return false;
	}

	public static Set<String> divideMentionedUsers(Tweet tweet) {
		Set<String> anSet = new HashSet<>();
//		String reg = "@*\\d*[a-z]*[A-Z]*-*_*";
		
//		String[] words = tweet.getText().split(" ");
//		for (int i = 0; i < words.length; i++) {
//			if (words[i].contains("@")) {
//				String subString = words[i].substring(words[i].indexOf("@") + 1, words[i].length()).toLowerCase();
//				// anSet.add(words[i].substring(words[i].indexOf("@")+1,
//				// words[i].length()).toUpperCase());
////				String reg = "[\\-\\w]{1,}";
//				Pattern pattern = Pattern.compile(reg);
//				Matcher matcher = pattern.matcher(subString);
//				if(matcher.find())
//					anSet.add(matcher.group().toLowerCase());
//				if (isConSpeCharacters(subString)) {
//					if (!subString.contains(":"))
//						anSet.add(subString);
//					else if(subString.endsWith(":"))
//						anSet.add(subString.substring(0, subString.length()-1));
//				}
//			}
//		}
//		String reg = "@.+?[^\\-\\w]";
		String reg = "@[a-zA-Z0-9_-]{1,}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(tweet.getText());
		while(matcher.find()){
			anSet.add(matcher.group().substring(1, matcher.group().length()).toLowerCase());
		}
		return anSet;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets.
	 *         A username-mention is "@" followed by a Twitter username (as
	 *         defined by Tweet.getAuthor()'s spec). The username-mention cannot
	 *         be immediately preceded or followed by any character valid in a
	 *         Twitter username. For this reason, an email address like
	 *         bitdiddle@mit.edu does NOT contain a mention of the username mit.
	 *         Twitter usernames are case-insensitive, and the returned set may
	 *         include a username at most once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		// throw new RuntimeException("not implemented");
		// Map<String, String> User = new HashMap<>();
		Set<String> anSet = new HashSet<>();
		for (int i = 0; i < tweets.size(); i++) {
			anSet.addAll(divideMentionedUsers(tweets.get(i)));
			// User.put(tweets.get(i).getAuthor().toUpperCase(),
			// tweets.get(i).getAuthor());
		}
		// for(int i = 0;i<tweets.size();i++){
		// Set<String> tempSet = divideMentionedUsers(tweets.get(i));
		// for(String string : tempSet){
		// if(User.containsKey(string))
		// //如果一个人的用户名是大小写交替的那么留下的只有小写的
		// anSet.add(User.get(string));
		// }
		// }
		return anSet;
	}

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T19:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alySsa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(3, "Kobe", "You the dog@Alyssa @sdaf @asfd @mit.edu", d3);

	public static void main(String[] args) {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet3, tweet2));
		System.out.println(mentionedUsers.isEmpty());
		System.out.println(mentionedUsers);
		
		Timespan ans = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
		System.out.println(ans.toString());
	}
}
