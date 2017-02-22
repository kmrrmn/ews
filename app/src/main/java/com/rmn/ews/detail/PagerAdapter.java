package com.rmn.ews.detail;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.rmn.ews.data.DBContract.OfflineData;
import com.rmn.ews.model.Article;
import com.rmn.ews.model.ArticleWithBitmap;
import com.rmn.ews.model.ArticleWithDrawable;

import java.util.ArrayList;

/**
 * Created by rmn on 28-08-2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int i = 0;
    int mPosition;
    ArrayList<ArticleWithDrawable> mArticles;
    ArrayList<ArticleWithBitmap> mBitmapArticles;
    Context mContext;
    public PagerAdapter(FragmentManager fm, Context context, int pos) {
        super(fm);
        Log.e("PagerAdapter", "called");
        mArticles = new ArrayList<ArticleWithDrawable>();
        mBitmapArticles = new ArrayList<ArticleWithBitmap>();
        mPosition = pos;
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        PagerFragment fragment;
        if (mArticles != null && mArticles.size()!=0)
            fragment = PagerFragment.newInstance(mArticles.get(position),null);
        else
            fragment = PagerFragment.newInstance(null,mBitmapArticles.get(position));
        Log.e("getItem ", position + "");

        return fragment;
    }

    @Override
    public int getCount() {
        if (mArticles != null && mArticles.size() != 0)
            return mArticles.size();
        else if (mBitmapArticles != null && mBitmapArticles.size() != 0)
            return mBitmapArticles.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("instantiateItem ", "called " + position);
        if (mPosition != -1 && i == 0) {
            position = mPosition;
            i = 1;
        }
        return super.instantiateItem(container, position);
    }

    public void setData(ArrayList<ArticleWithDrawable> articles) {
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }

    public void setBitmapData(Article article,byte[] bytes) {

        ArticleWithBitmap articleWithBitmap=new ArticleWithBitmap();
        articleWithBitmap.setBitmap(bytes);
        articleWithBitmap.setTitle(article.getTitle());
        articleWithBitmap.setDescription(article.getDescription());
        articleWithBitmap.setUrlToImage(article.getUrlToImage());
        mBitmapArticles.add(articleWithBitmap);
        Cursor cursor=mContext.getContentResolver().query(OfflineData.CONTENT_URI,null,OfflineData.COLUMN_TITLE+" != ?",new String[] {article.getTitle()},null);
        if (cursor!=null & cursor.getCount()!=0){

            while (cursor.moveToNext()){

                ArticleWithBitmap withBitmap=new ArticleWithBitmap();
                withBitmap.setTitle(cursor.getString(cursor.getColumnIndex(OfflineData.COLUMN_TITLE)));
                withBitmap.setDescription(cursor.getString(cursor.getColumnIndex(OfflineData.COLUMN_DESCRIPTION)));
                withBitmap.setUrlToImage(cursor.getString(cursor.getColumnIndex(OfflineData.COLUMN_IMAGE_URL)));
                withBitmap.setBitmap(cursor.getBlob(cursor.getColumnIndex(OfflineData.COLUMN_IMAGE)));
                    mBitmapArticles.add(withBitmap);
                notifyDataSetChanged();
            }
        }

    }
}
