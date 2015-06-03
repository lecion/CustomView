package com.yliec.customview.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Lecion on 3/12/15.
 */
public class MeasureUtil {
    public static int[] getScreenWidthAndHeight(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return new int[] {metrics.widthPixels, metrics.heightPixels};
    }

    public static int dp2px(Context ctx, int dp) {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }
}
