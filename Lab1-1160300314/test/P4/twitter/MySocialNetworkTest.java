package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.junit.*;

public class MySocialNetworkTest {
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

  @Test
  public void testMySocialNetworkTest(){
    Map<String, Set<String>> followsGraph = SocialNetwork
        .guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    assertTrue(followsGraph.get(tweet1.getAuthor().toLowerCase()).contains("ccc"));
  }
}
