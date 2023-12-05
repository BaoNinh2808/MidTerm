package com.example.movieticketbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketbooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private ImageView back_button;
    private AppCompatButton getTicket_button;
    private TextView titleText, movieRateText, movieTimeText, movieDateText, dayTimeText, startTimeText, cinemaText;
    private TextView numOfTickets, priceText;
    private EditText codeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        addToHistory();
    }

    private void addToHistory() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String userId = user.getUid();

        // Create a new user with a first and last name
        Map<String, Object> history = new HashMap<>();
        history.put("id", userId);
        history.put("numOfTickets", numOfTickets.getText().toString());
        history.put("price", priceText.getText().toString());
        history.put("title",titleText.getText().toString());
        history.put("rate", movieRateText.getText().toString());
        history.put("time", movieTimeText.getText().toString());
        history.put("date", movieDateText.getText().toString());
        history.put("screeningDay", dayTimeText.getText().toString());
        history.put("screeningTime", startTimeText.getText().toString());
        history.put("cinema", cinemaText.getText().toString());

        // Add a new document with a generated ID
        db.collection("cartHistory")
                .add(history)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("History", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("History", "Error adding document", e);
                    }
                });
    }

    private void initView() {
        back_button = findViewById(R.id.back_button5);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        getTicket_button = findViewById(R.id.getTicketButton);
        codeText = findViewById(R.id.codeText);

        getTicket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeText.getText().toString();
                if (code.equals("123")){
                    //delete history
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference collectionRef = db.collection("cartHistory");

                    String nameToSearch = titleText.getText().toString(); // Replace with the name you want to search for
                    String priceOfTickets = priceText.getText().toString();
                    collectionRef.whereEqualTo("title", nameToSearch)
                            .whereEqualTo("price", priceOfTickets)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference documentRef = document.getReference();

                                        // Delete the document
                                        documentRef.delete()
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("Delete history", "DocumentSnapshot successfully deleted!");
                                                    // You can perform additional actions here after deletion if needed
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.w("Delete history", "Error deleting document", e);
                                                    // Handle deletion failure
                                                });
                                    }
                                } else {
                                    // Handle errors
                                    Log.d("Delete history", "Error getting documents: ", task.getException());
                                }
                            });

                    //go to QR code page
                    Intent intent = new Intent(PaymentActivity.this, TicketActivity.class);
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
            }
        });
    }
}