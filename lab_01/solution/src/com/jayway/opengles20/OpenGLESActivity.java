package com.jayway.opengles20;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.jayway.opengles1x.TriangleRenderer;
import com.jayway.opengles20.renderer.MyRenderer;

public class OpenGLESActivity extends Activity {

	private GLSurfaceView mGLSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)

		mGLSurfaceView = new GLSurfaceView(this);
		if (detectOpenGLES20()) {
			mGLSurfaceView.setEGLContextClientVersion(2);
			mGLSurfaceView.setRenderer(new MyRenderer(this));
			setContentView(mGLSurfaceView);
		} else {
			// Set an OpenGL ES 1.x-compatible renderer. In a real application
			// this renderer might approximate the same output as the 2.0
			// renderer.
			mGLSurfaceView.setRenderer(new TriangleRenderer(this));
			setContentView(mGLSurfaceView);
		}
	}

	private boolean detectOpenGLES20() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return (info.reqGlEsVersion >= 0x20000);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLSurfaceView.onPause();
	}

}
