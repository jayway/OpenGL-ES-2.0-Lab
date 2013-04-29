package com.jayway;

import android.app.Application;
import com.jayway.gles20.qualifier.QualifierFactory;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-29
 */
public class App extends Application{

    public static String VERTEX_SHADER_SIMPLE_TEXTURE = null;
    public static String FRAGMENT_SHADER_SIMPLE_TEXTURE = null;

    @Override
    public void onCreate() {
        super.onCreate();
        
        loadShaders();

        System.out.println(QualifierFactory.QualifierMapper.getPrintableMapString());
    }

    private void loadShaders() {
        VERTEX_SHADER_SIMPLE_TEXTURE   = GeneralUtil.stringFromResource(getAssets(), "shader/simple_texture.vs");
        FRAGMENT_SHADER_SIMPLE_TEXTURE = GeneralUtil.stringFromResource(getAssets(), "shader/simple_texture.fs");
    }
}
