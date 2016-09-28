package IO;

import twitter.Tweet;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by User on 28/09/2016.
 */
public class TweetReader {

    public static final String DATA_SOURCE = "Tweets.txt";

    public static List<Tweet> readTweets()
    {
        List<Tweet> tweets = new ArrayList<Tweet>();
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream(DATA_SOURCE));
            while (in.hasNext())
                tweets.add(Tweet.create(in.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null)
                in.close();
        }
        return tweets;
    }

}
