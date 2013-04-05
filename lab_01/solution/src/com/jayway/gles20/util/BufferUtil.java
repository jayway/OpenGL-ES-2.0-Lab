package com.jayway.gles20.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtil {
	public static final int FLOAT_SIZE_BYTES = 4;

	public static final FloatBuffer createFloatBuffer(float[] floatData) {
		FloatBuffer floatBuffer = ByteBuffer
				.allocateDirect(floatData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		floatBuffer.put(floatData).position(0);
		return floatBuffer;
	}
}
