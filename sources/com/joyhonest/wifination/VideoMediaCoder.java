package com.joyhonest.wifination;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.util.Log;
import java.nio.ByteBuffer;

public class VideoMediaCoder {
    private static final String TAG = "MediaCoder";
    private static final String VCODEC = "video/avc";
    private boolean bGetPPS = false;
    int ddd = 0;
    int fps;
    private MediaCodec mMediaCodec = null;
    long pts;

    private MediaFormat F_GetMediaFormat(int i, int i2, int i3, int i4, int i5) {
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(VCODEC, i, i2);
        createVideoFormat.setInteger("width", i);
        createVideoFormat.setInteger("height", i2);
        createVideoFormat.setInteger("bitrate", i3);
        createVideoFormat.setInteger("frame-rate", i4);
        createVideoFormat.setInteger("color-format", i5);
        createVideoFormat.setInteger("i-frame-interval", 2);
        try {
            this.mMediaCodec.configure(createVideoFormat, null, null, 1);
            return createVideoFormat;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int initMediaCodec(int r17, int r18, int r19, int r20) {
        /*
            r16 = this;
            r7 = r16
            r8 = r20
            java.lang.String r9 = "video/avc"
            android.media.MediaCodec r0 = r7.mMediaCodec
            r10 = 0
            r12 = 0
            if (r0 == 0) goto L_0x0019
            r0.stop()
            android.media.MediaCodec r0 = r7.mMediaCodec
            r0.release()
            r7.mMediaCodec = r12
            r7.pts = r10
        L_0x0019:
            r13 = 0
            r7.bGetPPS = r13
            r7.pts = r10
            r7.fps = r8
            r14 = 1
            android.media.MediaCodec r0 = android.media.MediaCodec.createEncoderByType(r9)     // Catch:{ IOException -> 0x002d, IllegalArgumentException -> 0x002b, NullPointerException -> 0x0029 }
            r7.mMediaCodec = r0     // Catch:{ IOException -> 0x002d, IllegalArgumentException -> 0x002b, NullPointerException -> 0x0029 }
            r0 = 1
            goto L_0x003b
        L_0x0029:
            r0 = move-exception
            goto L_0x002f
        L_0x002b:
            r0 = move-exception
            goto L_0x0033
        L_0x002d:
            r0 = move-exception
            goto L_0x0037
        L_0x002f:
            r0.printStackTrace()
            goto L_0x003a
        L_0x0033:
            r0.printStackTrace()
            goto L_0x003a
        L_0x0037:
            r0.printStackTrace()
        L_0x003a:
            r0 = 0
        L_0x003b:
            r15 = 524287(0x7ffff, float:7.34683E-40)
            if (r0 != 0) goto L_0x0043
            r7.mMediaCodec = r12
            return r15
        L_0x0043:
            r0 = 21
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r0
            android.media.MediaFormat r1 = r1.F_GetMediaFormat(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x009e
            android.media.MediaCodec r0 = r7.mMediaCodec
            if (r0 == 0) goto L_0x0066
            r0.stop()
            android.media.MediaCodec r0 = r7.mMediaCodec
            r0.release()
            r7.mMediaCodec = r12
            r7.pts = r10
        L_0x0066:
            r7.bGetPPS = r13
            r7.pts = r10
            r7.fps = r8
            android.media.MediaCodec r0 = android.media.MediaCodec.createEncoderByType(r9)     // Catch:{ IOException -> 0x0077, IllegalArgumentException -> 0x0075, NullPointerException -> 0x0073 }
            r7.mMediaCodec = r0     // Catch:{ IOException -> 0x0077, IllegalArgumentException -> 0x0075, NullPointerException -> 0x0073 }
            goto L_0x0085
        L_0x0073:
            r0 = move-exception
            goto L_0x0079
        L_0x0075:
            r0 = move-exception
            goto L_0x007d
        L_0x0077:
            r0 = move-exception
            goto L_0x0081
        L_0x0079:
            r0.printStackTrace()
            goto L_0x0084
        L_0x007d:
            r0.printStackTrace()
            goto L_0x0084
        L_0x0081:
            r0.printStackTrace()
        L_0x0084:
            r14 = 0
        L_0x0085:
            if (r14 != 0) goto L_0x008a
            r7.mMediaCodec = r12
            return r15
        L_0x008a:
            r0 = 19
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r0
            android.media.MediaFormat r1 = r1.F_GetMediaFormat(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x009e
            r0 = 0
        L_0x009e:
            if (r0 == 0) goto L_0x00a6
            android.media.MediaCodec r1 = r7.mMediaCodec
            r1.start()
            goto L_0x00ad
        L_0x00a6:
            android.media.MediaCodec r1 = r7.mMediaCodec
            r1.release()
            r7.mMediaCodec = r12
        L_0x00ad:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.joyhonest.wifination.VideoMediaCoder.initMediaCodec(int, int, int, int):int");
    }

    public void F_CloseEncoder() {
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.mMediaCodec.release();
            this.mMediaCodec = null;
            this.bGetPPS = false;
        }
    }

    public void offerEncoder(byte[] bArr, int i) {
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
            ByteBuffer[] outputBuffers = this.mMediaCodec.getOutputBuffers();
            int dequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(5000);
            if (dequeueInputBuffer >= 0) {
                long j = this.pts;
                long j2 = j * ((long) (1000000 / this.fps));
                this.pts = j + 1;
                ByteBuffer byteBuffer = inputBuffers[dequeueInputBuffer];
                byteBuffer.clear();
                byteBuffer.put(bArr);
                this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, bArr.length, j2, 0);
                BufferInfo bufferInfo = new BufferInfo();
                int dequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(bufferInfo, 2000);
                if (dequeueOutputBuffer >= 0) {
                    byte[] bArr2 = new byte[bufferInfo.size];
                    outputBuffers[dequeueOutputBuffer].get(bArr2);
                    if (bufferInfo.flags == 2) {
                        if (!this.bGetPPS) {
                            this.bGetPPS = true;
                            C0300wifination.naSave2FrameMp4(bArr2, bufferInfo.size, 0, false);
                        }
                    } else if (bufferInfo.flags == 1) {
                        C0300wifination.naSave2FrameMp4(bArr2, bufferInfo.size, 1, true);
                    } else {
                        C0300wifination.naSave2FrameMp4(bArr2, bufferInfo.size, 1, false);
                    }
                    this.mMediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                }
            }
        }
    }

    private MediaCodecInfo chooseVideoEncoder(String str, MediaCodecInfo mediaCodecInfo) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (String equalsIgnoreCase : supportedTypes) {
                    if (equalsIgnoreCase.equalsIgnoreCase(VCODEC) && (str == null || codecInfoAt.getName().contains(str))) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return mediaCodecInfo;
    }

    private int chooseVideoEncoder() {
        String str;
        MediaCodecInfo chooseVideoEncoder = chooseVideoEncoder(null, null);
        CodecCapabilities capabilitiesForType = chooseVideoEncoder.getCapabilitiesForType(VCODEC);
        int i = 0;
        int i2 = 0;
        while (true) {
            int length = capabilitiesForType.colorFormats.length;
            str = TAG;
            if (i >= length) {
                break;
            }
            int i3 = capabilitiesForType.colorFormats[i];
            Log.i(str, String.format("vencoder %s supports color fomart 0x%x(%d)", new Object[]{chooseVideoEncoder.getName(), Integer.valueOf(i3), Integer.valueOf(i3)}));
            if (i3 >= 19 && i3 <= 21 && i3 > i2) {
                i2 = i3;
            }
            i++;
        }
        for (CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
            Log.i(str, String.format("vencoder %s support profile %d, level %d", new Object[]{chooseVideoEncoder.getName(), Integer.valueOf(codecProfileLevel.profile), Integer.valueOf(codecProfileLevel.level)}));
        }
        Log.i(str, String.format("vencoder %s choose color format 0x%x(%d)", new Object[]{chooseVideoEncoder.getName(), Integer.valueOf(i2), Integer.valueOf(i2)}));
        return i2;
    }
}
