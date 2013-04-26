package com.jayway.gles20.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-22
 */
public class Texture {
    public final int width;
    public final int height;

    public Texture(Context context, int resId){
        //FIXME default options
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);

        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
}
