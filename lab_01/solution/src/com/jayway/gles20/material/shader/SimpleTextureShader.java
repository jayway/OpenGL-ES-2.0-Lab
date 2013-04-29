package com.jayway.gles20.material.shader;

import android.opengl.GLES20;
import com.jayway.gles20.qualifier.Qualifier;
import com.jayway.gles20.renderer.PerFrameParams;
import com.jayway.gles20.renderer.PerInstanceParams;
import com.jayway.gles20.util.GLESUtil;

/**
 * Simple basic shader who only renders with a solid COLOR.
 */
public class SimpleTextureShader extends Shader{
    private static final String TAG = "Shader";

    public SimpleTextureShader(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);

        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
    }


    @Override
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

    @Override
    public void bindPerInstance(PerInstanceParams params) {
        for (Qualifier qualifier : mPerInstance) {
            switch (qualifier.type) {
                case ATTRIBUTE_POSITION:
                    params.vertices.position(params.verticesDataOffset);
                    GLES20.glVertexAttribPointer(qualifier.handle, 3,
                        GLES20.GL_FLOAT, false, params.stride, params.vertices);
                    GLESUtil.checkGlError("glVertexAttribPointer maPosition");

                    GLES20.glEnableVertexAttribArray(qualifier.handle);
                    GLESUtil.checkGlError("glEnableVertexAttribArray maPositionHandle");

                    break;
                case UNIFORM_MVP_MATRIX:
                    GLES20.glUniformMatrix4fv(qualifier.handle, 1, false, params.MVPMatrix, 0);
                    break;
                case ATTRIBUTE_TEXTURE_COORDINATE:
                    params.uv.position(params.uvDataOffset);
                    GLES20.glVertexAttribPointer(qualifier.handle, 2, GLES20.GL_FLOAT, false, params.stride, params.uv);
                    GLESUtil.checkGlError("glVertexAttribPointer maTextureHandle");
                    GLES20.glEnableVertexAttribArray(qualifier.handle);
                    GLESUtil.checkGlError("glEnableVertexAttribArray maTextureHandle");
                    break;
                case ATTRIBUTE_TEXTURE_0:
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, params.textureId);
                    break;
                default:
                    break;
            }
        }
    }
}
