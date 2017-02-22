package com.rmn.ews.menuItem.setting;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.rmn.ews.R;
import com.rmn.ews.utiles.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    RelativeLayout permissionView,timeEnterView;
    CheckBox mCheck;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        permissionView=(RelativeLayout)view.findViewById(R.id.per_view);
        timeEnterView=(RelativeLayout)view.findViewById(R.id.time_enter);
        mCheck=(CheckBox)view.findViewById(R.id.check);
        mCheck.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        SharedPreferences preferences=getActivity().getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE);

        switch (view.getId()){

            case R.id.check :
                if (mCheck.isChecked()){

                }
                break;
        }
    }
}
