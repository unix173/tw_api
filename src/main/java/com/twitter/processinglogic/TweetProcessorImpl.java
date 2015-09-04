package com.twitter.processinglogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.model.Feed;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by ivsi on 8/19/2015.
 */
@Component
public class TweetProcessorImpl implements TweetProcessor {

    private static Deque<Feed> feeds = new ConcurrentLinkedDeque<Feed>();

    @Override
    public Deque<Feed> getFeeds() {
        return feeds;
    }

    @Override
    public List<Feed> getListOfFeedsAndClearMemory() {
        List<Feed> ret = new LinkedList<Feed>();
        ret.addAll(feeds);
        feeds.clear();
        return ret;
    }

    @Override
    public List<Feed> getOldestByNumberAndRemoveThem(int number) {
        List<Feed> ret = new LinkedList<Feed>();
        if (number < 1) {
            System.out.println("Number must be greater than 1");
            return ret;
        }
        for (Iterator<Feed> iter = feeds.iterator(); iter.hasNext() && number > 0; --number) {
            Feed feed = iter.next();
            ret.add(feed);
            feeds.remove(feed);
        }
        return ret;
    }

    @Override
    public List<Feed> getLatestFeedsByNumberOfFeeds(int number) {
        List<Feed> ret = new ArrayList<Feed>();
        if (number < 1) {
            System.out.println("Number must be greater than 1");
            return ret;
        }
        for (Iterator<Feed> iterator = feeds.descendingIterator(); iterator.hasNext() && number > 0; --number) {
            ret.add((iterator.next()));
        }
        return ret;
    }

    private Feed parseStringMessageAsFeed(String message) {
        Feed feed = null;
        try {
            feed = new ObjectMapper().readValue(message, Feed.class);
            feed.addaptFeedMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feed;
    }

    @Override
    public void addFeed(String message) {
        Feed feed = parseStringMessageAsFeed(message);
        feeds.add(feed);
    }


}
