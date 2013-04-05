package com.jayway.opengles20.mesh;

import android.content.Context;

import com.jayway.gles20.renderer.PerInstanceParams;
import com.jayway.gles20.util.BufferUtil;

public class Mesh {

	protected float[] mMMatrix = new float[16];

	protected PerInstanceParams mPerInstanceParams = new PerInstanceParams();

	public Mesh(Context context) {

	}

	public void bind() {

	}

	public void setVertexData(float[] verticesData) {
		mPerInstanceParams.vertices = BufferUtil
				.createFloatBuffer(verticesData);
	}

	public PerInstanceParams getParam() {
		return mPerInstanceParams;
	}
}
