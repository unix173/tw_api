package com.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ivsi on 8/21/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("in_reply_to_screen_name")
    private Object inReplyToScreenName;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user")
    private User user;

    @JsonProperty("timestamp_ms")
    private long timestampMs;


    public void addaptFeedMessage(){
        setDateInCorrectFormat();
        removeNewLinesFromMessage();
    }

    private void setDateInCorrectFormat() {
        final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
        sf.setLenient(true);
        Date date = null;
        try {
            date = sf.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sf = new SimpleDateFormat("dd MMM HH:mm:ss");
        this.createdAt = sf.format(date);
        System.out.println("Parsed: " + createdAt + "to: " + this.createdAt);
    }

    private void removeNewLinesFromMessage() {
        String newText = text.replaceAll("\n", " ");
        System.out.println(text);
        System.out.println(newText);
        this.text = newText;
    }




}
