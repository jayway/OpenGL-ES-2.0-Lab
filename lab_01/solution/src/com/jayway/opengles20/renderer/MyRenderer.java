package com.jayway.opengles20.renderer;

import android.content.Context;
import com.jayway.gles20.renderer.ComponentRenderer;
import com.jayway.opengles20.mesh.Triangle;

public class MyRenderer extends ComponentRenderer {
	protected Context mContext;
	
	public MyRenderer(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public void init(int width, int height, boolean contextLost) {
		super.init(width, height, contextLost);
		getDatabase().add(new Triangle(mContext));
//        getDatabase().add(new Square(mContext));

        setClearColor(0.0f, 0.0f, 1.0f, 1.0f);
	}
}
