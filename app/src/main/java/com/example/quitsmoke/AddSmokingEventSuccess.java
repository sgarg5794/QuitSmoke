package com.example.quitsmoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSmokingEventSuccess extends AppCompatActivity {

    TextView textViewLoc;
    TextView cigAmount;
    MyDBHandler db = new MyDBHandler(this);
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_smoking_event_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getActionBar().setHomeButtonEnabled(true);
        // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewLoc= (TextView) findViewById(R.id.tv_done);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        int locationid=6;
        if(b!=null)
        {
            locationid = b.getInt("location");
            System.out.println("LocationID from Intent:" + locationid);
        }
        cigAmount=(TextView)findViewById(R.id.cig_amount_today);
        cigAmount.setTextColor(Color.RED);
        cigAmount.setText(db.getNumOfCigByDate(dateFormat.format(date))+" Cigarette(s)");
        if(locationid==R.id.button_home)
            textViewLoc.setCompoundDrawablesWithIntrinsicBounds( R.drawable.home_symbol, 0, 0, 0);
        else if(locationid==R.id.button_work)
            textViewLoc.setCompoundDrawablesWithIntrinsicBounds( R.drawable.work_symbol, 0, 0, 0);
        else if(locationid==R.id.button_driving)
            textViewLoc.setCompoundDrawablesWithIntrinsicBounds( R.drawable.driving_symbol, 0, 0, 0);
        else if(locationid==R.id.button_walking)
            textViewLoc.setCompoundDrawablesWithIntrinsicBounds( R.drawable.walk_symbol, 0, 0, 0);
        else if(locationid==R.id.button_school)
            textViewLoc.setCompoundDrawablesWithIntrinsicBounds( R.drawable.school_symbol, 0, 0, 0);
        else
            textViewLoc.setCompoundDrawablesWithIntrinsicBounds( R.drawable.others_symbol, 0, 0, 0);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        System.out.println("Back Button");
        startActivity(new Intent(AddSmokingEventSuccess.this, UserDashboard.class));
        finish();
    }
}
