package com.rmn.ews.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmn on 28-08-2016.
 */
public class Data {
    String status;
    String source;
    String sortBy;
    List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
        return status;
    }
}
