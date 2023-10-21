package com.example.ead;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View.OnClickListener;
import android.content.Intent;

import com.example.ead.R;
import com.example.ead.Train;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {
    private Context context;
    private List<Train> trainList;

    public TrainAdapter(Context context) {
        this.context = context;
    }

    public void setTrainList(List<Train> trains) {
        this.trainList = trains;
        notifyDataSetChanged(); // Call notifyDataSetChanged on the adapter instance
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_train_view, parent, false);
        return new TrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainViewHolder holder, int position) {
        if (trainList != null && position < trainList.size()) {
            Train train = trainList.get(position);
            holder.trainNumberTextView.setText("Train Number: " + train.getTrainNumber());
            holder.timeTextView.setText("Train Departure Time: " + train.getDepartureTime());
            holder.capacityTextView.setText("Train Capacity: " + train.getCapacity());

            // Set an onClickListener for the bookButton
            holder.bookButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Here, you can add code to handle booking the train or any other action you want.
                    Train train = trainList.get(position);

                    // Create an Intent to start the next activity
                    Intent intent = new Intent(context, BookingActivity.class);

                    // Add the train ID as an extra to the intent
                    intent.putExtra("trainId", train.getId());

                    // Start the next activity
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (trainList != null) {
            return trainList.size();
        } else {
            return 0;
        }
    }

    static class TrainViewHolder extends RecyclerView.ViewHolder {
        TextView trainNumberTextView;
        TextView timeTextView;
        TextView capacityTextView;
        Button bookButton;

        TrainViewHolder(View itemView) {
            super(itemView);
            trainNumberTextView = itemView.findViewById(R.id.trainNumberTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            capacityTextView = itemView.findViewById(R.id.capacityTextView);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}
