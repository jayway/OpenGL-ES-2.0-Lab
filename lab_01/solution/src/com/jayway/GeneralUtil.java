package com.jayway;

import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GeneralUtil {
    private static final int BYTES_PER_FLOAT = 4;

    /**
     * Converts an inputstream object to a java String
     * 
     * @param in
     * @return
     */
    public static String inputStreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(in);
        try {
            int c = 0;
            while ((c = reader.read()) > -1) {
                sb.append((char) c);
            }
            in.close();
        } catch (IOException e) {
            Log.e("IO", e.getMessage(), e);
        } finally {
            closeQuietly(in);
        }

        return sb.toString();
    }

    private static void closeQuietly(Closeable stream){
        try {
            stream.close();
        } catch (IOException e) {

        }

    }

    public static void printGLErrorInHex(String notice) {
        int error = GLES20.glGetError(); 
        if (error != 0) {
            Log.e("OPENGL", notice + "\tError: " + Integer.toHexString(error));
            
            
            //Log.e("OPENGL", );
        }
    }
    
    public static FloatBuffer createFloatBuffer(float[] array, int stride) {
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * BYTES_PER_FLOAT);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(array);
        fb.position(0);

        return fb;
    }
    
    public static FloatBuffer createFloatBuffer(int noElements, int stride) {
        ByteBuffer bb = ByteBuffer.allocateDirect(noElements * stride * BYTES_PER_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();

        return fb;
    }

    public static String resourceToString(AssetManager manager, String s) {
        try {
            InputStream is = manager.open(s);
            return inputStreamToString(is);
        } catch (IOException e) {
            //TODO logg
            e.printStackTrace();
        }
        return null;
    }
}
