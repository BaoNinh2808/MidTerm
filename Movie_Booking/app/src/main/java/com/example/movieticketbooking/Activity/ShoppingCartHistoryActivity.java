//package com.example.movieticketbooking.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//import com.example.movieticketbooking.Adapter.CartHistoryAdapter;
//import com.example.movieticketbooking.R;
//
//public class ShoppingCartHistoryActivity extends AppCompatActivity {
//    private RecyclerView.Adapter adapterCart;
//    private RecyclerView recyclerViewCart;
//    private ProgressBar loading;
//    private ImageView back_button;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping_cart_history);
//        initView();
//        loadDataIntoView();
//    }
//
//    private void loadDataIntoView() {
//        loading.setVisibility(View.VISIBLE);
//        adapterCart = new CartHistoryAdapter(this);
//        recyclerViewCart.setAdapter(adapterCart);
//    }
//
//    private void initView() {
//        recyclerViewCart = findViewById(R.id.view1);
//        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        back_button = findViewById(R.id.back_button5);
//        loading = findViewById(R.id.loading1);
//        back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//}