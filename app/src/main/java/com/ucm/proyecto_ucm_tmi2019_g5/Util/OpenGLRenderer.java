package com.ucm.proyecto_ucm_tmi2019_g5.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "OpenGLRenderer";
    private Carta mCarta;
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    public volatile float mAngle;
    private int[] textures = new int[1];
    private int programHandle = -1;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        mCarta = new Carta();
        GLES20.glGenTextures(1, textures, 0);

        if (textures[0] == GLES20.GL_FALSE)
            throw new RuntimeException("Error loading texture");

        // bind the texture and set parameters
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        // Load a bitmap from resources folder and pass it to OpenGL
        // in the end, we recycle it to free unneeded resources
        File imgFile = new File("/Users/simone/AndroidStudioProjects/Proyecto-UCM-TMI2019-G5/app/src/main/res/drawable/menu.jpg");
        if(imgFile.exists()){
            Bitmap b = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            //Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, b, 0);
            b.recycle();
        }
    }
    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw Carta
        mCarta.draw(scratch);
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        // Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        final int FLOAT_SIZE = 4;
        final int POSITION_SIZE = 2;
        final int TEXTURE_SIZE = 2;
        final int TOTAL_SIZE = POSITION_SIZE + TEXTURE_SIZE;
        final int POSITION_OFFSET = 0;
        final int TEXTURE_OFFSET = 2;
        int aPosition = GLES20.glGetAttribLocation(programHandle, "aPosition");
        int aTexPos = GLES20.glGetAttribLocation(programHandle, "aTexPos");

        // Again, a FloatBuffer will be used to pass the values
        FloatBuffer b = ByteBuffer.allocateDirect(mCarta.squareCoords.length* FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        b.put(mCarta.squareCoords.length);

        // Position of our image
        b.position(POSITION_OFFSET);
        GLES20.glVertexAttribPointer(aPosition, POSITION_SIZE, GLES20.GL_FLOAT, false, TOTAL_SIZE * FLOAT_SIZE, b);
        GLES20.glEnableVertexAttribArray(aPosition);

        // Positions of the texture
        b.position(TEXTURE_OFFSET);
        GLES20.glVertexAttribPointer(aTexPos, TEXTURE_SIZE, GLES20.GL_FLOAT, false, TOTAL_SIZE * FLOAT_SIZE, b);
        GLES20.glEnableVertexAttribArray(aTexPos);

        // Clear the screen and draw the rectangle
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }
    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }
}