package com.jayway.gles20.qualifier;

import android.opengl.GLES20;

public class QualifierFactory {
	public static final Qualifier create(int program, String name) {
		Qualifier qualifier = new Qualifier();
		if (name.equals("uMVPMatrix")) {
			qualifier.type = Qualifier.UNIFORM_MVP_MATRIX;
            // FIXME Commented because I imagine mvp being set per instance since M can be different for each model.
//			qualifier.perFrame = true;

			qualifier.handle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
		} else if (name.equals("aPosition")) {
			qualifier.type = Qualifier.ATTRIBUTE_POSITION;
			qualifier.handle = GLES20.glGetAttribLocation(program, "aPosition");
		} else if (name.equals("aTextureCoord")) {
			qualifier.type = Qualifier.ATTRIBUTE_TEXTURE_COORDS;
			qualifier.handle = GLES20.glGetAttribLocation(program,"aTextureCoord");
		} else if (name.equals("sTexture")) {
			qualifier.type = Qualifier.ATTRIBUTE_TEXTURE;
		}

		return qualifier;
	}
}
