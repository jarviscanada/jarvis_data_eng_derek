package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service {
    private CrdDao dao;
    private String[] tweetFields = {
            "created_at", "id", "id_str", "text", "coordinates", "entities",
            "retweet_count", "favorite_count", "favorited", "retweeted"};

    //@Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) throws Exception {
        //Business logic:
        //e.g. text length, lat/lon range, id format
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) throws Exception {
        int textLength = tweet.getText().length();
        double longitude = tweet.getCoordinates().getCoordinates().get(0);
        double latitude = tweet.getCoordinates().getCoordinates().get(1);
        if (textLength <= 140 && longitude >= (-180) &&
                longitude <= 180 && latitude >= (-90) && latitude <= 90) {
            System.out.println("Tweet is valid.");
        } else {
            if (tweet.getText().length() > 140) {
                throw new IllegalArgumentException("Text length is over 140!");
            } else if (tweet.getCoordinates().getCoordinates().get(1) < (-90) ||
                    tweet.getCoordinates().getCoordinates().get(1) > 90) {
                throw new IllegalArgumentException("Latitude is out of range!");
            } else {
                throw new IllegalArgumentException("Longitude is out of range!");
            }
        }
    }

    @Override
    public Tweet showTweet(String id, String[] fields) throws URISyntaxException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        validateId(id);
        for (String f : fields) {
            validateFields(f);
        }
        return (Tweet) dao.findById(id);
    }

    private void validateId(String id) {
        try {
            int validId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id");
        }
    }

    private void validateFields(String fields) {
        if (!Arrays.asList(tweetFields).contains(fields)) {
            throw new IllegalArgumentException("Invalid fields!");
        }
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        List<Tweet> delete = new ArrayList<Tweet>();
        for (String f : ids) {
            validateId(f);
            delete.add((Tweet) dao.deleteById(f));
        }
        return delete;
    }
}
