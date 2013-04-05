package com.jayway.gles20.material.shader;

import java.util.ArrayList;

import android.opengl.GLES20;
import android.util.Log;

import com.jayway.gles20.qualifier.Qualifier;
import com.jayway.gles20.qualifier.QualifierFactory;
import com.jayway.gles20.renderer.PerFrameParams;
import com.jayway.gles20.renderer.PerInstanceParams;
import com.jayway.gles20.util.GLESUtil;

public class Shader {

	private final static String mVertexShader = "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n" + "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n" + "void main() {\n"
			+ "  gl_Position = uMVPMatrix * aPosition;\n"
			+ "  vTextureCoord = aTextureCoord;\n" + "}\n";

	private final static String mFragmentShader = "precision mediump float;\n"
			+ "varying vec2 vTextureCoord;\n" + "uniform sampler2D sTexture;\n"
			+ "void main() {\n"
			+ "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" + "}\n";

	private static final String TAG = "Shader";

	private static int sActiveShader = 0;

	private int mProgram = 0;

	private ArrayList<Qualifier> mPerFrame = new ArrayList<Qualifier>();
	private ArrayList<Qualifier> mPerInstance = new ArrayList<Qualifier>();

	public Shader() {
		mProgram = createProgram(mVertexShader, mFragmentShader);

		mPerFrame.clear();
		mPerInstance.clear();

		String[] attrib = getAllAttributes();
		String[] uniform = getAllUniforms();

		collectQualifiers(attrib);
		collectQualifiers(uniform);
	}

	private void collectQualifiers(String[] names) {
		for (String name : names) {
			Qualifier qualifier = QualifierFactory.create(mProgram, name);
			if (qualifier.perFrame) {
				mPerFrame.add(qualifier);
			} else {
				mPerInstance.add(qualifier);
			}
		}
	}

	public void bindPerFrame(PerFrameParams params) {
		if (mProgram == sActiveShader) {
			return;
		}
		GLES20.glUseProgram(mProgram);
		GLESUtil.checkGlError("glUseProgram");
		
		for (Qualifier qualifier : mPerFrame) {
			switch (qualifier.type) {

			default:
				break;
			}
		}
	}

	public void bindPerInstance(PerInstanceParams params) {
		for (Qualifier qualifier : mPerInstance) {
			switch (qualifier.type) {
			case Qualifier.ATTRIBUTE_POSITION:
				params.vertices.position(params.verticesDataOffset);
				GLES20.glVertexAttribPointer(qualifier.handle, 3,
						GLES20.GL_FLOAT, false, params.stride, params.vertices);
				GLESUtil.checkGlError("glVertexAttribPointer maPosition");

				GLES20.glEnableVertexAttribArray(qualifier.handle);
				GLESUtil.checkGlError("glEnableVertexAttribArray maPositionHandle");

				break;
			case Qualifier.ATTRIBUTE_TEXTURE_COORDS:
				params.uv.position(params.uvDataOffset);
				GLES20.glVertexAttribPointer(qualifier.handle, 2,
						GLES20.GL_FLOAT, false, params.stride, params.uv);
				GLESUtil.checkGlError("glVertexAttribPointer maTextureHandle");
				GLES20.glEnableVertexAttribArray(qualifier.handle);
				GLESUtil.checkGlError("glEnableVertexAttribArray maTextureHandle");
				break;
			case Qualifier.ATTRIBUTE_TEXTURE:
				GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, params.textureId);
				break;
			case Qualifier.UNIFORM_MVP_MATRIX:
				GLES20.glUniformMatrix4fv(qualifier.handle, 1, false,
						params.MVPMatrix, 0);
				break;
			default:
				break;
			}
		}
	}

	private int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == 0) {
			return 0;
		}

		int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		if (pixelShader == 0) {
			return 0;
		}

		int program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			GLESUtil.checkGlError("glAttachShader");
			GLES20.glAttachShader(program, pixelShader);
			GLESUtil.checkGlError("glAttachShader");
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				Log.e(TAG, "Could not link program: ");
				Log.e(TAG, GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}

	private int loadShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				Log.e(TAG, "Could not compile shader " + shaderType + ":");
				Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}

	private String[] getAllAttributes() {
		int[] numberOfAttribute = new int[1];
		GLES20.glGetProgramiv(mProgram, GLES20.GL_ACTIVE_ATTRIBUTES,
				numberOfAttribute, 0);

		String[] result = new String[numberOfAttribute[0]];

		int[] maxAttributeLength = new int[1];
		GLES20.glGetProgramiv(mProgram, GLES20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH,
				maxAttributeLength, 0);

		int[] LENGTH_CONTAINER = new int[1];
		int[] SIZE_CONTAINER = new int[1];
		int[] TYPE_CONTAINER = new int[1];
		int NAME_CONTAINER_SIZE = maxAttributeLength[0];
		byte[] NAME_CONTAINER = new byte[maxAttributeLength[0]];

		for (int i = 0; i < numberOfAttribute[0]; ++i) {
			GLES20.glGetActiveAttrib(mProgram, i, NAME_CONTAINER_SIZE,
					LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0,
					NAME_CONTAINER, 0);
			result[i] = new String(NAME_CONTAINER).trim();
		}
		return result;
	}

	private String[] getAllUniforms() {
		int[] numberOfAttribute = new int[1];
		GLES20.glGetProgramiv(mProgram, GLES20.GL_ACTIVE_UNIFORMS,
				numberOfAttribute, 0);

		String[] result = new String[numberOfAttribute[0]];

		int[] maxAttributeLength = new int[1];
		GLES20.glGetProgramiv(mProgram, GLES20.GL_ACTIVE_UNIFORM_MAX_LENGTH,
				maxAttributeLength, 0);

		int[] LENGTH_CONTAINER = new int[1];
		int[] SIZE_CONTAINER = new int[1];
		int[] TYPE_CONTAINER = new int[1];
		int NAME_CONTAINER_SIZE = maxAttributeLength[0];
		byte[] NAME_CONTAINER = new byte[maxAttributeLength[0]];

		for (int i = 0; i < numberOfAttribute[0]; ++i) {
			GLES20.glGetActiveUniform(mProgram, i, NAME_CONTAINER_SIZE,
					LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0,
					NAME_CONTAINER, 0);
			result[i] = new String(NAME_CONTAINER).trim();
		}
		return result;
	}

}
