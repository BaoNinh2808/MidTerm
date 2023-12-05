package com.example.movieticketbooking.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieticketbooking.Adapter.CalendarAdapter;
import com.example.movieticketbooking.Adapter.FilmListAdapter;
import com.example.movieticketbooking.Domain.FilmDataItem;
import com.example.movieticketbooking.Domain.FilmGeneralData;
import com.example.movieticketbooking.Domain.ListCalenderDay;
import com.example.movieticketbooking.Domain.ListFilm;
import com.example.movieticketbooking.Domain.List_Film;
import com.example.movieticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllMoviesActivity extends AppCompatActivity {
    private EditText searchText;
    private RecyclerView recyclerViewAllMovie;
    private RecyclerView.Adapter adapterAllMovie;
    private ProgressBar loading;
    private ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        initView();
        loadDataIntoView();
    }

    private void loadDataIntoView() {
        loading.setVisibility(View.VISIBLE);
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
                            adapterAllMovie = new FilmListAdapter(listFilm);
                            recyclerViewAllMovie.setAdapter(adapterAllMovie);
                            loading.setVisibility(View.GONE);
                        } else {
                            loading.setVisibility(View.GONE);
                            Log.w("Load film data from firebase", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initView() {
        searchText = findViewById(R.id.searchText);
        recyclerViewAllMovie = findViewById(R.id.all_movie_recycler);

        int numOfColumns = 3;
        recyclerViewAllMovie.setLayoutManager(new GridLayoutManager(this, numOfColumns));

        loading = findViewById(R.id.loading3);
        back_button = findViewById(R.id.back_button2);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
