package com.joyhonest.wifination;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.RectF;
import com.joyhonest.wifination.Classifier.Recognition;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;
import org.tensorflow.Graph;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class JH_ObjectDetectionAPIModel implements Classifier {
    private static final Logger LOGGER = new Logger();
    private static final int MAX_RESULTS = 100;
    private byte[] byteValues;
    private TensorFlowInferenceInterface inferenceInterface;
    private int inputH;
    private String inputName;
    private int inputW;
    private int[] intValues;
    private Vector<String> labels = new Vector<>();
    private boolean logStats = false;
    private float[] outputClasses;
    private float[] outputLocations;
    private String[] outputNames;
    private float[] outputNumDetections;
    private float[] outputScores;

    public static Classifier create(AssetManager assetManager, String str, String str2, int i, int i2) throws IOException {
        JH_ObjectDetectionAPIModel jH_ObjectDetectionAPIModel = new JH_ObjectDetectionAPIModel();
        String str3 = str2.split("file:///android_asset/")[1];
        InputStream open = (str3 == null || assetManager == null) ? null : assetManager.open(str3);
        if (open == null) {
            open = new FileInputStream(str2);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            LOGGER.mo5600w(readLine, new Object[0]);
            jH_ObjectDetectionAPIModel.labels.add(readLine);
        }
        bufferedReader.close();
        jH_ObjectDetectionAPIModel.inferenceInterface = new TensorFlowInferenceInterface(assetManager, str);
        Graph graph = jH_ObjectDetectionAPIModel.inferenceInterface.graph();
        jH_ObjectDetectionAPIModel.inputName = "image_tensor";
        if (graph.operation(jH_ObjectDetectionAPIModel.inputName) != null) {
            jH_ObjectDetectionAPIModel.inputW = i;
            jH_ObjectDetectionAPIModel.inputH = i2;
            String str4 = "detection_scores";
            if (graph.operation(str4) != null) {
                String str5 = "detection_boxes";
                if (graph.operation(str5) != null) {
                    String str6 = "detection_classes";
                    if (graph.operation(str6) != null) {
                        jH_ObjectDetectionAPIModel.outputNames = new String[]{str5, str4, str6, "num_detections"};
                        int i3 = jH_ObjectDetectionAPIModel.inputW;
                        int i4 = jH_ObjectDetectionAPIModel.inputH;
                        jH_ObjectDetectionAPIModel.intValues = new int[(i3 * i4)];
                        jH_ObjectDetectionAPIModel.byteValues = new byte[(i3 * i4 * 3)];
                        jH_ObjectDetectionAPIModel.outputScores = new float[100];
                        jH_ObjectDetectionAPIModel.outputLocations = new float[400];
                        jH_ObjectDetectionAPIModel.outputClasses = new float[100];
                        jH_ObjectDetectionAPIModel.outputNumDetections = new float[1];
                        return jH_ObjectDetectionAPIModel;
                    }
                    throw new RuntimeException("Failed to find output Node 'detection_classes'");
                }
                throw new RuntimeException("Failed to find output Node 'detection_boxes'");
            }
            throw new RuntimeException("Failed to find output Node 'detection_scores'");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to find input Node '");
        sb.append(jH_ObjectDetectionAPIModel.inputName);
        sb.append("'");
        throw new RuntimeException(sb.toString());
    }

    private JH_ObjectDetectionAPIModel() {
    }

    public List<Recognition> recognizeImage(Bitmap bitmap) {
        bitmap.getPixels(this.intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int i = 0;
        while (true) {
            int[] iArr = this.intValues;
            if (i >= iArr.length) {
                break;
            }
            byte[] bArr = this.byteValues;
            int i2 = i * 3;
            bArr[i2 + 2] = (byte) (iArr[i] & 255);
            bArr[i2 + 1] = (byte) ((iArr[i] >> 8) & 255);
            bArr[i2 + 0] = (byte) ((iArr[i] >> 16) & 255);
            i++;
        }
        this.inferenceInterface.feed(this.inputName, this.byteValues, new long[]{1, (long) this.inputW, (long) this.inputH, 3});
        this.inferenceInterface.run(this.outputNames, this.logStats);
        this.outputLocations = new float[400];
        this.outputScores = new float[100];
        this.outputClasses = new float[100];
        this.outputNumDetections = new float[1];
        this.inferenceInterface.fetch(this.outputNames[0], this.outputLocations);
        this.inferenceInterface.fetch(this.outputNames[1], this.outputScores);
        this.inferenceInterface.fetch(this.outputNames[2], this.outputClasses);
        this.inferenceInterface.fetch(this.outputNames[3], this.outputNumDetections);
        PriorityQueue priorityQueue = new PriorityQueue(1, new Comparator<Recognition>() {
            public int compare(Recognition recognition, Recognition recognition2) {
                return Float.compare(recognition2.getConfidence().floatValue(), recognition.getConfidence().floatValue());
            }
        });
        for (int i3 = 0; i3 < this.outputScores.length; i3++) {
            float[] fArr = this.outputLocations;
            int i4 = i3 * 4;
            float f = fArr[i4 + 1];
            int i5 = this.inputW;
            float f2 = f * ((float) i5);
            float f3 = fArr[i4];
            int i6 = this.inputH;
            RectF rectF = new RectF(f2, f3 * ((float) i6), fArr[i4 + 3] * ((float) i5), fArr[i4 + 2] * ((float) i6));
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(i3);
            priorityQueue.add(new Recognition(sb.toString(), (String) this.labels.get((int) this.outputClasses[i3]), Float.valueOf(this.outputScores[i3]), rectF));
        }
        ArrayList arrayList = new ArrayList();
        for (int i7 = 0; i7 < Math.min(priorityQueue.size(), 100); i7++) {
            arrayList.add(priorityQueue.poll());
        }
        return arrayList;
    }

    public void enableStatLogging(boolean z) {
        this.logStats = z;
    }

    public String getStatString() {
        return this.inferenceInterface.getStatString();
    }

    public void close() {
        this.inferenceInterface.close();
    }
}
