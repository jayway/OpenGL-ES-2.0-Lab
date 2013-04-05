package com.jayway.gles20.util;

import android.opengl.GLES20;
import android.util.Log;

public class GLESUtil {
	public static void checkGlError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e("GLESUtil", op + ": glError " + error);
			throw new RuntimeException(op + ": glError " + error);
		}
	}
}
