package com.joyhonest.wifi_check;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.p003v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.joyhonest.wifination.C0300wifination;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    static Handler RecHandler = null;
    private static String TAG = "WIFI_Check";
    static final int nNoSelectColor = -16777216;
    static final int nSelectColor = -16678289;
    static Handler openHandler;
    Runnable RecRunnable = new Runnable() {
        boolean bhalf = true;
        int nStep = 0;

        public void run() {
            if ((MyApp.nStatus & 2) != 0) {
                int naGetRecordTime = C0300wifination.naGetRecordTime() / 1000;
                int i = naGetRecordTime / 60;
                int i2 = naGetRecordTime % 60;
                MainActivity.this.strRecTime = String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
                MainActivity.this.RecTimeView.setText(MainActivity.this.strRecTime);
                if ((this.nStep & 1) == 0) {
                    if (this.bhalf) {
                        MainActivity.this.btn_Video.setBackgroundResource(C0287R.mipmap.video_icon_active);
                    } else {
                        MainActivity.this.btn_Video.setBackgroundResource(C0287R.mipmap.video_icon);
                    }
                    this.bhalf = !this.bhalf;
                }
                this.nStep++;
            }
            MainActivity.RecHandler.postDelayed(this, 250);
        }
    };
    TextView RecTimeView;
    LinearLayout ResolutionSelectView;
    private final int TYPE_CHECK = 0;
    private final int TYPE_TC = 1;
    private final int TYPE_WIFI_TEST = 2;
    boolean bKeyProcessed_1 = false;
    boolean bMirro = false;
    private ImageView battery_view;
    Button btn_Resolution1080;
    Button btn_Resolution480;
    Button btn_Resolution720;
    Button btn_Video;
    private View line1;
    private View line2;
    private View line3;
    int nResolutionSel = 0;
    int nRota = 0;
    int[] nRotaArray = {0, 90, 180, 270};
    int nScal = 0;
    private int nType = 0;
    long preTime = System.currentTimeMillis();
    String strRecTime = "00:00";
    private HandlerThread thread1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApp.nStatus = 0;
        MyApp.checkDeviceHasNavigationBar(this);
        this.nType = 0;
        TAG = "WIFI_Check";
        MyApp.F_CreateLocalDir(TAG);
        C0300wifination.naSetVrBackground(false);
        C0300wifination.naSetbRotaHV(true);
        C0300wifination.F_AdjBackGround(this, C0287R.mipmap.background);
        C0300wifination.naSetMirror(this.bMirro);
        setContentView((int) C0287R.layout.activity_main);
        this.line1 = findViewById(C0287R.C0289id.line1);
        this.line2 = findViewById(C0287R.C0289id.line2);
        this.line3 = findViewById(C0287R.C0289id.line3);
        this.battery_view = (ImageView) findViewById(C0287R.C0289id.battery_view);
        this.battery_view.setVisibility(4);
        this.ResolutionSelectView = (LinearLayout) findViewById(C0287R.C0289id.ResolutionSelectView);
        this.btn_Resolution480 = (Button) findViewById(C0287R.C0289id.btn_Resolution480);
        this.btn_Resolution720 = (Button) findViewById(C0287R.C0289id.btn_Resolution720);
        this.btn_Resolution1080 = (Button) findViewById(C0287R.C0289id.btn_Resolution1080);
        if (this.nType == 1) {
            this.line1.setVisibility(8);
            this.line2.setVisibility(8);
            this.line3.setVisibility(8);
            this.btn_Resolution480.setVisibility(8);
            this.btn_Resolution720.setVisibility(8);
            this.btn_Resolution1080.setText("2560x1920");
            C0300wifination.naSetRecordWH(2560, 1920);
        }
        this.RecTimeView = (TextView) findViewById(C0287R.C0289id.RecTimeView);
        this.RecTimeView.setVisibility(4);
        this.btn_Video = (Button) findViewById(C0287R.C0289id.but_video);
        setRequestedOrientation(6);
        this.thread1 = new HandlerThread(TAG);
        this.thread1.start();
        openHandler = new Handler(this.thread1.getLooper());
        RecHandler = new Handler();
        F_SetClickListener();
        findViewById(C0287R.C0289id.surfaceView).setOnClickListener(this);
        EventBus.getDefault().register(this);
        this.ResolutionSelectView.setVisibility(4);
    }

    private void F_StartDispRecTime(boolean z) {
        if (z) {
            this.RecTimeView.setText("00:00");
            this.RecTimeView.setVisibility(0);
            RecHandler.removeCallbacksAndMessages(null);
            RecHandler.post(this.RecRunnable);
            return;
        }
        this.RecTimeView.setVisibility(4);
        RecHandler.removeCallbacksAndMessages(null);
        this.btn_Video.setBackgroundResource(C0287R.mipmap.video_icon);
    }

    private void F_SetClickListener() {
        findViewById(C0287R.C0289id.but_photo).setOnClickListener(this);
        findViewById(C0287R.C0289id.but_video).setOnClickListener(this);
        findViewById(C0287R.C0289id.but_brow).setOnClickListener(this);
        findViewById(C0287R.C0289id.but_rota).setOnClickListener(this);
        findViewById(C0287R.C0289id.but_mirr).setOnClickListener(this);
        findViewById(C0287R.C0289id.but_setup).setOnClickListener(this);
        if (this.nType == 1) {
            findViewById(C0287R.C0289id.but_photo).setBackgroundResource(C0287R.mipmap.photo_icon_tc);
            findViewById(C0287R.C0289id.but_video).setBackgroundResource(C0287R.mipmap.video_icon_tc);
            findViewById(C0287R.C0289id.but_brow).setBackgroundResource(C0287R.mipmap.brow_icon_tc);
            findViewById(C0287R.C0289id.but_rota).setBackgroundResource(C0287R.mipmap.rota_icon_tc);
            findViewById(C0287R.C0289id.but_mirr).setBackgroundResource(C0287R.mipmap.mirr_icon_tc);
            findViewById(C0287R.C0289id.but_setup).setBackgroundResource(C0287R.mipmap.setup_icon_tc);
        }
        this.btn_Resolution480.setTextColor(nSelectColor);
        this.btn_Resolution480.setOnClickListener(this);
        this.btn_Resolution720.setOnClickListener(this);
        this.btn_Resolution1080.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != C0287R.C0289id.surfaceView) {
            switch (id) {
                case C0287R.C0289id.btn_Resolution1080 /*2131165233*/:
                    MyApp.F_PlayBtnVoice();
                    this.nResolutionSel = 2;
                    F_SetResolution();
                    break;
                case C0287R.C0289id.btn_Resolution480 /*2131165234*/:
                    MyApp.F_PlayBtnVoice();
                    this.nResolutionSel = 0;
                    F_SetResolution();
                    break;
                case C0287R.C0289id.btn_Resolution720 /*2131165235*/:
                    MyApp.F_PlayBtnVoice();
                    this.nResolutionSel = 1;
                    F_SetResolution();
                    break;
                case C0287R.C0289id.but_brow /*2131165236*/:
                    MyApp.F_PlayBtnVoice();
                    if (this.ResolutionSelectView.getVisibility() != 0) {
                        startActivity(new Intent(this, BrowSelect.class));
                        overridePendingTransition(0, 0);
                        break;
                    } else {
                        this.ResolutionSelectView.setVisibility(4);
                        return;
                    }
                default:
                    switch (id) {
                        case C0287R.C0289id.but_mirr /*2131165238*/:
                            MyApp.F_PlayBtnVoice();
                            if (this.ResolutionSelectView.getVisibility() != 0) {
                                this.bMirro = !this.bMirro;
                                C0300wifination.naSetMirror(this.bMirro);
                                break;
                            } else {
                                this.ResolutionSelectView.setVisibility(4);
                                return;
                            }
                        case C0287R.C0289id.but_photo /*2131165239*/:
                            if (this.ResolutionSelectView.getVisibility() != 0) {
                                F_TakePhoto();
                                break;
                            } else {
                                this.ResolutionSelectView.setVisibility(4);
                                return;
                            }
                        case C0287R.C0289id.but_rota /*2131165240*/:
                            MyApp.F_PlayBtnVoice();
                            if (this.ResolutionSelectView.getVisibility() != 0) {
                                this.nRota++;
                                if (this.nRota > 3) {
                                    this.nRota = 0;
                                }
                                C0300wifination.naRotation(this.nRotaArray[this.nRota]);
                                break;
                            } else {
                                this.ResolutionSelectView.setVisibility(4);
                                return;
                            }
                        case C0287R.C0289id.but_setup /*2131165241*/:
                            MyApp.F_PlayBtnVoice();
                            if (this.ResolutionSelectView.getVisibility() == 0) {
                                this.ResolutionSelectView.setVisibility(4);
                                break;
                            } else {
                                this.ResolutionSelectView.setVisibility(0);
                                break;
                            }
                        case C0287R.C0289id.but_video /*2131165242*/:
                            if (this.ResolutionSelectView.getVisibility() != 0) {
                                if (isConnected()) {
                                    if ((MyApp.nStatus & 2) != 0) {
                                        C0300wifination.naStopRecord_All();
                                        break;
                                    } else {
                                        F_SetResolution();
                                        C0300wifination.naStartRecord(MyApp.getFileNameFromDate(true, true), 0);
                                        break;
                                    }
                                }
                            } else {
                                this.ResolutionSelectView.setVisibility(4);
                                return;
                            }
                            break;
                    }
            }
        } else {
            MyApp.F_PlayBtnVoice();
            if (this.ResolutionSelectView.getVisibility() == 0) {
                this.ResolutionSelectView.setVisibility(4);
                return;
            }
            Show_hid_toolbar(isConnected());
        }
    }

    private void DispResolution() {
        int i = this.nResolutionSel;
        if (i == 2) {
            this.btn_Resolution720.setTextColor(-16777216);
            this.btn_Resolution480.setTextColor(-16777216);
            this.btn_Resolution1080.setTextColor(nSelectColor);
        } else if (i == 1) {
            this.btn_Resolution720.setTextColor(nSelectColor);
            this.btn_Resolution480.setTextColor(-16777216);
            this.btn_Resolution1080.setTextColor(-16777216);
        } else {
            this.btn_Resolution480.setTextColor(nSelectColor);
            this.btn_Resolution720.setTextColor(-16777216);
            this.btn_Resolution1080.setTextColor(-16777216);
        }
    }

    private void F_SetResolution() {
        if (this.nType == 1) {
            C0300wifination.naSetRecordWH(2560, 1920);
            return;
        }
        int i = this.nResolutionSel;
        if (i == 2) {
            this.btn_Resolution1080.setTextColor(nSelectColor);
            this.btn_Resolution720.setTextColor(-16777216);
            this.btn_Resolution480.setTextColor(-16777216);
            C0300wifination.naSetRecordWH(1600, 1200);
        } else if (i == 1) {
            this.btn_Resolution720.setTextColor(nSelectColor);
            this.btn_Resolution480.setTextColor(-16777216);
            this.btn_Resolution1080.setTextColor(-16777216);
            C0300wifination.naSetRecordWH(1280, 1080);
        } else {
            this.btn_Resolution480.setTextColor(nSelectColor);
            this.btn_Resolution720.setTextColor(-16777216);
            this.btn_Resolution1080.setTextColor(-16777216);
            C0300wifination.naSetRecordWH(640, 480);
        }
    }

    private void Show_hid_toolbar(boolean z) {
        LinearLayout linearLayout = (LinearLayout) findViewById(C0287R.C0289id.toolbar);
        if (linearLayout != null) {
            int width = linearLayout.getWidth();
            int translationX = (int) linearLayout.getTranslationX();
            String str = "TranslationX";
            if (z) {
                if (translationX == 0) {
                    ObjectAnimator.ofFloat(linearLayout, str, new float[]{(float) width}).setDuration(200).start();
                    return;
                }
                ObjectAnimator.ofFloat(linearLayout, str, new float[]{0.0f}).setDuration(200).start();
            } else if (translationX != 0) {
                ObjectAnimator.ofFloat(linearLayout, str, new float[]{0.0f}).setDuration(2).start();
            }
        }
    }

    private boolean isConnected() {
        return (MyApp.nStatus & 1) != 0;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        C0300wifination.naStopRecord_All();
        C0300wifination.naStop();
        MyApp.nStatus = 0;
        openHandler.removeCallbacksAndMessages(null);
        RecHandler.removeCallbacksAndMessages(null);
        this.thread1.quit();
        EventBus.getDefault().unregister(this);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        MyApp.checkDeviceHasNavigationBar(this);
        if ((MyApp.nStatus & 1) == 0) {
            C0300wifination.naStop();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MainActivity.openHandler.removeCallbacksAndMessages(null);
                    MainActivity.openHandler.postDelayed(new Runnable() {
                        public void run() {
                            MainActivity.this.F_OpenStream();
                        }
                    }, 10);
                }
            }, 20);
        }
    }

    @Subscriber(tag = "SDStatus_Changed")
    private void _OnStatusChanged(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        Log.d("   Status", sb.toString());
        if ((i & 1) != 0) {
            if ((MyApp.nStatus & 1) == 0) {
                F_SetResolution();
            }
            MyApp.nStatus |= 1;
        }
        if ((i & 2) != 0 && (MyApp.nStatus & 2) == 0) {
            MyApp.nStatus |= 2;
            F_StartDispRecTime(true);
        } else if ((MyApp.nStatus & 2) != 0) {
            MyApp.nStatus &= -3;
            F_StartDispRecTime(false);
        }
    }

    @Subscriber(tag = "Key_Pressed")
    private void Key_Pressed(Integer num) {
        if (num.intValue() == 1) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.preTime > 500) {
                this.preTime = currentTimeMillis;
                if (!this.bKeyProcessed_1) {
                    this.bKeyProcessed_1 = true;
                    F_TakePhoto();
                    return;
                }
                return;
            }
            return;
        }
        this.bKeyProcessed_1 = false;
    }

    @Subscriber(tag = "SavePhotoOK")
    private void SavePhotoOK(String str) {
        if (str.length() >= 5) {
            String substring = str.substring(0, 2);
            String substring2 = str.substring(2);
            if (Integer.parseInt(substring) == 0) {
                MyApp.F_Save2ToGallery(substring2, true);
            } else {
                MyApp.F_Save2ToGallery(substring2, false);
            }
        }
    }

    private void F_TakePhoto() {
        if (isConnected()) {
            C0300wifination.naSnapPhoto(MyApp.getFileNameFromDate(false, true), 0);
            MyApp.F_PhotoMusic();
        }
    }

    /* access modifiers changed from: private */
    public void F_OpenStream() {
        MyApp.nStatus = 0;
        MyApp.forceSendRequestByWifiData(this);
        C0300wifination.naInit("");
        C0300wifination.naWriteport20000(new byte[]{74, 72, 67, 77, 68, 48, 8}, 7);
    }

    @Subscriber(tag = "OnGetBatteryLevel")
    private void OnGetBatteryLevel(Integer num) {
        int intValue = num.intValue();
        if (intValue > 3) {
            intValue = 3;
        }
        int[] iArr = {C0287R.mipmap.battery_0, C0287R.mipmap.battery_1, C0287R.mipmap.battery_2, C0287R.mipmap.battery_3};
        this.battery_view.setVisibility(0);
        this.battery_view.setBackgroundResource(iArr[intValue]);
    }
}
