package com.jayway.gles20.material.shader;

import android.util.Log;
import com.jayway.gles20.qualifier.GLQualifier;
import com.jayway.gles20.qualifier.QualifierFactory;
import com.jayway.gles20.util.GLESUtil;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.*;

public class ShaderUtil {
	private static final String TAG = "ShaderUtil";

	private final static String mVertexShader =
              "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n"
            + "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n"
            + "void main() {\n"
			+ "  gl_Position = aPosition * uMVPMatrix;\n"
			+ "  vTextureCoord = aTextureCoord;\n"
            + "}\n";

	private final static String mFragmentShader =
              "precision mediump float;\n"
			+ "varying vec2 vTextureCoord;\n"
            + "uniform sampler2D uTexture0;\n"
			+ "void main() {\n"
			+ "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n"
            + "}\n";


    private static final int SHADER_COMPILED_WITH_ERROR = 0;
    private static final int PROGRAM_COMPILED_WITH_ERROR = 0;

	public static final int createDefaultShader() {
		return createProgram(mVertexShader, mFragmentShader);
	}

	public static int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == SHADER_COMPILED_WITH_ERROR) {
			return PROGRAM_COMPILED_WITH_ERROR;
		}

		int pixelShader = loadShader(GL_FRAGMENT_SHADER, fragmentSource);
		if (pixelShader == SHADER_COMPILED_WITH_ERROR) {
			return PROGRAM_COMPILED_WITH_ERROR;
		}

		int program = glCreateProgram();
		if (program != PROGRAM_COMPILED_WITH_ERROR) {
			glAttachShader(program, vertexShader);
			GLESUtil.checkGlError("glAttachShader");
			glAttachShader(program, pixelShader);
			GLESUtil.checkGlError("glAttachShader");
			glLinkProgram(program);
			int[] linkStatus = new int[1];
			glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GL_TRUE) {
				Log.e(TAG, "Could not link program: ");
				Log.e(TAG, glGetProgramInfoLog(program));
				glDeleteProgram(program);
				program = PROGRAM_COMPILED_WITH_ERROR;
			}
		}
		return program;
	}

	private static int loadShader(int shaderType, String source) {
		int shader = glCreateShader(shaderType);
		if (shader != SHADER_COMPILED_WITH_ERROR) {
			glShaderSource(shader, source);
			glCompileShader(shader);
			int[] compiled = new int[1];
			glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == SHADER_COMPILED_WITH_ERROR) {
				Log.e(TAG, getShaderType(shaderType) + " compile failed: " + shaderType + ":");
				Log.e(TAG, glGetShaderInfoLog(shader));
				glDeleteShader(shader);
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
            case GL_FRAGMENT_SHADER:
                return "Fragment Shader";
            case GL_VERTEX_SHADER:
                return "Vertex Shader";
        }
        return "Shader type not recognized";
    }

    public static List<GLQualifier> getAllQualifiers(int program) {
        int[][] qualifierTypes = {
            {GL_ACTIVE_ATTRIBUTES, GL_ACTIVE_ATTRIBUTE_MAX_LENGTH},
            {GL_ACTIVE_UNIFORMS,   GL_ACTIVE_UNIFORM_MAX_LENGTH}
        };

        ArrayList<GLQualifier> allQualifiers = new ArrayList<GLQualifier>(20);

        int[] nQualifiers                 = new int[1];
        int[] maxQualifierLengthContainer = new int[1];


        for(int[] qt : qualifierTypes){
            int typeId      = qt[0];
            int maxLengthId = qt[1];

            //Get qualifier information
            glGetProgramiv(program, typeId, nQualifiers, 0);
            glGetProgramiv(program, maxLengthId, maxQualifierLengthContainer, 0);

            for (int i = 0; i < nQualifiers[0]; ++i) {
                GLQualifier qualifier = createQualifier(program, typeId, i, maxQualifierLengthContainer[0]);
                allQualifiers.add(qualifier);
            }
        }

        return allQualifiers;
    }

    /**
     * Creates a gl qualifier from the provided data.
     * //TODO Push to QualifierFactory?
     *
     * @param program OpenGL shader program id
     * @param glQualifierType type of gl qualifier e.g. {@link android.opengl.GLES20#GL_ACTIVE_ATTRIBUTES}, {@link android.opengl.GLES20#GL_ACTIVE_UNIFORMS}
     * @param qualifierId The by OpenGL generated qualifier id, i.e location id.
     * @param maxQualifierLength Max length of the qualifier with longest name.
     * @return Created Qualifier
     */
    private static GLQualifier createQualifier(final int program, final int glQualifierType, final int qualifierId, final int maxQualifierLength) {
        byte[] NAME_CONTAINER  = new byte[maxQualifierLength];
        int[] LENGTH_CONTAINER = new int[1];
        int[] SIZE_CONTAINER   = new int[1];
        int[] TYPE_CONTAINER   = new int[1];

        switch (glQualifierType){
            case GL_ACTIVE_UNIFORMS:
                glGetActiveUniform(program, qualifierId, maxQualifierLength, LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);
                break;
            case GL_ACTIVE_ATTRIBUTES:
                glGetActiveAttrib(program, qualifierId, maxQualifierLength, LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);
                break;
        }

        return QualifierFactory.createGLQualifier(program,
            new String(NAME_CONTAINER, 0, LENGTH_CONTAINER[0]), //Name
            glQualifierType,
            TYPE_CONTAINER[0] //glVariableType
        );
    }
}
