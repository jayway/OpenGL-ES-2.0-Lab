package com.jayway.gles20.qualifier;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-28
 */
public class GLQualifier extends Qualifier{
    public final int glQualifierType;
    public final int glType;

    public GLQualifier(Qualifier qualifier,int glQualifierType, int glType) {
        super(qualifier);

        this.glQualifierType = glQualifierType;
        this.glType = glType;
    }
}
