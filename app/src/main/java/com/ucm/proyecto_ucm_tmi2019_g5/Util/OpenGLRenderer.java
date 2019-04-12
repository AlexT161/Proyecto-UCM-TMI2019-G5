package com.ucm.proyecto_ucm_tmi2019_g5.Util;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig){
        GLES20.glClearColor(1f,0,0,1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1){

    }

    @Override
    public void onDrawFrame(GL10 gl10){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    }
}
