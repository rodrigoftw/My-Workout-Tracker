package com.rodrigoftw.myworkouttracker.myworkouttracker.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 11/05/2017.
 */

public class HelpAdapter extends PagerAdapter {

    private LayoutInflater mInflater;
    private ArrayList<String> mData;
    private Context ctx;

    public HelpAdapter(Context ctx, ArrayList<String> mData) {
        this.ctx = ctx;
        this.mData = mData;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager pager = (ViewPager) container;
        View view = mInflater.inflate(R.layout.help_image_item, null);

        Glide.with(ctx)
                .load(mData.get(position))
                .centerCrop()
                .into((ImageView) view.findViewById(R.id.imageHelp));

        pager.addView(view);

        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}