package com.rmn.ews.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.rmn.ews.HomeActivity;
import com.rmn.ews.R;
import com.rmn.ews.model.Article;
import com.rmn.ews.model.ArticleWithBitmap;
import com.rmn.ews.model.ArticleWithDrawable;
import com.rmn.ews.utiles.Constants;

import java.util.ArrayList;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link HomeActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Show the Up button in the action bar.

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int position = 0;
        ArrayList<ArticleWithDrawable> mArticleList = new ArrayList<ArticleWithDrawable>();
        ArrayList<ArticleWithBitmap> mArticleBitmapList = new ArrayList<ArticleWithBitmap>();
        Intent intent = getIntent();

        Bundle bundleArt = new Bundle();
        Bundle bundleoff = new Bundle();

         bundleArt = intent.getBundleExtra(Constants.EXTRA_ARTICLE_LIST);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),this, position);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new DepthPagerTransformation());

        Log.e("POSITION ", position + "    ");

        if (bundleArt != null) {
            adapter.setData((ArrayList<ArticleWithDrawable>) bundleArt.get("art"));
        } else {
            Article article= (Article) intent.getSerializableExtra(Constants.EXTRA_ARTICLE_BITMAP_LIST);
            adapter.setBitmapData(article, (byte[]) intent.getSerializableExtra(Constants.EXTRA_ARTICLE_BITMAP_LIST+"a"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, HomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
