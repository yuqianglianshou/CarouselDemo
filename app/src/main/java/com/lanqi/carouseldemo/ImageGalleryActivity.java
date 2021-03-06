package com.lanqi.carouseldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lanqi.carouseldemo.photoview.PhotoViewAdapter;

public class ImageGalleryActivity extends FragmentActivity {

    private int position;
    private int[] imageViewId; //图片列表
    private TextView headTitle;
    private Button headBackBtn;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_gallery);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        imageViewId = intent.getIntArrayExtra("imageViewId");
        if (imageViewId == null) {
            imageViewId = new int[]{};
        }
        initView();
        initViewEvent();
        initGalleryViewPager();
    }

    private void initView() {
        headTitle = (TextView) findViewById(R.id.textHeadTitle);
        headTitle.setText("1/" + imageViewId.length);
        headBackBtn = (Button) findViewById(R.id.btnBack);
        headBackBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViewEvent() {
        headBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initGalleryViewPager() {
        PhotoViewAdapter pagerAdapter = new PhotoViewAdapter(this, imageViewId);
        pagerAdapter.setOnItemChangeListener(new PhotoViewAdapter.OnItemChangeListener() {
            int len = imageViewId.length;

            @Override
            public void onItemChange(int currentPosition) {
                headTitle.setText((currentPosition + 1) + "/" + len);
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(position);
    }

}
