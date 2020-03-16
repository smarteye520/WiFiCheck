package com.joyhonest.wifination;

import android.graphics.Bitmap;
import android.graphics.RectF;
import java.util.List;

public interface Classifier {

    public static class Recognition {
        private final Float confidence;

        /* renamed from: id */
        private final String f24id;
        private RectF location;
        private final String title;

        public Recognition(String str, String str2, Float f, RectF rectF) {
            this.f24id = str;
            this.title = str2;
            this.confidence = f;
            this.location = rectF;
        }

        public String getId() {
            return this.f24id;
        }

        public String getTitle() {
            return this.title;
        }

        public Float getConfidence() {
            return this.confidence;
        }

        public RectF getLocation() {
            return new RectF(this.location);
        }

        public void setLocation(RectF rectF) {
            this.location = rectF;
        }

        public String toString() {
            String str = "";
            if (this.f24id != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("[");
                sb.append(this.f24id);
                sb.append("] ");
                str = sb.toString();
            }
            String str2 = " ";
            if (this.title != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(this.title);
                sb2.append(str2);
                str = sb2.toString();
            }
            if (this.confidence != null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(String.format("(%.1f%%) ", new Object[]{Float.valueOf(this.confidence.floatValue() * 100.0f)}));
                str = sb3.toString();
            }
            if (this.location != null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(this.location);
                sb4.append(str2);
                str = sb4.toString();
            }
            return str.trim();
        }
    }

    void close();

    void enableStatLogging(boolean z);

    String getStatString();

    List<Recognition> recognizeImage(Bitmap bitmap);
}
