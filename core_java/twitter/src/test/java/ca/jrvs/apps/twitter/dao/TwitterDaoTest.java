package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterDaoTest {
    private TwitterDao dao;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println("consumerKey:" + consumerKey + "|" + "consumerSecret:" + consumerSecret + "|" +
                "accessToken:" + accessToken + "|" + "tokenSecret:" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        String hashTag = "#happycoding";
        String text = "@someone sometext" + hashTag + " " + System.currentTimeMillis();
        Double lat = 15d;
        Double lon = -10d;
        Tweet postTweet = buildTweet(text, lon, lat);
        System.out.println(JsonUtil.toJson(postTweet, true, true));

        Tweet tweet = dao.create(postTweet);
        System.out.println("first assertEquals");
        assertEquals(text, tweet.getText());
        System.out.println("second asstNotNull");
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon,tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat,tweet.getCoordinates().getCoordinates().get(1));
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));

    }
    private Tweet buildTweet(String tweet_txt, Double lon, Double lat){
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
    @Test
    public void findById() {
    }

    @Test
    public void deleteById() {
    }
}