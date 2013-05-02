package com.jayway.gles20.qualifier;

/**
 * User: Andreas Nilsson, Jayway
 * Date: 2013-04-28
 */
public class GLQualifier extends Qualifier{
    public final int glQualifierType;
    public final int glVariableType;

    public GLQualifier(Qualifier qualifier,int glQualifierType, int glVariableType) {
        super(qualifier);

        this.glQualifierType = glQualifierType;
        this.glVariableType  = glVariableType;
    }
}
