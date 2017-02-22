package com.rmn.ews.menuItem.selectsource;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmn.ews.R;
import com.rmn.ews.data.DBContract.SourceTable;
import com.rmn.ews.utiles.Sources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmn on 29-08-2016.
 */
public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.Holder> {

    List<String> mURLList;
    List<Integer> mIconList;
    List<String> mNameList;
    Context mContext;

    public SourceAdapter(Context context){
        mContext=context;
        mIconList=new ArrayList<>();
        mNameList=new ArrayList<>();
        mURLList=new ArrayList<>();

        mURLList=Sources.srcURLList;
        mIconList= Sources.srcIconList;
        mNameList=Sources.srcNameList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.source_row_layout,null);
        Holder holder=new Holder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        holder.iconView.setImageResource(mIconList.get(position));
        holder.nameView.setText(mNameList.get(position));

       Cursor cursor= mContext.getContentResolver().query(SourceTable.CONTENT_URI,null,SourceTable.COLUMN_ID+" =? "
                ,new String[] {Integer.toString(mIconList.get(position))},null);

        if (cursor.getCount()>0){
            holder.statusView.setVisibility(View.VISIBLE);
        }else {
            holder.statusView.setVisibility(View.INVISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.statusView.getVisibility()==View.VISIBLE){
                    holder.statusView.setVisibility(View.INVISIBLE);
                    holder.token=-1;
                    mContext.getContentResolver().delete(SourceTable.CONTENT_URI,SourceTable.COLUMN_ID+"=?",new String[]{Integer.toString(mIconList.get(position))});
                }else {
                    holder.statusView.setVisibility(View.VISIBLE);
                    holder.token=1;
                    ContentValues cv=new ContentValues();
                    cv.put(SourceTable.COLUMN_ID,mIconList.get(position));
                    cv.put(SourceTable.COLUMN_SRC_NAME,mNameList.get(position));
                    cv.put(SourceTable.COLUMN_SRC_URL_PRAM,mURLList.get(position));
                    mContext.getContentResolver().insert(SourceTable.CONTENT_URI,cv);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNameList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        int token=-1;
        View mView;
        ImageView iconView,statusView;
        TextView nameView;
        public Holder(View itemView) {
            super(itemView);
            mView=itemView;
            iconView=(ImageView)itemView.findViewById(R.id.icon);
            statusView=(ImageView)itemView.findViewById(R.id.status);
            nameView=(TextView) itemView.findViewById(R.id.name);
        }
    }
}
