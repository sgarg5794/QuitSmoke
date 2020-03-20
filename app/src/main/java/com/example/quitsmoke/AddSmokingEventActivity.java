package com.example.quitsmoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSmokingEventActivity extends AppCompatActivity {

    int selectedlocationId, locationId;
    TextView cigAmount;
    private LocationManager locationManager;
    private LocationListener listener;
    private Location location;
    User user;
    Double lat = 0.0;
    Double lon = 0.0;
    Button btnSave;
    MyDBHandler db = new MyDBHandler(this);
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_smoking_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupLocation);
        System.out.println("********"+(TextView)findViewById(R.id.add_event_heading));
        System.out.println("--------------"+radioGroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
        selectedlocationId = radioButton.getId();
        btnSave = (Button) findViewById(R.id.button_save);
        cigAmount=(TextView)findViewById(R.id.cig_amount_today);
        cigAmount.setTextColor(Color.RED);
        cigAmount.setText(db.getNumOfCigByDate(dateFormat.format(date))+" Cigarette(s)");
        //System.out.println("Number of cig:" + db.getNumOfCigByDate(Utility.getDate()));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedlocationId = checkedId;
                if(selectedlocationId==R.id.button_home)
                    locationId=1;
                else if(selectedlocationId==R.id.button_work)
                    locationId=2;
                else if(selectedlocationId==R.id.button_driving)
                    locationId=3;
                else if(selectedlocationId==R.id.button_walking)
                    locationId=4;
                else if(selectedlocationId==R.id.button_school)
                    locationId=5;
                else
                    locationId=6;
                System.out.println("Location ID: " + locationId);
            }
        });

        System.out.println("Day Period: " + TimeUtil.getDayPeriod());
        user = db.getSingleUser();
        System.out.println("Date Time: " + dateFormat.format(date));
        System.out.println("UserID: " + user.getId());
        System.out.println("Longitude: " + String.valueOf(lon));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Save Button Clicked: " + String.valueOf(lon));
                db.addEventMaster(new UserEvent(TimeUtil.getDayPeriod(),user.getId(),locationId,lat,lon,dateTimeFormat.format(date)));
                Intent intent = new Intent(AddSmokingEventActivity.this, AddSmokingEventSuccess.class);
                intent.putExtra("location",selectedlocationId);
                startActivity(intent);
            }
        });
    }
}
