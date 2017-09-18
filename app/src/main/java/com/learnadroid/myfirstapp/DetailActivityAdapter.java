package com.learnadroid.myfirstapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DetailActivityAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.nvb,R.drawable.tl,R.drawable.dis};
    private Context context;
    private LayoutInflater layoutInflater;

    public DetailActivityAdapter(Context context)
    {
        this.context=context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.content_detail,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.detail_product_image_view);
        imageView.setImageResource(image_resources[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
