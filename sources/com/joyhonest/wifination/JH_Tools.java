package com.joyhonest.wifination;

import java.util.ArrayList;
import java.util.List;
import org.simple.eventbus.EventBus;

public class JH_Tools {
    private static List<MyCmdData> array = new ArrayList(100);
    private static int nCmdResType;
    private static List<Byte> wifiData = new ArrayList(100);

    private static class MyCmdData {
        /* access modifiers changed from: private */
        public byte[] data;
        /* access modifiers changed from: private */
        public int udpInx;

        private MyCmdData(int i, byte[] bArr) {
            this.data = null;
            this.data = null;
            if (bArr != null && bArr.length > 4) {
                this.data = new byte[(bArr.length - 4)];
                System.arraycopy(bArr, 4, this.data, 0, bArr.length - 4);
            }
            this.udpInx = i;
        }
    }

    public static void F_SetResType(int i) {
        nCmdResType = i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004e, code lost:
        r1 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean AdjData(byte[] r6) {
        /*
            int r0 = r6.length
            r1 = 0
            r2 = 4
            if (r0 > r2) goto L_0x0006
            return r1
        L_0x0006:
            byte r0 = r6[r1]
            r2 = 1
            byte r3 = r6[r2]
            int r3 = r3 * 256
            int r0 = r0 + r3
            r3 = 2
            byte r3 = r6[r3]
            r4 = 65536(0x10000, float:9.18355E-41)
            int r3 = r3 * r4
            int r0 = r0 + r3
            r3 = 3
            byte r3 = r6[r3]
            r4 = 16777216(0x1000000, float:2.3509887E-38)
            int r3 = r3 * r4
            int r0 = r0 + r3
            com.joyhonest.wifination.JH_Tools$MyCmdData r3 = new com.joyhonest.wifination.JH_Tools$MyCmdData
            r4 = 0
            r3.<init>(r0, r6)
            java.util.List<com.joyhonest.wifination.JH_Tools$MyCmdData> r6 = array
            int r6 = r6.size()
            if (r6 != 0) goto L_0x0032
            java.util.List<com.joyhonest.wifination.JH_Tools$MyCmdData> r6 = array
            r6.add(r1, r3)
            goto L_0x0061
        L_0x0032:
            r6 = 0
        L_0x0033:
            java.util.List<com.joyhonest.wifination.JH_Tools$MyCmdData> r4 = array
            int r4 = r4.size()
            if (r6 >= r4) goto L_0x005a
            java.util.List<com.joyhonest.wifination.JH_Tools$MyCmdData> r4 = array
            java.lang.Object r4 = r4.get(r6)
            com.joyhonest.wifination.JH_Tools$MyCmdData r4 = (com.joyhonest.wifination.JH_Tools.MyCmdData) r4
            int r5 = r4.udpInx
            if (r0 >= r5) goto L_0x0050
            java.util.List<com.joyhonest.wifination.JH_Tools$MyCmdData> r0 = array
            r0.add(r6, r3)
        L_0x004e:
            r1 = 1
            goto L_0x005a
        L_0x0050:
            int r4 = r4.udpInx
            if (r0 != r4) goto L_0x0057
            goto L_0x004e
        L_0x0057:
            int r6 = r6 + 1
            goto L_0x0033
        L_0x005a:
            if (r1 != 0) goto L_0x0061
            java.util.List<com.joyhonest.wifination.JH_Tools$MyCmdData> r6 = array
            r6.add(r3)
        L_0x0061:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.joyhonest.wifination.JH_Tools.AdjData(byte[]):boolean");
    }

    private static boolean Process(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte valueOf : bArr) {
            wifiData.add(Byte.valueOf(valueOf));
        }
        boolean z = false;
        while (wifiData.size() >= 8) {
            if (((Byte) wifiData.get(0)).byteValue() == 102 && ((Byte) wifiData.get(7)).byteValue() == -103) {
                byte b = 0;
                for (int i = 1; i < 6; i++) {
                    b = (byte) (b ^ ((Byte) wifiData.get(i)).byteValue());
                }
                if (b == ((Byte) wifiData.get(6)).byteValue()) {
                    byte[] bArr2 = new byte[8];
                    for (int i2 = 0; i2 < 8; i2++) {
                        bArr2[i2] = ((Byte) wifiData.get(i2)).byteValue();
                    }
                    EventBus.getDefault().post(bArr2, "GetWifiSendData");
                    for (int i3 = 0; i3 < 8; i3++) {
                        wifiData.remove(0);
                    }
                    z = true;
                } else {
                    wifiData.remove(0);
                }
            } else {
                wifiData.remove(0);
            }
        }
        return z;
    }

    public static void FindCmd() {
        int i = nCmdResType;
        if (i == 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i2 < array.size()) {
                MyCmdData myCmdData = (MyCmdData) array.get(i2);
                if (i2 == 0) {
                    int length = myCmdData.data.length;
                    int access$100 = myCmdData.udpInx;
                    if (length >= 8) {
                        byte[] bArr = new byte[length];
                        System.arraycopy(myCmdData.data, 0, bArr, 0, myCmdData.data.length);
                        Process(bArr);
                        while (i2 >= 0) {
                            array.remove(i2);
                            i2--;
                        }
                        return;
                    }
                    i5 = i2;
                    int i6 = access$100;
                    i4 = length;
                    i3 = i6;
                } else {
                    int access$1002 = myCmdData.udpInx;
                    if (access$1002 - i3 == 1) {
                        i4 += myCmdData.data.length;
                        if (i4 >= 8) {
                            byte[] bArr2 = new byte[i4];
                            while (i5 <= i2) {
                                MyCmdData myCmdData2 = (MyCmdData) array.get(i5);
                                System.arraycopy(myCmdData2.data, 0, bArr2, 0, myCmdData2.data.length);
                                i5++;
                            }
                            Process(bArr2);
                            while (i2 >= 0) {
                                array.remove(i2);
                                i2--;
                            }
                            return;
                        }
                    } else {
                        i4 = myCmdData.data.length;
                        i5 = i2;
                    }
                    i3 = access$1002;
                }
                i2++;
            }
        } else if (i == 1) {
            F_ProgressResType1();
        }
    }

    private static int ProgressA(byte[] bArr, int i) {
        if (i <= 2) {
            return 0;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= i - 2) {
                break;
            }
            byte b = bArr[i2];
            int i4 = i2 + 1;
            byte b2 = bArr[i4];
            if (b == 88) {
                int i5 = b2 == -125 ? 3 : 0;
                if (b2 == -124) {
                    i5 = 3;
                }
                if (b2 == -118) {
                    i5 = 14;
                }
                if (b2 == -117) {
                    i5 = 13;
                }
                if (b2 == -116) {
                    i5 = 15;
                }
                if (b2 == -114) {
                    i5 = 4;
                }
                if (i5 != 0) {
                    if (i2 + 2 + i5 < i) {
                        int i6 = i5 + 2;
                        byte[] bArr2 = new byte[i6];
                        System.arraycopy(bArr, i2, bArr2, 0, i6);
                        byte b3 = bArr2[i5 + 1];
                        byte b4 = 0;
                        for (int i7 = 0; i7 < i5; i7++) {
                            if (i7 == 0) {
                                b4 = bArr2[i7 + 1];
                            } else {
                                b4 = (byte) (b4 ^ bArr2[i7 + 1]);
                            }
                        }
                        if (b4 == b3) {
                            EventBus.getDefault().post(bArr2, "GetWifiSendData");
                        }
                        i2 += i6;
                        if (b2 == -117) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException unused) {
                            }
                        }
                        i3 = i2;
                    } else if (i3 == 0) {
                        return -1;
                    }
                }
            }
            i2 = i4;
        }
        return i3;
    }

    private static void F_ProgressResType1() {
        if (array.size() > 0) {
            MyCmdData myCmdData = (MyCmdData) array.get(0);
            int length = myCmdData.data.length;
            int ProgressA = ProgressA(myCmdData.data, myCmdData.data.length);
            if (ProgressA > 0) {
                int i = length - ProgressA;
                if (i > 0) {
                    byte[] bArr = new byte[i];
                    System.arraycopy(myCmdData.data, ProgressA, bArr, 0, i);
                    myCmdData.data = new byte[i];
                    System.arraycopy(bArr, 0, myCmdData.data, 0, i);
                } else if (i == 0) {
                    array.remove(0);
                }
            } else if (array.size() >= 2) {
                MyCmdData myCmdData2 = (MyCmdData) array.get(1);
                int access$100 = myCmdData.udpInx;
                int access$1002 = myCmdData2.udpInx;
                int length2 = myCmdData2.data.length;
                if (access$1002 - access$100 == 1) {
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(myCmdData.data, 0, bArr2, 0, length);
                    myCmdData.data = new byte[(length + length2)];
                    System.arraycopy(bArr2, 0, myCmdData.data, 0, length);
                    System.arraycopy(myCmdData2.data, 0, myCmdData.data, length, length2);
                    myCmdData.udpInx = access$1002;
                    array.remove(1);
                    F_ProgressResType1();
                }
            }
        }
    }

    public static void F_ClearData() {
        if (array.size() > 5) {
            array.clear();
            wifiData.clear();
        }
    }
}
