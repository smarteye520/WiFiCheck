package com.joyhonest.wifi_check;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.media.SoundPool.Builder;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.util.Log;
import android.view.Window;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApp extends Application {
    public static int Brow_Photo = 0;
    public static int Brow_Video = 1;
    private static int music_Mid = -1;
    private static int music_butn = -1;
    private static int music_photo = -1;
    public static int nBrowType = Brow_Photo;
    public static int nStatus;
    public static String sLocalPhoto;
    public static String sLocalVideo;
    public static String sRemotePhoto;
    public static String sRemoteVideo;
    private static MyApp singleton;
    private static SoundPool soundPool;

    public static void F_PlayBtnVoice() {
    }

    public void onCreate() {
        super.onCreate();
        singleton = this;
        if (VERSION.SDK_INT >= 21) {
            Builder builder = new Builder();
            builder.setMaxStreams(3);
            AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
            builder2.setLegacyStreamType(3);
            builder.setAudioAttributes(builder2.build());
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(3, 3, 0);
        }
        music_Mid = soundPool.load(this, C0287R.raw.mid, 1);
        music_butn = soundPool.load(this, C0287R.raw.button46, 1);
        music_photo = soundPool.load(this, C0287R.raw.photo_m, 1);
    }

    public MyApp getSingleton() {
        return singleton;
    }

    public static void F_CreateLocalDir(String str) {
        if (str.length() != 0) {
            try {
                String normalSDCardPath = Storage.getNormalSDCardPath();
                if (normalSDCardPath.length() == 0) {
                    normalSDCardPath = Storage.getNormalSDCardPath();
                }
                StringBuilder sb = new StringBuilder();
                String str2 = "%s/%s/";
                sb.append(str2);
                sb.append(str);
                sb.append("_Video");
                String format = String.format(sb.toString(), new Object[]{normalSDCardPath, str});
                sLocalVideo = format;
                File file = new File(format);
                if (!file.exists()) {
                    file.mkdirs();
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(str);
                sb2.append("_Photo");
                String format2 = String.format(sb2.toString(), new Object[]{normalSDCardPath, str});
                sLocalPhoto = format2;
                File file2 = new File(format2);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
            } catch (Exception unused) {
            }
        }
    }

    public static void checkDeviceHasNavigationBar(Context context) {
        Window window = ((Activity) context).getWindow();
        window.setFlags(1024, 1024);
        window.setFlags(128, 128);
        if (VERSION.SDK_INT < 19) {
            window.getDecorView().setSystemUiVisibility(8);
            return;
        }
        window.setFlags(67108864, 67108864);
        window.setFlags(134217728, 134217728);
        window.getDecorView().setSystemUiVisibility(4102);
    }

    public static void F_Save2ToGallery(String str, boolean z) {
        Context applicationContext = singleton.getApplicationContext();
        try {
            ContentResolver contentResolver = applicationContext.getContentResolver();
            ContentValues contentValues = new ContentValues();
            String str2 = "_data";
            String str3 = "mime_type";
            if (z) {
                contentValues.put(str3, "image/jpeg");
                contentValues.put(str2, str);
                contentResolver.insert(Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                contentValues.put(str3, "video/mp4");
                contentValues.put(str2, str);
                contentResolver.insert(Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("file://");
            sb.append(str);
            applicationContext.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(sb.toString())));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteImage(String str) {
        Cursor cursor;
        Context applicationContext = singleton.getApplicationContext();
        String substring = str.substring(str.lastIndexOf(".") + 1);
        ContentResolver contentResolver = applicationContext.getContentResolver();
        String str2 = "_id";
        if (substring.equalsIgnoreCase("jpg") || substring.equalsIgnoreCase("png")) {
            ContentResolver contentResolver2 = contentResolver;
            Uri uri = Media.EXTERNAL_CONTENT_URI;
            cursor = contentResolver2.query(uri, new String[]{str2}, "_data=?", new String[]{str}, null);
        } else {
            ContentResolver contentResolver3 = contentResolver;
            Uri uri2 = Video.Media.EXTERNAL_CONTENT_URI;
            cursor = contentResolver3.query(uri2, new String[]{str2}, "_data=?", new String[]{str}, null);
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int delete = contentResolver.delete(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, cursor.getLong(0)), null, null);
            }
            File file = new File(str);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    public static String getFileNameFromDate(boolean z, boolean z2) {
        String str;
        String format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        if (z2) {
            str = sLocalPhoto;
            if (z) {
                str = sLocalVideo;
            }
        } else {
            str = sRemotePhoto;
            if (z) {
                str = sRemoteVideo;
            }
        }
        if (str == null) {
            return null;
        }
        String str2 = !z ? ".png" : ".mp4";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/");
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(format);
        sb3.append(str2);
        return sb3.toString();
    }

    public static void F_PlayBtnMid() {
        int i = music_Mid;
        if (i != -1) {
            soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public static void F_PhotoMusic() {
        int i = music_photo;
        if (i != -1) {
            soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    @TargetApi(21)
    public static void forceSendRequestByWifiData(Context context) {
        if (VERSION.SDK_INT >= 21) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addTransportType(1);
            connectivityManager.requestNetwork(builder.build(), new NetworkCallback() {
                @TargetApi(23)
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    Log.i("test", "已根据功能和传输类型找到合适的网络");
                    if (VERSION.SDK_INT >= 23) {
                        connectivityManager.bindProcessToNetwork(network);
                    } else {
                        ConnectivityManager.setProcessDefaultNetwork(network);
                    }
                    connectivityManager.unregisterNetworkCallback(this);
                }
            });
        }
    }
}
