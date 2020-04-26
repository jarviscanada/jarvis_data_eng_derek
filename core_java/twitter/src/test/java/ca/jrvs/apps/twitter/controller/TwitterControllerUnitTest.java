package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.JsonUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TwitterControllerUnitTest {
    @Mock
    Service service;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() throws Exception {
        String[] args = {"test1", "text1", "15.0:-10.0"};
        when(service.postTweet(any())).thenReturn(new Tweet());
        Tweet testPostTweet = controller.postTweet(args);
        System.out.println(JsonUtil.toJson(testPostTweet, true, true));
        assertEquals(null, testPostTweet.getText());
        assertEquals(0, testPostTweet.getId());
        assertEquals(null, testPostTweet.getCoordinates());
    }

    @Test
    public void showTweet() throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        when(service.showTweet(any())).thenReturn(new Tweet());
        String[] text = {"test", "1251900576075481090"};
        Tweet testShowTweet = controller.showTweet(text);
        assertEquals(null, testShowTweet.getText());
    }

    @Test
    public void deleteTweet() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        when(service.deleteTweets(any())).thenReturn(new ArrayList<>());
        String[] testDelete = {"test", "1251900576075481090, 1251658310891208704"};
        List<Tweet> testDeleteTweet = controller.deleteTweet(testDelete);
        for (Tweet t : testDeleteTweet) {
            assertEquals(null, t.getText());
            assertEquals(null, t.getId_str());
        }

    }
}