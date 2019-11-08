package com.example.xiaole2_ridebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

/**
 * The SecondActivity Class represents the Ride Info that users want to add
 * a new ride or update a clicked ride from the MainActivity.
 * DeleteButton cannot delete a empty ride.
 * Users have to add a new ride to back to the MainActivity if they click addButton from the MainActivity.
 * Users can update their changes from the clicked ride at Main Page
 */
public class SecondActivity extends AppCompatActivity {

    private Button addButton;
    private Button deleteButton;

    private EditText dateEditText;
    private EditText timeEditText;
    private EditText distanceEditText;
    private  EditText speedEditText;
    private EditText cadenceEditText;
    private EditText commentEditText;
    private TextView count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_info);
        setTitle("Ride Info");

        addButton = findViewById(R.id.add_edit_ride);
        deleteButton = findViewById(R.id.delete_ride);

        dateEditText =findViewById(R.id.date_ride);
        timeEditText = findViewById(R.id.time_ride);
        distanceEditText = findViewById(R.id.distance_ride);
        speedEditText = findViewById(R.id.speed_ride);
        cadenceEditText=findViewById(R.id.cadence_ride);
        commentEditText=findViewById(R.id.comment_ride);
        count=findViewById(R.id.count);

        //Add back Button
        //https://www.youtube.com/watch?v=s3pheMAmaPI
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * Show the Ride Info from the MainActivity
         */
        final Intent intent = getIntent();
        if (intent.getExtras() != null){
            Ride clickedRide = (Ride) intent.getSerializableExtra("RideInfo");
            dateEditText.setText(clickedRide.getDate());
            timeEditText.setText(clickedRide.getTime());
            distanceEditText.setText(clickedRide.getDistance());
            speedEditText.setText(clickedRide.getAvg_speed());
            cadenceEditText.setText(clickedRide.getAvg_cadence());
            commentEditText.setText(clickedRide.getComment());
        }
        /**
         * addButton will do 2 different functionalities.
         * One is to add a new ride to the RideList.
         * Another is to update/edit the current clicked ride from the RideList.
         * It checks the validation of the format of each EditText box.
         * After a successful validation, it gets the new/updated value from each EditText box.
         * Pass the value through Intent to the MainActivity
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( dateEditText.getText().toString().length() == 0 ){
                    dateEditText.setError( "Date is required!" );}
                else if( dateEditText.getText().toString().length() != 10 ){
                    dateEditText.setError( "yyyy-mm-dd" );}
                else if( !CheckValidDate(dateEditText.getText().toString())){
                    dateEditText.setError( "invalid range for year,mon,day\nyear:1900-2200\nmon:1-12\nday:1-31" );}
                else if( timeEditText.getText().toString().length() == 0 ){
                    timeEditText.setError( "Time is required!" );}
                else if( timeEditText.getText().toString().length() != 5 ){
                    timeEditText.setError( "hh:mm" );}
                else if( !CheckValidTime(timeEditText.getText().toString())){
                    timeEditText.setError( "invalid range for hour,minute\nhour:0-24\nmin:0-59" );}
                else if( distanceEditText.getText().toString().length() == 0 ){
                    distanceEditText.setError( "Distance is required!" );}
                else if( !CheckValidNum(distanceEditText.getText().toString())){
                    distanceEditText.setError( "invalid non-negative decimal" );}
                else if( speedEditText.getText().toString().length() == 0 ){
                    speedEditText.setError( "Average Speed is required!" );}
                else if( !CheckValidNum(speedEditText.getText().toString())){
                    speedEditText.setError( "invalid non-negative decimal" );}
                else if( cadenceEditText.getText().toString().length() == 0 ){
                    cadenceEditText.setError( "Average Cadence is required!" );}

                else {
                    String date = dateEditText.getText().toString();
                    String time = timeEditText.getText().toString();
                    String distance = distanceEditText.getText().toString();
                    String speed = speedEditText.getText().toString();
                    String cadence = cadenceEditText.getText().toString();
                    String comment = commentEditText.getText().toString();

                    if (intent.getExtras() != null) {
                        Ride clickedRide = (Ride) intent.getSerializableExtra("RideInfo");
                        clickedRide.setDate(date);
                        clickedRide.setTime(time);
                        clickedRide.setDistance(distance);
                        clickedRide.setAvg_speed(speed);
                        clickedRide.setAvg_cadence(cadence);
                        clickedRide.setComment(comment);

                        setResult(RESULT_OK, intent);
                        finish();

                    } else {
                        Ride ride = new Ride(date, time, distance, speed, cadence, comment);
                        ride.setDate(date);
                        ride.setTime(time);
                        ride.setDistance(distance);
                        ride.setAvg_speed(speed);
                        ride.setAvg_cadence(cadence);
                        ride.setComment(comment);
                        Intent intent = new Intent();
                        intent.putExtra("RideInfo", (Serializable) ride);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
        /**
         * The function of deleteButton is return nothing from the SecondActivity to MainActivity
         * If user clicked the deleteButton, the SecondActivity will return RESULT_CANCELED,
         * so MainActivity knows it needs to delete the ride from the dataList
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getExtras() != null){
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            }
        });

        /**
         *This function of commentEditText is to tracking and updating the number of characters
         * in the commentEditText box. A small number will update at the right bottom of the box.
         */
        //https://www.youtube.com/watch?v=sk3GWcbgijI
        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = commentEditText.getText().toString();
                int num =s.length();
                count.setText(""+num);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
//Cannot return to the main_activity if OPEN Second Activity by OnClickItem
//    @Override
//   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        int id = item.getItemId();

 //       if(id == android.R.id.home){
    //       this.finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * This method is used to check the valid range for year, month and day.
     * The year is designed to be between 1900 to 2200
     * The month is between 0 to 12
     * The day is between 0 to 31
     * @param date
     * @return true or false
     */
    private static boolean CheckValidDate(String date){
        String clean = date.replaceAll("-","");
        int year = Integer.parseInt(clean.substring(0,4));
        int mon =Integer.parseInt(clean.substring(4,6));
        int day = Integer.parseInt(clean.substring(6,8));

        if ((year<=2200 && year>= 1900 ) && (mon <=12 && mon >=1) &&(day <=31 && day >=1)){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * This method is used to check the valid range for hour and minute
     * The hour should be between 0 to 24
     * The minute should be between 0 to 59
     * @param time
     * @return true or false
     */
    private static boolean CheckValidTime(String time){
        String clean = time.replaceAll(":","");
        int hour = Integer.parseInt(clean.substring(0,2));
        int min =Integer.parseInt(clean.substring(2,4));
        if ((hour<=24 && hour>=0 ) && (min < 60 && min >=0) ){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method is to used to check the input string
     * whether is a valid decimal number or not.
     * @param num
     * @return true or false
     */
    private static boolean CheckValidNum(String num){
        int k =0;
        for (int i =0;i<num.length();i++){
            if (num.charAt(i)=='.')
                k+=1;
        }
        if (k > 1){
            return false;
        }else {
            return true;
        }
    }
}

