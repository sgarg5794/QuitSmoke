package com.example.quitsmoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddUserActivity extends AppCompatActivity {

    Button submitButton;
    EditText nameField,emailField,ageField,costPerPktField,cigPerPacketField,cigPerDayField,txtTargetAmount;
    DatePicker datePickerTargetDate;
    TextView textViewTargetDate;
    Date targetDate;
    MyDBHandler db = new MyDBHandler(this);
    int day,month,years;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        submitButton = (Button) findViewById(R.id.btnSave);
        nameField=(EditText)findViewById(R.id.txtName);
        emailField=(EditText)findViewById(R.id.txtEmail);
        ageField=(EditText)findViewById(R.id.txtAge);
        costPerPktField=(EditText)findViewById(R.id.txtCostPrPkt);
        cigPerPacketField=(EditText)findViewById(R.id.txtCigPerPkt);
        cigPerDayField=(EditText)findViewById(R.id.txtCigPerDay);
        txtTargetAmount=(EditText)findViewById(R.id.txtTargetAmount);
        datePickerTargetDate=(DatePicker)findViewById(R.id.datePickerTargetDate);
        textViewTargetDate =(TextView)findViewById(R.id.tvTargetDate);
        Calendar today = Calendar.getInstance();
        day = datePickerTargetDate.getDayOfMonth();
        month = datePickerTargetDate.getMonth();
        years = datePickerTargetDate.getYear();
        targetDate = new Date(years - 1900, month, day);


        textViewTargetDate.setText("Achieve Goal By: " + dateFormat.format(targetDate).toString());
        datePickerTargetDate.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        day = dayOfMonth;
                        month = monthOfYear;
                        years = year;
                        targetDate = new Date(years - 1900, month, day);
                        textViewTargetDate.setText("Achieve Goal By: " + dateFormat.format(targetDate).toString());
                        //Toast.makeText(getApplicationContext(),"onDateChanged", Toast.LENGTH_SHORT).show();
                    }});
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Data is saving ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                progressBarStatus = 0;

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {
                            db.addUser(new User(nameField.getText().toString(),Integer.parseInt(ageField.getText().toString()), emailField.getText().toString()));
                            progressBarStatus = 50;
                            User user = db.getSingleUser();
                            System.out.println("UID: " + user.getId());
                            System.out.println("Number of Cig: " + cigPerPacketField.getText().toString());
                            System.out.println("Cost of Cig: " + new Double(costPerPktField.getText().toString()));
                            System.out.println("Cig per day: " + cigPerDayField.getText().toString());
                            System.out.println("Target: " + txtTargetAmount.getText().toString());
                            System.out.println("Target Date: " + dateFormat.format(targetDate));
                            db.addRecordMaster(new UserRecord(user.getId(),Integer.parseInt(cigPerPacketField.getText().toString()),new Double(costPerPktField.getText().toString()),
                                    Integer.parseInt(cigPerDayField.getText().toString()),
                                    Integer.parseInt(txtTargetAmount.getText().toString()),dateFormat.format(targetDate)));
                            progressBarStatus=100;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            progressBarbHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }

                        if (progressBarStatus >= 100) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBar.dismiss();
                            Intent intent = new Intent(AddUserActivity.this, UserDashboard.class);
                            startActivity(intent);
                        }
                    }
                }).start();
            }
        });
    }
}
