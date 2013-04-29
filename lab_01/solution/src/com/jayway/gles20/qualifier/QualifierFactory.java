package com.jayway.gles20.qualifier;

import android.opengl.GLES20;

import java.util.HashMap;
import java.util.Map;

public class QualifierFactory {
    public static class QualifierMapper{
        //TODO Move to its own class?
        //TODO could be defined in JSON or XML

        private static HashMap<String, Qualifier.QualifierType> map = new HashMap<String, Qualifier.QualifierType>();

        /** Mapping between variable names and their internal id*/
        static{
            map.put("uMVPMatrix",    Qualifier.QualifierType.UNIFORM_MVP_MATRIX);
            map.put("aPosition",     Qualifier.QualifierType.ATTRIBUTE_POSITION);
            map.put("aTextureCoord", Qualifier.QualifierType.ATTRIBUTE_TEXTURE_COORDINATE);
            map.put("uSampler0",     Qualifier.QualifierType.ATTRIBUTE_TEXTURE_0);
        }

        public static Qualifier.QualifierType getType(String name) {
            return map.get(name);
        }

       public static String getVariableName(Qualifier.QualifierType qualifierType){
           for(Map.Entry<String, Qualifier.QualifierType> entry : map.entrySet()){
               if(entry.getValue() == qualifierType){
                   return entry.getKey();
               }
           }

           return null;
       }

        public static String getPrintableMapString() {
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String, Qualifier.QualifierType> e : map.entrySet()){
                sb.append(e.getValue().name()).append(" - ").append(e.getKey()).append("\n");
            }

            return sb.toString();
        }
    }

    public static final Qualifier create(int program, String name) {

        Qualifier.QualifierType type = QualifierMapper.getType(name);
        int handle = -1;

        switch (type){
            case UNIFORM_MVP_MATRIX:
                handle = GLES20.glGetUniformLocation(program, QualifierMapper.getVariableName(type));
                break;
            case ATTRIBUTE_POSITION:
            case ATTRIBUTE_TEXTURE_COORDINATE:
            case ATTRIBUTE_TEXTURE_0:
                handle = GLES20.glGetAttribLocation(program, QualifierMapper.getVariableName(type));
        }

        boolean isPerFrame = false;

		return new Qualifier(handle, type, name, isPerFrame);
	}

    public static final GLQualifier createGLQualifier(int program, String name, int glQualifierType, int glType) {
        return new GLQualifier(create(program, name), glQualifierType, glType);
    }
}
