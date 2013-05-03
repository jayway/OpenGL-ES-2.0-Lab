package com.jayway.gles20.renderer;

import java.nio.FloatBuffer;

public class PerInstanceParams {

	public float[] MVPMatrix = new float[16];
	public int textureId;
	public FloatBuffer vertices;
	public int verticesDataOffset;
	public int stride;
	public FloatBuffer uv;
	public int uvDataOffset;
	public float[] modelMatrix;
	public int numberOfVertices;
	public int drawMode;
	public int firstVertexIndex;

}
