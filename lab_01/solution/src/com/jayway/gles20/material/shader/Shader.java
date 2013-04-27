package com.jayway.gles20.material.shader;

import com.jayway.gles20.qualifier.Qualifier;
import com.jayway.gles20.qualifier.QualifierFactory;
import com.jayway.gles20.renderer.PerFrameParams;
import com.jayway.gles20.renderer.PerInstanceParams;

import java.util.ArrayList;

/**
 * Abstract General shader class
 */
public abstract class Shader {
    private static final String TAG = "Shader";

    private static final String COLOR = "0.7, 0.6, 0.3";

    protected static int sActiveShader = 0;

	protected int mProgram = 0;
	protected ArrayList<Qualifier> mPerFrame = new ArrayList<Qualifier>();
	protected ArrayList<Qualifier> mPerInstance = new ArrayList<Qualifier>();

	public Shader(String vertexShader, String fragmentShader) {
        //TODO add these to member?
		mProgram = createProgram(vertexShader, fragmentShader);

		mPerFrame.clear();
		mPerInstance.clear();

        collectQualifiers(ShaderUtil.getQualifierList(mProgram));
    }

    private void collectQualifiers(ArrayList<String> names) {
        for (String name : names) {
            Qualifier qualifier = QualifierFactory.create(mProgram, name);
            if (qualifier.perFrame) {
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
