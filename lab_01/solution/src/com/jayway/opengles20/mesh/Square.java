package com.jayway.opengles20.mesh;

import android.content.Context;
import android.opengl.Matrix;
import android.os.SystemClock;
import com.jayway.gles20.mesh.Mesh;
import com.jayway.gles20.texture.Texture;
import com.jayway.gles20.texture.TextureFactory;
import com.jayway.opengles20.R;

public class Square extends Mesh {

	private static final int VERTICES_DATA_STRIDE = 5;
	private static final int VERTICES_DATA_POS_OFFSET = 0;
	private static final int VERTICES_DATA_UV_OFFSET = 3;

	private final float[] mVerticesData = {
			//  X,     Y, Z,     U,  V
			-0.5f, -0.5f, 0f,  0, 0,
			 0.5f, -0.5f, 0f,  1, 0,
			 0.5f,	0.5f, 0f,  1, 1,

			-0.5f, -0.5f, 0f,  0, 0,
			 0.5f,  0.5f, 0f,  1, 1,
			-0.5f,  0.5f, 0f,  0, 1
    };
    private Texture mTexture;

    public Square(Context context) {
		super(context);

        init(mVerticesData, VERTICES_DATA_STRIDE, VERTICES_DATA_POS_OFFSET, VERTICES_DATA_UV_OFFSET);
		mPerInstanceParams.firstVertexIndex = 0;

        setupTextures(context);
	}

    private void setupTextures(Context context) {
        //TODO Might want this as a singleton in application
        TextureFactory tf = new TextureFactory(context);
        mTexture = tf.fromResId(R.raw.robot);
    }

    @Override
	public void bind() {
		long time = SystemClock.uptimeMillis() % 4000L;
		float angle = 0.090f * ((int) time);
		Matrix.setRotateM(mMMatrix, 0, angle, 0, 0, 1.0f);

		mPerInstanceParams.modelMatrix = mMMatrix;
        mPerInstanceParams.textureId = mTexture.textureId;
	}
}