package com.rmn.ews.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by rmn on 29-08-2016.
 */
public class ArticleWithDrawable implements Serializable {
     Drawable drawable;

    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;

    public void setArticleData(Article article){
        this.author=article.getAuthor();
        this.title=article.getTitle();
        this.description=article.getDescription();
        this.urlToImage=article.getUrlToImage();
        this.publishedAt=article.getPublishedAt();
        this.url=article.getUrl();
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public ArticleWithDrawable() {
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
