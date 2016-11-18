package com.gols.thehappylearning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gols.thehappylearning.R;
import com.gols.thehappylearning.beans.Feed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 11/18/2016.
 */
public class FeedSummaryAdapter extends RecyclerView.Adapter<FeedSummaryAdapter.ViewHolder> {

    private List<Feed> feeds = new ArrayList<>();
    private Context mContext;

    public FeedSummaryAdapter(Context context){
        mContext = context;
    }
    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_feed, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewFeedTitle.setText(feeds.get(position).getTitle());
        Picasso.with(mContext).load(feeds.get(position).getImageURL()).into(holder.imageViewFeedImage);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewFeedTitle;
        public ImageView imageViewFeedImage;
        public ViewHolder(View v) {
            super(v);
            textViewFeedTitle = (TextView) v.findViewById(R.id.txtfeedTitle);
            imageViewFeedImage = (ImageView) v.findViewById(R.id.imgFeedImage);

        }
    }

}
