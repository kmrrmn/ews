package com.rmn.ews.menuItem.selectsource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmn.ews.R;
import com.rmn.ews.menuItem.home.Adapter;
import com.rmn.ews.utiles.Sources;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
  * to handle interaction events.
 * Use the {@link NewsSourceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsSourceFragment extends Fragment {


    RecyclerView mRecyclerView;
    SourceAdapter mAdapter;

    public static NewsSourceFragment newInstance(String param1, String param2) {
        NewsSourceFragment fragment = new NewsSourceFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_source_fragmnt, container, false);


        mRecyclerView=(RecyclerView)view.findViewById(R.id.recyle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter=new SourceAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
