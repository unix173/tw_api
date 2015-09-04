package com.twitter.processinglogic;

import com.twitter.model.Feed;

import java.util.Deque;
import java.util.List;

/**
 * Created by ivsi on 9/4/2015.
 */
public interface TweetProcessor {

    Deque<Feed> getFeeds();

    List<Feed> getListOfFeedsAndClearMemory();

    List<Feed> getOldestByNumberAndRemoveThem(int number);

    List<Feed> getLatestFeedsByNumberOfFeeds(int number);

    void addFeed(String message);
}
