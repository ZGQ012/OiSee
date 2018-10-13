package com.example.administrator.oisee.PtrF;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class FixScrollerRecyclerView extends RecyclerView{

    private int lastXIntercepted, lastYIntercepted;

    public FixScrollerRecyclerView(Context context) {
        super(context);
    }

    public FixScrollerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixScrollerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = x - lastXIntercepted;
                final int deltaY = y - lastYIntercepted;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastXIntercepted = x;
        lastYIntercepted = y;
        return super.dispatchTouchEvent(ev);
    }
}
