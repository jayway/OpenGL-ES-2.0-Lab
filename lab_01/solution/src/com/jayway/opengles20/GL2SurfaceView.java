package com.jayway.opengles20;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.jayway.opengles20.renderer.MyRenderer;

/**
 * Surface view specialized for OpenGL ES 2.0.
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-25
 */
public class GL2SurfaceView extends GLSurfaceView {
    private static final int GL2_VERSION = 2;

    private static final int COLOR_BITS   = 8;
    private static final int ALPHA_BITS   = 8;
    private static final int DEPTH_BITS   = 16;
    private static final int STENCIL_BITS = 0;

    private final MyRenderer mRenderer;
    private final GestureDetector mDetector;

    public GL2SurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(GL2_VERSION);
        setEGLConfigChooser(COLOR_BITS, COLOR_BITS, COLOR_BITS, ALPHA_BITS, DEPTH_BITS, STENCIL_BITS);

        mRenderer = new MyRenderer(context);
        setRenderer(mRenderer);

        mDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mRenderer.rotate(distanceX, distanceY);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return  mDetector.onTouchEvent(event);
    }
}
