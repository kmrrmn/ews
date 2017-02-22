package com.rmn.ews.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by rmn on 31-08-2016.
 */
public class ArticleWithBitmap  implements Serializable{

    byte[] bitmap;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;
    String article;

    public ArticleWithBitmap(){}

  public Article getArticle(){

      Article article=new Article();
      article.setTitle(title);
      article.setDescription(description);
      article.setUrlToImage(urlToImage);

      return article;
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

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setArticle(String article) {
        this.article = article;
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

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getBitmap() {
        return bitmap;
    }


}
