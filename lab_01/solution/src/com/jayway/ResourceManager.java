package com.jayway;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceManager {

    /**
     * Parses {@link InputStream} into a {@link String}
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



    /**
     * Opens asset path and reads resource into a {@link String}
     * @param manager
     * @param assetsPath
     * @return
     */
    //TODO should throw IOException perhaps?
    public static String stringFromResource(AssetManager manager, String assetsPath) {
        try {
            InputStream is = manager.open(assetsPath);
            return inputStreamToString(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load string <" + assetsPath + "> from resources", e);
        }
    }

    public static void closeQuietly(Closeable stream){
        try {
            stream.close();
        } catch (IOException e) {
        }
    }
}
