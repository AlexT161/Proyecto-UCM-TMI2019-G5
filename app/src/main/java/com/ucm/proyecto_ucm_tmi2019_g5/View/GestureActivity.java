package com.ucm.proyecto_ucm_tmi2019_g5.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.ucm.proyecto_ucm_tmi2019_g5.Gesture.*;
import com.ucm.proyecto_ucm_tmi2019_g5.R;

import java.io.File;

public class GestureActivity extends AppCompatActivity implements View.OnTouchListener {
    public float mScaleFactor = 0.5f;
    public float mRotationDegree = 0.f;
    public float mFocusX = 0.f;
    public float mFocusY = 0.f;
    public int mScreenHeight;
    public int mScreenWidth;
    public Matrix matrix = new Matrix();
    public int mImageWidth, mImageHeight;
    public ScaleGestureDetector mScaleDetector;
    public RotateGestureDetector mRotateDetector;
    public MoveGestureDetector mMoveDetector;
    public String pathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        Bundle extras = getIntent().getExtras();
        pathImage = extras.getString("path");
        ImageView img_scale = findViewById(R.id.img_scale);
        img_scale.setOnTouchListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        // Load a bitmap from resources folder and pass it to OpenGL
        // in the end, we recycle it to free unneeded resources
        File imgFile = new File(pathImage);
        if(imgFile.exists()){
            Bitmap loadTempImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mImageHeight = loadTempImg.getHeight();
            mImageWidth = loadTempImg.getWidth();
            img_scale.setImageBitmap(loadTempImg);
        }
        //Bitmap loadTempImg = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
        //view anh thu nho lai boi ma tran so voi anh goc
        matrix.postScale(mScaleFactor, mScaleFactor);
        img_scale.setImageMatrix(matrix);

        // Thiết lập Detectors Gesture
        mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
        mRotateDetector = new RotateGestureDetector(getApplication(), new RotateListener());
        mMoveDetector = new MoveGestureDetector(getApplication(), new MoveListener());

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 1.0f));
            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegree -= detector.getRotationDegreesDelta();
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;

            return true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mRotateDetector.onTouchEvent(event);
        mMoveDetector.onTouchEvent(event);
        float scaleImageCenterX = (mImageWidth * mScaleFactor) / 2;
        float scaleImageCenterY = (mImageHeight * mScaleFactor) / 2;

        matrix.reset();
        matrix.postScale(mScaleFactor, mScaleFactor);
        matrix.postRotate(mRotationDegree, scaleImageCenterX, scaleImageCenterY);
        matrix.postTranslate(mFocusX - scaleImageCenterX, mFocusY - scaleImageCenterY);

        ImageView view = (ImageView) v;
        view.setImageMatrix(matrix);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, CameraActivity.class));
        finish();

    }
}
