package com.example.administrator.oisee.PtrF;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class FixScrollerPtrFrameLayout extends PtrFrameLayout{
    private MaterialHeader mPtrEHeader;
    private RecyclerView recyclerView;

    public FixScrollerPtrFrameLayout(Context context) {
        super(context);
        initView();
    }

    public FixScrollerPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FixScrollerPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(getContext());
        recyclerView = new RecyclerView(getContext());
        mPtrEHeader = new MaterialHeader(getContext());//下拉刷新头部
        addPtrUIHandler(mPtrEHeader);
        setHeaderView(mPtrEHeader);

    }

    private boolean disallowInterceptTouchEvent = false;
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        disallowInterceptTouchEvent = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
                this.requestDisallowInterceptTouchEvent(false);
                disableWhenHorizontalMove(true);
                break;
        }
        if (disallowInterceptTouchEvent) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }
}