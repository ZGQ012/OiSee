package com.example.administrator.oisee.bean;

import android.content.Context;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class Resulh {
    public void setPtrFrameAttribute(final PtrFrameLayout ptr_frame, Context context) {
        // 头部阻尼系数
        ptr_frame.setResistanceHeader(1.7f);
        // 底部阻尼系数
        ptr_frame.setResistanceFooter(1.7f);
        // 默认1.2f，移动达到头部高度1.2倍时触发刷新操作
        ptr_frame.setRatioOfHeaderHeightToRefresh(1.2f);
        // 头部回弹时间
        ptr_frame.setDurationToCloseHeader(2000);
        // 底部回弹时间
        ptr_frame.setDurationToCloseFooter(2000);
        // 释放刷新
        ptr_frame.setPullToRefresh(false);
        // 释放时恢复到刷新状态的时间
        ptr_frame.setDurationToBackHeader(2000);
        ptr_frame.setDurationToBackFooter(2000);

        // Matrial风格头部的实现
        final MaterialHeader header = new MaterialHeader(context);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr_frame.setHeaderView(header);
        ptr_frame.addPtrUIHandler(header);

        // 经典的底部布局实现
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(context);
        footer.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr_frame.setFooterView(footer);
        ptr_frame.addPtrUIHandler(footer);

        // 设置支持刷新和加载更多 可以任意开启或者关闭某一个特性（开关）
        ptr_frame.setMode(PtrFrameLayout.Mode.BOTH);

        // 进入界面自动刷新
        ptr_frame.post(new Runnable() {
            @Override
            public void run() {
                // 进入界面自动刷新
                ptr_frame.autoRefresh();
            }
        });

        /**
         * 不用判断什么时候刷新和加载，方法里有自己的判断，适合大多数的情况
         */
        ptr_frame.setPtrHandler(new PtrDefaultHandler2() {
            // 加载更多开始会执行该方法
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                // 这里做一些加载的操作
                LoadMore();

                // 用于关闭上拉加载
                ptr_frame.refreshComplete();
            }

            // 刷新开始会执行该方法
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 这里做一些刷新操作
                Refresh();

                // 用于关闭下拉刷新
                ptr_frame.refreshComplete();
            }
        });

    }

    public void LoadMore() {

    }
    public void Refresh() {

    }
}
