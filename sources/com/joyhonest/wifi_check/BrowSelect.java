package com.joyhonest.wifi_check;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class BrowSelect extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C0287R.layout.activity_brow_select);
        MyApp.checkDeviceHasNavigationBar(this);
        findViewById(C0287R.C0289id.but_exit).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                BrowSelect.this.Exit();
            }
        });
        findViewById(C0287R.C0289id.but_photo).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                MyApp.nBrowType = MyApp.Brow_Photo;
                Intent intent = new Intent();
                intent.setClass(BrowSelect.this, Grid_View.class);
                BrowSelect.this.startActivity(intent);
                BrowSelect.this.finish();
                BrowSelect.this.overridePendingTransition(0, 0);
            }
        });
        findViewById(C0287R.C0289id.but_video).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                MyApp.nBrowType = MyApp.Brow_Video;
                Intent intent = new Intent();
                intent.setClass(BrowSelect.this, Grid_View.class);
                BrowSelect.this.startActivity(intent);
                BrowSelect.this.finish();
                BrowSelect.this.overridePendingTransition(0, 0);
            }
        });
    }

    private void F_SetBtnImg(int i, int i2, final int i3, int i4) {
        final View findViewById = findViewById(i);
        if (findViewById != null) {
            findViewById.setBackgroundResource(i2);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    findViewById.setBackgroundResource(i3);
                }
            }, (long) i4);
        }
    }

    /* access modifiers changed from: private */
    public void Exit() {
        finish();
        overridePendingTransition(0, 0);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4 || keyEvent.getAction() != 0 || keyEvent.getRepeatCount() != 0) {
            return super.dispatchKeyEvent(keyEvent);
        }
        Exit();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }
}
