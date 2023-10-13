package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {
    private static final Instant d1 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    public static void main(String[] args) {
//        int t = d2.compareTo(d1);
//
//        System.out.println(d1);
//        System.out.println(t);
        String input = "Here are some usernames: @Mike, @JohnDoe, and @JohnDoe.";
        Set<String> usernames = new HashSet<>();
        Pattern pattern = Pattern.compile("(?<=^|[^a-zA-Z0-9_-])@([a-zA-Z0-9_-]+)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String matchedUser = matcher.group(1);
            if (!usernames.contains(matchedUser)) {
                usernames.add(matchedUser);
            }
        }


        System.out.println("Usernames found: " + usernames);
    }
}