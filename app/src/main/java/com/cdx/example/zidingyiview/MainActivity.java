package com.cdx.example.zidingyiview;

import android.view.View;

import com.cdx.example.zidingyiview.activity.ScrollerMainActivity;

import java.util.ArrayList;

import apis.amapv2.com.listviewlibrary.activity.BaseListActivty;
import apis.amapv2.com.listviewlibrary.bean.ItemObject;

public class MainActivity extends BaseListActivty{

    @Override
    protected void initData() {
        mTvTitle.setText("自定义控件的知识");
        mTvTitle.setVisibility(View.VISIBLE);

        ArrayList<ItemObject> data = new ArrayList<>();
        data.add(new ItemObject("自定义View中Scroller的知识",ScrollerMainActivity.class));
        mMyListView.setData(data);
    }
}
