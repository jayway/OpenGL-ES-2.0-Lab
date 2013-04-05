package com.jayway.gles20;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.jayway.gles20.renderer.PerFrameParams;

public class Camera {
	public float[] mProjMatrix = new float[16];
	public float[] mVMatrix = new float[16];

	public Camera(int width, int height) {
		Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	public void bind(PerFrameParams params) {
		params.projMatrix = mProjMatrix;
		params.viewMatrix = mVMatrix;
	}
}
