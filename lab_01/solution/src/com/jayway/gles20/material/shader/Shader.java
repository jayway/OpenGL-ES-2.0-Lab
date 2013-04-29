package com.jayway.gles20.material.shader;

import com.jayway.gles20.qualifier.GLQualifier;
import com.jayway.gles20.qualifier.Qualifier;
import com.jayway.gles20.qualifier.QualifierFactory;
import com.jayway.gles20.renderer.PerFrameParams;
import com.jayway.gles20.renderer.PerInstanceParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract General shader class
 */
public abstract class Shader {
    private static final String TAG = "Shader";

    protected static int sActiveShader = 0;

	protected int mProgram = 0;
	protected ArrayList<Qualifier> mPerFrame = new ArrayList<Qualifier>();
	protected ArrayList<Qualifier> mPerInstance = new ArrayList<Qualifier>();

	public Shader(String vertexShader, String fragmentShader) {
        //TODO add these to member?
		mProgram = createProgram(vertexShader, fragmentShader);

		mPerFrame.clear();
		mPerInstance.clear();


        List<GLQualifier> glQualifiers = ShaderUtil.getAllQualifiers(mProgram);
        collectQualifiers(glQualifiers);

        //TODO number of samplers indicate how many textures will be used.. setup params accordingly?
    }

    private void collectQualifiers(List<GLQualifier> glQualifiers) {
        for (GLQualifier glq : glQualifiers) {
            Qualifier qualifier = QualifierFactory.create(mProgram, glq.name);
            if (qualifier.isPerFrame) {
                mPerFrame.add(qualifier);
            } else {
                mPerInstance.add(qualifier);
            }
        }
    }

	private int createProgram(String vertexSource, String fragmentSource) {
        return ShaderUtil.createProgram(vertexSource, fragmentSource);
	}

    public abstract void bindPerFrame(PerFrameParams params);

	public abstract void bindPerInstance(PerInstanceParams params);

}
