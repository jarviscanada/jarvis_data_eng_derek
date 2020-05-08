package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TwitterUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TwitterServiceIntTest {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    TwitterDao dao = new TwitterDao(httpHelper);
    TwitterService service = new TwitterService(dao);
    String hashTag = "#happycoding";
    String text1 = "@Hello April" + hashTag + " " + System.currentTimeMillis();
    Double lat1 = 15d;
    Double lon1 = -10d;
    Tweet postTweet = TwitterUtil.buildTweet(text1, lon1, lat1);
    @Test
    public void postTweet() throws Exception {
        Tweet testPostTweet = service.postTweet(postTweet);
//        System.out.println(JsonUtil.toJson(testPostTweet, true, true));
//        System.out.println("Successfully post tweet!");
        Assert.assertEquals(text1,testPostTweet.getText());
        Assert.assertEquals(lon1, testPostTweet.getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(lat1,testPostTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void showTweet() throws Exception {
        String[] field = {"id"};
        Tweet testPostTweet = service.postTweet(postTweet);
        long id = testPostTweet.getId();
        System.out.println(id);
        Tweet testShowTweet = service.showTweet(Long.toString(id),field);
        Assert.assertEquals(text1,testShowTweet.getText());
        Assert.assertEquals(lon1,testShowTweet.getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(lat1,testShowTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void deleteTweets() throws Exception {

        Tweet testPostTweet = service.postTweet(postTweet);
        long id = testPostTweet.getId();

        String hashTag = "#happycoding";
        String text2 = "@Hello May" + hashTag + " " + System.currentTimeMillis();
        Double lat2 = 50d;
        Double lon2 = -50d;
        Tweet postTweet2 = TwitterUtil.buildTweet(text2, lon2, lat2);
        Tweet testPostTweet2 = service.postTweet(postTweet2);
        long id2 = testPostTweet2.getId();
        String[] ids = {Long.toString(id), Long.toString(id2)};

        List<Tweet> testDeleteTweet = service.deleteTweets(ids);
        Assert.assertEquals(text1,testDeleteTweet.get(0).getText());
        Assert.assertEquals(lon1,testDeleteTweet.get(0).getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(lat1,testDeleteTweet.get(0).getCoordinates().getCoordinates().get(1));
        Assert.assertEquals(text2,testDeleteTweet.get(1).getText());
        Assert.assertEquals(lon2,testDeleteTweet.get(1).getCoordinates().getCoordinates().get(0));
        Assert.assertEquals(lat2,testDeleteTweet.get(1).getCoordinates().getCoordinates().get(1));
    }
}