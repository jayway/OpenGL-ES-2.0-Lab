package com.jayway.gles20;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.jayway.gles20.renderer.PerFrameParams;


//TODO user projection matrix instead of frustrum projection?
//TODO add PV Matrix which is projection and view multplied, that way we don' have to multiply them for each draw.
public class Camera {
	public float[] mProjMatrix = new float[16];
	public float[] mViewMatrix = new float[16];

	public Camera(int width, int height) {
        float ratio = (float) width / height;

        //Setup OpenGL viewport
        GLES20.glViewport(0, 0, width, height);

        //Setup view and projection matrices.
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	public void bind(PerFrameParams params) {
		params.projMatrix = mProjMatrix;
		params.viewMatrix = mViewMatrix;
	}
}
