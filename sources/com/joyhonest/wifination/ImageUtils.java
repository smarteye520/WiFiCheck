package com.joyhonest.wifination;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.p000v4.view.MotionEventCompat;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils {
    private static final Logger LOGGER = new Logger();
    static final int kMaxChannelValue = 262143;
    private static boolean useNativeConversion = true;

    private static int YUV2RGB(int i, int i2, int i3) {
        int i4 = i - 16;
        if (i4 < 0) {
            i4 = 0;
        }
        int i5 = i2 - 128;
        int i6 = i3 - 128;
        int i7 = i4 * 1192;
        int i8 = (i6 * 1634) + i7;
        int i9 = (i7 - (i6 * 833)) - (i5 * 400);
        int i10 = i7 + (i5 * 2066);
        if (i8 > kMaxChannelValue) {
            i8 = kMaxChannelValue;
        } else if (i8 < 0) {
            i8 = 0;
        }
        if (i9 > kMaxChannelValue) {
            i9 = kMaxChannelValue;
        } else if (i9 < 0) {
            i9 = 0;
        }
        if (i10 > kMaxChannelValue) {
            i10 = kMaxChannelValue;
        } else if (i10 < 0) {
            i10 = 0;
        }
        return ((i10 >> 10) & 255) | -16777216 | ((i8 << 6) & 16711680) | ((i9 >> 2) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    private static native void convertARGB8888ToYUV420SP(int[] iArr, byte[] bArr, int i, int i2);

    private static native void convertRGB565ToYUV420SP(byte[] bArr, byte[] bArr2, int i, int i2);

    private static native void convertYUV420SPToARGB8888(byte[] bArr, int[] iArr, int i, int i2, boolean z);

    private static native void convertYUV420SPToRGB565(byte[] bArr, byte[] bArr2, int i, int i2);

    private static native void convertYUV420ToARGB8888(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr, int i, int i2, int i3, int i4, int i5, boolean z);

    static {
        try {
            System.loadLibrary("jh_wifi");
        } catch (UnsatisfiedLinkError unused) {
            LOGGER.mo5600w("Native library not found, native RGB -> YUV conversion may be unavailable.", new Object[0]);
        }
    }

    public static int getYUVByteSize(int i, int i2) {
        return (i * i2) + (((i + 1) / 2) * ((i2 + 1) / 2) * 2);
    }

    public static void saveBitmap(Bitmap bitmap) {
        saveBitmap(bitmap, "preview.png");
    }

    public static void saveBitmap(Bitmap bitmap, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append("tensorflow");
        String sb2 = sb.toString();
        LOGGER.mo5594i("Saving %dx%d bitmap to %s.", Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()), sb2);
        File file = new File(sb2);
        if (!file.mkdirs()) {
            LOGGER.mo5594i("Make dir failed", new Object[0]);
        }
        File file2 = new File(file, str);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.PNG, 99, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            LOGGER.mo5593e(e, "Exception!", new Object[0]);
        }
    }

    public static void convertYUV420SPToARGB8888(byte[] bArr, int i, int i2, int[] iArr) {
        if (useNativeConversion) {
            try {
                convertYUV420SPToARGB8888(bArr, iArr, i, i2, false);
                return;
            } catch (UnsatisfiedLinkError unused) {
                LOGGER.mo5600w("Native YUV420SP -> RGB implementation not found, falling back to Java implementation", new Object[0]);
                useNativeConversion = false;
            }
        }
        int i3 = i * i2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = ((i4 >> 1) * i) + i3;
            byte b = 0;
            byte b2 = 0;
            int i7 = i5;
            int i8 = 0;
            while (i8 < i) {
                byte b3 = bArr[i7] & 255;
                if ((i8 & 1) == 0) {
                    int i9 = i6 + 1;
                    int i10 = i9 + 1;
                    b = bArr[i9] & 255;
                    int i11 = i10;
                    b2 = bArr[i6] & 255;
                    i6 = i11;
                }
                iArr[i7] = YUV2RGB(b3, b, b2);
                i8++;
                i7++;
            }
            i4++;
            i5 = i7;
        }
    }

    public static void convertYUV420ToARGB8888(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (useNativeConversion) {
            try {
                convertYUV420ToARGB8888(bArr, bArr2, bArr3, iArr, i, i2, i3, i4, i5, false);
                return;
            } catch (UnsatisfiedLinkError unused) {
                LOGGER.mo5600w("Native YUV420 -> RGB implementation not found, falling back to Java implementation", new Object[0]);
                useNativeConversion = false;
            }
        }
        int i6 = i2;
        int i7 = 0;
        int i8 = 0;
        while (i7 < i6) {
            int i9 = i3 * i7;
            int i10 = (i7 >> 1) * i4;
            int i11 = i8;
            int i12 = 0;
            int i13 = i;
            while (i12 < i13) {
                int i14 = ((i12 >> 1) * i5) + i10;
                int i15 = i11 + 1;
                iArr[i11] = YUV2RGB(bArr[i9 + i12] & 255, bArr2[i14] & 255, bArr3[i14] & 255);
                i12++;
                i11 = i15;
            }
            i7++;
            i8 = i11;
        }
    }

    public static Matrix getTransformationMatrix(int i, int i2, int i3, int i4, int i5, boolean z) {
        Matrix matrix = new Matrix();
        boolean z2 = true;
        if (i5 != 0) {
            if (i5 % 90 != 0) {
                LOGGER.mo5600w("Rotation of %d % 90 != 0", Integer.valueOf(i5));
            }
            matrix.postTranslate(((float) (-i)) / 2.0f, ((float) (-i2)) / 2.0f);
            matrix.postRotate((float) i5);
        }
        if ((Math.abs(i5) + 90) % 180 != 0) {
            z2 = false;
        }
        int i6 = z2 ? i2 : i;
        if (!z2) {
            i = i2;
        }
        if (!(i6 == i3 && i == i4)) {
            float f = ((float) i3) / ((float) i6);
            float f2 = ((float) i4) / ((float) i);
            if (z) {
                float max = Math.max(f, f2);
                matrix.postScale(max, max);
            } else {
                matrix.postScale(f, f2);
            }
        }
        if (i5 != 0) {
            matrix.postTranslate(((float) i3) / 2.0f, ((float) i4) / 2.0f);
        }
        return matrix;
    }
}
