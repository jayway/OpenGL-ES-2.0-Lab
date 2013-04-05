package com.jayway.gles20.qualifier;

public class Qualifier {
	public static final int UNIFORM_MVP_MATRIX = 0;
	public static final int ATTRIBUTE_POSITION = 1;
	public static final int ATTRIBUTE_TEXTURE_COORDS = 2;
	public static final int ATTRIBUTE_TEXTURE = 3;

	public int handle = 0;
	public boolean perFrame = false;
	public int type;
}
