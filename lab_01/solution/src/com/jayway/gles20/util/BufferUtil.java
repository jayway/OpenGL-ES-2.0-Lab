package com.jayway.gles20.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtil {
	public static final int FLOAT_SIZE_BYTES = 4;

	public static final FloatBuffer createFloatBuffer(float[] floatData) {
		FloatBuffer buffer = ByteBuffer
				.allocateDirect(floatData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(floatData);
		buffer.position(0);

        return buffer;
	}

    /**
     * @param nElements Number of elements to create.
     * @param stride The stride between elements.
     * @return The constructed FloatBuffer
     */
    public static final FloatBuffer createFloatBuffer(int nElements, int stride) {
        FloatBuffer buffer = ByteBuffer
            .allocateDirect(nElements * stride * FLOAT_SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
        buffer.position(0);

        return buffer;
    }
}
