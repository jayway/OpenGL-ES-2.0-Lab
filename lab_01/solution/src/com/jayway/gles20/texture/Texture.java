package com.jayway.gles20.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import com.jayway.gles20.util.GLESUtil;

import static android.opengl.GLES20.*;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-22
 */
public class Texture {
    public final int width;
    public final int height;
    public final Bitmap bitmap;
    public final int textureId;

    public static class TexParams{
        public int TEXTURE_TYPE = GL_TEXTURE_2D;
        public int MIN_FILTER   = GL_NEAREST;
        public int MAX_FILTER   = GL_LINEAR;
        public int WRAP_S       = GL_REPEAT;
        public int WRAP_T       = GL_REPEAT;
    }

    public Texture(Context context, int resId,int texId, boolean doUploadToGPU){
        textureId = texId;
        //FIXME default options
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        if(doUploadToGPU){
            uploadToGPU(new TexParams());
        }
    }

    public Texture(Context context, int resId,int texId, TexParams params, boolean doUploadToGPU){
        textureId = texId;
        //FIXME default options
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);

        width  = bitmap.getWidth();
        height = bitmap.getHeight();

        if(doUploadToGPU){
            uploadToGPU(params);
        }
    }

    public void uploadToGPU(TexParams params){
        //TODO verify texid and bitmap
        glBindTexture(params.TEXTURE_TYPE, textureId);

        glTexParameterf(params.TEXTURE_TYPE, GL_TEXTURE_MIN_FILTER, params.MIN_FILTER);
        glTexParameterf(params.TEXTURE_TYPE, GL_TEXTURE_MAG_FILTER, params.MAX_FILTER);
        glTexParameteri(params.TEXTURE_TYPE, GL_TEXTURE_WRAP_S, params.WRAP_S);
        glTexParameteri(params.TEXTURE_TYPE, GL_TEXTURE_WRAP_T, params.WRAP_T);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        GLESUtil.checkGlError("texImage2D");
        bitmap.recycle();
    }

    public void cleanUp(){
        bitmap.recycle();
    }
}
