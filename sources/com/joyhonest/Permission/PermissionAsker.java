package com.joyhonest.Permission;

import android.app.Activity;
import android.support.p000v4.app.ActivityCompat;

public class PermissionAsker {
    private static final Runnable RUN = new Runnable() {
        public void run() {
        }
    };
    private Runnable mDeniRun;
    private Runnable mOkRun;
    private int mReqCode = 1;

    public PermissionAsker() {
        Runnable runnable = RUN;
        this.mOkRun = runnable;
        this.mDeniRun = runnable;
    }

    public PermissionAsker(int i, Runnable runnable, Runnable runnable2) {
        Runnable runnable3 = RUN;
        this.mOkRun = runnable3;
        this.mDeniRun = runnable3;
        this.mReqCode = i;
        this.mOkRun = runnable;
        this.mDeniRun = runnable2;
    }

    public void setReqCode(int i) {
        this.mReqCode = i;
    }

    public void setSuccedCallback(Runnable runnable) {
        this.mOkRun = runnable;
    }

    public void setFailedCallback(Runnable runnable) {
        this.mDeniRun = runnable;
    }

    public PermissionAsker askPermission(Activity activity, String... strArr) {
        int i = 0;
        for (String checkSelfPermission : strArr) {
            i += ActivityCompat.checkSelfPermission(activity, checkSelfPermission);
        }
        if (i == 0) {
            this.mOkRun.run();
        } else {
            ActivityCompat.requestPermissions(activity, strArr, this.mReqCode);
        }
        return this;
    }

    public void onRequestPermissionsResult(int[] iArr) {
        boolean z = true;
        for (int i = 0; i < iArr.length; i++) {
            z &= iArr[i] == 0;
        }
        if (iArr.length <= 0 || !z) {
            this.mDeniRun.run();
        } else {
            this.mOkRun.run();
        }
    }
}
