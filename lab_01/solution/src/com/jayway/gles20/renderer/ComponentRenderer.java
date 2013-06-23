package com.jayway.gles20.renderer;

import android.content.Context;
import android.opengl.Matrix;
import com.jayway.App;
import com.jayway.gles20.Camera;
import com.jayway.gles20.material.shader.SimpleTextureShader;
import com.jayway.gles20.mesh.Mesh;

import static android.opengl.GLES20.*;

public class ComponentRenderer extends CommonRenderer {

    private Context mContext;

    private MeshDB mDatabase = new MeshDB();

    private SimpleTextureShader mShader;

    private Camera mCamera;

    private PerFrameParams mPerFrameParams = new PerFrameParams();

    public ComponentRenderer(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void init(int width, int height, boolean contextLost) {
        mShader = new SimpleTextureShader(App.VERTEX_SHADER_SIMPLE_TEXTURE, App.FRAGMENT_SHADER_SIMPLE_TEXTURE);
        mCamera = new Camera(width, height);

        //Set GL States
        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void draw(boolean isFirstDraw) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

        mCamera.bind(mPerFrameParams);
        mShader.bindPerFrame(mPerFrameParams);

        // TODO for each is usually slower than normal for loop
        for (Mesh mesh : mDatabase) {
            mesh.bind();

            //TODO optimize by direct access instead of a getter?
            PerInstanceParams mPerInstanceParams = mesh.getParam();

            // MVP matrix calculation
            // P*(V*M) ===============================================================================================================

            // Calculate ModelView Matrix
            Matrix.multiplyMM(mPerInstanceParams.MVPMatrix, 0, mPerFrameParams.viewMatrix, 0, mPerInstanceParams.modelMatrix,0);

            // Calculate ModelViewProjection Matrix
            Matrix.multiplyMM(mPerInstanceParams.MVPMatrix, 0, mPerFrameParams.projMatrix, 0, mPerInstanceParams.MVPMatrix , 0);

            // ========================================================================================================================

            mShader.bindPerInstance(mPerInstanceParams);

            //Draw Mesh.
            glDrawArrays(mPerInstanceParams.drawMode, mPerInstanceParams.firstVertexIndex, mPerInstanceParams.numberOfVertices);
        }
    }

    public MeshDB getDatabase() {
        return mDatabase;
    }
}
