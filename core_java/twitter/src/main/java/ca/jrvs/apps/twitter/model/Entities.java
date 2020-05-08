package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entities {
    @JsonProperty("hashtags")
    private List<Hashtag> hashtags;
    @JsonProperty("user_mention")
    private List<UserMention> user_mention;

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<UserMention> getUser_mention() {
        return user_mention;
    }

    public void setUser_mention(List<UserMention> user_mention) {
        this.user_mention = user_mention;
    }
}
