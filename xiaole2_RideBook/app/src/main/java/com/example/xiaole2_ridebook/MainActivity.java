/**
 *The RideBook program implements an application that
 * allows users to add/update/delete rides in the RideList.
 * Users can view their rides in the RideList at the Main page.
 *
 * Name: Xiaole Zeng
 * Student ID:1516080
 */
package com.example.xiaole2_ridebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * The MainActivity Class is the main page
 * It shows the list of ride
 * addButton can add a new ride
 * Click the item on the list can view and edit the selected ride
 * At the bottom, it calculate the total distances of all the rides in the dataList
 */
public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView rideList;
    private ArrayList<Ride> dataList;
    private ArrayAdapter<Ride> rideAdapter;
    private TextView totalDist;


    //add a different request code for every activity you are starting from here
    private static final int SECOND_ACTIVITY_REQUEST_CODE=0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE_edit=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        rideList = findViewById(R.id.my_ride);
        totalDist = findViewById(R.id.totalDistance);


        dataList = new ArrayList<>();
        //dataList.addAll(Arrays.asList(rides));
        rideAdapter = new RideList(this, dataList);
        rideList.setAdapter(rideAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent,SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        //pass selected item to the SecondActivity through Intent
        rideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ride clickedRide = dataList.get(i);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("RideInfo",clickedRide);
                intent.putExtra("index",i);
                startActivityForResult(intent,SECOND_ACTIVITY_REQUEST_CODE_edit);

            }
        });
    }

    //https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android/10407371#10407371
    /**
     * This get the result from the SecondActivity
     * According to the SECOND_ACTIVITY_REQUEST_CODE to decide which action it need to implement
     * Add: add a new ride to the dataList
     * Update: update a current ride's information
     * Delete: delete the current selected ride.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                Ride ride = (Ride) data.getSerializableExtra("RideInfo");
                rideAdapter.add(ride);
            }
            double totalDistance = sum(dataList);
            totalDist.setText(String.valueOf(totalDistance));
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE_edit){
            if(resultCode==RESULT_OK){
                Ride ride = (Ride) data.getSerializableExtra("RideInfo");
                Integer index = data.getIntExtra("index",0);
                dataList.set(index,ride);
                rideAdapter.notifyDataSetChanged();
                double totalDistance = sum(dataList);
                totalDist.setText(String.valueOf(totalDistance));

            }else{
                Integer index = data.getIntExtra("index",0);
                rideAdapter.remove(rideAdapter.getItem(index));
                rideAdapter.notifyDataSetChanged();
                double totalDistance = sum(dataList);
                totalDist.setText(String.valueOf(totalDistance));
            }
        }
    }

    /**
     * This method is used to calculate the total distance of all the rides in the dataList
     * @param list
     * @return integer (the total distance)
     */
    // define a sum method
    public static double sum(ArrayList<Ride> list){
        double sum = 0;
        for(int i=0; i<list.size();i++){
            double d = Double.parseDouble(list.get(i).getDistance());
            sum += d;
        }
        return sum;
    }
}
