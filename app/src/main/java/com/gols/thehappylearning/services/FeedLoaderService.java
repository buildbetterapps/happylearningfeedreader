package com.gols.thehappylearning.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.gols.thehappylearning.parser.FeedParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class FeedLoaderService extends IntentService {

    private static final String PARAM_URL = "feedUrl";


    public FeedLoaderService() {
        super("FeedLoaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            try {
                URL url = new URL(intent.getStringExtra(PARAM_URL));
                InputStream stream = url.openConnection().getInputStream();
                FeedParser.getInstance().parseFeeds(stream);
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("FEED_READY"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
