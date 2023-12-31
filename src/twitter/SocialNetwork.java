/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map <String, Set<String>> followsGraph = new HashMap<String, Set<String>>();
        for(Tweet tweet : tweets) {
            String username = tweet.getAuthor();
            if (!followsGraph.containsKey(username)) followsGraph.put(username, new HashSet<String>());
            Set<String> mentionedUsers = SocialNetwork.getMentioned(tweet.getText());
            for (String mentionedUser : mentionedUsers) {
                if (!mentionedUser.equals(username) )
                    followsGraph.get(username).add(mentionedUser);
            }
        }
        return followsGraph;


    }

    private static  Set<String> getMentioned(String tweet) {
        String regex = "(?<=^|[^a-zA-Z0-9_-])@([a-zA-Z0-9_-]+)";
        Set<String> mentioned = new HashSet<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tweet);
        while (matcher.find()) {
            String mentionedUser = matcher.group(1).toLowerCase();
            if ( !(mentioned.contains(mentionedUser))) mentioned.add(mentionedUser);}
        return mentioned;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {

        Map<String, Integer> rankMapInfluencers = new HashMap<>();
        for (Map.Entry <String, Set<String>> user : followsGraph.entrySet()) {
            if (!user.getValue().isEmpty()) {
                for( String userFollow : user.getValue()) {
                    if (!rankMapInfluencers.containsKey(userFollow))
                        rankMapInfluencers.put( userFollow, 0);
                    else {
                        Integer count = rankMapInfluencers.get(userFollow);

                        rankMapInfluencers.replace(userFollow, count + 1);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(rankMapInfluencers.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

//        List<Map.Entry<String, Set<String>>> entryList = new ArrayList<Map.Entry<String, Set<String>>>(followsGraph.entrySet());
//        Collections.sort(entryList, new Comparator<Map.Entry<String, Set<String>>>() {
//            @Override
//            public int compare(Map.Entry<String, Set<String>> o1, Map.Entry<String, Set<String>> o2) {
//                return o1.getValue().size() - o2.getValue().size();
//            }
//        });
        List<String> influenceRank = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            influenceRank.add(entry.getKey());
        }
        return influenceRank;
    };

}
