package com.jayway.gles20.qualifier;

import android.opengl.GLES20;

import java.util.List;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-29
 */
public class QualifierUtil {

    public static int countPerFrameQualifiers(List<? extends Qualifier> qualifiers){
        int foundMatches = 0;
        for(Qualifier q : qualifiers){
            if(q.isPerFrame){
                ++foundMatches;
            }
        }

        return foundMatches;
    }

    public static int countPerInstanceQualifiers(List<? extends Qualifier> qualifiers) {
        int foundMatches = 0;
        for(Qualifier q : qualifiers){
            if(!q.isPerFrame){
                ++foundMatches;
            }
        }

        return foundMatches;
    }

    public static int countSampler2dQualifiers(List<? extends GLQualifier> glQualifiers) {
        int foundMatches = 0;
        for(GLQualifier q : glQualifiers){
            if(q.glVariableType == GLES20.GL_SAMPLER_2D){
                ++foundMatches;
            }
        }

        return foundMatches;
    }
}
