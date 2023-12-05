
package com.example.movieticketbooking.Domain;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ListFilmScreening {

    private List<ScreeningDatum> data;

    public List<ScreeningDatum> getData() {
        return data;
    }

    public void setData(List<ScreeningDatum> data) {
        this.data = data;
    }

}
