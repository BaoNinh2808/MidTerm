//package com.example.movieticketbooking.Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.AppCompatButton;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.movieticketbooking.Activity.PaymentActivity;
//import com.example.movieticketbooking.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartHistoryAdapter extends RecyclerView.Adapter<CartHistoryAdapter.ViewHolder>{
//    private Context context;
//    private List<HistoryItem> data;
//    public CartHistoryAdapter(Context context){
//        this.context = context;
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        String userId = user.getUid();
//
//        data = new ArrayList<>();
//
//        db.collection("cartHistory")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                // Convert each document to FilmItem and add to the list
//                                HistoryItem historyItem = document.toObject(HistoryItem.class);
//                                if (historyItem.getId().equals(userId)){
//                                    data.add(historyItem);
//                                }
//                            }
//                        } else {
//                            Log.w("Load film data from firebase", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//
//    }
//    @NonNull
//    @Override
//    public CartHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart_history, parent, false);
//        context = parent.getContext();
//        return new CartHistoryAdapter.ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CartHistoryAdapter.ViewHolder holder, int position) {
//        holder.numOfTickets.setText(data.get(position).getNumOfTickets());
//        holder.cinemaText.setText(data.get(position).getCinema());
//        holder.dayTimeText.setText(data.get(position).getScreeningDay());
//        holder.movieDateText.setText(data.get(position).getDate());
//        holder.movieRateText.setText(data.get(position).getRate());
//        holder.movieTimeText.setText(data.get(position).getTime());
//        holder.startTimeText.setText(data.get(position).getScreeningTime());
//        holder.priceText.setText(data.get(position).getPrice());
//        holder.titleText.setText(data.get(position).getTitle());
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private AppCompatButton getTicket_button;
//        private TextView titleText, movieRateText, movieTimeText, movieDateText, dayTimeText, startTimeText, cinemaText;
//        private TextView numOfTickets, priceText;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            numOfTickets = itemView.findViewById(R.id.noTicketsText);
//            priceText = itemView.findViewById(R.id.priceText);
//            titleText = itemView.findViewById(R.id.movieTitle_PayPage);
//            movieRateText = itemView.findViewById(R.id.movieRateText_MoviePage);
//            movieTimeText = itemView.findViewById(R.id.movieTimeText_MoviePage);
//            movieDateText = itemView.findViewById(R.id.movieDateText_MoviePage);
//            dayTimeText = itemView.findViewById(R.id.dayTimeText);
//            startTimeText = itemView.findViewById(R.id.startTimeText);
//            cinemaText = itemView.findViewById(R.id.cinemaText);
//            getTicket_button = itemView.findViewById(R.id.getTicketButton);
//            getTicket_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, PaymentActivity.class);
//                    intent.putExtra("numOfTickets", numOfTickets.getText().toString());
//                    intent.putExtra("price", priceText.getText().toString());
//                    intent.putExtra("title",titleText.getText().toString());
//                    intent.putExtra("rate", movieRateText.getText().toString());
//                    intent.putExtra("time", movieTimeText.getText().toString());
//                    intent.putExtra("date", movieDateText.getText().toString());
//                    intent.putExtra("screeningDay", dayTimeText.getText().toString());
//                    intent.putExtra("screeningTime", startTimeText.getText().toString());
//                    intent.putExtra("cinema", cinemaText.getText().toString());
//                    context.startActivity(intent);
//                }
//            });
//        }
//    }
//}
