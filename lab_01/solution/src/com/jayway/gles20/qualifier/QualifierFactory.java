package com.jayway.gles20.qualifier;

import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static com.jayway.gles20.qualifier.Qualifier.QualifierType;

public class QualifierFactory {
    public static class QualifierMapper{
        //TODO Move to its own class?
        //TODO could be defined in JSON or XML

        private static HashMap<String, QualifierType> map = new HashMap<String, QualifierType>();

        /** Mapping between variable names and their internal id*/
        static{
            map.put("aPosition",     QualifierType.ATTRIBUTE_POSITION);
            map.put("aTextureCoord", QualifierType.ATTRIBUTE_TEXTURE_COORDINATE);
            map.put("uMVPMatrix",    QualifierType.UNIFORM_MVP_MATRIX);
            map.put("uTexture0",     QualifierType.UNIFORM_TEXTURE_0);
        }

        public static Qualifier.QualifierType getType(String name) {
            return map.get(name);
        }

       public static String getVariableName(QualifierType qualifierType){
           for(Map.Entry<String, QualifierType> entry : map.entrySet()){
               if(entry.getValue() == qualifierType){
                   return entry.getKey();
               }
           }

           return null;
       }

        public static String getPrintableMapString() {
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String, QualifierType> e : map.entrySet()){
                sb.append(e.getValue().name()).append(" - ").append(e.getKey()).append("\n");
            }

            return sb.toString();
        }
    }

    public static final Qualifier create(int program, String name) {

        QualifierType type = QualifierMapper.getType(name);
        int handle = -1;
        boolean isPerFrame = false;

        switch (type){
            case UNIFORM_MVP_MATRIX:
                handle = glGetUniformLocation(program, QualifierMapper.getVariableName(type));
                break;
            case ATTRIBUTE_POSITION:
            case ATTRIBUTE_TEXTURE_COORDINATE:
            case UNIFORM_TEXTURE_0:
                handle = glGetAttribLocation(program, QualifierMapper.getVariableName(type));
        }



		return new Qualifier(type, name, handle, isPerFrame);
	}

    public static final GLQualifier createGLQualifier(int program, String name, int glQualifierType, int glType) {
        return new GLQualifier(create(program, name), glQualifierType, glType);
    }
}
