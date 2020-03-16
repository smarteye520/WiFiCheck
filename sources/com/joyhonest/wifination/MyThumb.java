package com.joyhonest.wifination;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import java.nio.ByteBuffer;

public class MyThumb {
    public String sFilename;
    public Bitmap thumb = Bitmap.createBitmap(160, 90, Config.ARGB_8888);

    public MyThumb(byte[] bArr, String str) {
        this.thumb.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        this.sFilename = str;
    }
}
