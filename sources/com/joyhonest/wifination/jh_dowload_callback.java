package com.joyhonest.wifination;

public class jh_dowload_callback {
    public int nError;
    public int nPercentage;
    public String sFileName;

    public jh_dowload_callback(int i, String str, int i2) {
        this.sFileName = str;
        this.nPercentage = i;
        this.nError = i2;
    }
}
