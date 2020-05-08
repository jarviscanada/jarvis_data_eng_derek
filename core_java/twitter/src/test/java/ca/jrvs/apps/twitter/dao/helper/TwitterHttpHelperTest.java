package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterHttpHelperTest {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

    @Test
    public void httpPost() throws URISyntaxException, IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=first_tweet5"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void httpGet() throws URISyntaxException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/show.json?id=1251992697906384902"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}