package com.joyhonest.wifi_check;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.p003v7.app.AppCompatActivity;
import com.joyhonest.Permission.PermissionAsker;

public class SplashActivity extends AppCompatActivity {
    private PermissionAsker mAsker;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApp.checkDeviceHasNavigationBar(this);
        this.mAsker = new PermissionAsker(10, new Runnable() {
            public void run() {
                SplashActivity.this.F_Init();
            }
        }, new Runnable() {
            public void run() {
                SplashActivity.this.F_DispDialg();
            }
        }).askPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.mAsker.onRequestPermissionsResult(iArr);
    }

    /* access modifiers changed from: private */
    public void F_DispDialg() {
        new Builder(this).setTitle("warning").setMessage("The necessary permission denied, the application exit").setNegativeButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SplashActivity.this.finish();
            }
        }).create().show();
    }

    /* access modifiers changed from: private */
    public void F_Init() {
        setContentView((int) C0287R.layout.activity_splash);
        setRequestedOrientation(6);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
                SplashActivity.this.overridePendingTransition(0, 0);
            }
        }, 5);
    }
}
