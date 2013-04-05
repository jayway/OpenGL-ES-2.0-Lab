package com.jayway.opengles20.renderer;

import android.content.Context;
import android.opengl.GLES20;

import com.jayway.gles20.renderer.ComponentRenderer;
import com.jayway.opengles20.mesh.Triangle;

public class MyRenderer extends ComponentRenderer {
	protected Context mContext;
	
	public MyRenderer(Context context) {
		super();
		mContext = context;
	}

	@Override
	public void init(int width, int height, boolean contextLost) {
		super.init(width, height, contextLost);
		getDatabase().add(new Triangle(mContext));
		GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
	}
}
