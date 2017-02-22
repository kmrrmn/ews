package com.rmn.ews.detail;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by rmn on 28-08-2016.
 */
public class DepthPagerTransformation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

        int pageWidth=page.getWidth();

        if (position < -1){
            page.setAlpha(0);
            Log.e("position<=-1","CALLED");
        }
        else if (position<=0){
            page.setAlpha(1);
            Log.e("position<=0","CALLED");
            page.setTranslationX(pageWidth*position);
            page.setScaleX(1);
            page.setScaleY(1);
        }else if (position<=1){
            page.setAlpha(1-position);
            Log.e("position<=1","CALLED");
            page.setTranslationX(pageWidth*-position);
            float scaleFactor=(float) (0.75+(1-0.75)*(1-Math.abs(position)));
            page.setScaleX(scaleFactor);
            page.setScaleX(scaleFactor);
        }else {
            Log.e("position<=else","CALLED");
            page.setAlpha(0);
        }
    }

}
