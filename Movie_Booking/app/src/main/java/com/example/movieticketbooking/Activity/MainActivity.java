package com.example.movieticketbooking.Activity;

import androidx.annotation.NonNull;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieticketbooking.Adapter.FilmListAdapter;
import com.example.movieticketbooking.Domain.FilmGeneralData;
import com.example.movieticketbooking.Domain.FilmItem;
import com.example.movieticketbooking.Domain.ListFilm;
import com.example.movieticketbooking.Domain.List_Film;
import com.example.movieticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterNewMovies, adapterAllMovies;
    private RecyclerView recyclerViewNewMovies, recyclerViewAllMovies;
    private ProgressBar loading1, loading2;
    private TextView viewAll;
    private ImageView mapIcon, allMoviesIcon, shoppingCartIcon, ticketIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadDataIntoNewMoviesView();
        loadDataIntoAllMoviesView();
    }

    private void loadDataIntoNewMoviesView() {
        loading1.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<FilmGeneralData> filmItemList = new ArrayList<>();

        db.collection("NewFilmData")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convert each document to FilmItem and add to the list
                                FilmGeneralData filmItem = document.toObject(FilmGeneralData.class);
                                filmItemList.add(filmItem);
                            }

                            List_Film listFilm = new List_Film();
                            listFilm.setData(filmItemList);
                            adapterNewMovies = new FilmListAdapter(listFilm);
                            recyclerViewNewMovies.setAdapter(adapterNewMovies);
                            loading1.setVisibility(View.GONE);
                        } else {
                            loading1.setVisibility(View.GONE);
                            Log.w("Load film data from firebase", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void loadDataIntoAllMoviesView() {
        loading2.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<FilmGeneralData> filmItemList = new ArrayList<>();

        db.collection("FilmData")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convert each document to FilmItem and add to the list
                                FilmGeneralData filmItem = document.toObject(FilmGeneralData.class);
                                filmItemList.add(filmItem);
                            }

                            List_Film listFilm = new List_Film();
                            listFilm.setData(filmItemList);
                            adapterAllMovies = new FilmListAdapter(listFilm);
                            recyclerViewAllMovies.setAdapter(adapterAllMovies);
                            loading2.setVisibility(View.GONE);
                        } else {
                            loading2.setVisibility(View.GONE);
                            Log.w("Load film data from firebase", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initView() {
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAllMovies = findViewById(R.id.view2);
        recyclerViewAllMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);
        viewAll = findViewById(R.id.viewAllText);

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllMoviesActivity.class);
                startActivity(intent);
            }
        });

        mapIcon = findViewById(R.id.mapIcon);
        shoppingCartIcon = findViewById(R.id.shoppingCartIcon);
        allMoviesIcon = findViewById(R.id.allMovieIcon);
        ticketIcon = findViewById(R.id.ticketIcon);

        allMoviesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllMoviesActivity.class);
                startActivity(intent);
            }
        });

        shoppingCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}