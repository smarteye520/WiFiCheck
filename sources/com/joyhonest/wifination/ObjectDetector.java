package com.joyhonest.wifination;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import com.joyhonest.wifination.Classifier.Recognition;
import java.io.IOException;
import org.simple.eventbus.EventBus;

public class ObjectDetector {
    private static Context AppContext = null;
    public static float MINIMUM_CONFIDENCE_TF_OD_API = 0.8f;
    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/mydata.txt";
    private static final String TF_OD_API_MODEL_FILE = "file:///android_asset/frozen_inference_graph.pb";
    public static int nHeight = 300;
    public static int nWidth = 300;
    private boolean bBusy;
    private boolean bStar;
    private Canvas canvas;
    private Matrix cropToFrameTransform;
    private Bitmap croppedBitmap;
    private Classifier detector;
    private Matrix frameToCropTransform;
    private Handler handler;
    private HandlerThread handlerThread;

    public static class SingleTonHoulder {
        /* access modifiers changed from: private */
        public static final ObjectDetector singleTonInstance = new ObjectDetector();
    }

    public void F_SetWidth_Height(int i, int i2) {
        nWidth = i;
        nHeight = i2;
        Bitmap bitmap = this.croppedBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.croppedBitmap = Bitmap.createBitmap(nWidth, nHeight, Config.ARGB_8888);
        this.canvas = new Canvas(this.croppedBitmap);
    }

    private ObjectDetector() {
        this.bBusy = false;
        this.bStar = false;
        this.croppedBitmap = null;
        this.croppedBitmap = Bitmap.createBitmap(nWidth, nHeight, Config.ARGB_8888);
        this.canvas = new Canvas(this.croppedBitmap);
    }

    public void SetAppCentext(Context context) {
        if (AppContext == null) {
            AppContext = context;
        }
        Context context2 = AppContext;
        if (context2 != null && this.detector == null) {
            try {
                this.detector = JH_ObjectDetectionAPIModel.create(context2.getAssets(), TF_OD_API_MODEL_FILE, TF_OD_API_LABELS_FILE, nWidth, nHeight);
            } catch (IOException unused) {
            }
        }
    }

    public static ObjectDetector getInstance() {
        return SingleTonHoulder.singleTonInstance;
    }

    public void F_Start(boolean z) {
        if (!this.bStar || z) {
            if (!this.bStar && z) {
                this.handlerThread = new HandlerThread("_Obj__jhabc_");
                this.handlerThread.start();
                this.handler = new Handler(this.handlerThread.getLooper());
                this.bStar = z;
            }
            return;
        }
        HandlerThread handlerThread2 = this.handlerThread;
        if (handlerThread2 != null) {
            handlerThread2.quit();
        }
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
        this.bStar = z;
    }

    public int GetNumber(Bitmap bitmap) {
        if (bitmap == null) {
            return -1;
        }
        if (!this.bStar) {
            return -2;
        }
        if (this.bBusy) {
            return -3;
        }
        this.bBusy = true;
        if (this.frameToCropTransform == null) {
            this.frameToCropTransform = ImageUtils.getTransformationMatrix(bitmap.getWidth(), bitmap.getHeight(), nWidth, nHeight, 0, false);
            this.cropToFrameTransform = new Matrix();
            this.frameToCropTransform.invert(this.cropToFrameTransform);
        }
        this.canvas.drawBitmap(bitmap, this.frameToCropTransform, null);
        runInBackground(new Runnable() {
            public void run() {
                ObjectDetector.this.progressImage();
            }
        });
        return 0;
    }

    /* access modifiers changed from: private */
    public void progressImage() {
        int i;
        int i2 = (int) (MINIMUM_CONFIDENCE_TF_OD_API * 100.0f);
        boolean z = true;
        String str = "";
        String str2 = str;
        int i3 = 0;
        boolean z2 = true;
        int i4 = 0;
        for (Recognition recognition : this.detector.recognizeImage(this.croppedBitmap)) {
            if (recognition.getLocation() != null) {
                if (z2) {
                    i3 = (int) (recognition.getConfidence().floatValue() * 100.0f);
                    str2 = recognition.getTitle();
                    i = i3;
                } else {
                    i = (int) (recognition.getConfidence().floatValue() * 100.0f);
                    if (i >= i4) {
                        str2 = recognition.getTitle();
                        i3 = i;
                    } else {
                        i = i4;
                    }
                }
                i4 = i;
                z2 = false;
            }
        }
        String str3 = "GetGueset";
        if (str2.isEmpty() || i3 < i2) {
            z = false;
        } else {
            EventBus.getDefault().post(str2, str3);
        }
        if (!z) {
            EventBus.getDefault().post(str, str3);
        }
        this.bBusy = false;
    }

    private synchronized void runInBackground(Runnable runnable) {
        if (this.handler != null) {
            this.handler.post(runnable);
        }
    }
}
