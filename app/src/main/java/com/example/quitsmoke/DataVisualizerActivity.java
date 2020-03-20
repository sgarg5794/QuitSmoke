package com.example.quitsmoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataVisualizerActivity extends AppCompatActivity {

    Button btnDVlocation,btnDVdayperiod, btnDVprogress;
    TextView tvHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_visualizer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnDVdayperiod =(Button)findViewById(R.id.dayPeriodWise);
        btnDVlocation = (Button)findViewById(R.id.locationWise);
        btnDVprogress = (Button)findViewById(R.id.progress);
        // Font path
        String fontPath = "fonts/Quicksand-Bold.otf";

        // Loading Font Face
        Typeface m_typeFace = Typeface.createFromAsset(getAssets(), fontPath);


        // Applying font
        tvHeading = (TextView) findViewById(R.id.heading);
        tvHeading.setTypeface(m_typeFace);

        btnDVlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataVisualizerActivity.this, LocationWiseDataVisualizationActivity.class);
                startActivity(intent);
            }
        });
        btnDVdayperiod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataVisualizerActivity.this, DaywiseDataVisualizationActivity.class);
                startActivity(intent);

            }
        });
        btnDVprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataVisualizerActivity.this, UserProgressActivity.class);
                startActivity(intent);
            }
        });
    }
}
