package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet, String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";
    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet tweet) throws URISyntaxException, IOException, OAuthCommunicationException,
            OAuthExpectationFailedException, OAuthMessageSignerException {
        URI uri;
        try {
            uri = getPostUri(tweet);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        //Execute HTTP Request
        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    private URI getPostUri(Tweet tweet) throws URISyntaxException {

        String status = tweet.getText();
        Double longitude = tweet.getCoordinates().getCoordinates().get(0);
        Double latitude = tweet.getCoordinates().getCoordinates().get(1);
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        return new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(status) +
                AMPERSAND + "lat" + EQUAL + latitude + AMPERSAND + "long" + EQUAL + longitude);
    }

    /**
     * Checck response status code Convert Response Entity to Tweet
     */
    Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
        Tweet tweet = null;
        //Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status " + status);
        }
        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }
        try {
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON to Java Object", e);
        }
        return tweet;
    }

    @Override
    public Tweet findById(String s) throws URISyntaxException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        URI uri;
        try {
            uri = getById(s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid id", e);
        }
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    private URI getById(String s) throws URISyntaxException {
        return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);
    }

    @Override
    public Tweet deleteById(String s) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        URI uri;
        try {
            uri = removeById(s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid id", e);
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    private URI removeById(String s) throws URISyntaxException {
        return new URI(API_BASE_URI + DELETE_PATH + "/" + s + ".json");
    }

//    public static void main(String[] args) throws IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
//
//        String consumerKey = System.getenv("consumerKey");
//        String consumerSecret = System.getenv("consumerSecret");
//        String accessToken = System.getenv("accessToken");
//        String tokenSecret = System.getenv("tokenSecret");
//        System.out.println("consumerKey:" + consumerKey + "|" + "consumerSecret:" + consumerSecret + "|" +
//                "accessToken:" + accessToken + "|" + "tokenSecret:" + tokenSecret);
//        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
//        TwitterDao dao = new TwitterDao(httpHelper);
//        //test create()
//        String hashTag = "#happycoding";
//        String text = "@Hello April" + hashTag + " " + System.currentTimeMillis();
//        Double lat = 15d;
//        Double lon = -10d;
//        Tweet postTweet = buildTweet(text, lon, lat);
//        System.out.println(JsonUtil.toJson(postTweet,true,true));
//        Tweet createTweet = dao.create(postTweet);
//        System.out.println(JsonUtil.toJson(createTweet,true,true));
//        //test findbyId()
//        String testId = "1251655382537244675";
//        Tweet findTweet= dao.findById(testId);
//        System.out.println(JsonUtil.toJson(findTweet, true, true));
//        System.out.println("Successfully find tweet!");
//        //test deleteById()
//        String testId2 = "1251497856957112320";
//        Tweet deleteTweet = dao.deleteById(testId2);
//        System.out.println(JsonUtil.toJson(deleteTweet,true,true));
//        System.out.println("Successfully delete tweet");
//   }
}
