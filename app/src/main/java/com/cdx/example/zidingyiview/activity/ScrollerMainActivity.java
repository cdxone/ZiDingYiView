package com.cdx.example.zidingyiview.activity;

import android.view.View;

import java.util.ArrayList;

import apis.amapv2.com.listviewlibrary.activity.BaseListActivty;
import apis.amapv2.com.listviewlibrary.bean.ItemObject;

public class ScrollerMainActivity extends BaseListActivty {
    @Override
    protected void initData() {
        mTvTitle.setText("Scroller的相关知识");
        mTvTitle.setVisibility(View.VISIBLE);

        ArrayList<ItemObject> data = new ArrayList<>();
        data.add(new ItemObject("Scroller实现手指滑动-离开,然后实现继续滑动效果",ScrollerTest1Activity.class));
        mMyListView.setData(data);
    }
}
