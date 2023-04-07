package com.group05.emarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.group05.emarket.R;
import com.group05.emarket.models.BannerItem;

import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {

    private List<BannerItem> mData;
    private LayoutInflater mInflater;

    public BannerPagerAdapter(Context context, List<BannerItem> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mInflater.inflate(R.layout.banner_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.banner_image);

        BannerItem item = mData.get(position);
        imageView.setImageResource(item.getImageResId());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
