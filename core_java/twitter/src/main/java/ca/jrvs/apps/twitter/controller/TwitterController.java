package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TwitterUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    //@Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) throws Exception {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        String tweet_txt = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || StringUtils.isEmpty(tweet_txt)) {
            throw new IllegalArgumentException(
                    "Invalid location format\nUSAGE: TwitterCLIAPP post \"tweet_text\" \"latitude:longitude\"");
        }
        Double lat = null;
        Double lon = null;
        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", e);
        }
        Tweet postTweet = TwitterUtil.buildTweet(tweet_txt, lon, lat);
        return service.postTweet(postTweet);
    }

    @Override
    public Tweet showTweet(String[] args) throws URISyntaxException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {

        String tweet_id = args[1];
        Tweet tweet = new Tweet();

        if (args.length == 2) {
            return tweet = service.showTweet(tweet_id);
        }
        if (args.length == 3) {
            String field = args[2];
            String[] fileds = field.split(COMMA);
            return tweet = service.showTweet(tweet_id,fileds);
        }
        return tweet;
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"[id1,id2,..]\"");
        }

        String id = args[1];
        String[] ids = id.split(COMMA);
        return service.deleteTweets(ids);
    }
}
