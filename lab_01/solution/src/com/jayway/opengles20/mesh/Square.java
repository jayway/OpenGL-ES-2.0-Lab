package com.jayway.opengles20.mesh;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.jayway.gles20.mesh.Mesh;
import com.jayway.gles20.util.BufferUtil;
import com.jayway.opengles20.R;

public class Square extends Mesh {

	private static final int VERTICES_DATA_STRIDE = 5;
	private static final int VERTICES_DATA_STRIDE_BYTES = VERTICES_DATA_STRIDE
			* BufferUtil.FLOAT_SIZE_BYTES;
	private static final int VERTICES_DATA_POS_OFFSET = 0;
	private static final int VERTICES_DATA_UV_OFFSET = 3;

	private final float[] mVerticesData = {
			//  X,     Y, Z,     U,  V
			-0.5f, -0.5f, 0, -0.5f,  0.0f, 
			 0.5f, -0.5f, 0,  1.5f, -0.0f, 
			 0.5f,	0.5f, 0,  0.5f,  1.61803399f,

			-0.5f, -0.5f, 0, -0.5f, 0.0f, 
			 0.5f,  0.5f, 0,  0.5f, 1.61803399f,
			-0.5f,  0.5f, 0,  0.5f, 1.61803399f };

	public Square(Context context) {
		super(context);

		setVertexData(mVerticesData);

		mPerInstanceParams.numberOfVertices = mVerticesData.length
				/ VERTICES_DATA_STRIDE;
		mPerInstanceParams.verticesDataOffset = VERTICES_DATA_POS_OFFSET;
		mPerInstanceParams.uv = mPerInstanceParams.vertices;
		mPerInstanceParams.uvDataOffset = VERTICES_DATA_UV_OFFSET;
		mPerInstanceParams.stride = VERTICES_DATA_STRIDE_BYTES;
		mPerInstanceParams.drawMode = GLES20.GL_TRIANGLES;
		mPerInstanceParams.drawFirst = 0;
		
		// Texture
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);

		mPerInstanceParams.textureId = textures[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mPerInstanceParams.textureId);

		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);

		InputStream is = context.getResources().openRawResource(R.raw.robot);
		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// Ignore.
			}
		}

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();

	}

	@Override
	public void bind() {
		// long time = SystemClock.uptimeMillis() % 4000L;
		// float angle = 0.090f * ((int) time);
		// Matrix.setRotateM(mMMatrix, 0, angle, 0, 0, 1.0f);

		mPerInstanceParams.modelMatrix = mMMatrix;
	}
}