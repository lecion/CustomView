package com.yliec.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.yliec.customview.R;

/**
 * Created by Lecion on 3/12/15.
 */
public class MyViewGroup extends LinearLayout {
    private String TAG = "ViewGroup";

    private int lastY;
    private int lastX;
    private int startY;

    private Scroller mScroller = null;

    public MyViewGroup(Context context) {
        super(context);
        init(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " dispatchTouchEvent => DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG," dispatchTouchEvent => UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, " dispatchTouchEvent => MOVE");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " onTouchEvent => DOWN");
                lastY = (int) event.getY();
                startY = (int) event.getY();
//                lastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, " onTouchEvent => UP");
//                mScroller.startScroll(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), 0, 2000);
//                postInvalidate();
//                setPadding(getPaddingLeft(), 0, getPaddingRight(), getPaddingBottom());
                scrollUp();
                startY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getY();
                int deltaY = y - lastY;
                lastY = y;
                if (deltaY > 0) {
//                    setPadding(getPaddingLeft(), getPaddingTop() + deltaY, getPaddingRight(), getPaddingBottom());
//                    getChildAt(0).setVisibility(VISIBLE);
                    scrollBy(0, -deltaY);
                } else {
                    findViewById(R.id.cv_mine).onTouchEvent(event);
                }

                Log.d(TAG, " onTouchEvent => MOVE");
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    private void scrollUp() {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), 1000);
        postInvalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " onInterceptTouchEvent => DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, " onInterceptTouchEvent => UP");
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, " onInterceptTouchEvent => MOVE");
                break;
        }
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        Log.d("computeScroll", mScroller.getCurrX() + " " + mScroller.getCurrY());
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}
