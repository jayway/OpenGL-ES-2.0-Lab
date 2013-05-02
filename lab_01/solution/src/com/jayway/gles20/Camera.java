package com.jayway.gles20;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.jayway.gles20.renderer.PerFrameParams;

public class Camera {
    /** Projection Matrix */
	public float[] projMatrix = new float[16];

    /** View Matrix */
	public float[] viewMatrix = new float[16];

    /** View Projection Matrix */
    public float[] viewProjectionMatrix = new float[16];

	public Camera(int width, int height) {
        float ratio = (float) width / height;

        //Setup OpenGL viewport
        GLES20.glViewport(0, 0, width, height);

        //Setup view and projection matrices.
		Matrix.setLookAtM(viewMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.frustumM(projMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        //Pre-multiplying view and projection matrix for optimizing performance.
        Matrix.multiplyMM(viewProjectionMatrix, 0, projMatrix, 0, viewMatrix, 0);
	}

	public void bind(PerFrameParams params) {
		params.projMatrix = projMatrix;
		params.viewMatrix = viewMatrix;
        params.viewProjMatrix = viewProjectionMatrix;
	}
}
