package com.jayway.gles20.mesh;

import android.content.Context;
import android.opengl.GLES20;
import com.jayway.gles20.renderer.PerInstanceParams;
import com.jayway.gles20.util.BufferUtil;

// TODO Rework abstraction
public class Mesh {

	protected float[] mMMatrix = new float[16];

	protected PerInstanceParams mPerInstanceParams = new PerInstanceParams();

    public Mesh(Context context) {
    }

    protected void init(float[] vertexData, int vertexStride, int positionOffset, int uvOffset) {
        init(vertexData, null, vertexStride, positionOffset, uvOffset);
        mPerInstanceParams.uv = mPerInstanceParams.vertices;
    }

    protected void init(float[] vertexData, float[] uvData, int vertexStride, int positionOffset, int uvOffset) {
        mPerInstanceParams.stride             = vertexStride * BufferUtil.FLOAT_SIZE_BYTES;
        mPerInstanceParams.verticesDataOffset = positionOffset;
        mPerInstanceParams.uvDataOffset       = uvOffset;
        mPerInstanceParams.vertices = BufferUtil.createFloatBuffer(vertexData);
        mPerInstanceParams.numberOfVertices = vertexData.length / vertexStride;

        mPerInstanceParams.drawMode           = GLES20.GL_TRIANGLES;

        //FIXME
        if(uvData != null){
            mPerInstanceParams.uv = BufferUtil.createFloatBuffer(uvData);
        }
    }

    public void bind() {

    }

    private void setVertexStride(int vertexStride) {
        mPerInstanceParams.stride = vertexStride;
    }

	public void setVertexData(float[] verticesData) {
        mPerInstanceParams.vertices = BufferUtil.createFloatBuffer(verticesData);
	}

    public void setUvData(float[] uvData) {
        mPerInstanceParams.uv = BufferUtil.createFloatBuffer(uvData);
    }

	public PerInstanceParams getParam() {
		return mPerInstanceParams;
	}

    public void setPositionOffset(int positionOffset) {
        mPerInstanceParams.verticesDataOffset = positionOffset;
    }

    public void setUvOffset(int uvOffset) {
        mPerInstanceParams.uvDataOffset = uvOffset;
    }

    public void setDrawMode(int drawMode) {
        mPerInstanceParams.drawMode = drawMode;
    }

}
