package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterHttpHelper implements HttpHelper {
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    public static void main(String[] args) throws IOException, URISyntaxException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println("consumerKey:" + consumerKey + "|" + "consumerSecret:" + consumerSecret + "|" +
                "accessToken:" + accessToken + "|" + "tokenSecret:" + tokenSecret);
        //create component
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=first_tweet2"));
    }

    @Override
    public HttpResponse httpPost(URI uri) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, null);
        } catch (OAuthException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    @Override
    public HttpResponse httpGet(URI uri) throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        try {
            return executeHttpRequest(HttpMethod.GET, uri, null);
        } catch (OAuthException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity stringEntity) throws
            OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, IOException {
        if (method == HttpMethod.GET) {
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);
        } else if (method == HttpMethod.POST) {
            HttpPost request = new HttpPost(uri);
            if (stringEntity != null) {
                request.setEntity(stringEntity);
            }
            consumer.sign(request);
            return httpClient.execute(request);
        } else {
            throw new IllegalAccessError("Unknown HTTP method:" + method.name());
        }
    }
}