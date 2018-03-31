package com.lanqi.carouseldemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 轮播图图片来源  写死到 mipmap
 */

public class MainActivity extends FragmentActivity {
    private static final String TAG = "lq";
    private Activity mActivity;
    //图片列表，需要几张放几张就可以了
    private int[] imageViewId = new int[]{R.mipmap.imggirl1, R.mipmap.imggirl2,
            R.mipmap.imggirl3,R.mipmap.imggirl4};
    private GalleryPagerAdapter galleryAdapter;
    private AutoLoopViewPager mPager;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initView();

    }

    private void initView() {
        mPager = findViewById(R.id.pager);
        mIndicator = findViewById(R.id.indicator);

        galleryAdapter = new GalleryPagerAdapter();
        mPager.setAdapter(galleryAdapter);
        mIndicator.setViewPager(mPager);
        mIndicator.setPadding(5, 5, 10, 5);
    }

    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView item = new ImageView(mActivity);
            item.setImageResource(imageViewId[position]);
            Log.i(TAG, "instantiateItem: next img position == " + position);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ImageGalleryActivity.class);
                    //将图片 id 传递到2级页面。
                    intent.putExtra("imageViewId", imageViewId);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //开启播放
        mPager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止播放
        mPager.stopAutoScroll();
    }


}
