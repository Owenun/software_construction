/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import org.junit.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T14:00:00Z");
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is @Mike it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "Mike", "Hello, I am Mike. And who are you @alyssa ", d2);
    private static final Tweet tweet3 = new Tweet(3, "Nubiya", "is @alyssa it reasonable to talk about rivest so much?", d3);
    private static final Tweet tweet4 = new Tweet(4, "WangHong", "is @Mike it reasonable to talk about rivest so much?", d3);
    private static final Tweet tweet5 = new Tweet(5, "Liu", "is @Jane @Mike it reasonable to talk about rivest so much?", d3);
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNotEmpty() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet1);

        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected empty graph", !followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSize() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet1);
        tweets.add(tweet2);

        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertEquals("expected size 2", 2, followsGraph.size());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersName() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        tweets.add(tweet4);
        tweets.add(tweet5);

        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertEquals("expected Mike", "mike", influencers.get(0));
        assertEquals("expected Mike", "alyssa", influencers.get(1));
        assertEquals("expected Mike", "jane", influencers.get(2));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
