package com.twitter.controller;

import com.twitter.processinglogic.TweetProcessor;
import com.twitter.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Deque;
import java.util.List;

/**
 * Created by ivsi on 8/20/2015.
 */
@RestController
@RequestMapping("/api")
public class TwitterRestController {

    /**
     * Method returns all items in list
     *
     * @return
     */
    @Autowired
    private TweetProcessor tweetProcessor;

    @RequestMapping(value = "/complete", produces = "application/json")
    public Deque<Feed> getAllFeeds() {
        return tweetProcessor.getFeeds();
}

    /**
     * Method returns specified number of latest items
     * Future addition is added on the end of the queue
     * Items are not removed
     *
     * @param number
     * @return
     */
    @RequestMapping(value = "/latest/{number}", produces = "application/json")
    public List<Feed> getLatestFeedsByNumberOfFeeds(@PathVariable("number") int number) {
        return tweetProcessor.getLatestFeedsByNumberOfFeeds(number);
    }

    /**
     * Method returns specified number of items from the beginning of the list
     * After the items are returned, they are removed from the list
     *
     * @param number
     * @return
     */
    @RequestMapping(value = "/oldest/{number}", produces = "application/json")
    public List<Feed> getOldestFeedsByNumberOfFeeds(@PathVariable("number") int number) {
        return tweetProcessor.getOldestByNumberAndRemoveThem(number);
    }

    /**
     * Method returns list of all items added so far
     * After returning all items, the list is cleared
     *
     * @return
     */
    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feed> getListOfFeeds() {
        return tweetProcessor.getListOfFeedsAndClearMemory();
    }


}
