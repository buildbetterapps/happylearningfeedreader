package com.gols.thehappylearning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gols.thehappylearning.adapter.FeedSummaryAdapter;
import com.gols.thehappylearning.network.NetworkResponseListener;
import com.gols.thehappylearning.parser.FeedParser;
import com.gols.thehappylearning.services.FeedLoaderService;

public class MainActivity extends AppCompatActivity implements NetworkResponseListener {


    private RecyclerView recyclerViewFeeds;
    private RecyclerView.LayoutManager mLayoutManager;
    private FeedSummaryAdapter mAdapter;
    //private List<Feed> feeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        loadFeeds();
    }

    private BroadcastReceiver feedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "received", Toast.LENGTH_SHORT).show();
            mAdapter.setFeeds(FeedParser.feeds);
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(feedReceiver,new IntentFilter("FEED_READY"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(feedReceiver);
    }

    private void setupViews() {
        recyclerViewFeeds = (RecyclerView) findViewById(R.id.recyclerViewFeeds);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewFeeds.setLayoutManager(mLayoutManager);
        mAdapter = new FeedSummaryAdapter(this);
        recyclerViewFeeds.setAdapter(mAdapter);
    }

    private void loadFeeds() {
        /*NetworkManager.getInstance(this)
                .makeNetworkRequestForString(101,
                        Request.Method.GET,
                        "http://thehappylearning.com/feed",
                        null ,this);*/

            Intent feedLoadService = new Intent(this,FeedLoaderService.class);
            feedLoadService.putExtra("feedUrl","http://thehappylearning.com/feed");
            startService(feedLoadService);
    }

    @Override
    public void onDataReceived(int requestCode, Object data) {
        if(requestCode == 101){
            //feeds = FeedParser.getInstance().parseFeeds((String) data);
        }
    }

    @Override
    public void onDataFailed(int requestCode, Object error) {
        Toast.makeText(MainActivity.this, "Fail to load Feeds", Toast.LENGTH_SHORT).show();
    }
}
