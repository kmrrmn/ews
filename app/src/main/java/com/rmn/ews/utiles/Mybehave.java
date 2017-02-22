package com.rmn.ews.utiles;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by rmn on 01-09-2016.
 */
public class Mybehave extends CoordinatorLayout.Behavior<Toolbar> {

    public void onDependentViewRemoved(CoordinatorLayout parent, Toolbar child, View dependency) {

        super.onDependentViewRemoved(parent, child, dependency);

    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency instanceof FloatingActionButton;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {
        float y = dependency.getTranslationY() - dependency.getWidth();
        Log.e("yyyyyyyyyy","--------------------"+y);
        child.setTranslationY(y);

        return true;
    }
}
