package com.twitter.tasks;

import com.google.common.collect.Lists;
import com.twitter.processinglogic.TweetProcessor;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ivsi on 8/19/2015.
 */
public class TwitterStreamingTask implements ApplicationListener<ContextClosedEvent> {

    /**
     * Task starts running after 5 seconds from starting the application
     * It runs in the background for the whole time of application execution
     */

    private boolean shutdown;

    @Autowired
    private TweetProcessor tweetProcessor;

    @Scheduled(fixedDelay = 5000)
    public void work() {
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(10000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("java");
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a configuration file
        Authentication hosebirdAuth = new OAuth1("c4IseJSBTDH4PjpauhL6ueZT4", "jLICMVNfbBkewyOkuVb7uzBxkitfsZueDq6DHHmWSoDubq68Oi", "3082911441-mX5BxS3LUB4RSKeJu3Cx8EnfHosOSGrf26pLmSt", "ZX4zT6vX7J3Xp8fz1YjxI28C8RBIzMOczlyVnruhBCYyJ");

        ClientBuilder builder = new ClientBuilder()
                .name("Zuhlke client")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

        Client hosebirdClient = builder.build();

        // Attempts to establish a connection.
        hosebirdClient.connect();
        while ((!shutdown) && (!hosebirdClient.isDone())) {
            String msg;
            try {
                msg = msgQueue.take();
                //handling message parsing and storing to collection
                tweetProcessor.addFeed(msg);
                System.out.println(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Excepion on message queue reading");
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        shutdown = true;
    }
}
