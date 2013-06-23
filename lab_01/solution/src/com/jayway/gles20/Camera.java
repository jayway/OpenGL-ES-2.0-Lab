package com.jayway.gles20;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.jayway.gles20.renderer.PerFrameParams;

public class Camera {
    /** Projection Matrix */
	public float[] projMatrix = new float[16];

    /** View Matrix */
	public float[] viewMatrix = new float[16];

    /** The center postion */
    private final float[] center = {0, 0f, 0f};
    /** The eye postion */
    private final float[] eye    = {0, 0f, 5f};
    /** The up vector */
    private final float[] up     = {0f, 1f, 0f};

	public Camera(int width, int height) {
        float aspect = (float) width / height;

        //Setup OpenGL viewport
        GLES20.glViewport(0, 0, width, height);

        // Setup View Matrix using eye, center and up-vector
		Matrix.setLookAtM(viewMatrix, 0,
                eye[0], eye[1], eye[2],
                center[0], center[1], center[2],
                up[0], up[1], up[2]);

        // Projection using projection matrix, requires later API level.
//        Matrix.perspectiveM(projMatrix, 0, 45f, aspect, 0.1f, 100f);

        // Projection using frustum matrix
        Matrix.frustumM(projMatrix, 0, -aspect, aspect, -1, 1, 4, 7);
	}

	public void bind(PerFrameParams params) {
		params.projMatrix = projMatrix;
		params.viewMatrix = viewMatrix;
	}
}
