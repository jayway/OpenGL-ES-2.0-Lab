package com.jayway.gles20.material.shader;

import com.jayway.gles20.qualifier.Qualifier;
import com.jayway.gles20.qualifier.QualifierUtil;
import com.jayway.gles20.renderer.PerFrameParams;
import com.jayway.gles20.renderer.PerInstanceParams;

import java.util.List;

/**
 * Abstract General shader class
 */
public abstract class Shader {
    private static final String TAG = "Shader";

    public static int sActiveShader = 0;

    private final List<? extends Qualifier> mQualifiers;
    protected int mProgram = 0;
    protected Qualifier mPerFrame[] = null;
    protected Qualifier mPerInstance[] = null;

    public Shader(String vertexShader, String fragmentShader) {
        mProgram    = ShaderUtil.createProgram(vertexShader, fragmentShader);
        mQualifiers = ShaderUtil.getAllQualifiers(mProgram);

        generateQualifierArrays();
    }

    /**
     * Generate arrays from the list of Qualifiers to enhance performance.
     * This, since mPerFrame and mPerInstance is frequently accessed in the draw loop.
     */
    private void generateQualifierArrays() {
        mPerFrame    = new Qualifier[QualifierUtil.countPerFrameQualifiers(mQualifiers)];
        mPerInstance = new Qualifier[QualifierUtil.countPerInstanceQualifiers(mQualifiers)];

        int perFrameCount    = 0;
        int perInstanceCount = 0;
        for (Qualifier q : mQualifiers) {
            if (q.isPerFrame) {
                mPerFrame[perFrameCount++] = q;
            } else {
                mPerInstance[perInstanceCount++] = q;
            }
        }
    }

    public List<? extends Qualifier> getQualifiers(){
        return mQualifiers;
    }

    public abstract void bindPerFrame(PerFrameParams params);

	public abstract void bindPerInstance(PerInstanceParams params);

}
