package com.jayway;

import android.app.Application;
import android.util.Log;
import com.jayway.gles20.qualifier.QualifierFactory;
import com.jayway.gles20.texture.Texture;
import com.jayway.gles20.texture.TextureFactory;
import com.jayway.gles20.util.GLESUtil;
import com.jayway.opengles20.R;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-29
 */
public class App extends Application{
    private static final String LOG_TAG = "App";
    /**
     * As long as shaders are loaded properly these should not appear null to the rest of the application.
     */

    //Shader sources
    public static String VERTEX_SHADER_SIMPLE_TEXTURE   = null;
    public static String FRAGMENT_SHADER_SIMPLE_TEXTURE = null;

    //Textures
    public static Texture mRobotTexture = null;


    //FIXME should probably go into the ResourceManager
    public void loadShaders() {
        Log.i(LOG_TAG, "Loading Shaders...");
        VERTEX_SHADER_SIMPLE_TEXTURE   = ResourceManager.stringFromResource(getAssets(), "shader/simple_texture.vs");
        FRAGMENT_SHADER_SIMPLE_TEXTURE = ResourceManager.stringFromResource(getAssets(), "shader/simple_texture.fs");

        Log.i(LOG_TAG, QualifierFactory.QualifierMapper.getPrintableMapString());
        GLESUtil.checkGlError("loadShaders");
    }

    //FIXME should probably go into the ResourceManager
    public void loadTextures() {
        Log.i(LOG_TAG, "Loading Textures...");
        //TODO Might want this as a singleton in application
        TextureFactory tf = new TextureFactory(this);
        mRobotTexture = tf.fromResId(R.raw.robot);

        GLESUtil.checkGlError("loadTextures");
    }
}
