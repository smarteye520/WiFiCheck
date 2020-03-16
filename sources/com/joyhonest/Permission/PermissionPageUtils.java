package com.joyhonest.Permission;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PermissionPageUtils {
    private final String TAG = "PermissionPageManager";
    private Context mContext;
    private String packageName = "com.joyhonest.no_invasive";

    public PermissionPageUtils(Context context, String str) {
        this.mContext = context;
        this.packageName = str;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void jumpPermissionPage() {
        /*
            r3 = this;
            java.lang.String r0 = android.os.Build.MANUFACTURER
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "jumpPermissionPage --- name : "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "PermissionPageManager"
            android.util.Log.e(r2, r1)
            int r1 = r0.hashCode()
            switch(r1) {
                case -1678088054: goto L_0x0071;
                case -1675632421: goto L_0x0067;
                case 2427: goto L_0x005c;
                case 2432928: goto L_0x0052;
                case 2582855: goto L_0x0048;
                case 3620012: goto L_0x003e;
                case 74224812: goto L_0x0034;
                case 1864941562: goto L_0x002a;
                case 2141820391: goto L_0x0020;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x007b
        L_0x0020:
            java.lang.String r1 = "HUAWEI"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 0
            goto L_0x007c
        L_0x002a:
            java.lang.String r1 = "samsung"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 6
            goto L_0x007c
        L_0x0034:
            java.lang.String r1 = "Meizu"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 4
            goto L_0x007c
        L_0x003e:
            java.lang.String r1 = "vivo"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 1
            goto L_0x007c
        L_0x0048:
            java.lang.String r1 = "Sony"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 7
            goto L_0x007c
        L_0x0052:
            java.lang.String r1 = "OPPO"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 2
            goto L_0x007c
        L_0x005c:
            java.lang.String r1 = "LG"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 8
            goto L_0x007c
        L_0x0067:
            java.lang.String r1 = "Xiaomi"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 5
            goto L_0x007c
        L_0x0071:
            java.lang.String r1 = "Coolpad"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 3
            goto L_0x007c
        L_0x007b:
            r0 = -1
        L_0x007c:
            switch(r0) {
                case 0: goto L_0x00a3;
                case 1: goto L_0x009f;
                case 2: goto L_0x009b;
                case 3: goto L_0x0097;
                case 4: goto L_0x0093;
                case 5: goto L_0x008f;
                case 6: goto L_0x008b;
                case 7: goto L_0x0087;
                case 8: goto L_0x0083;
                default: goto L_0x007f;
            }
        L_0x007f:
            r3.goIntentSetting()
            goto L_0x00a6
        L_0x0083:
            r3.goLGMainager()
            goto L_0x00a6
        L_0x0087:
            r3.goSonyMainager()
            goto L_0x00a6
        L_0x008b:
            r3.goSangXinMainager()
            goto L_0x00a6
        L_0x008f:
            r3.goXiaoMiMainager()
            goto L_0x00a6
        L_0x0093:
            r3.goMeizuMainager()
            goto L_0x00a6
        L_0x0097:
            r3.goCoolpadMainager()
            goto L_0x00a6
        L_0x009b:
            r3.goOppoMainager()
            goto L_0x00a6
        L_0x009f:
            r3.goVivoMainager()
            goto L_0x00a6
        L_0x00a3:
            r3.goHuaWeiMainager()
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.joyhonest.Permission.PermissionPageUtils.jumpPermissionPage():void");
    }

    private void goLGMainager() {
        try {
            Intent intent = new Intent(this.packageName);
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
            this.mContext.startActivity(intent);
        } catch (Exception unused) {
            goIntentSetting();
        }
    }

    private void goSonyMainager() {
        try {
            Intent intent = new Intent(this.packageName);
            intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
            this.mContext.startActivity(intent);
        } catch (Exception unused) {
            goIntentSetting();
        }
    }

    private void goHuaWeiMainager() {
        try {
            Intent intent = new Intent(this.packageName);
            intent.setFlags(268435456);
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            this.mContext.startActivity(intent);
        } catch (Exception unused) {
            goIntentSetting();
        }
    }

    private static String getMiuiVersion() {
        BufferedReader bufferedReader;
        String str = "ro.miui.ui.version.name";
        BufferedReader bufferedReader2 = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            StringBuilder sb = new StringBuilder();
            sb.append("getprop ");
            sb.append(str);
            bufferedReader = new BufferedReader(new InputStreamReader(runtime.exec(sb.toString()).getInputStream()), 1024);
            try {
                String readLine = bufferedReader.readLine();
                bufferedReader.close();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return readLine;
            } catch (IOException e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader2 = bufferedReader;
                    try {
                        bufferedReader2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    throw th;
                }
            }
        } catch (IOException e5) {
            e = e5;
            bufferedReader = null;
            e.printStackTrace();
            bufferedReader.close();
            return null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2.close();
            throw th;
        }
    }

    private void goXiaoMiMainager() {
        String miuiVersion = getMiuiVersion();
        StringBuilder sb = new StringBuilder();
        sb.append("goMiaoMiMainager --- rom : ");
        sb.append(miuiVersion);
        Log.e("PermissionPageManager", sb.toString());
        Intent intent = new Intent();
        String str = "extra_pkgname";
        String str2 = "com.miui.securitycenter";
        String str3 = "miui.intent.action.APP_PERM_EDITOR";
        if ("V6".equals(miuiVersion) || "V7".equals(miuiVersion)) {
            intent.setAction(str3);
            intent.setClassName(str2, "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra(str, this.packageName);
        } else if ("V8".equals(miuiVersion) || "V9".equals(miuiVersion)) {
            intent.setAction(str3);
            intent.setClassName(str2, "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra(str, this.packageName);
        } else {
            goIntentSetting();
        }
        this.mContext.startActivity(intent);
    }

    private void goMeizuMainager() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra("packageName", this.packageName);
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSangXinMainager() {
        goIntentSetting();
    }

    private void goIntentSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
        try {
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goOppoMainager() {
        doStartApplicationWithPackageName("com.coloros.safecenter");
    }

    private void goCoolpadMainager() {
        doStartApplicationWithPackageName("com.yulong.android.security:remote");
    }

    private void goVivoMainager() {
        doStartApplicationWithPackageName("com.bairenkeji.icaller");
    }

    private Intent getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        if (VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
        } else if (VERSION.SDK_INT <= 8) {
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", this.mContext.getPackageName());
        }
        return intent;
    }

    private void doStartApplicationWithPackageName(String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = this.mContext.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            String str2 = "android.intent.action.MAIN";
            Intent intent = new Intent(str2, null);
            String str3 = "android.intent.category.LAUNCHER";
            intent.addCategory(str3);
            intent.setPackage(packageInfo.packageName);
            List queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(intent, 0);
            StringBuilder sb = new StringBuilder();
            sb.append("resolveinfoList");
            sb.append(queryIntentActivities.size());
            String str4 = "PermissionPageManager";
            Log.e(str4, sb.toString());
            for (int i = 0; i < queryIntentActivities.size(); i++) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.packageName);
                sb2.append(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.name);
                Log.e(str4, sb2.toString());
            }
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivities.iterator().next();
            if (resolveInfo != null) {
                String str5 = resolveInfo.activityInfo.packageName;
                String str6 = resolveInfo.activityInfo.name;
                Intent intent2 = new Intent(str2);
                intent2.addCategory(str3);
                intent2.setComponent(new ComponentName(str5, str6));
                try {
                    this.mContext.startActivity(intent2);
                } catch (Exception e2) {
                    goIntentSetting();
                    e2.printStackTrace();
                }
            }
        }
    }
}
