package IO;

import twitter.Tweet;

import java.util.List;

/**
 * Created by User on 28/09/2016.
 */
public class TweetWritter {

    public static void writeTweets(List<Tweet> tweets) {
        for(Tweet tweet : tweets)
            System.out.println(tweet + "----------------------------------------------------");
    }

}
