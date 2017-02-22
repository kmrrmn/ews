package com.rmn.ews.menuItem.home;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rmn.ews.R;
import com.rmn.ews.data.DBContract.*;
import com.rmn.ews.model.Article;
import com.rmn.ews.model.ArticleWithBitmap;
import com.rmn.ews.model.Data;
import com.rmn.ews.services.NewsService;
import com.rmn.ews.services.ServiceInstance;
import com.rmn.ews.utiles.Sources;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    RelativeLayout mMsgView;
    RecyclerView mRecyclerView;
    Adapter mAdapter;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mMsgView = (RelativeLayout) view.findViewById(R.id.msg);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mAdapter = new Adapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        if (isNetworkAvailable()) {
            Log.e("GetOnlineData();", "callled");
            GetOnlineData();
        } else {
            Log.e("GetOfflineData", "called");
            GetOfflineData();
        }

        return view;
    }


    public void GetOnlineData() {

        NewsService service = ServiceInstance.createRetrofitService(NewsService.class, NewsService.SERVICE_ENDPOINT);
        Cursor cursor = getActivity().getContentResolver().query(SourceTable.CONTENT_URI, new String[]{SourceTable.COLUMN_SRC_URL_PRAM}, null, null, null);
        Log.e("Cursor count ", cursor.getCount() + "  COUNT");
        if (cursor != null && cursor.getCount() != 0) {
             mMsgView.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                String src = cursor.getString(0);
                Log.e("SOURCE  ", src);
                if (src != "")
                    service.getUser(src, "top")
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Data>() {
                                @Override
                                public void onCompleted() {
                                    Log.e("onCompleted", "called");
                                }
                                @Override
                                public void onError(Throwable e) {
                                    Log.e("onError", "called" + e.getMessage());
                                }
                                @Override
                                public void onNext(Data data) {
                                    mAdapter.setData(data.getArticles());
                                    Log.e("onCompleted", "called");
                                    Log.e("onCompleted", "Article ---------------------------------------------  size" + data.getArticles().size());
                                    Log.e("DATA ", data.getArticles().get(0).getTitle());
                                }
                            });
            }

           }

    }

    public void GetOfflineData() {
        Cursor cursor = getActivity().getContentResolver().query(OfflineData.CONTENT_URI, null, null, null, null);
        Log.e("Cursor count ", cursor.getCount() + "  COUNT");
        if (cursor != null && cursor.getCount() != 0) {
            Log.e("Cursor count ", cursor.getCount() + "");
            mMsgView.setVisibility(View.GONE);
            while (cursor.moveToNext()) {

                ArticleWithBitmap article = new ArticleWithBitmap();
                article.setTitle(cursor.getString(cursor.getColumnIndex(OfflineData.COLUMN_TITLE)));
                article.setDescription(cursor.getString(cursor.getColumnIndex(OfflineData.COLUMN_DESCRIPTION)));
                Log.e("titlr",article.getTitle()+" d");
                Log.e("desc",article.getDescription()+" d");
                byte[] image = cursor.getBlob(cursor.getColumnIndex(OfflineData.COLUMN_IMAGE));
                if (image!=null)
                article.setBitmap(image);
                  article.setUrlToImage(cursor.getString(cursor.getColumnIndex(OfflineData.COLUMN_IMAGE_URL)));
                 mAdapter.setArticleBitmap(article);
            }
            getActivity().startManagingCursor(cursor);
            cursor.close();

        } else {
            mMsgView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
