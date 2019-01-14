/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.basic.BasicToolBarUI.DockingListener;

import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is the set of people
 * that person A follows on Twitter, and all people are represented by their Twitter usernames.
 * Users can't follow themselves. If A doesn't follow anybody, then map[A] may be the empty set, or
 * A may not even exist as a key in the map; this is true even if A is followed by other people in
 * the network. Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie". A
 * username should appear at most once as a key in the map or in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but you should implement
 * their method bodies, and you may add new public or private methods or classes if you like.
 */
public class SocialNetwork {

  /**
   * Guess who might follow whom, from evidence found in tweets.
   * 
   * @param tweets
   *          a list of tweets providing the evidence, not modified by this method.
   * @return a social network (as defined above) in which Ernie follows Bert if and only if there is
   *         evidence for it in the given list of tweets. One kind of evidence that Ernie follows
   *         Bert is if Ernie
   * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence may be used at
   *            the implementor's discretion. All the Twitter usernames in the returned social
   *            network must be either authors or @-mentions in the list of tweets.
   */
  public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    // throw new RuntimeException("not implemented");
    class Pair<E extends Object, F extends Object> {
      private E first;
      private F second;

      public E getFirst() {
        return first;
      }

      public void setFirst(E first) {
        this.first = first;
      }

      public F getSecond() {
        return second;
      }

      public void setSecond(F second) {
        this.second = second;
      }

    }
    Map<String, Set<String>> ans = new HashMap<>();
    List<Pair<String, String>> mList = new ArrayList<>();
    for (int i = 0; i < tweets.size(); i++) {
      if (ans.containsKey(tweets.get(i).getAuthor().toLowerCase()))
        ans.get(tweets.get(i).getAuthor().toLowerCase())
            .addAll(Extract.divideMentionedUsers(tweets.get(i)));
      else
        ans.put(tweets.get(i).getAuthor().toLowerCase(),
            Extract.divideMentionedUsers(tweets.get(i)));
    }
    for (Map.Entry<String, Set<String>> entry : ans.entrySet()) {
      for (String string : entry.getValue()) {
          if (ans.containsKey(string) && !ans.get(string).isEmpty() && ans.get(string).contains(entry.getKey())) {
            Pair<String, String> pair = new Pair();
            pair.setFirst((String) entry.getKey());
            pair.setSecond(string);
            mList.add(pair);
          }
        }
      }
    // Iterator<Entry<String, Set<String>>> iterator = ans.entrySet().iterator();
    // while (iterator.hasNext()) {
    // Map.Entry entry = (Map.Entry)iterator.next();
    // Set<String> set = (Set<String>)entry.getValue();
    // for(String string : set){
    // if(!string.isEmpty() && !ans.get(string).isEmpty() &&
    // ans.get(string).contains((String)entry.getKey())){
    // }
    // }
    // }
    for (int i = 0; i < mList.size(); i++) {
      for (int j = 0; j < mList.size(); j++) {
        if (mList.get(i).getSecond().equals(mList.get(j).getFirst()))
          ans.get(mList.get(i).getFirst()).add(mList.get(j).getSecond());
      }
    }
    return ans;
    // Map<String, List<Tweet>> someoneTweets = new HashMap<>();
    // for(int i = 0;i<tweets.size();i++){
    // if(someoneTweets.containsKey(tweets.get(i).getAuthor()))
    // someoneTweets.get(tweets.get(i).getAuthor()).add(tweets.get(i));
    // else
    // someoneTweets.put(tweets.get(i).getAuthor(),
    // Arrays.asList(tweets.get(i)));
    // }
    // Iterator iterator = someoneTweets.entrySet().iterator();
    // while(iterator.hasNext()){
    // Map.Entry entry = (Map.Entry)iterator.next();
    // ans.put((String)entry.getKey(),
    // Extract.getMentionedUsers((List<Tweet>)entry.getValue()));
    // }
    // return ans;
  }

  /**
   * Find the people in a social network who have the greatest influence, in the sense that they
   * have the most followers.
   * 
   * @param followsGraph
   *          a social network (as defined above)
   * @return a list of all distinct Twitter usernames in followsGraph, in descending order of
   *         follower count.
   */
  public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    // throw new RuntimeException("not implemented");
    // List<Map.Entry<String, Set<String>>> list = new
    // ArrayList<>(followsGraph.entrySet());
    // Collections.sort(list, new Comparator<Map.Entry<String,
    // Set<String>>>() {
    //
    // @Override
    // public int compare(Entry<String, Set<String>> o1, Entry<String,
    // Set<String>> o2) {
    // // TODO Auto-generated method stub
    // return o2.getValue().size() - o1.getValue().size();
    // }
    // });
    // List<String> anStrings = new ArrayList<>();
    // for (int i = 0; i < list.size(); i++) {
    // anStrings.add(list.get(i).getKey());
    // }
    // return anStrings;
    Map<String, Integer> ans = new HashMap<>();
    for (Map.Entry<String, Set<String>> entry : followsGraph.entrySet()) {
      for (String string : entry.getValue()) {
        if (ans.containsKey(string.toLowerCase()))
          ans.put(string.toLowerCase(), ans.get(string.toLowerCase()) + 1);
        else
          ans.put(string.toLowerCase(), 1);
      }
    }
    List<Map.Entry<String, Integer>> list = new ArrayList<>(ans.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

      @Override
      public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
        // TODO Auto-generated method stub
        return o2.getValue() - o1.getValue();
      }
    });
    List<String> reStrings = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      reStrings.add(list.get(i).getKey());
      // if(i < 30)
      // System.out.println(list.get(i).getKey() + " " + list.get(i).getValue());
    }
    System.out.println();
    return reStrings;
  }

  private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
  private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
  private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

  private static final Tweet tweet1 = new Tweet(1, "alyssa",
      "is it reasonable to talk about rivest so much?@obama", d1);
  private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes @ccc @alyssa #hype",
      d2);
  private static final Tweet tweet3 = new Tweet(3, "Obama", "Long time no see", d3);
  private static final Tweet tweet4 = new Tweet(4, "alyssa",
      "is it reasonable to talk about rivest so much?@bbitdiddle", d3);
  
  private static final Tweet tweet5 = new Tweet(5, "ccc", "asdfjl fdsla j sadlfj @bbitdiddle", d3);

  public static void main(String[] args) {
    Map<String, Set<String>> followsGraph = SocialNetwork
        .guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    System.out.println(followsGraph.get(tweet1.getAuthor()));
  }

}
