package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    @Test
    public void create() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        //test failed request
        String hashTag = "#Happy coding!";
        String text = "@Hello April" + hashTag + " " + System.currentTimeMillis();
        Double lon = 20d;
        Double lat = -10d;
        //exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(TwitterUtil.buildTweet(text,lon,lat));
            fail();
        } catch (RuntimeException | URISyntaxException e) {
            assertTrue(true);
        }
        String tweetJsonStr = "{\n"
                +" \"created_at\":\"Mon Feb 18 21:23:00 +0000 2020\",\n"
                +" \"id\":1097607853932564480,\n"
                +" \"id_str\":\"1097607853932564480\",\n"
                +" \"text\":\"test with loc223\",\n"
                +" \"entities\":{\n"
                +"   \"hashTags\":[],"
                +"   \"user_mentions\":[]"
                +"  },\n"
                +" \"coordinates\":null,"
                +" \"retweet_count\":0,\n"
                +" \"favorite_count\":0,\n"
                +" \"favorited\":false,\n"
                +" \"retweeted\":false\n"
                +"}";
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr,Tweet.class);
        //mock parseResponseBody
        //there was a fault, it is fixed by making TwitterDao package private
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
        Tweet tweet = spyDao.create(TwitterUtil.buildTweet(text,lon,lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());

    }

    @Test
    public void findById() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {

        String hashTag = "#Happy finding tweet!";
        String text = "@Hello findById" + hashTag + " " + System.currentTimeMillis();
        Double lon = 30d;
        Double lat = -30d;
        //exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(TwitterUtil.buildTweet(text,lon,lat));
            fail();
        } catch (RuntimeException | URISyntaxException e) {
            assertTrue(true);
        }
        String tweetJsonStr = "{\n"
                +" \"created_at\":\"Sat Apr 18 21:23:00 +0000 2020\",\n"
                +" \"id\":2097607853932564480,\n"
                +" \"id_str\":\"2097607853932564480\",\n"
                +" \"text\":\"test with loc224\",\n"
                +" \"entities\":{\n"
                +"   \"hashTags\":[],"
                +"   \"user_mentions\":[]"
                +"  },\n"
                +" \"coordinates\":null,"
                +" \"retweet_count\":0,\n"
                +" \"favorite_count\":0,\n"
                +" \"favorited\":false,\n"
                +" \"retweeted\":false\n"
                +"}";
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr,Tweet.class);
        //mock parseResponseBody
        //there was a fault, it is fixed by making TwitterDao package private
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
        Tweet tweet = spyDao.findById("2097607853932564480");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());

    }

    @Test
    public void deleteById() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        String hashTag = "#Happy finding tweet!";
        String text = "@Hello findById" + hashTag + " " + System.currentTimeMillis();
        Double lon = 40d;
        Double lat = -40d;
        //exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(TwitterUtil.buildTweet(text,lon,lat));
            fail();
        } catch (RuntimeException | URISyntaxException e) {
            assertTrue(true);
        }
        String tweetJsonStr = "{\n"
                +" \"created_at\":\"Sat Apr 18 21:23:00 +0000 2020\",\n"
                +" \"id\":3097607853932564480,\n"
                +" \"id_str\":\"3097607853932564480\",\n"
                +" \"text\":\"test with loc224\",\n"
                +" \"entities\":{\n"
                +"   \"hashTags\":[],"
                +"   \"user_mentions\":[]"
                +"  },\n"
                +" \"coordinates\":null,"
                +" \"retweet_count\":0,\n"
                +" \"favorite_count\":0,\n"
                +" \"favorited\":false,\n"
                +" \"retweeted\":false\n"
                +"}";
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr,Tweet.class);
        //mock parseResponseBody
        //there was a fault, it is fixed by making TwitterDao package private
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
        Tweet tweet = spyDao.deleteById("3097607853932564480");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}