package com.kitsyambochcka.loginpage;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import uk.co.senab.photoview.PhotoView;
import android.view.ViewGroup.LayoutParams;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Developer on 10.09.2016.
 */
public class ImagePagerAdapter extends PagerAdapter {


    private ArrayList<String> uriPhoto;
    public ImagePagerAdapter(ArrayList<String> uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    @Override
    public int getCount() {
        return uriPhoto.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Picasso.with(container.getContext())
                .load(uriPhoto.get(position))
                .into(photoView);

        // Now just add PhotoView to ViewPager and return it
        photoView.setAdjustViewBounds(true);
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}