package com.gols.thehappylearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerViewFeeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        recyclerViewFeeds = (RecyclerView) findViewById(R.id.recyclerViewFeeds);

        
    }
}
