package com.ucm.proyecto_ucm_tmi2019_g5.View;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;

import com.ucm.proyecto_ucm_tmi2019_g5.Util.OpenGLRenderer;

public class OpenGLView extends GLSurfaceView {

    public OpenGLView(Context context) {
        super(context);
        init();
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setRenderer(new OpenGLRenderer());
    }
}

