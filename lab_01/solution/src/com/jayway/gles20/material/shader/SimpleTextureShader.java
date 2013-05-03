package com.jayway.gles20.material.shader;

import com.jayway.gles20.qualifier.Qualifier;
import com.jayway.gles20.renderer.PerFrameParams;
import com.jayway.gles20.renderer.PerInstanceParams;
import com.jayway.gles20.util.GLESUtil;

import static android.opengl.GLES20.*;

/**
 * Simple basic shader who only renders with a solid COLOR.
 */
public class SimpleTextureShader extends Shader{
    private static final String TAG = "Shader";

    public SimpleTextureShader(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
    }

    @Override
    protected void initGLStates() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glDepthFunc(GL_LEQUAL);
        GLESUtil.checkGlError("SimpleTextureShader >> set gl states");
    }

    @Override
    public void bindPerFrame(PerFrameParams params) {
        if (mProgram == sActiveShader) {
            return;
        }
        glUseProgram(mProgram);
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
                    glVertexAttribPointer(qualifier.handle, 3, GL_FLOAT, false, params.stride, params.vertices);
                    GLESUtil.checkGlError("glVertexAttribPointer maPosition");

                    glEnableVertexAttribArray(qualifier.handle);
                    GLESUtil.checkGlError("glEnableVertexAttribArray maPositionHandle");
                    break;
                case UNIFORM_MVP_MATRIX:
                    glUniformMatrix4fv(qualifier.handle, 1, false, params.MVPMatrix, 0);
                    break;
                case ATTRIBUTE_TEXTURE_COORDINATE:
                    params.uv.position(params.uvDataOffset);
                    glVertexAttribPointer(qualifier.handle, 2, GL_FLOAT, false, params.stride, params.vertices);
                    GLESUtil.checkGlError("glVertexAttribPointer mTextureHandle");
                    glEnableVertexAttribArray(qualifier.handle);
                    GLESUtil.checkGlError("glEnableVertexAttribArray mTextureHandle");
                    break;
                case UNIFORM_TEXTURE_0:
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D, params.textureId);
                    break;
                default:
                    break;
            }
        }
    }
}
