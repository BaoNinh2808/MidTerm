package com.example.movieticketbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketbooking.R;

public class TicketActivity extends AppCompatActivity {
    private ImageView back_button;
    private TextView titleText, movieRateText, movieTimeText, movieDateText, dayTimeText, startTimeText, cinemaText;
    private TextView numOfTickets, priceText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        initView();
    }

    private void initView() {
        back_button = findViewById(R.id.back_button5);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        numOfTickets = findViewById(R.id.noTicketsText);
        numOfTickets.setText(getIntent().getStringExtra("numOfTickets"));
        priceText = findViewById(R.id.priceText);
        priceText.setText(getIntent().getStringExtra("price"));
        titleText = findViewById(R.id.movieTitle_PayPage);
        titleText.setText(getIntent().getStringExtra("title"));
        movieRateText = findViewById(R.id.movieRateText_MoviePage);
        movieRateText.setText(getIntent().getStringExtra("rate"));
        movieTimeText = findViewById(R.id.movieTimeText_MoviePage);
        movieTimeText.setText(getIntent().getStringExtra("time"));
        movieDateText = findViewById(R.id.movieDateText_MoviePage);
        movieDateText.setText(getIntent().getStringExtra("date"));
        dayTimeText = findViewById(R.id.dayTimeText);
        dayTimeText.setText(getIntent().getStringExtra("screeningDay"));
        startTimeText = findViewById(R.id.startTimeText);
        startTimeText.setText(getIntent().getStringExtra("screeningTime"));
        cinemaText = findViewById(R.id.cinemaText);
        cinemaText.setText(getIntent().getStringExtra("cinema"));
    }

}