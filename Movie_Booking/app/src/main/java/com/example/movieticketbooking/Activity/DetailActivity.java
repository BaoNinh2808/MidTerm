package com.example.movieticketbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieticketbooking.Domain.FilmDataItem;
import com.example.movieticketbooking.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetailActivity extends AppCompatActivity {
    private TextView titleText, movieRateText, movieTimeText, movieDateText, summaryText, actorText;
    private ImageView bg_image, back_button;
    private ShapeableImageView fore_image;
    private NestedScrollView scrollView;
    private int idFilm;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        idFilm = getIntent().getIntExtra("id",0);
        initView();
        loadDataIntoView();
    }

    private void loadDataIntoView() {
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        // Assuming you have a Firestore instance initialized
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
                    Glide.with(DetailActivity.this)
                            .load(filmItem.getPoster())
                            .into(fore_image);

                    Glide.with(DetailActivity.this)
                            .load(filmItem.getPoster())
                            .into(bg_image);

                    titleText.setText(filmItem.getTitle());
                    movieRateText.setText(filmItem.getScore());
                    movieTimeText.setText(filmItem.getRunTime());
                    movieDateText.setText(filmItem.getReleasedDay());
                    summaryText.setText(filmItem.getSummary());
                    actorText.setText(filmItem.getActors());

                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            } else {
                // Handle errors
                Log.d("Load data into Detail", "Error getting documents: ", task.getException());
            }
        });
    }

    private void initView() {
        titleText = findViewById(R.id.movieTitleText);
        movieRateText = findViewById(R.id.movieRateText);
        movieTimeText = findViewById(R.id.movieTimeText);
        movieDateText = findViewById(R.id.movieDateText);

        progressBar = findViewById(R.id.detailProgressBar);
        scrollView = findViewById(R.id.scrollView3);

        fore_image = findViewById(R.id.posterNormaling);
        bg_image = findViewById(R.id.posterBigImage);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        summaryText = findViewById(R.id.summaryText);
        actorText = findViewById(R.id.actorText);
    }
}