package com.jayway.opengles20.mesh;

import android.content.Context;
import android.opengl.Matrix;
import com.jayway.App;
import com.jayway.gles20.mesh.Mesh;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-29
 */
public class Cube extends Mesh {
    private static final int VERTICES_DATA_STRIDE     = 5;
    private static final int VERTICES_DATA_POS_OFFSET = 0;
    private static final int VERTICES_DATA_UV_OFFSET  = 3;

    private static final float width  = 1.0f;
    private static final float height = 1.0f;
    private static final float depth  = 1.0f;

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;


//    CUBE
//    v6----- v5
//   /|      /|
//  v1------v0|
//  | |     | |
//  | |v7---|-|v4
//  |/      |/
//  v2------v3
    private static final float[][] V = {
        { width * 0.5f,  height * 0.5f, depth * 0.5f}, //v0
        {-width * 0.5f,  height * 0.5f, depth * 0.5f}, //v1
        {-width * 0.5f, -height * 0.5f, depth * 0.5f}, //v2
        { width * 0.5f, -height * 0.5f, depth * 0.5f}, //v3

        { width * 0.5f, -height * 0.5f, -depth * 0.5f}, //v4
        { width * 0.5f,  height * 0.5f, -depth * 0.5f}, //v5
        {-width * 0.5f,  height * 0.5f, -depth * 0.5f}, //v6
        {-width * 0.5f, -height * 0.5f, -depth * 0.5f}, //v7
    };

    private final float[] mVertices = {
        // v0-v1-v2
        V[0][X], V[0][Y], V[0][Z], 1f, 0f,
        V[1][X], V[1][Y], V[1][Z], 0f, 0f,
        V[2][X], V[2][Y], V[2][Z], 0f, 1f,
        // v2-v3-v0
        V[2][X], V[2][Y], V[2][Z], 0f, 1f,
        V[3][X], V[3][Y], V[3][Z], 1f, 1f,
        V[0][X], V[0][Y], V[0][Z], 1f, 0f,

        // v0-v3-v4
        V[0][X], V[0][Y], V[0][Z], 0f, 0f,
        V[3][X], V[3][Y], V[3][Z], 0f, 1f,
        V[4][X], V[4][Y], V[4][Z], 1f, 1f,
        // v4-v5-v0
        V[4][X], V[4][Y], V[4][Z], 1f, 1f,
        V[5][X], V[5][Y], V[5][Z], 1f, 0f,
        V[0][X], V[0][Y], V[0][Z], 0f, 0f,

        // v0-v3-v4
        V[1][X], V[1][Y], V[1][Z], 1f, 0f,
        V[6][X], V[6][Y], V[6][Z], 0f, 0f,
        V[7][X], V[7][Y], V[7][Z], 0f, 1f,
        // v4-v5-v0
        V[7][X], V[7][Y], V[7][Z], 0f, 1f,
        V[2][X], V[2][Y], V[2][Z], 1f, 1f,
        V[1][X], V[1][Y], V[1][Z], 1f, 0f,

        // v0-v5-v6
        V[0][X], V[0][Y], V[0][Z], 1f, 1f,
        V[5][X], V[5][Y], V[5][Z], 1f, 0f,
        V[6][X], V[6][Y], V[6][Z], 0f, 0f,
        // v6-v1-v0
        V[6][X], V[6][Y], V[6][Z], 0f, 0f,
        V[1][X], V[1][Y], V[1][Z], 0f, 1f,
        V[0][X], V[0][Y], V[0][Z], 1f, 1f,

        // v1-v6-v7
        V[1][X], V[1][Y], V[1][Z], 1f, 0f,
        V[6][X], V[6][Y], V[6][Z], 0f, 0f,
        V[7][X], V[7][Y], V[7][Z], 0f, 1f,
        // v7-v2-v1
        V[7][X], V[7][Y], V[7][Z], 0f, 1f,
        V[2][X], V[2][Y], V[2][Z], 1f, 1f,
        V[1][X], V[1][Y], V[1][Z], 1f, 0f,

        // v7-v4-v3
        V[7][X], V[7][Y], V[7][Z], 0f, 1f,
        V[4][X], V[4][Y], V[4][Z], 1f, 1f,
        V[3][X], V[3][Y], V[3][Z], 1f, 0f,
        // v3-v2-v7
        V[3][X], V[3][Y], V[3][Z], 1f, 0f,
        V[2][X], V[2][Y], V[2][Z], 0f, 0f,
        V[7][X], V[7][Y], V[7][Z], 0f, 1f,

        // v4-v7-v6
        V[4][X], V[4][Y], V[4][Z], 0f, 1f,
        V[7][X], V[7][Y], V[7][Z], 1f, 1f,
        V[6][X], V[6][Y], V[6][Z], 1f, 0f,
        // v6-v5-v4
        V[6][X], V[6][Y], V[6][Z], 1f, 0f,
        V[5][X], V[5][Y], V[5][Z], 0f, 0f,
        V[4][X], V[4][Y], V[4][Z], 0f, 1f
    };

    private float rotationX = 0;
    private float rotationY = 0;

    public Cube(Context context) {
        super(context);

        init(mVertices, VERTICES_DATA_STRIDE, VERTICES_DATA_POS_OFFSET, VERTICES_DATA_UV_OFFSET);

        mPerInstanceParams.firstVertexIndex = 0;
    }

    @Override
    public void bind() {
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Matrix.setRotateM(mMMatrix, 0, angle, 0f, 0f, 1);
//        Matrix.rotateM(mMMatrix, 0, 45, 0f, 1f, 0);
//        Matrix.setRotateM(mMMatrix, 0f, angle, 0f, 1f, 0);

        Matrix.setIdentityM(mMMatrix, 0);
        Matrix.setRotateM(mMMatrix, 0, rotationY, 1f, 0f, 0);
        Matrix.rotateM(mMMatrix, 0, rotationX, 0f, 1f, 0);

//        Matrix.rotateM(mMMatrix, 0, angle, 0f, 0f, 1f);

        mPerInstanceParams.modelMatrix = mMMatrix;
        mPerInstanceParams.textureId = App.mRobotTexture.textureId;
    }

    public void rotate(float distanceX, float distanceY) {
        rotationX -= distanceX;
        rotationY -= distanceY;
    }
}