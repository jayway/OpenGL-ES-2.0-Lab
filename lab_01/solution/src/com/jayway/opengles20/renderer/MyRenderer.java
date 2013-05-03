package com.jayway.opengles20.renderer;

import android.content.Context;
import com.jayway.App;
import com.jayway.gles20.renderer.ComponentRenderer;
import com.jayway.gles20.util.GLESUtil;
import com.jayway.opengles20.mesh.Cube;

public class MyRenderer extends ComponentRenderer {
	protected Context mContext;
    private Cube mCube;
    private boolean doOnce = true;

    public MyRenderer(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public void init(int width, int height, boolean contextLost) {
		super.init(width, height, contextLost);
        setClearColor(0.2f, 0.2f, 0.5f, 1.0f);

        GLESUtil.checkGlError("MyRenderer.init");

        //FIXME contextLost does not work properly..
        if(doOnce){
            //TODO Models only need to be loaded once since that data is not "lost" with context loss.
            mCube = new Cube(mContext);
            getDatabase().add(mCube);
            // getDatabase().add(new Triangle(mContext));
            // getDatabase().add(new Square(mContext));

            doOnce = false;
        }
	}


    @Override
    protected void loadResources() {
        App app = (App) mContext.getApplicationContext();
        app.loadTextures();
        app.loadShaders();
    }

    //TODO Temporary perhaps?
    public void rotate(float distanceX, float distanceY) {
        mCube.rotate(distanceX, distanceY);
    }
}
