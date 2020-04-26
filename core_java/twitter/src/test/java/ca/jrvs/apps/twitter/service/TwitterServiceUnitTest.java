package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TwitterUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() throws Exception {
        String hashTag = "#Happy finding tweet!";
        String text = "@Hello findById" + hashTag + " " + System.currentTimeMillis();
        Double lon = 20.0;
        Double lat = 0.0;
        when(dao.create(any())).thenReturn(new Tweet());
        Tweet postTweet = TwitterUtil.buildTweet(text, lon, lat);
        Tweet testPostTweet = service.postTweet(postTweet);
        System.out.println(JsonUtil.toJson(testPostTweet, true, true));
        assertEquals(null, testPostTweet.getText());
        assertEquals(0, testPostTweet.getId());
        assertEquals(null, testPostTweet.getCoordinates());
    }

    @Test
    public void showTweet() throws Exception {
        String id = "1234567890123456789";
        when(dao.findById(id)).thenReturn(new Tweet());
        String[] field = {"id"};
        Tweet testShowTweet = service.showTweet(id,field);
        assertEquals(null,testShowTweet.getId_str());
        assertEquals(null, testShowTweet.getEntities());

    }


    @Test
    public void deleteTweets() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        String[] id = {"1234567890123456789"};
        List<Tweet> list;
        when(dao.deleteById(id)).thenReturn(new Tweet());
        List<Tweet> testDeleteTweet = service.deleteTweets(id);
        System.out.println(JsonUtil.toJson(testDeleteTweet.get(0), true, true));
        assertEquals(null,testDeleteTweet.get(0).getId_str());
        assertEquals(null, testDeleteTweet.get(0).getEntities());
    }
}