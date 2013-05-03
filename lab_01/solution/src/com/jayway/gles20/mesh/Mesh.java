package com.jayway.gles20.mesh;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.jayway.gles20.renderer.PerInstanceParams;
import com.jayway.gles20.util.BufferUtil;

public abstract class Mesh {

	protected float[] mMMatrix = new float[16];

	protected PerInstanceParams mPerInstanceParams = new PerInstanceParams();

    public Mesh(Context context) {
        Matrix.setIdentityM(mMMatrix, 0);
    }

    protected void init(float[] vertexData, int vertexStride, int positionOffset, int uvOffset) {
        init(vertexData, null, vertexStride, positionOffset, uvOffset);
    }

    protected void init(float[] vertexData, float[] uvData, int vertexStride, int positionOffset, int uvOffset) {
        validateVertexData(vertexData, vertexStride);

        mPerInstanceParams.stride             = vertexStride * BufferUtil.FLOAT_SIZE_BYTES;
        mPerInstanceParams.verticesDataOffset = positionOffset;
        mPerInstanceParams.uvDataOffset       = uvOffset;
        mPerInstanceParams.vertices = BufferUtil.createFloatBuffer(vertexData);
        mPerInstanceParams.numberOfVertices = vertexData.length / vertexStride;

        mPerInstanceParams.drawMode = GLES20.GL_TRIANGLES;

        //TODO how do we want to handle separate tex arrays.
        // Should the array be able to be null or do we want the user to send reference to the same array twice.
        if(uvData != null){
            mPerInstanceParams.uv = BufferUtil.createFloatBuffer(uvData);
        }else{
            mPerInstanceParams.uv = mPerInstanceParams.vertices;
        }
    }

    /**
     * Criterias:
     * vertexData != null
     * Validates vertex stride != 0.
     * ...
     *
     * @param vertexData
     * @param vertexStride
     * @throws RuntimeException
     */

    protected  void validateVertexData(float[] vertexData, int vertexStride) {
        if (vertexData != null){
            //TODO Log instead? or swap with dummy model?
            throw new RuntimeException("Vertex data cannot be null");
        }
        if(vertexStride == 0){
            //TODO Log instead? or swap with dummy model?
            throw new RuntimeException("Stride was set to 0, application will terminate");
        }
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

    /** Bind all per instance and per frame variables here. */
    public abstract void bind();

}
