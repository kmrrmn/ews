package com.rmn.ews.menuItem.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmn.ews.R;
import com.rmn.ews.detail.ItemDetailActivity;
import com.rmn.ews.model.Article;
import com.rmn.ews.model.ArticleWithBitmap;
import com.rmn.ews.model.ArticleWithDrawable;
import com.rmn.ews.utiles.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmn on 28-08-2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    ArrayList<byte[]> mByteList;
    ArrayList<Article> mArticles;
    ArrayList<Article> mDataLists;
    ArrayList<ArticleWithBitmap> mArticleBitmapLists;
    Context mContext;
    ArrayList<ArticleWithDrawable> mArticleDraw;

    Adapter(Context context) {
        mContext = context;

        Log.e("Adapter", "called");
        mDataLists = new ArrayList<>();
        mArticleBitmapLists = new ArrayList<>();
        mArticleDraw = new ArrayList<>();
        mArticles = new ArrayList<Article>();
        mByteList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_head_row, null);
        Holder holder = new Holder(view);

        Log.e("onCreateviewholder", "called");
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        if (mArticleBitmapLists != null && mArticleBitmapLists.size() != 0) {
            BindOfflineData(holder, position);
        } else {
            bindDataList(holder, position);
        }


    }

    @Override
    public int getItemCount() {
        if (mDataLists != null && mDataLists.size() != 0) {
            Log.e("getItemCount", "called" + mDataLists.size());
            return mDataLists.size();
        } else if (mArticleBitmapLists != null && mArticleBitmapLists.size() != 0) {
            return mArticleBitmapLists.size();
        }
        return 0;
    }

    public void setData(List<Article> articles) {
        mDataLists.addAll(articles);
        for (Article article : articles) {
            ArticleWithDrawable withDrawable = new ArticleWithDrawable();
            withDrawable.setArticleData(article);
            withDrawable.setDrawable(null);
            mArticleDraw.add(withDrawable);
            notifyDataSetChanged();
        }
    }


    public void setArticleBitmap(ArticleWithBitmap articleBitmap) {
        mArticleBitmapLists.add(articleBitmap);
        Article article = articleBitmap.getArticle();
        mArticles.add(article);
        mByteList.add(articleBitmap.getBitmap());

        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView headView, srcView;
        ImageView imageView;
        View mView;
        Bitmap bitmap;
        Drawable drawable;

        public Holder(View itemView) {
            super(itemView);

            mView = itemView;
            headView = (TextView) itemView.findViewById(R.id.head);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public void bindDataList(Adapter.Holder holder, final int position) {
        ArticleWithDrawable data = mArticleDraw.get(position);
        Picasso picasso = Picasso.with(mContext);

        if (holder.drawable == null) {
            picasso.load(data.getUrlToImage())
                    .into(holder.imageView);
            holder.drawable = holder.imageView.getDrawable();
            mArticleDraw.get(position).setDrawable(holder.imageView.getDrawable());

            Log.e("IF----  ___", "IN");
        } else {
            Log.e("ELSE  ___", "IN");
            //holder.itemView.setImageBitmap(holder.bitmap);
            holder.imageView.setImageDrawable(holder.drawable);
        }

        holder.headView.setText(data.getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("art", mArticleDraw);
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra(Constants.EXTRA_POSITION, position);
                intent.putExtra(Constants.EXTRA_ARTICLE_LIST, bundle);

                mContext.startActivity(intent);

            }
        });
    }

    public void BindOfflineData(Holder holder, final int pos) {
        ArticleWithBitmap articleWithBitmap = mArticleBitmapLists.get(pos);
        holder.headView.setText(articleWithBitmap.getTitle());
        Log.e("ADAPTER ", "BITMAO+P :" + articleWithBitmap.getBitmap());
        if (articleWithBitmap.getBitmap() == null) {
            Picasso.with(mContext).load(articleWithBitmap.getUrlToImage()).into(holder.imageView);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(articleWithBitmap.getBitmap(), 0, articleWithBitmap.getBitmap().length);
            holder.imageView.setImageBitmap(bitmap);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                if (mArticleBitmapLists != null) {
                    bundle.putSerializable("bit", mArticleDraw);
                    bundle.putSerializable("byte", mByteList);

                    Intent intent = new Intent(mContext, ItemDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_POSITION, pos);
                    intent.putExtra(Constants.EXTRA_ARTICLE_BITMAP_LIST, mArticles.get(pos));
                    intent.putExtra(Constants.EXTRA_ARTICLE_BITMAP_LIST+"a", mByteList.get(pos));

                    Log.e("hfhgf", "hgjhfj");
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
