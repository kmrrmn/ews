package com.rmn.ews.utiles;

import com.rmn.ews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmn on 28-08-2016.
 */
public class Sources {

    public static List<String> srcURLList = new ArrayList<String>() {{
        add(0, "bbc-news");
        add(1, "bbc-sport");
        add(2, "cnbc");
        add(3, "cnn");
        add(4, "espn");
        add(5, "google-news");
        add(6, "the-guardian-uk");
        add(7, "reddit-r-all");

        add(8, "techcrunch");
        add(9, "the-times-of-india");
        add(10, "the-hindu");
    }};

    public static List<Integer> srcIconList = new ArrayList<Integer>() {{
        add(0, R.drawable.bbc);
        add(1, R.drawable.bbcsport);
        add(2, R.drawable.cnbc);
        add(3, R.drawable.cnn);
        add(4, R.drawable.espn);
        add(5, R.drawable.google);
        add(6, R.drawable.guardian);
        add(7, R.drawable.reddit);
        add(8, R.drawable.techcrunch);
        add(9, R.drawable.toi);
        add(10, R.drawable.thehindu);
    }};

    public static List<String> srcNameList = new ArrayList<String>() {{
        add(0, "BBC News");
        add(1, "BBC Sport");
        add(2, "CNBC");
        add(3, "CNN");
        add(4, "ESPN");
        add(5, "Google");
        add(6, "The Guardian");
        add(7, "Reddit");
        add(8, "Techcrunch");
        add(9, "Times of India");
        add(10, "The Hindu");
    }};

}
