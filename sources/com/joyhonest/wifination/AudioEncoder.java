package com.joyhonest.wifination;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioEncoder implements AudioCodec {
    private final String TAG = "AudioEncoder";
    private byte[] mFrameByte;
    private Worker mWorker;

    private class Worker extends Thread {
        private boolean isRunning;
        private byte[] mBuffer;
        BufferInfo mBufferInfo;
        private MediaCodec mEncoder;
        private int mFrameSize;
        private AudioRecord mRecord;

        private Worker() {
            this.mFrameSize = 2048;
            this.isRunning = false;
        }

        public void run() {
            if (!prepare()) {
                Log.d("AudioEncoder", "音频编码器初始化失败");
                this.isRunning = false;
            }
            while (this.isRunning) {
                this.mRecord.read(this.mBuffer, 0, this.mFrameSize);
                encode(this.mBuffer);
            }
            release();
        }

        public void setRunning(boolean z) {
            this.isRunning = z;
        }

        private void release() {
            MediaCodec mediaCodec = this.mEncoder;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mEncoder.release();
            }
            AudioRecord audioRecord = this.mRecord;
            if (audioRecord != null) {
                audioRecord.stop();
                this.mRecord.release();
                this.mRecord = null;
            }
        }

        private boolean prepare() {
            String str = AudioCodec.MIME_TYPE;
            try {
                this.mBufferInfo = new BufferInfo();
                this.mEncoder = MediaCodec.createEncoderByType(str);
                MediaFormat createAudioFormat = MediaFormat.createAudioFormat(str, AudioCodec.KEY_SAMPLE_RATE, 1);
                createAudioFormat.setInteger("bitrate", AudioCodec.KEY_BIT_RATE);
                createAudioFormat.setInteger("sample-rate", AudioCodec.KEY_SAMPLE_RATE);
                createAudioFormat.setInteger("channel-count", 1);
                createAudioFormat.setInteger("aac-profile", 2);
                this.mEncoder.configure(createAudioFormat, null, null, 1);
                this.mEncoder.start();
                int minBufferSize = AudioRecord.getMinBufferSize(AudioCodec.KEY_SAMPLE_RATE, 16, 2) * 2;
                AudioRecord audioRecord = new AudioRecord(1, AudioCodec.KEY_SAMPLE_RATE, 16, 2, minBufferSize);
                this.mRecord = audioRecord;
                this.mFrameSize = Math.min(4096, minBufferSize);
                this.mBuffer = new byte[this.mFrameSize];
                this.mRecord.startRecording();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private void encode(byte[] bArr) {
            ByteBuffer[] inputBuffers = this.mEncoder.getInputBuffers();
            ByteBuffer[] outputBuffers = this.mEncoder.getOutputBuffers();
            int dequeueInputBuffer = this.mEncoder.dequeueInputBuffer(50000);
            if (dequeueInputBuffer >= 0) {
                inputBuffers[dequeueInputBuffer].put(bArr, 0, bArr.length);
                this.mEncoder.queueInputBuffer(dequeueInputBuffer, 0, bArr.length, 1, 0);
            }
            BufferInfo bufferInfo = new BufferInfo();
            int dequeueOutputBuffer = this.mEncoder.dequeueOutputBuffer(bufferInfo, 10000);
            while (dequeueOutputBuffer >= 0) {
                ByteBuffer byteBuffer = outputBuffers[dequeueOutputBuffer];
                byteBuffer.rewind();
                byte[] bArr2 = new byte[bufferInfo.size];
                byteBuffer.get(bArr2, 0, bArr2.length);
                AudioEncoder.naSentVoiceData(bArr2, bufferInfo.size);
                this.mEncoder.releaseOutputBuffer(dequeueOutputBuffer, false);
                dequeueOutputBuffer = this.mEncoder.dequeueOutputBuffer(bufferInfo, 0);
            }
        }

        private void addADTStoPacket(byte[] bArr, int i) {
            bArr[0] = -1;
            bArr[1] = -7;
            bArr[2] = (byte) 80;
            bArr[3] = (byte) (128 + (i >> 11));
            bArr[4] = (byte) ((i & 2047) >> 3);
            bArr[5] = (byte) (((i & 7) << 5) + 31);
            bArr[6] = -4;
        }
    }

    /* access modifiers changed from: private */
    public static native boolean naSentVoiceData(byte[] bArr, int i);

    public void start() {
        if (this.mWorker == null) {
            this.mWorker = new Worker();
            this.mWorker.setRunning(true);
            this.mWorker.start();
        }
    }

    public void stop() {
        Worker worker = this.mWorker;
        if (worker != null) {
            worker.setRunning(false);
            this.mWorker = null;
        }
    }
}
