package com.example.movieticketbooking.Adapter;

public class HistoryItem {
    private String numOfTickets;
    private String price;
    private String title;
    private String rate;
    private String time;
    private String date;
    private String screeningDay;
    private String screeningTime;
    private String cinema;
    private String id;

    public HistoryItem(String numOfTickets, String price, String title, String rate, String time, String date, String screeningDay, String screeningTime,  String cinema, String id){
               this.numOfTickets = numOfTickets;
               this.cinema = cinema;
               this.date = date;
               this.rate = rate;
               this.price = price;
               this.screeningTime = screeningTime;
               this.screeningDay = screeningDay;
               this.title = title;
               this.time = time;
               this.id = id;
    }
    public HistoryItem(){}
    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCinema() {
        return cinema;
    }

    public String getDate() {
        return date;
    }

    public String getNumOfTickets() {
        return numOfTickets;
    }

    public String getPrice() {
        return price;
    }

    public String getRate() {
        return rate;
    }

    public String getScreeningDay() {
        return screeningDay;
    }

    public String getScreeningTime() {
        return screeningTime;
    }

    public String getTime() {
        return time;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumOfTickets(String numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setScreeningDay(String screeningDay) {
        this.screeningDay = screeningDay;
    }

    public void setScreeningTime(String screeningTime) {
        this.screeningTime = screeningTime;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
