package com.yliec.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yliec.customview.R;
import com.yliec.customview.util.MeasureUtil;

/**
 * Created by Lecion on 3/12/15.
 */
public class CircleView extends View implements Runnable {
    private Paint mPaint;
    private Paint mPaint2;
    private int radius = 50;
    private int radius2 = 60;
    private boolean mFlag = true;
    private final String TAG = "View";
    private int lastY;

    private Bitmap bitmap;
    private int x;
    private int y;

    public CircleView(Context context) {
        super(context);

        initPaint();
        initRes(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initRes(context);
    }

    void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint2.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(10);
        mPaint2.setStrokeWidth(10);

        mPaint.setColor(Color.rgb(211, 211, 211));
        mPaint2.setColor(Color.rgb(211, 211, 211));
    }

    @Override
    protected void onDraw(Canvas canvas) {



//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
//        canvas.drawCircle(getWidth() / 2, getHeight   () / 2, radius2, mPaint2);
//        lightingColorFilter();
//        porterDuffFilter();
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        invalidate();
    }


    void porterDuffXfermode() {

    }

    void porterDuffFilter() {
        /**
         * 图像色彩混合
         */
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(0xFFFF0000, PorterDuff.Mode.DARKEN);
        mPaint.setColorFilter(porterDuffColorFilter);
    }

    void lightingColorFilter() {
        mPaint.setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x00FFFF00));
    }

    void colorMatrixFilter() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[] {
                0.5f, 0, 0, 0, 0,
                0, 0.5f, 0, 0, 0,
                0, 0, 0.5f, 0, 0,
                0, 0, 0, 0.5f, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    void initRes(Context ctx) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.test, options);

        Log.d("initRes", options.outWidth +  " " + options.outHeight );
        //原图宽
        float srcWidth = options.outWidth;
        //原图高
        float srcHeight = options.outHeight;
        //设置压缩比
        options.inSampleSize = 5;
        //设置为false
        options.inJustDecodeBounds = false;
        //开始载入内存
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
        x = (MeasureUtil.getScreenWidthAndHeight(ctx)[0] - bitmap.getWidth()) / 2;
        y = (MeasureUtil.getScreenWidthAndHeight(ctx)[1] - bitmap.getHeight()) / 2;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void stop() {
        radius = 0;
        postInvalidate();
        this.mFlag = false;
    }

    public void start() {
        this.mFlag = true;
    }

    @Override
    public void run() {
        boolean flag = true;
        boolean flag2 = true;
        while (mFlag) {
            if (flag) {

                if (radius >= 50) {
                    radius+=5;
                    mPaint.setColor(Color.rgb(255, 0, 0));
                } else {
                    mPaint.setColor(Color.rgb(0, 255, 0));
                    radius++;
                }
            } else {
                if (radius >= 50) {
                    mPaint.setColor(Color.rgb(0, 0, 255));
                    radius-=5;
                } else {
                    mPaint.setColor(Color.rgb(211, 211, 211));
                    radius--;
                }
            }
            if (radius == 0 || radius == 150) {
                flag = !flag;
            }

            if (flag2) {

                if (radius2 >= 50) {
                    mPaint2.setColor(Color.rgb(0, 255, 0));
                    radius2++;
                } else {
                    mPaint2.setColor(Color.rgb(0, 0, 255));
                    radius2+=5;
                }
            } else {
                if (radius2 >= 50) {
                    mPaint2.setColor(Color.rgb(211, 211, 211));
                    radius2--;
                } else {
                    mPaint2.setColor(Color.rgb(255, 0, 0));
                    radius2-=5;
                }
            }
            if (radius2 <= 0 || radius2 >= 150) {
                flag2 = !flag2;
            }

            try {
                Thread.sleep(15);
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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


        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getY();
                Log.d(TAG, " onTouchEvent => DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, " onTouchEvent => UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, " onTouchEvent => MOVE + lastY: " + lastY);

                int y = (int) event.getY();
                int deltaY = y - lastY;
                lastY = y;
//                this.setVisibility(INVISIBLE);
                break;

        }
        return super.onTouchEvent(event);
    }


}
