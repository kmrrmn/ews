package com.rmn.ews.services;

import com.rmn.ews.model.Data;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rmn on 28-08-2016.
 */
public interface NewsService {
    String SERVICE_ENDPOINT="https://newsapi.org/v1";

    // https://newsapi.org/v1/articles?source=google-news&sortBy=top
    // &apiKey=1de365accb034bf1b007e7c3221be27b

    @GET("/articles?apiKey=1de365accb034bf1b007e7c3221be27b")
    Observable<Data> getUser(@Query("source") String src, @Query("sortBy") String sort) ;
}
