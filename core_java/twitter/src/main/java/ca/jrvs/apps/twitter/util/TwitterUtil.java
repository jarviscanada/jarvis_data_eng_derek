package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TwitterUtil {

    public static Tweet buildTweet(String tweet_txt, Double lon, Double lat) {
        Tweet tweet = new Tweet();
        tweet.setText(tweet_txt);
        List<Double> list = new ArrayList<>();
        list.add(lon);
        list.add(lat);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(list);
        tweet.setCoordinates(coordinates);
        return tweet;
    }

}
