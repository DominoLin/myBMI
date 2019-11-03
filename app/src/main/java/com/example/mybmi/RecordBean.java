package com.example.mybmi;

import org.litepal.crud.LitePalSupport;

public class RecordBean extends LitePalSupport {
    private String time;
    private double result;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
