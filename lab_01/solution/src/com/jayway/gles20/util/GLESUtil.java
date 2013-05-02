package com.jayway.gles20.util;

import android.util.Log;

import static android.opengl.GLES20.*;

public class GLESUtil {
	public static void checkGlError(String op) {
		int error;
		while ((error = glGetError()) != GL_NO_ERROR) {
			Log.e("GLESUtil", op + ": glError "+ error + ":" + getShortDescription(error));
			throw new RuntimeException(op + ": glError " + error);
		}
	}

    public static String getLongDescription(int glError){
        switch (glError)  {
            case GL_INVALID_ENUM:
                return "GL_INVALID_ENUM: Given when an enumeration parameter is not a legal enumeration for that function. This is given only for local problems; if the spec allows the enumeration in certain circumstances, and other parameters or state dictate those circumstances, then GL_INVALID_OPERATION​ is the result instead";
            case GL_INVALID_VALUE:
                return "GL_INVALID_VALUE: Given when a value parameter is not a legal value for that function. This is only given for local problems; if the spec allows the value in certain circumstances, and other parameters or state dictate those circumstances, then GL_INVALID_OPERATION is the result instead.";
            case GL_INVALID_OPERATION:
                return "GL_INVALID_OPERATION: Given when the set of state for a command is not legal for the parameters given to that command. It is also given for commands where combinations of parameters define what the legal parameters are.";
            case GL_OUT_OF_MEMORY:
                return "GL_OUT_OF_MEMORY: Given when performing an operation that can allocate memory, but the memory cannot be allocated. The results of OpenGL functions that return this error are undefined; it is allowable for partial operations to happen.";
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                return "GL_INVALID_FRAMEBUFFER_OPERATION: Given when doing anything that would attempt to read from or write/render to a framebuffer that is not complete, as defined here.";
        }
        return "";
    }

    public static String getShortDescription(int glError){
        switch (glError)  {
            case GL_INVALID_ENUM:
                return "GL_INVALID_ENUM";
            case GL_INVALID_VALUE:
                return "GL_INVALID_VALUE";
            case GL_INVALID_OPERATION:
                return "GL_INVALID_OPERATION";
            case GL_OUT_OF_MEMORY:
                return "GL_OUT_OF_MEMORY";
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                return "GL_INVALID_FRAMEBUFFER_OPERATION";
        }
        return "";
    }
}
