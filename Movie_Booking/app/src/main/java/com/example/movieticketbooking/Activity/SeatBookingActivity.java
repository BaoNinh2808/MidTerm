package com.example.movieticketbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieticketbooking.Domain.FilmDataItem;
import com.example.movieticketbooking.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeatBookingActivity extends AppCompatActivity implements View.OnClickListener{
    ViewGroup layout;
    private TextView titleText, movieRateText, movieTimeText, movieDateText, summaryText, dayTimeText, startTimeText, cinemaText;
    private ImageView bg_image, back_button;
    TextView numOfTickets, priceText;
    ImageView addToCartView;
    int idFilm;
    String dayData, startTimeData, cinema;

//    U is for already booked seats
//    R is for reserved seats
//    A is for available seats
    String seats =  "_UUUUUUAAARRRR_/"
        + "_________________/"
        + "UU__AAAARRRR__RR/"
        + "UU__UUUAAAAA__AA/"
        + "AA__AAAAAAAA__AA/"
        + "AA__AARUUURR__AA/"
        + "AA__AAR__UUU__RR/"
        + "AA__UUA__URR__RR/"
        + "_________________/"
        + "UU_AAAAAUUUU_RR/"
        + "AA_AAAAUUUUU_AA/"
        + "_________________/";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 7;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    int selectedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_booking);

        idFilm = getIntent().getIntExtra("idFilm",0);
        dayData = getIntent().getStringExtra("dayTimeData");
        startTimeData = getIntent().getStringExtra("startTimeData");
        cinema = getIntent().getStringExtra("cinema");
        initView();
        loadDataIntoView();
    }

    private void loadDataIntoView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Assuming "FilmData" is your collection name and idToSearch is the ID (number) you want to query
        int idToSearch = idFilm; // Replace with the actual number you want to query

        // Create a reference to the FilmData collection
        CollectionReference filmDataRef = db.collection("MovieItem");

        // Construct the query to find documents where the "id" field matches your ID
        Query query = filmDataRef.whereEqualTo("id", idToSearch);

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    FilmDataItem filmItem = document.toObject(FilmDataItem.class);

                    Glide.with(SeatBookingActivity.this)
                            .load(filmItem.getPoster())
                            .into(bg_image);

                    titleText.setText(filmItem.getTitle());
                    movieRateText.setText(filmItem.getScore());
                    movieTimeText.setText(filmItem.getRunTime());
                    movieDateText.setText(filmItem.getReleasedDay());
                    summaryText.setText(filmItem.getSummary());
                }
            } else {
                // Handle errors
                Log.d("Load data into Detail", "Error getting documents: ", task.getException());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
                selectedCount--;
                int price = 25 * selectedCount;
                numOfTickets.setText("x"+String.valueOf(selectedCount));
                priceText.setText("$" + String.valueOf(price));
            } else {
                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
                selectedCount++;
                int price = 25 * selectedCount;
                numOfTickets.setText("x"+String.valueOf(selectedCount));
                priceText.setText("$" + String.valueOf(price));
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        titleText = findViewById(R.id.movieTitle_MoviePage);
        movieRateText = findViewById(R.id.movieRateText_MoviePage);
        movieTimeText = findViewById(R.id.movieTimeText_MoviePage);
        movieDateText = findViewById(R.id.movieDateText_MoviePage);
        bg_image = findViewById(R.id.imageBackground_MoviePage);
        back_button = findViewById(R.id.back_button3);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        summaryText = findViewById(R.id.synopsisText);
        dayTimeText = findViewById(R.id.dayTimeText);
        startTimeText = findViewById(R.id.startTimeText);
        cinemaText = findViewById(R.id.cinemaText);

        dayTimeText.setText(dayData);
        startTimeText.setText(startTimeData);
        cinemaText.setText(cinema);

        layout = findViewById(R.id.layoutSeat);
        numOfTickets = findViewById(R.id.noTicketsText);
        priceText = findViewById(R.id.priceText);
        addToCartView = findViewById(R.id.addToCartView);
        selectedCount = 0;

        addToCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount > 0){
                    Intent intent = new Intent(SeatBookingActivity.this, PaymentActivity.class);
                    intent.putExtra("numOfTickets", numOfTickets.getText().toString());
                    intent.putExtra("price", priceText.getText().toString());
                    intent.putExtra("title",titleText.getText().toString());
                    intent.putExtra("rate", movieRateText.getText().toString());
                    intent.putExtra("time", movieTimeText.getText().toString());
                    intent.putExtra("date", movieDateText.getText().toString());
                    intent.putExtra("screeningDay", dayTimeText.getText().toString());
                    intent.putExtra("screeningTime", startTimeText.getText().toString());
                    intent.putExtra("cinema", cinemaText.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(v.getContext(), "Please choose at least one seat!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'R') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }

    }
}
