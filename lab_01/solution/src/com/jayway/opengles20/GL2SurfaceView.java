package com.jayway.opengles20;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.jayway.opengles20.renderer.MyRenderer;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-25
 */
public class GL2SurfaceView extends GLSurfaceView {

    private final Renderer mRenderer;

    public GL2SurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        mRenderer = new MyRenderer(context);
        setRenderer(mRenderer);
    }
}
