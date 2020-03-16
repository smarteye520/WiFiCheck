package com.joyhonest.wifi_check;

import android.graphics.Bitmap;

public class MyNode {
    public static final int Status_downloaded = 2;
    public static final int Status_downloading = 1;
    public static final int Stauts_normal = 0;
    public static int TYPE_Local = 0;
    public static int TYPE_Remote = 1;
    public static final int select_Select_No = 1;
    public static final int select_Select_YES = 2;
    public static final int select_normal = 0;
    public Bitmap bitmap;
    public long bytesWritten;
    public int nGetType;
    public int nPostion;
    public long nPre;
    public int nSelectType;
    public int nStatus;
    public int nType;
    public String sPath;
    public String sText;
    public String sUrl;
    public long totalSize;

    public MyNode() {
        this.nSelectType = 0;
        this.nStatus = 0;
        this.nType = TYPE_Local;
        this.bitmap = null;
        String str = "";
        this.sPath = str;
        this.sUrl = str;
        this.sText = str;
        this.nSelectType = 0;
    }

    public MyNode(int i) {
        this.nSelectType = 0;
        this.nStatus = 0;
        this.nType = i;
        this.bitmap = null;
        String str = "";
        this.sPath = str;
        this.sUrl = str;
        this.sText = str;
        this.nSelectType = 0;
    }
}
