package com.example.movieticketbooking.Adapter;

public interface OnItemClickListener {
    void onItemClick(String selectedTime, String cinemaName, int chosenPosition);
    void onItemClick(String dayChosen);
}
