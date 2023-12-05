package com.example.movieticketbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketbooking.Domain.ListFilmScreening;
import com.example.movieticketbooking.R;

public class ScreeningFilmAdapter extends RecyclerView.Adapter<ScreeningFilmAdapter.ViewHolder>{
    private ListFilmScreening items;
    Context context;
    int selectedItem;
    String selectedTime;
    String cinemaName;
    private OnItemClickListener listener;
    public ScreeningFilmAdapter(ListFilmScreening items){
        this.items = items;
        selectedTime = "example time";
        this.cinemaName = "cinema Name";
        selectedItem = -1;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public void setSelectedItem(int selectedItem) {
        if (selectedItem == 0) {
            for (int position = 0; position < items.getData().size(); position++){
                String state = items.getData().get(position).getState();
                if (state.equals("available")){
                    this.selectedItem = position;
                    this.selectedTime = items.getData().get(position).getTime();
                    listener.onItemClick(selectedTime, cinemaName, position);
                    return;
                }
            }
        }
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public ScreeningFilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_screening, parent, false);
        context = parent.getContext();
        return new ScreeningFilmAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreeningFilmAdapter.ViewHolder holder, int position) {
        String state = items.getData().get(position).getState();
        holder.screeningTimeText.setText(items.getData().get(position).getTime());

        if (state.equals("available")){
            holder.itemView.setAlpha(1.0f);
            if (selectedItem == position){
                //create a bounder of this view
                holder.screenView.setBackgroundResource(R.drawable.background_selected_time);
            }
            else {
                //use normal bounder
                holder.screenView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.tertiary_container));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTime = holder.screeningTimeText.getText().toString();
                    if (listener != null){
                        listener.onItemClick(selectedTime, cinemaName, holder.getAdapterPosition());
                        // remove bounder of the previous view and set bounder for new view
                        if (selectedItem != 0){
                            int prevItem = selectedItem;
                            selectedItem = holder.getAdapterPosition();
                            notifyItemChanged(prevItem);
                            notifyItemChanged(selectedItem);
                        }
                        else{
                            selectedItem = holder.getAdapterPosition();
                            notifyItemChanged(selectedItem);
                        }
                    }
                }
            });
        }
        else{ //unavailable
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "This screening is not available!" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView screeningTimeText;
        private ImageView screenView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            screeningTimeText = itemView.findViewById(R.id.screeningTimeText);
            screenView = itemView.findViewById(R.id.screeningView);
        }
    }
}
