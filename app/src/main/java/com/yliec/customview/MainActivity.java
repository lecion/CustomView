package com.yliec.customview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yliec.customview.view.CircleView;
import com.yliec.customview.view.MyViewGroup;


public class MainActivity extends ActionBarActivity {
    private CircleView cvMine;
    private final String TAG = "Activity";
    private MyViewGroup mvg;
    int mHeight;

    private TextView tv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        cvMine = (CircleView) findViewById(R.id.cv_mine);
        mvg = (MyViewGroup) findViewById(R.id.mvg);
//        new Thread(cvMine).start();
//        mvg.setPadding(mvg.getPaddingLeft(), -MeasureUtil.dp2px(this, 98), mvg.getPaddingRight(), mvg.getPaddingBottom());

        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((View)tv.getParent()).scrollBy(10, 0);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " dispatchTouchEvent => DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, " dispatchTouchEvent => UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, " dispatchTouchEvent => MOVE");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " onTouchEvent => DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, " onTouchEvent => UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, " onTouchEvent => MOVE");
                break;
        }
        return super.onTouchEvent(event);
    }
}
