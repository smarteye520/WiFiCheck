package com.joyhonest.wifination;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.p000v4.internal.view.SupportMenu;
import android.util.Log;
import java.nio.ByteBuffer;
import org.simple.eventbus.EventBus;

/* renamed from: com.joyhonest.wifination.wifination */
public class C0300wifination {
    public static AudioEncoder AudioEncoder = null;
    private static final int BMP_Len = 8295424;
    private static final int CmdLen = 1024;
    public static final int IC_GK = 0;
    public static final int IC_GKA = 3;
    public static final int IC_GK_UDP = 9;
    public static final int IC_GP = 1;
    public static final int IC_GPH264 = 5;
    public static final int IC_GPH264A = 7;
    public static final int IC_GPRTP = 6;
    public static final int IC_GPRTPB = 8;
    public static final int IC_GPRTSP = 4;
    public static final int IC_NO = -1;
    public static final int IC_SN = 2;
    private static final String TAG = "wifination";
    public static final int TYPE_BOTH_PHONE_SD = 3;
    public static final int TYPE_ONLY_PHONE = 0;
    public static final int TYPE_ONLY_SD = 1;
    public static final int TYPE_PHOTOS = 0;
    public static final int TYPE_VIDEOS = 1;
    public static Context appContext = null;
    public static boolean bDisping = false;
    public static boolean bGesture = false;
    public static boolean bRevBmp = false;
    public static ByteBuffer mDirectBuffer;
    private static final C0300wifination m_Instance = new C0300wifination();
    private static ObjectDetector sig = null;
    private static VideoMediaCoder videoMediaCoder;

    private static void RevTestInfo(byte[] bArr) {
    }

    public static native void changeLayout(int i, int i2);

    public static native void drawFrame();

    public static native void init();

    public static native boolean isPhoneRecording();

    public static native int naCancelDownload();

    public static native int naCancelGetThumb();

    public static native int naCancelRTL();

    public static native boolean naCheckDevice();

    public static native int naDeleteSDFile(String str);

    public static native int naDownLoadRtlFile(String str);

    public static native int naDownloadFile(String str, String str2);

    public static native void naFillFlyCmdByC(int i);

    public static native String naGetControlType();

    public static native int naGetFiles(int i);

    public static native int naGetFps();

    public static native int naGetGP_RTSP_Status();

    public static native void naGetLedPWM();

    public static native int naGetPhotoDir();

    public static native int naGetRecordTime();

    public static native int naGetRtl_List(int i, int i2);

    public static native int naGetRtl_Mode();

    public static native int naGetSessionId();

    public static native int naGetSettings();

    public static native int naGetThumb(String str);

    public static native int naGetVideoDir();

    public static native int naGetwifiFps();

    public static native int naGkASetRecordResolution(boolean z);

    public static native int naInit(String str);

    public static native int naPlay();

    public static native void naReadDataFromFlash();

    public static native int naRemoteSaveVideo();

    public static native int naRemoteSnapshot();

    public static native void naRotation(int i);

    public static native int naSave2FrameMp4(byte[] bArr, int i, int i2, boolean z);

    public static native int naSaveSnapshot(String str);

    public static native int naSaveVideo(String str);

    public static native int naSentCmd(byte[] bArr, int i);

    public static native boolean naSentUdpData(String str, int i, byte[] bArr, int i2);

    public static native void naSet3D(boolean z);

    public static native void naSet3DA(boolean z);

    public static native void naSetAdjFps(boolean z);

    public static native boolean naSetBackground(byte[] bArr, int i, int i2);

    public static native void naSetContinue();

    public static native void naSetCustomer(String str);

    public static native void naSetDebug(boolean z);

    private static native void naSetDirectBuffer(Object obj, int i);

    private static native void naSetDirectBufferYUV(Object obj, int i);

    public static native void naSetDislplayData(byte[] bArr, int i, int i2);

    public static native void naSetDispStyle(int i);

    public static native void naSetFlip(boolean z);

    public static native void naSetFollow(boolean z);

    public static native void naSetGKA_SentCmdByUDP(boolean z);

    public static native int naSetGPFps(int i);

    private static native void naSetGestureA(boolean z);

    public static native void naSetLedOnOff(boolean z);

    public static native void naSetLedPWM(byte b);

    public static native int naSetMenuFilelanguage(int i);

    public static native void naSetMirror(boolean z);

    public static native void naSetNoTimeOut(boolean z);

    public static native void naSetRecordAudio(boolean z);

    public static native int naSetRecordWH(int i, int i2);

    private static native void naSetRevBmpA(boolean z);

    public static native void naSetScal(float f);

    public static native void naSetSnapPhoto(int i, int i2, boolean z);

    public static native void naSetVrBackground(boolean z);

    public static native boolean naSetWifiPassword(String str);

    public static native void naSetbRotaHV(boolean z);

    public static native void naSetdispRect(int i, int i2);

    public static native int naSnapPhoto(String str, int i);

    public static native int naStartCheckSDStatus(boolean z);

    public static native boolean naStartReadUdp(int i);

    public static native int naStartRecord(String str, int i);

    public static native int naStatus();

    public static native int naStop();

    public static native boolean naStopReadUdp();

    public static native void naStopRecord(int i);

    public static native int naStopRecord_All();

    public static native int naStopSaveVideo();

    public static native void naWriteData2Flash(byte[] bArr, int i);

    public static native void naWriteport20000(byte[] bArr, int i);

    public static native void release();

    static {
        try {
            System.loadLibrary("jh_wifi");
            AudioEncoder = new AudioEncoder();
            videoMediaCoder = new VideoMediaCoder();
            mDirectBuffer = ByteBuffer.allocateDirect(8296448);
            naSetDirectBuffer(mDirectBuffer, 8296448);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Cannot load jh_wifi.so ...");
            e.printStackTrace();
        }
        return;
    }

    private C0300wifination() {
    }

    public static C0300wifination getInstance() {
        return m_Instance;
    }

    public static void naSetRevBmp(boolean z) {
        bRevBmp = z;
        naSetRevBmpA(z);
    }

    public static void naSetGesture(boolean z, Context context) {
        bGesture = z;
        if (bGesture && sig == null) {
            sig = ObjectDetector.getInstance();
            sig.SetAppCentext(context);
        }
        ObjectDetector objectDetector = sig;
        if (objectDetector != null) {
            objectDetector.F_Start(bGesture);
        }
        naSetGestureA(z);
    }

    public static void naSetCmdResType(int i) {
        JH_Tools.F_SetResType(i);
    }

    public static void naSetGesture_vol(float f) {
        ObjectDetector.MINIMUM_CONFIDENCE_TF_OD_API = f;
    }

    private static void onUdpRevData(byte[] bArr) {
        EventBus.getDefault().post(bArr, "onUdpRevData");
    }

    private static void G_StartAudio(int i) {
        if (i != 0) {
            AudioEncoder.start();
        } else {
            AudioEncoder.stop();
        }
    }

    public static void F_AdjBackGround(Context context, int i) {
        Bitmap bitmap;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), i, options);
        int i2 = options.outHeight;
        int i3 = options.outWidth;
        if (i3 > 640 || i2 > 480) {
            int i4 = i3 / 640;
            if (i4 <= 0) {
                i4 = 2;
            }
            options.inSampleSize = i4;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(context.getResources(), i, options);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), i);
        }
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i5 = ((width + 7) / 8) * 8;
            int i6 = ((height + 7) / 8) * 8;
            Bitmap createBitmap = Bitmap.createBitmap(i5, i6, Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Matrix transformationMatrix = ImageUtils.getTransformationMatrix(width, height, i5, i6, 0, false);
            transformationMatrix.invert(new Matrix());
            canvas.drawBitmap(bitmap, transformationMatrix, null);
            bitmap.recycle();
            int width2 = createBitmap.getWidth();
            int height2 = createBitmap.getHeight();
            ByteBuffer allocate = ByteBuffer.allocate(createBitmap.getByteCount());
            createBitmap.copyPixelsToBuffer(allocate);
            naSetBackground(allocate.array(), width2, height2);
            createBitmap.recycle();
        }
    }

    private static int getIP() {
        Context context = appContext;
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    return connectionInfo.getIpAddress();
                }
            }
        }
        return 0;
    }

    public static void naInitgl(Context context, int i) {
        init();
    }

    private static void OnSave2ToGallery(String str, int i) {
        EventBus.getDefault().post(String.format("%02d%s", new Object[]{Integer.valueOf(i), str}), "SavePhotoOK");
    }

    private static void OnGetWifiData(byte[] bArr) {
        JH_Tools.AdjData(bArr);
        JH_Tools.FindCmd();
        JH_Tools.F_ClearData();
    }

    private static void OnGetGP_Status(int i) {
        int i2 = 0;
        switch ((i >> 16) & SupportMenu.USER_MASK) {
            case 6:
                EventBus.getDefault().post(Integer.valueOf(i & 15), "OnGetSetStyle");
                return;
            case 4128:
                EventBus.getDefault().post(Integer.valueOf(i & 255), "OnGetPwmData");
                return;
            case 4129:
            case 65534:
                EventBus.getDefault().post(Integer.valueOf(i & 15), "OnGetBatteryLevel");
                return;
            case 8192:
                int i3 = i & 255;
                byte[] bArr = new byte[i3];
                ByteBuffer byteBuffer = mDirectBuffer;
                while (i2 < i3) {
                    bArr[i2] = byteBuffer.get(i2 + BMP_Len);
                    i2++;
                }
                EventBus.getDefault().post(bArr, "GetWifiInfoData");
                return;
            case 12293:
                int i4 = i & SupportMenu.USER_MASK;
                if (i4 > 1024) {
                    i4 = 1024;
                }
                byte[] bArr2 = new byte[i4];
                ByteBuffer byteBuffer2 = mDirectBuffer;
                while (i2 < i4) {
                    bArr2[i2] = byteBuffer2.get(i2 + BMP_Len);
                    i2++;
                }
                EventBus.getDefault().post(bArr2, "ReadDataFromFlash");
                return;
            case 12294:
                int i5 = 1;
                if ((i & 255) != 1) {
                    i5 = 0;
                }
                EventBus.getDefault().post(Integer.valueOf(i5), "WriteData2FlashResult");
                return;
            case 21571:
                int i6 = i & 255;
                byte[] bArr3 = new byte[i6];
                ByteBuffer byteBuffer3 = mDirectBuffer;
                while (i2 < i6) {
                    bArr3[i2] = byteBuffer3.get(i2 + BMP_Len);
                    i2++;
                }
                EventBus.getDefault().post(bArr3, "GetWifiSendData");
                return;
            case 64507:
                int i7 = i & SupportMenu.USER_MASK;
                byte[] bArr4 = new byte[i7];
                ByteBuffer byteBuffer4 = mDirectBuffer;
                while (i2 < i7) {
                    bArr4[i2] = byteBuffer4.get(i2 + BMP_Len);
                    i2++;
                }
                EventBus.getDefault().post(bArr4, "GetDataFromWifi");
                return;
            case 65532:
                EventBus.getDefault().post(Integer.valueOf(i & 255), "OnGetGP_Status");
                return;
            case SupportMenu.USER_MASK /*65535*/:
                int i8 = i & SupportMenu.USER_MASK;
                if (i8 > 1024) {
                    i8 = 1024;
                }
                byte[] bArr5 = new byte[i8];
                ByteBuffer byteBuffer5 = mDirectBuffer;
                while (i2 < i8) {
                    bArr5[i2] = byteBuffer5.get(i2 + BMP_Len);
                    i2++;
                }
                EventBus.getDefault().post(bArr5, "GetDataFromRs232");
                return;
            default:
                return;
        }
    }

    private static void GetFiles(byte[] bArr) {
        EventBus.getDefault().post(new String(bArr), "GetFiles");
    }

    private static void OnStatusChamnge(int i) {
        EventBus.getDefault().post(Integer.valueOf(i), "SDStatus_Changed");
    }

    private static void DownloadFile_callback(int i, String str, int i2) {
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("downloading  ");
            sb.append(i);
            sb.append("%     ");
            sb.append(str);
            Log.e("downloading", sb.toString());
        }
        EventBus.getDefault().post(new jh_dowload_callback(i, str, i2), "DownloadFile");
    }

    private static void GetThumb(byte[] bArr, String str) {
        if (bArr != null) {
            EventBus.getDefault().post(new MyThumb(bArr, str), "GetThumb");
        }
    }

    private static void OnKeyPress(int i) {
        Integer valueOf = Integer.valueOf(i);
        StringBuilder sb = new StringBuilder();
        sb.append("Key = ");
        sb.append(i);
        Log.v("GKey", sb.toString());
        EventBus.getDefault().post(valueOf, "key_Press");
        EventBus.getDefault().post(valueOf, "Key_Pressed");
    }

    private static void ReceiveBmp(int i) {
        Bitmap createBitmap = Bitmap.createBitmap(i & SupportMenu.USER_MASK, (i >> 16) & SupportMenu.USER_MASK, Config.ARGB_8888);
        ByteBuffer byteBuffer = mDirectBuffer;
        byteBuffer.rewind();
        createBitmap.copyPixelsFromBuffer(byteBuffer);
        if (bRevBmp) {
            EventBus.getDefault().post(createBitmap, "ReviceBMP");
        }
        if (bGesture) {
            ObjectDetector objectDetector = sig;
            if (objectDetector != null) {
                objectDetector.GetNumber(createBitmap);
            }
        }
    }

    private static int F_InitEncoder(int i, int i2, int i3, int i4) {
        return videoMediaCoder.initMediaCodec(i, i2, i3, i4);
    }

    private static void offerEncoder(byte[] bArr, int i) {
        VideoMediaCoder videoMediaCoder2 = videoMediaCoder;
        if (videoMediaCoder2 != null) {
            videoMediaCoder2.offerEncoder(bArr, i);
        }
    }

    private static void F_CloseEncoder() {
        VideoMediaCoder videoMediaCoder2 = videoMediaCoder;
        if (videoMediaCoder2 != null) {
            videoMediaCoder2.F_CloseEncoder();
        }
    }

    private static void onReadRtlData(byte[] bArr) {
        if (bArr.length == 8) {
            String str = new String(bArr);
            int i = str.equals("REMODE0;") ? 0 : -1;
            if (str.equals("REMODE1;")) {
                i = 1;
            }
            if (i != -1) {
                EventBus.getDefault().post(Integer.valueOf(i), "onGetRtlMode");
                return;
            }
        }
        EventBus.getDefault().post(bArr, "onReadRtlData");
    }
}
