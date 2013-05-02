package com.jayway.opengles20;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.jayway.opengles20.renderer.MyRenderer;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-25
 */
public class GL2SurfaceView extends GLSurfaceView {

    private final MyRenderer mRenderer;
    private GestureDetector mDetector;

    public GL2SurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        setEGLConfigChooser(true);
        setEGLConfigChooser(8, 8, 8, 8, 24, 0);
//        GLES20.glEnable( GLES20.GL_DEPTH_TEST );
//        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
//        GLES20.glDepthMask( true );

        mRenderer = new MyRenderer(context);
        setRenderer(mRenderer);

        mDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onShowPress(MotionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mRenderer.scroll(distanceX, distanceY);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return  mDetector.onTouchEvent(event);
    }
}
