package com.example.xiaole2_ridebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * This RideList read the most important values (date, time, distance) from the dataList.
 * It will show the key words in the RideList at MainActivity
 * Most parts from the Lab 3 example
 */
public class RideList extends ArrayAdapter<Ride> {

    private Context context;
    private List<Ride> rides;

    public RideList(@NonNull Context context, List<Ride> rides) {
        super(context,0, rides);
        this.context = context;
        this.rides = rides;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.my_ride,parent,false);
        }
        TextView dateTextView =view.findViewById(R.id.ride_text_date);
        TextView timeTextView = view.findViewById(R.id.ride_text_time);
        TextView distanceTextView = view.findViewById(R.id.ride_text_distance);

        Ride ride = rides.get(position);

        dateTextView.setText(ride.getDate());
        timeTextView.setText(ride.getTime());
        distanceTextView.setText(ride.getDistance());

        return view;
    }
}
