//package com.rmn.ews.model;
//
//import android.content.ClipData;
//import android.os.Bundle;
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.util.List;
//
///**
// * Created by rmn on 28-08-2016.
// */
//public class ParcelableArticle implements Parcelable {
//
//    List<Article> articles;
//    String parceableAticle;
//
//    protected ParcelableArticle(Parcel in) {
//        articles = in.createTypedArrayList(Article);
//        parceableAticle = in.readString();
//    }
//
//    public static final Creator<ParcelableArticle> CREATOR = new Creator<ParcelableArticle>() {
//        @Override
//        public ParcelableArticle createFromParcel(Parcel in) {
//            return new ParcelableArticle(in);
//        }
//
//        @Override
//        public ParcelableArticle[] newArray(int size) {
//            return new ParcelableArticle[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeTypedList(articles);
//        parcel.writeString(parceableAticle);
//    }
//
//
// public static final Parcelable.Creator<ParcelableArticle> craetor=new Parcelable.Creator<Article>(){
//
//        @Override
//        public Article createFromParcel(Parcel parcel) {
//
//            ParcelableArticle article = new ParcelableArticle();
//            article.parceableAticle = parcel.readString();
//            Bundle b = parcel.readBundle(Article.class.getClassLoader());
//            article.articles = b.getParcelableArrayList("items");
//
//            return article;
//        }
//
//        @Override
//        public Article[] newArray(int i) {
//            return new Article[i];
//        }
//    };
//}
