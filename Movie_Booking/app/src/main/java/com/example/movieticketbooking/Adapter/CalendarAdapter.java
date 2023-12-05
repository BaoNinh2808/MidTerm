package com.example.movieticketbooking.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieticketbooking.Domain.ListCalenderDay;
import com.example.movieticketbooking.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{
    private ListCalenderDay items;
    private Context context;
    private int selectedItem;
    private OnItemClickListener listener;

    public CalendarAdapter(ListCalenderDay items){
        this.items = items;
        selectedItem = 0;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public void clickItem0(){
        listener.onItemClick(items.getData().get(0).getTag().toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_calender, parent, false);
        context = parent.getContext();
        return new CalendarAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayName.setText(items.getData().get(position).getDayName());
        holder.dayNumber.setText(items.getData().get(position).getDayNumber());
        if (position == selectedItem){
            holder.itemView.setAlpha(1.0f);
        }
        else{
            holder.itemView.setAlpha(0.35f);
        }
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayName, dayNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            dayNumber = itemView.findViewById(R.id.dayNumber);

            itemView.setOnClickListener(null);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int oldPosition = selectedItem;
                    //current position
                    selectedItem = getAdapterPosition();
                    //change the transparent of old position
                    if (selectedItem == RecyclerView.NO_POSITION){
                        Log.d("Recycler view", "No Position");
                    }
                    else{
                        notifyItemChanged(oldPosition);
                        notifyItemChanged(selectedItem);
                        listener.onItemClick(items.getData().get(selectedItem).getTag().toString());
                    }
                }
            });
        }
    }
}
