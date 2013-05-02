package com.jayway.gles20.qualifier;

public class Qualifier {
    public static enum QualifierType {
        UNIFORM_MVP_MATRIX,
        ATTRIBUTE_POSITION,
        ATTRIBUTE_TEXTURE_COORDINATE,
        UNIFORM_TEXTURE_0,
    }

    public final int handle;
    public final String name;
    public final boolean isPerFrame;
    public final QualifierType type;

    public Qualifier(QualifierType type, String name, int handle, boolean isPerFrame){
        this.handle = handle;
        this.type = type;
        this.name = name;
        this.isPerFrame = isPerFrame;
    }

    /** Copy Constructor */
    public Qualifier(Qualifier qualifier){
        this.name = qualifier.name;
        this.handle = qualifier.handle;
        this.type = qualifier.type;
        this.isPerFrame = qualifier.isPerFrame;
    }
}


