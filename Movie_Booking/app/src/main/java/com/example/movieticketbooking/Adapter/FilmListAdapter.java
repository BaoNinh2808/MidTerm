package com.example.movieticketbooking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieticketbooking.Activity.MoviePageActivity;
import com.example.movieticketbooking.Domain.ListFilm;
import com.example.movieticketbooking.Domain.List_Film;
import com.example.movieticketbooking.R;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    List_Film items;
    Context context;

    public FilmListAdapter(List_Film items){
        this.items = items;
    }
    @NonNull
    @Override
    public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_films, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
        holder.titleText.setText(items.getData().get(position).getTitle());
        holder.scoreText.setText(items.getData().get(position).getScore());

        Glide.with(holder.itemView.getContext())
                .load(items.getData().get(position).getPosterURL())
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, scoreText;
        ImageView picture;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            scoreText = itemView.findViewById(R.id.scoreText);
            picture = itemView.findViewById(R.id.picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), MoviePageActivity.class);
                    intent.putExtra("id", items.getData().get(getAdapterPosition()).getId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
