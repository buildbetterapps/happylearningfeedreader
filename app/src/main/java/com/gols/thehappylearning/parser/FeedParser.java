package com.gols.thehappylearning.parser;

import com.gols.thehappylearning.beans.Feed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 11/17/2016.
 */
public class FeedParser {
    private static FeedParser feedParser;

    public static List<Feed> feeds = new ArrayList<Feed>();
    private FeedParser() {

    }

    public static synchronized FeedParser getInstance() {
        if (feedParser == null) {
            feedParser = new FeedParser();
        }
        return feedParser;
    }

    public List<Feed> parseFeeds(String dataString) {
        List<Feed> feeds = new ArrayList<Feed>();
        //parse XML

        return feeds;
    }

    public List<Feed> parseFeeds(InputStream stream) {
        //parse XML

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(stream, "UTF_8");
            boolean insideItem = false;

            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            Feed feed = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                        feed = new Feed();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            feed.setTitle(xpp.nextText()); //extract the headline
                    }
                    else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem) {
                            try {
                                int token = xpp.nextToken();
                                while(token!=XmlPullParser.CDSECT){
                                    token = xpp.nextToken();
                                }
                                String cdata = xpp.getText();
                                int lastIndex = (cdata.indexOf("png") == -1)? cdata.indexOf("png"): cdata.indexOf("jpg");
                                String imgUrl = cdata.substring(cdata.indexOf("src=\"")+5, lastIndex+3);
                                feed.setImageURL(imgUrl);
                                //todo move to last tag
                                insideItem=false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem){
                            feed.setLink(xpp.nextText()); //extract the link of article
                            feeds.add(feed);
                        }
                    }
                }
                else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    //insideItem = false;
                }
                eventType = xpp.next(); //move to next element
            }
            return feeds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feeds;
    }
}