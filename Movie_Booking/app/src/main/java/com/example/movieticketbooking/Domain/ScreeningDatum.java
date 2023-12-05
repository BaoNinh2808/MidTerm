package com.example.movieticketbooking.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ScreeningDatum {
    private String time;
    private String state;

    public ScreeningDatum(Map<String, String> data){
        time = data.get("time");
        state = data.get("state");
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}