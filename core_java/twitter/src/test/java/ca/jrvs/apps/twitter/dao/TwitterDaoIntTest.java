//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TwitterUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TwitterDaoIntTest {
    String hashTag = "#happycoding";
    String text;
    Double lat;
    Double lon;
    Tweet postTweet;
    private TwitterDao dao;
    private long id;

    public TwitterDaoIntTest() {
        this.text = "@Hello April" + this.hashTag + " " + System.currentTimeMillis();
        this.lat = 15.0D;
        this.lon = -10.0D;
        this.postTweet = TwitterUtil.buildTweet(this.text, this.lon, this.lat);
    }

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println("consumerKey:" + consumerKey + "|consumerSecret:" + consumerSecret + "|accessToken:" + accessToken + "|tokenSecret:" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        System.out.println(JsonUtil.toJson(this.postTweet, true, true));
        Tweet tweet = this.dao.create(this.postTweet);
        Assert.assertEquals(this.text, tweet.getText());
        Assert.assertNotNull(tweet.getCoordinates());
        Assert.assertEquals(2L, (long)tweet.getCoordinates().getCoordinates().size());
        Assert.assertEquals(this.lon, tweet.getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(this.lat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void findById() throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        Tweet tweet = this.dao.create(this.postTweet);
        this.id = tweet.getId();
        Tweet findTweet = this.dao.findById(Long.toString(this.id));
        System.out.println(JsonUtil.toJson(findTweet, true, true));
        Assert.assertEquals(findTweet.getText(), tweet.getText());
        Assert.assertNotNull(findTweet.getCoordinates());
        Assert.assertEquals(2L, (long)findTweet.getCoordinates().getCoordinates().size());
        Assert.assertEquals(tweet.getCoordinates().getCoordinates().get(0), findTweet.getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(tweet.getCoordinates().getCoordinates().get(1), findTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void deleteById() throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        Tweet tweet = this.dao.create(this.postTweet);
        this.id = tweet.getId();
        Tweet deleteTweet = this.dao.deleteById(Long.toString(this.id));
        Assert.assertEquals(tweet.getText(), deleteTweet.getText());
        Assert.assertNotNull(deleteTweet.getCoordinates());
        Assert.assertEquals(2L, (long)deleteTweet.getCoordinates().getCoordinates().size());
        Assert.assertEquals(tweet.getCoordinates().getCoordinates().get(0), deleteTweet.getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(tweet.getCoordinates().getCoordinates().get(1), deleteTweet.getCoordinates().getCoordinates().get(1));
    }
}
