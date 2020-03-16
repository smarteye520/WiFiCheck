package com.joyhonest.wifination;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class JH_GLSurfaceView extends GLSurfaceView {
    public boolean bDraw = true;

    public static native int naDecordFrame(byte[] bArr, int i);

    public static native int naDecordInit();

    public static native int naDecordRelease();

    public JH_GLSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public JH_GLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        setEGLContextClientVersion(2);
        setRenderer(new Renderer() {
            public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
                C0300wifination.init();
            }

            public void onSurfaceChanged(GL10 gl10, int i, int i2) {
                C0300wifination.changeLayout(i, i2);
            }

            public void onDrawFrame(GL10 gl10) {
                if (JH_GLSurfaceView.this.bDraw) {
                    C0300wifination.drawFrame();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C0300wifination.release();
    }
}
