package com.jayway.opengles20;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class OpenGLESActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)

        if(detectOpenGLES20()){
            mGLView = new GL2SurfaceView(this);
            setContentView(mGLView);
        }else{
            Log.e(OpenGLESActivity.class.getSimpleName(), " OpenGL 2.0 ES not available");
            finish();

            //TODO fallback GL ES 1.x
            // Set an OpenGL ES 1.x-compatible renderer. In a real application
			// this renderer might approximate the same output as the 2.0
			// renderer.
//			mGLSurfaceView.setRenderer(new TriangleRenderer(this));
//			setContentView(mGLSurfaceView);
        }
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}

    private boolean detectOpenGLES20() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }

}
