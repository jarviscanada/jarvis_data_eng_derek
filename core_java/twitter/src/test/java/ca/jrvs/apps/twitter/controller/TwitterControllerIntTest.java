package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TwitterUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TwitterControllerIntTest {

    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    TwitterDao dao = new TwitterDao(httpHelper);
    TwitterService service = new TwitterService(dao);
    TwitterController controller = new TwitterController(service);

    String hashTag = "#happycoding";
    String text1 = "@Hello April" + hashTag + " " + System.currentTimeMillis();
    Double lat1 = 15d;
    Double lon1 = -10d;
    Tweet postTweet = TwitterUtil.buildTweet(text1, lon1, lat1);

    @Test
    public void postTweet() throws Exception {
        String[] args = {"test1",text1,"15d:-10d"};
        Tweet testPostTweet = controller.postTweet(args);
        System.out.println(JsonUtil.toJson(testPostTweet,true,true));
        assertEquals(text1,testPostTweet.getText());
        assertEquals(lon1,testPostTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat1,testPostTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void showTweet() throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        String id = "1251900576075481090";
        String[] args = {"test2",id, "coordinates"};
        Tweet testShowTweet = controller.showTweet(args);
        System.out.println(JsonUtil.toJson(testShowTweet,true,true));
        assertEquals(id, testShowTweet.getId_str());
        assertEquals(lon1, testShowTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat1, testShowTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void deleteTweet() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        String id = "1251900576075481090";
        String[] args = {"test3", id};
        List<Tweet> testDeleteTweet = controller.deleteTweet(args);
        System.out.println(JsonUtil.toJson(testDeleteTweet.get(0),true,true));
        assertEquals(id, testDeleteTweet.get(0).getId_str());
        assertEquals(lon1, testDeleteTweet.get(0).getCoordinates().getCoordinates().get(0));
        assertEquals(lat1, testDeleteTweet.get(0).getCoordinates().getCoordinates().get(1));
    }
}