package com.jayway.gles20.texture;

import android.content.Context;
import android.opengl.GLES20;
import com.jayway.gles20.util.GLESUtil;

/**
 * Automatically expands the number of texture units.
 * Be aware that specifying the number of units you want to use is better for performance.
 *
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-22
 */
public class TextureFactory {
    private static final String LOG_TAG = "TextureFactory";
    private Context mContext;

    private int[] mTextures;
    private int mLastUsedTextureSlot;

    /**
     * Initiates factory with 1 texture unit.
     * @param context
     */
    public TextureFactory(Context context){
        this(context, 1);
    }

    /**
     * Initiates factory with n Texture units.
     * @param context
     */
    public TextureFactory(Context context, int nTextures){
        mContext = context;
        mTextures = new int[nTextures];
        mLastUsedTextureSlot = 0;

        GLES20.glGenTextures(nTextures, mTextures, 0);
        GLESUtil.checkGlError("glGenTextures");

        //TODO generate texture slots.
    }

    /**
     * Generates a {@link Texture} from provided {@code resId}.
     * @param resId Resource id.
     * @return texture id
     */
    public Texture fromResId(int resId){
        int texId = getNextFreeTextureId();
        return new Texture(mContext, resId, texId, true);
    }

    private int getNextFreeTextureId() {
        if(mLastUsedTextureSlot == mTextures.length){
            expand();
            return getNextFreeTextureId();
        }else{
            return ++mLastUsedTextureSlot;
        }
    }

    /** Expands the texture factory with more texture units. */
    private void expand(){
        //Create new array with double size

        //Copy current texture units

        //Generate new texture units

        //Reassign mTextures to the newly generated array

        throw  new RuntimeException("Not yet implemented");
    }
}
