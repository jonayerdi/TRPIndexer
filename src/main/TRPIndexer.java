package main;

import IO.TweetReader;
import IO.TweetWritter;
import twitter.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28/09/2016.
 */
public class TRPIndexer {

    List<Tweet> tweets;

    public static void main(String[] args) {
        TRPIndexer indexer = new TRPIndexer();
        indexer.start();
    }

    public void start() {
        tweets = TweetReader.readTweets();
        for(Tweet tweet : tweets) {
            try {
                tweet.process();
            } catch(Exception e) {
                System.out.println("Error reading tweet. Id = " + tweet.getId());
            }
        }
        TweetWritter.writeTweets(tweets);
    }

}
