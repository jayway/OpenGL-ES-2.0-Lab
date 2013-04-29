package com.jayway.gles20.texture;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-22
 */
public class TextureFactory {
    private static final String LOG_TAG = "TextureFactory";
    private Context mContext;

    private int[] mTextures;
    private int mLastUsedTextureSlot;

    public TextureFactory(Context context){
        this(context, 1);
    }
    public TextureFactory(Context context, int nTextures){
        mContext = context;
        mTextures = new int[nTextures];
        mLastUsedTextureSlot = 0;

        GLES20.glGenTextures(nTextures, mTextures, 0);

        //TODO generate texture slots.
    }

    /**
     * @param resId texture id.
     * @return
     */
    public Texture fromResId(int resId){
        int texId = getNextFreeTextureSlot();
        return new Texture(mContext, resId, texId, true);
    }

    private int getNextFreeTextureSlot() {
        if(mLastUsedTextureSlot == mTextures.length-1){
            //TODO throw exception or create more id's?
            Log.e(LOG_TAG, "no more texture slots available in texture factory");
//            throw new IndexOutOfBoundsException("No more texture units available in factory");
            return -1;
        }else{
            return ++mLastUsedTextureSlot;
        }
    }
}
