package com.jayway.gles20.qualifier;

public class Qualifier {
    public enum QualifierType {
        UNIFORM_MVP_MATRIX,
        ATTRIBUTE_POSITION,
        ATTRIBUTE_TEXTURE_COORDINATE,
        ATTRIBUTE_TEXTURE_0,
    }


    public final int handle;
    public final String name;
    public final boolean isPerFrame;
    public final QualifierType type;

    public Qualifier(int handle, QualifierType type, String name, boolean isPerFrame){
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


