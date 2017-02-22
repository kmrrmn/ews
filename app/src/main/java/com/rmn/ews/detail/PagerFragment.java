package com.rmn.ews.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmn.ews.HomeActivity;
import com.rmn.ews.R;
import com.rmn.ews.model.Article;
import com.rmn.ews.model.ArticleWithBitmap;
import com.rmn.ews.model.ArticleWithDrawable;
import com.rmn.ews.utiles.Constants;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PagerFragment extends Fragment {

    ImageView imageView;
    TextView titleView, descView;

    public PagerFragment() {
        // Required empty public constructor
    }

    ArticleWithDrawable mArticle;
    ArticleWithBitmap mBitmapArticle;

    public static PagerFragment newInstance(ArticleWithDrawable article, ArticleWithBitmap articleWithBitmap) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARTICLE, article);
        args.putSerializable(Constants.EXTRA_ARTICLE_BITMAP_LIST, articleWithBitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = (ArticleWithDrawable) getArguments().getSerializable(Constants.ARTICLE);
            mBitmapArticle = (ArticleWithBitmap) getArguments().getSerializable(Constants.EXTRA_ARTICLE_BITMAP_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool);

        titleView = (TextView) view.findViewById(R.id.title);
        descView = (TextView) view.findViewById(R.id.desc);
        imageView = (ImageView) view.findViewById(R.id.img);

        if (mArticle != null) {
            loadArticleData();
        } else if (mBitmapArticle != null) {
            loadBitmapArticleData();
        }

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(), HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadArticleData() {

        titleView.setText(mArticle.getTitle());
        descView.setText(mArticle.getDescription());

        if (mArticle.getDrawable() == null) {
            Picasso.with(getActivity())
                    .load(mArticle.getUrlToImage())
                    .into(imageView);
            mArticle.setDrawable(imageView.getDrawable());
        } else {
            imageView.setImageDrawable(mArticle.getDrawable());
        }
    }

    public void loadBitmapArticleData() {

        if (mBitmapArticle != null) {
            titleView.setText(mBitmapArticle.getTitle());
            descView.setText(mBitmapArticle.getDescription());
            if (mBitmapArticle.getBitmap() == null) {
                Picasso.with(getActivity()).load(mBitmapArticle.getUrlToImage()).into(imageView);

            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(mBitmapArticle.getBitmap(), 0, mBitmapArticle.getBitmap().length);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
