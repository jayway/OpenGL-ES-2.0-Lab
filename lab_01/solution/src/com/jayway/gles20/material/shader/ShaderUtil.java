package com.jayway.gles20.material.shader;

import android.opengl.GLES20;
import android.util.Log;
import com.jayway.gles20.qualifier.GLQualifier;
import com.jayway.gles20.qualifier.QualifierFactory;
import com.jayway.gles20.util.GLESUtil;

import java.util.ArrayList;
import java.util.List;

public class ShaderUtil {

	private final static String mVertexShader = "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n" + "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n" + "void main() {\n"
			+ "  gl_Position = uMVPMatrix * aPosition;\n"
			+ "  vTextureCoord = aTextureCoord;\n" + "}\n";

	private final static String mFragmentShader = "precision mediump float;\n"
			+ "varying vec2 vTextureCoord;\n"
            + "uniform sampler2D sTexture;\n"
			+ "void main() {\n"
			+ "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" + "}\n";

	private static final String TAG = "ShaderUtil";

    //FIXME use these..
    private static final int SHADER_COMPILED_WITH_ERROR = 0;
    private static final int PROGRAM_COMPILED_WITH_ERROR = 0;

	public static final int createDefaultShader() {
		return createProgram(mVertexShader, mFragmentShader);
	}

	public static int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == SHADER_COMPILED_WITH_ERROR) {
			return PROGRAM_COMPILED_WITH_ERROR;
		}

		int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		if (pixelShader == SHADER_COMPILED_WITH_ERROR) {
			return PROGRAM_COMPILED_WITH_ERROR;
		}

		int program = GLES20.glCreateProgram();
		if (program != PROGRAM_COMPILED_WITH_ERROR) {
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
				program = PROGRAM_COMPILED_WITH_ERROR;
			}
		}
		return program;
	}

	private static int loadShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != SHADER_COMPILED_WITH_ERROR) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == SHADER_COMPILED_WITH_ERROR) {
				Log.e(TAG, getShaderType(shaderType) + " compile failed: " + shaderType + ":");
				Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = SHADER_COMPILED_WITH_ERROR;
			}
		}
		return shader;
	}

    /**
     * Returns a human readable string of which type of shader is defined.
     * @param shaderType
     * @return
     */
    private static String getShaderType(int shaderType) {
        switch (shaderType){
            case GLES20.GL_FRAGMENT_SHADER:
                return "Fragment Shader";
            case GLES20.GL_VERTEX_SHADER:
                return "Vertex Shader";
        }
        return "Shader type not recognized";
    }


    public static ArrayList<String> getAllAttributes(int program) {
        int[] numberOfAttribute = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_ACTIVE_ATTRIBUTES, numberOfAttribute, 0);

        ArrayList<String> result = new ArrayList<String>(numberOfAttribute[0]);

        int[] maxAttributeLength = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH, maxAttributeLength, 0);

        int[] LENGTH_CONTAINER = new int[1];
        int[] SIZE_CONTAINER = new int[1];
        int[] TYPE_CONTAINER = new int[1];
        int NAME_CONTAINER_SIZE = maxAttributeLength[0];
        byte[] NAME_CONTAINER = new byte[maxAttributeLength[0]];
        //TODO IntBuffer vs int[]
        for (int i = 0; i < numberOfAttribute[0]; ++i) {
            GLES20.glGetActiveAttrib(program, i, NAME_CONTAINER_SIZE,
                LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);

            //TODO remove trim
            result.add(new String(NAME_CONTAINER, 0, LENGTH_CONTAINER[0]).trim());
        }
        return result;
    }

    public static List<GLQualifier> getAllQualifiers(int program) {

        int[][] qualifierTypes = {
            {GLES20.GL_ACTIVE_ATTRIBUTES, GLES20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH},
            {GLES20.GL_ACTIVE_UNIFORMS,   GLES20.GL_ACTIVE_UNIFORM_MAX_LENGTH}
        };


        ArrayList<GLQualifier> allQualifiers = new ArrayList<GLQualifier>();

//        ArrayList<String> glAttributes = new ArrayList<String>();
//        ArrayList<String> glUniforms   = new ArrayList<String>();
        int nSamplers = 0;

        int[] nQualifiers                 = new int[1];
        int[] maxQualifierLengthContainer = new int[1];


        for(int[] qt : qualifierTypes){
            int typeId      = qt[0];
            int maxLengthId = qt[1];

            //Get qualifier information
            GLES20.glGetProgramiv(program, typeId, nQualifiers, 0);
            GLES20.glGetProgramiv(program, maxLengthId, maxQualifierLengthContainer, 0);

            for (int i = 0; i < nQualifiers[0]; ++i) {
                GLQualifier qualifier = getQualifier(program, typeId, i, maxQualifierLengthContainer[0]);
                allQualifiers.add(qualifier);
            }
        }


        return allQualifiers;
    }

    /**
     * Accumulated data is updated in @param qualifierMeta, such as number of samplers.
     *
     *
     *
     * @param program
     * @param glQualifierType
     * @param qualifierId
     * @param maxQualifierLength
     * @return
     */
    private static GLQualifier getQualifier(final int program, final int glQualifierType, final int qualifierId, final int maxQualifierLength) {
        byte[] NAME_CONTAINER    = new byte[maxQualifierLength];
        int[] LENGTH_CONTAINER   = new int[1];
        int[] SIZE_CONTAINER     = new int[1];
        int[] TYPE_CONTAINER     = new int[1];

        switch (glQualifierType){
            case GLES20.GL_ACTIVE_UNIFORMS:
                GLES20.glGetActiveUniform(program, qualifierId, maxQualifierLength, LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);
                break;
            case GLES20.GL_ACTIVE_ATTRIBUTES:
                GLES20.glGetActiveAttrib(program, qualifierId, maxQualifierLength, LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);
                break;
        }

        return QualifierFactory.createGLQualifier(program,
            new String(NAME_CONTAINER, 0, LENGTH_CONTAINER[0]), //Name
            glQualifierType,
            TYPE_CONTAINER[0] //glType
        );
    }
}
