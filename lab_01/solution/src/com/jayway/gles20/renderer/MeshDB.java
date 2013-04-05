package com.jayway.gles20.renderer;

import java.util.ArrayList;
import java.util.Collection;

import com.jayway.opengles20.mesh.Mesh;

public class MeshDB extends ArrayList<Mesh> {

	private static final long serialVersionUID = 4746106428971937683L;

	public MeshDB() {
		super();
	}

	public MeshDB(Collection<? extends Mesh> collection) {
		super(collection);
	}

	public MeshDB(int capacity) {
		super(capacity);
	}
}
