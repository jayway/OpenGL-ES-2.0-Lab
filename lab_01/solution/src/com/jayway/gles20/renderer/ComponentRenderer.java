package com.jayway.gles20.renderer;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.jayway.gles20.Camera;
import com.jayway.gles20.material.shader.Shader;
import com.jayway.opengles20.mesh.Mesh;

public class ComponentRenderer extends CommonRenderer {

	private Shader mShader;
	
	private PerFrameParams mPerFrameParams = new PerFrameParams();
	private MeshDB mDatabase = new MeshDB();
	private Camera mCamera;

	public ComponentRenderer() {
		super();
	}

	@Override
	public void init(int width, int height, boolean contextLost) {
		mShader = new Shader();
		mCamera = new Camera(width, height);
	}

	@Override
	public void draw(boolean firstDraw) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

		mCamera.bind(mPerFrameParams);
		mShader.bindPerFrame(mPerFrameParams);

		for (Mesh mesh : mDatabase) {
			mesh.bind();
			PerInstanceParams mPerInstanceParams = mesh.getParam();
			Matrix.multiplyMM(mPerInstanceParams.MVPMatrix, 0,
					mCamera.mVMatrix, 0, mPerInstanceParams.modelMatrix, 0);
			Matrix.multiplyMM(mPerInstanceParams.MVPMatrix, 0,
					mCamera.mProjMatrix, 0, mPerInstanceParams.MVPMatrix, 0);
			mShader.bindPerInstance(mPerInstanceParams);
			GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
		}
	}

	public MeshDB getDatabase() {
		return mDatabase;
	}

}
