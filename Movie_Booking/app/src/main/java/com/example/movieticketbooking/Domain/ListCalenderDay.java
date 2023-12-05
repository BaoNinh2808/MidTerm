package com.example.movieticketbooking.Domain;

import java.util.List;

public class ListCalenderDay  {
    private List<DayDatum> data;
    public ListCalenderDay(List<DayDatum> data){
        this.data = data;
    }

    public List<DayDatum> getData() {
        return data;
    }
    public int getSize(){
        return data.size();
    }
    public void setData(List<DayDatum> data) {
        this.data = data;
    }

}