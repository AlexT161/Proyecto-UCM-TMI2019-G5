package com.ucm.proyecto_ucm_tmi2019_g5.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ucm.proyecto_ucm_tmi2019_g5.R;

public class OpenGLActivity extends AppCompatActivity {

    private OpenGLView openGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        openGLView = (OpenGLView) findViewById(R.id.openGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        openGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openGLView.onResume();
    }
}