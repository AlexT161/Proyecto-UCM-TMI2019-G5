package com.ucm.proyecto_ucm_tmi2019_g5.Util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver;

public class ScreenshotUtil {
    private static ScreenshotUtil mInstance;

    private ScreenshotUtil() {
    }

    public static ScreenshotUtil getInstance() {
        if (mInstance == null) {
            synchronized (ScreenshotUtil.class) {
                if (mInstance == null) {
                    mInstance = new ScreenshotUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * Measures and takes a screenshot of the provided {@link View}.
     *
     * @param view The view of which the screenshot is taken
     * @return A {@link Bitmap} for the taken screenshot.
     */
    public Bitmap takeScreenshotForView(View view) {
        System.out.println("Valore view: " + view);
        System.out.println("Valore getWidth: " + view.getWidth());
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(view.getHeight(), MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public Bitmap takeScreenshotForScreen(Activity activity) {
        return takeScreenshotForView(activity.getWindow().getDecorView().getRootView());
    }
}