package com.rmn.ews.services;

import retrofit.RestAdapter;

/**
 * Created by rmn on 28-08-2016.
 */
public class ServiceInstance {

    public  static <T> T createRetrofitService(final Class<T> tClass,final String endpoint){
        final RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();
        T service=restAdapter.create(tClass);
        return service;
    }
}
