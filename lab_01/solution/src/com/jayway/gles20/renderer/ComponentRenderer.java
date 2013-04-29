package com.jayway.gles20.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.jayway.App;
import com.jayway.gles20.Camera;
import com.jayway.gles20.material.shader.SimpleTextureShader;
import com.jayway.gles20.mesh.Mesh;

public class ComponentRenderer extends CommonRenderer {

    private SimpleTextureShader mShader;

    private PerFrameParams mPerFrameParams = new PerFrameParams();
    private MeshDB mDatabase = new MeshDB();
    private Camera mCamera;
    private Context mContext;

    public ComponentRenderer(Context context) {
        super();
        mContext = context;

        //TODO Necessary or not?
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    @Override
    public void init(int width, int height, boolean contextLost) {
        mShader = new SimpleTextureShader(App.VERTEX_SHADER_SIMPLE_TEXTURE, App.FRAGMENT_SHADER_SIMPLE_TEXTURE);
        mCamera = new Camera(width, height);
    }

    @Override
    public void draw(boolean firstDraw) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        mCamera.bind(mPerFrameParams);
        mShader.bindPerFrame(mPerFrameParams);

        for (Mesh mesh : mDatabase) {
            mesh.bind();
            PerInstanceParams mPerInstanceParams = mesh.getParam();

            //Compute MVP Matrix
            Matrix.multiplyMM(mPerInstanceParams.MVPMatrix, 0, mCamera.mProjMatrix, 0, mCamera.mViewMatrix, 0);
            //TODO if mPerInstanceParams.modelMatrix is Identity the below step can be optimized.
            Matrix.multiplyMM(mPerInstanceParams.MVPMatrix, 0, mPerInstanceParams.modelMatrix, 0, mPerInstanceParams.MVPMatrix, 0);

            mShader.bindPerInstance(mPerInstanceParams);
            GLES20.glDrawArrays(mPerInstanceParams.drawMode, mPerInstanceParams.drawFirst, mPerInstanceParams.numberOfVertices);
        }
    }

    public MeshDB getDatabase() {
        return mDatabase;
    }
}
