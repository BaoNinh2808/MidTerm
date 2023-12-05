package com.example.movieticketbooking.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movieticketbooking.Adapter.CalendarAdapter;
import com.example.movieticketbooking.Adapter.ImageListAdapter;
import com.example.movieticketbooking.Adapter.OnItemClickListener;
import com.example.movieticketbooking.Adapter.ScreeningFilmAdapter;
import com.example.movieticketbooking.Domain.DayDatum;
import com.example.movieticketbooking.Domain.FilmDataItem;
import com.example.movieticketbooking.Domain.FilmItem;
import com.example.movieticketbooking.Domain.ListCalenderDay;
import com.example.movieticketbooking.Domain.ListFilmScreening;
import com.example.movieticketbooking.Domain.ScreeningDatum;
import com.example.movieticketbooking.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MoviePageActivity extends AppCompatActivity implements OnItemClickListener {
    private TextView titleText, movieRateText, movieTimeText, movieDateText, summaryText, actorText;
    private ImageView bg_image, back_button, buy_button;
    private int idFilm;
    private String screeningTime, cinemaName, dayChosen;
    private int chosenPosition;
    private RecyclerView calender_recyclerView, cgv_recyclerView, cine_recyclerView;
    private RecyclerView.Adapter adapterCalender, adapterCgv, adapterCine;
    private ProgressBar loading;
    private boolean isFirstChosenTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        idFilm = getIntent().getIntExtra("id",0);
        initView();
        loadDataIntoView();
    }

    private void loadDataIntoView() {
        loading.setVisibility(View.VISIBLE);
        // Assuming you have a Firestore instance initialized
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        loadDataIntoInfomationView(db, idFilm);
        loadDataIntoCalendarView(db);
    }

    private void loadDataIntoCalendarView(FirebaseFirestore db) {
        CollectionReference screeningDataRef = db.collection("ScreeningData");

        Calendar calendar = Calendar.getInstance();
        int cur_day = calendar.get(Calendar.DAY_OF_MONTH);
        int cur_month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0 (January)
        int cur_year = calendar.get(Calendar.YEAR);
        List<DayDatum> data = new ArrayList<>();
        screeningDataRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Access the document data here
                    String fullDay = document.getString("fullDay");
                    // Split the string based on '/'
                    String[] dateParts = fullDay.split("/");

                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);

                    if (year > cur_year || ((year == cur_year) && (month > cur_month)) || ((year == cur_year) && (month == cur_month) && (day >= cur_day))) {
                        DayDatum datum = new DayDatum();
                        datum.setDayName(document.getString("dayName"));
                        datum.setDayNumber(document.getString("dayNumber"));
                        datum.setTag(fullDay);
                        data.add(datum);
                    }
                }

                ListCalenderDay items = new ListCalenderDay(data);
                if (items != null) {
                    adapterCalender = new CalendarAdapter(items);
                    ((CalendarAdapter) adapterCalender).setListener(this);
                    calender_recyclerView.setAdapter(adapterCalender);
                    ((CalendarAdapter) adapterCalender).clickItem0();
                }

            } else {
                // Handle errors
                Log.d("Load data to calendar view", "Error getting documents: ", task.getException());
            }
        });
    }

    private void loadDataIntoInfomationView(FirebaseFirestore db, int idToSearch) {
        // Create a reference to the FilmData collection
        CollectionReference filmDataRef = db.collection("MovieItem");

        // Construct the query to find documents where the "id" field matches your ID
        Query query = filmDataRef.whereEqualTo("id", idToSearch);

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    FilmDataItem filmItem = document.toObject(FilmDataItem.class);

                    Glide.with(MoviePageActivity.this)
                            .load(filmItem.getPoster())
                            .into(bg_image);

                    titleText.setText(filmItem.getTitle());
                    movieRateText.setText(filmItem.getScore());
                    movieTimeText.setText(filmItem.getRunTime());
                    movieDateText.setText(filmItem.getReleasedDay());
                    summaryText.setText(filmItem.getSummary());
                    actorText.setText(filmItem.getActors());

                    loading.setVisibility(View.GONE);
                }
            } else {
                // Handle errors
                Log.d("Load data into Detail", "Error getting documents: ", task.getException());
            }
        });
    }

    private void initView() {
        titleText = findViewById(R.id.movieTitle_MoviePage);
        movieRateText = findViewById(R.id.movieRateText_MoviePage);
        movieTimeText = findViewById(R.id.movieTimeText_MoviePage);
        movieDateText = findViewById(R.id.movieDateText_MoviePage);
        loading = findViewById(R.id.loading4);

        bg_image = findViewById(R.id.imageBackground_MoviePage);
        back_button = findViewById(R.id.back_button3);
        buy_button = findViewById(R.id.buyButton);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviePageActivity.this, SeatBookingActivity.class);
                intent.putExtra("idFilm",idFilm);
                intent.putExtra("dayTimeData",dayChosen);
                intent.putExtra("startTimeData",screeningTime);
                intent.putExtra("cinema",cinemaName);
                startActivity(intent);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        summaryText = findViewById(R.id.synopsisText);
        actorText = findViewById(R.id.actorText_MoviePage);

        RecyclerView.ItemAnimator customAnimator = new CustomItemAnimator(); // Your custom item animator

        calender_recyclerView = findViewById(R.id.calenderDay_RecycleView);
        calender_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        calender_recyclerView.setItemAnimator(customAnimator);

        cgv_recyclerView = findViewById(R.id.cgv_recycleView);
        cgv_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        cgv_recyclerView.setItemAnimator(customAnimator);

        cine_recyclerView = findViewById(R.id.cinestar_recyclerView);
        cine_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        cine_recyclerView.setItemAnimator(customAnimator);
    }

    @Override
    public void onItemClick(String selectedTime, String cinemaName, int chosenPosition) {
        if (!isFirstChosenTime){
            if (this.cinemaName.equals("CGV - Van Hanh Mall")){
                ((ScreeningFilmAdapter)cgv_recyclerView.getAdapter()).setSelectedItem(-1);
                cgv_recyclerView.getAdapter().notifyItemChanged(this.chosenPosition);
            } else if (this.cinemaName.equals("Cinestar - Nha Van Hoa SV")) {
                ((ScreeningFilmAdapter)cine_recyclerView.getAdapter()).setSelectedItem(-1);
                cine_recyclerView.getAdapter().notifyItemChanged(this.chosenPosition);
            }
        }

        this.screeningTime = selectedTime;
        this.cinemaName = cinemaName;
        this.chosenPosition = chosenPosition;
        isFirstChosenTime = false;
    }

    @Override
    public void onItemClick(String dayChosen) {
        this.dayChosen = dayChosen;
        Log.d("chosen", dayChosen);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference screeningDataRef = db.collection("ScreeningData");

        String[] chosenDateParts = dayChosen.split("/");

        int chosenDay = Integer.parseInt(chosenDateParts[0]);
        int chosenMonth = Integer.parseInt(chosenDateParts[1]);
        int chosenYear = Integer.parseInt(chosenDateParts[2]);
        isFirstChosenTime = true;

        screeningDataRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Access the document data here
                    String fullDay = document.getString("fullDay");
                    // Split the string based on '/'
                    String[] dateParts = fullDay.split("/");

                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);

                    if ((year == chosenYear) && (month == chosenMonth) && (day == chosenDay)) {
                        List<Map<String, String>> cgvArray = (List<Map<String, String>>) document.get("cgv");
                        List<ScreeningDatum> screeningDataList = new ArrayList<>();

                        if (cgvArray != null) {
                            for (Map<String, String> screening : cgvArray) {
                                ScreeningDatum screeningDatum = new ScreeningDatum(screening);
                                screeningDataList.add(screeningDatum);
                            }

                            ListFilmScreening cgvItems = new ListFilmScreening();
                            cgvItems.setData(screeningDataList);

                            Log.d("cgv", cgvItems.getData().toString());
                            if (cgvItems != null) {
                                adapterCgv = new ScreeningFilmAdapter(cgvItems);
                                ((ScreeningFilmAdapter) adapterCgv).setListener(this);
                                ((ScreeningFilmAdapter) adapterCgv).setCinemaName("CGV - Van Hanh Mall");
                                ((ScreeningFilmAdapter) adapterCgv).setSelectedItem(0); //default choice is the first available screening of CGV
                                cgv_recyclerView.setAdapter(adapterCgv);
                            }
                        }

                        //-------------------------

                        List<Map<String, String>> cinestarArray = (List<Map<String, String>>) document.get("cinestar");
                        List<ScreeningDatum> screeningDataList2 = new ArrayList<>();

                        if (cinestarArray != null) {
                            for (Map<String, String> screening : cinestarArray) {
                                ScreeningDatum screeningDatum = new ScreeningDatum(screening);
                                screeningDataList2.add(screeningDatum);
                            }

                            ListFilmScreening cinestarItems = new ListFilmScreening();
                            cinestarItems.setData(screeningDataList2);
                            Log.d("cine", cinestarItems.getData().toString());

                            if (cinestarItems != null) {
                                adapterCine = new ScreeningFilmAdapter(cinestarItems);
                                ((ScreeningFilmAdapter) adapterCine).setListener(this);
                                ((ScreeningFilmAdapter) adapterCine).setCinemaName("Cinestar - Nha Van Hoa SV");
                                cine_recyclerView.setAdapter(adapterCine);
                            }
                        }
                    }
                }
            } else {
                // Handle errors
                Log.d("Load data to calendar view", "Error getting documents: ", task.getException());
            }
        });
    }

    private class CustomItemAnimator extends RecyclerView.ItemAnimator {

        @Override
        public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            if (newHolder != null && newHolder != oldHolder) {
                newHolder.itemView.setAlpha(oldHolder.itemView.getAlpha()); //don't change alpha to the default 1.0f - keep the previous change on alpha
            }
            return false; // Return false to indicate you've handled the animation
        }

        @Override
        public void runPendingAnimations() {

        }

        @Override
        public void endAnimation(@NonNull RecyclerView.ViewHolder item) {

        }

        @Override
        public void endAnimations() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }
    }
}