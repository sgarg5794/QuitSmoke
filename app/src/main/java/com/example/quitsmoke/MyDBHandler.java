package com.example.quitsmoke;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {


    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuitSmokeDB.db";

    // User table name
    private static final String USER_TABLE = "UserConfig";
    // UserConfig Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_AGE = "age";
    private static final String KEY_USER_EMAIL = "email";

    //Location Master
    private static final String LOCATION_NAME = "name";
    private static final String LOCATION_ID = "id";
    private static final String LOCATION_TABLE = "LocationMaster";

    //Day Period
    private static final String PERIOD_NAME = "name";
    private static final String PERIOD_ID = "id";
    private static final String DAY_PERIOD_TABLE = "DayPeriod";

    // Record Master
    private static final String TABLE_RECORD_MASTER = "RecordMaster";
    private static final String RECORD_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String NUM_CIG_PKT = "num_cig_pkt";
    private static final String COST_OF_PCK = "cost_of_pkt";
    private static final String CIG_DAILY_CURR = "cig_daily_curr";
    private static final String GOAL_CIG_RED_DAILY = "goal_cig_red_daily";
    private static final String TARGET_DATE = "target_date";
    private static final String END_DATE = "end_date";

    // Key Event
    private static final String TABLE_EVENT_MASTER = "EventMaster";
    private static final String EVENT_ID = "id";
    private static final String EVENT_USER_ID = "event_user_id";
    private static final String EVENT_PERIOD_ID = "event_period_id";
    private static final String EVENT_LOC_ID = "event_loc_id";
    private static final String EVENT_DATE_TIME = "event_date_time";
    private static final String EVENT_LONGITUDE = "event_longitude";
    private static final String EVENT_LATITUDE = "event_latitude";



    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.execSQL("PRAGMA key = 'secretkey'");
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + USER_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_NAME + " TEXT,"
                + KEY_USER_AGE + " INTEGER," + KEY_USER_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_LOCATION_TABLE = "CREATE TABLE " + LOCATION_TABLE + "("
                + LOCATION_ID + " INTEGER PRIMARY KEY," + LOCATION_NAME + " TEXT" + ")";
        db.execSQL(CREATE_LOCATION_TABLE);

        String CREATE_DAY_PERIOD_TABLE = "CREATE TABLE " + DAY_PERIOD_TABLE + "("
                + PERIOD_ID + " INTEGER PRIMARY KEY," + PERIOD_NAME + " TEXT" + ")";
        db.execSQL(CREATE_DAY_PERIOD_TABLE);

        String CREATE_RECORD_MASTER_TABLE = "CREATE TABLE " + TABLE_RECORD_MASTER + "("
                + RECORD_ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER,"
                + NUM_CIG_PKT + " INTEGER," + COST_OF_PCK + " REAL,"
                + CIG_DAILY_CURR + " INTEGER," + GOAL_CIG_RED_DAILY + " INTEGER,"
                + TARGET_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," + END_DATE + " DATETIME DEFAULT NULL,"
                + "FOREIGN KEY(" + USER_ID + ") REFERENCES " + USER_TABLE + "(" + KEY_ID + ")" + ")";
        db.execSQL(CREATE_RECORD_MASTER_TABLE);

        String CREATE_EVENT_MASTER_TABLE = "CREATE TABLE " + TABLE_EVENT_MASTER + "("
                + EVENT_ID + " INTEGER PRIMARY KEY," + EVENT_USER_ID + " INTEGER,"
                + EVENT_PERIOD_ID + " INTEGER," + EVENT_LOC_ID + " INTEGER," + EVENT_DATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + EVENT_LATITUDE + " REAL," + EVENT_LONGITUDE + " REAL,"
                + "FOREIGN KEY(" + EVENT_USER_ID + ") REFERENCES " + USER_TABLE + "(" + KEY_ID + "),"

                + "FOREIGN KEY(" + EVENT_PERIOD_ID + ") REFERENCES " + DAY_PERIOD_TABLE + "(" + PERIOD_ID + "),"

                + "FOREIGN KEY(" + EVENT_LOC_ID + ") REFERENCES " + LOCATION_TABLE + "(" + LOCATION_ID + ")" + ")";
        db.execSQL(CREATE_EVENT_MASTER_TABLE);


        Log.d("Insert Periods: ", "Inserting ..");
        db.execSQL("INSERT INTO " + DAY_PERIOD_TABLE + "(" + PERIOD_NAME + ") VALUES('Morning')");
        db.execSQL("INSERT INTO " + DAY_PERIOD_TABLE + "(" + PERIOD_NAME + ") VALUES('Noon')");
        db.execSQL("INSERT INTO " + DAY_PERIOD_TABLE + "(" + PERIOD_NAME + ") VALUES('Afternoon')");
        db.execSQL("INSERT INTO " + DAY_PERIOD_TABLE + "(" + PERIOD_NAME + ") VALUES('Evening')");
        db.execSQL("INSERT INTO " + DAY_PERIOD_TABLE + "(" + PERIOD_NAME + ") VALUES('Night')");

        Log.d("Insert Locations: ", "Inserting ..");
        db.execSQL("INSERT INTO " + LOCATION_TABLE + "(" + LOCATION_NAME + ") VALUES('HOME')");
        db.execSQL("INSERT INTO " + LOCATION_TABLE + "(" + LOCATION_NAME + ") VALUES('OFFICE')");
        db.execSQL("INSERT INTO " + LOCATION_TABLE + "(" + LOCATION_NAME + ") VALUES('CAR')");
        db.execSQL("INSERT INTO " + LOCATION_TABLE + "(" + LOCATION_NAME + ") VALUES('WALK')");
        db.execSQL("INSERT INTO " + LOCATION_TABLE + "(" + LOCATION_NAME + ") VALUES('SCHOOL')");
        db.execSQL("INSERT INTO " + LOCATION_TABLE + "(" + LOCATION_NAME + ") VALUES('OTHERS')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_AGE, user.getAge());
        values.put(KEY_USER_EMAIL, user.getEmail());

        // Inserting Row
        db.insert(USER_TABLE, null, values);
        // Closing database connection
        db.close();
    }

    public void addRecordMaster(UserRecord userRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, userRecord.getUserId());
        values.put(COST_OF_PCK, userRecord.getCostOfPkt());
        values.put(NUM_CIG_PKT, userRecord.getNumCigInPkt());
        values.put(CIG_DAILY_CURR, userRecord.getAvgDailyCigUseCurr());
        values.put(GOAL_CIG_RED_DAILY, userRecord.getGoalDailyCigRedn());
        values.put(TARGET_DATE, userRecord.getTargetDt().toString());

        // Inserting Row
        db.insert(TABLE_RECORD_MASTER, null, values);
        // Closing database connection
        db.close();
    }

    public void addEventMaster(UserEvent event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EVENT_USER_ID, event.getEventUserID());
        values.put(EVENT_PERIOD_ID, event.getEventPeriodId());
        values.put(EVENT_LOC_ID, event.getEventLocationId());
        values.put(EVENT_DATE_TIME, event.getEventDateTime().toString());
        values.put(EVENT_LATITUDE, event.getEventLatitude());
        values.put(EVENT_LONGITUDE, event.getEventLongitude());
        // Inserting Row
        db.insert(TABLE_EVENT_MASTER, null, values);
        // Closing database connection
        db.close();
    }


    public User getSingleUser() {
        // Select All Query
        String selectQuery = "SELECT * FROM " + USER_TABLE + " limit 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        User user = new User();

        Log.d(String.valueOf((cursor.getCount())), "Inserting ..");
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setAge(Integer.parseInt(cursor.getString(2)));
            user.setEmail(cursor.getString(3));
        }
        // return config
        return user;
    }

    public int getNumOfCigByDate(String dt) {
        // Select All Query
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_EVENT_MASTER + " WHERE " + "date(" + EVENT_DATE_TIME + ")" + "=" + "'" + dt + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        System.out.println("QUERY :" + selectQuery);
        Log.d(String.valueOf((cursor.getCount())), "Inserting ..");
        int count = 0;
        System.out.println("Cursor :" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            System.out.println("Found :" + cursor.getString(0));
        }
        cursor.close();
        db.close();
        return count;
    }

    public List<CigCountDayPeriod> getCigConsumedTodayDayPer() {
        // Select All Query
        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + PERIOD_NAME + " as Period  FROM "
                + TABLE_EVENT_MASTER + " T1, " + DAY_PERIOD_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + "= CURRENT_DATE AND T1." + EVENT_PERIOD_ID + "= T2." + PERIOD_ID + " GROUP BY T1."
                + EVENT_PERIOD_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<CigCountDayPeriod> cigcntdaydaily = new ArrayList<CigCountDayPeriod>();
        System.out.println("QUERY Cig Count Period Daily:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Daily Dayperiod");
        if (cursor.moveToFirst()) {
            do {
                CigCountDayPeriod cig = new CigCountDayPeriod();
                cig.setCigCountPer(Integer.parseInt(cursor.getString(0)));
                cig.setPeriodName(cursor.getString(1));
                cigcntdaydaily.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntdaydaily;
    }


    public List<CigCountDayPeriod> getCigConsumedWeekDayPer() {
        // Select All Query
        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + PERIOD_NAME + " as Period  FROM "
                + TABLE_EVENT_MASTER + " T1, " + DAY_PERIOD_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + " BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime') AND T1." +
                EVENT_PERIOD_ID + "= T2." + PERIOD_ID + " GROUP BY T1."
                + EVENT_PERIOD_ID + " ORDER BY Cigarettecount";
        ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<CigCountDayPeriod> cigcntdayweek = new ArrayList<CigCountDayPeriod>();
        System.out.println("QUERY Cig Count Period Weekly:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Weekly Dayperiod");
        if (cursor.moveToFirst()) {
            do {
                CigCountDayPeriod cig = new CigCountDayPeriod();
                cig.setCigCountPer(Integer.parseInt(cursor.getString(0)));
                cig.setPeriodName(cursor.getString(1));
                cigcntdayweek.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntdayweek;
    }

    public List<CigCountDayPeriod> getCigConsumedMonthDayPer() {
        // Select All Query

        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + PERIOD_NAME + " as Period  FROM "
                + TABLE_EVENT_MASTER + " T1, " + DAY_PERIOD_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + " BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime') AND T1." +
                EVENT_PERIOD_ID + "= T2." + PERIOD_ID + " GROUP BY T1."
                + EVENT_PERIOD_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<CigCountDayPeriod> cigcntdaymonth = new ArrayList<CigCountDayPeriod>();
        System.out.println("QUERY Cig Count Period Monthly:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Monthly Dayperiod");
        if (cursor.moveToFirst()) {
            do {
                CigCountDayPeriod cig = new CigCountDayPeriod();
                cig.setCigCountPer(Integer.parseInt(cursor.getString(0)));
                cig.setPeriodName(cursor.getString(1));
                cigcntdaymonth.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntdaymonth;
    }

    public List<CigCountDayPeriod> getCigConsumedYearDayPer() {
        // Select All Query
        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + PERIOD_NAME + " as Period  FROM "
                + TABLE_EVENT_MASTER + " T1, " + DAY_PERIOD_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + " BETWEEN datetime('now', 'start of year') AND datetime('now', 'localtime') AND T1." +
                EVENT_PERIOD_ID + "= T2." + PERIOD_ID + " GROUP BY T1."
                + EVENT_PERIOD_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<CigCountDayPeriod> cigcntdayyear = new ArrayList<CigCountDayPeriod>();
        System.out.println("QUERY Cig Count Period Yearly:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Yearly Dayperiod");
        if (cursor.moveToFirst()) {
            do {
                CigCountDayPeriod cig = new CigCountDayPeriod();
                cig.setCigCountPer(Integer.parseInt(cursor.getString(0)));
                cig.setPeriodName(cursor.getString(1));
                cigcntdayyear.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntdayyear;
    }

    public List<CigCountLocation> getCigConsumedTodayLocation() {
        // Select All Query

        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + LOCATION_NAME + " as Location  FROM "
                + TABLE_EVENT_MASTER + " T1, " + LOCATION_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + "= CURRENT_DATE AND T1." + EVENT_LOC_ID + "= T2." + LOCATION_ID + " GROUP BY T1."
                + EVENT_LOC_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<CigCountLocation> cigcntdaylocation = new ArrayList<CigCountLocation>();
        System.out.println("QUERY Cig Count Location :" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Today Location ..");
        if (cursor.moveToFirst()) {
            do {
                CigCountLocation cig = new CigCountLocation();
                cig.setCigCountLoc(Integer.parseInt(cursor.getString(0)));
                cig.setLocationName(cursor.getString(1));
                cigcntdaylocation.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntdaylocation;
    }

    public List<CigCountLocation> getCigConsumedWeeklyLocation() {
        // Select All Query
        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + LOCATION_NAME + " as Location  FROM "
                + TABLE_EVENT_MASTER + " T1, " + LOCATION_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + " BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime') AND T1." +
                EVENT_LOC_ID + "= T2." + LOCATION_ID + " GROUP BY T1."
                + EVENT_LOC_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<CigCountLocation> cigcntweeklylocation = new ArrayList<CigCountLocation>();
        System.out.println("QUERY Cig Count Location Weekly:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Weekly Location ..");
        if (cursor.moveToFirst()) {
            do {
                CigCountLocation cig = new CigCountLocation();
                cig.setCigCountLoc(Integer.parseInt(cursor.getString(0)));
                cig.setLocationName(cursor.getString(1));
                cigcntweeklylocation.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntweeklylocation;
    }

    public List<CigCountLocation> getCigConsumedMonthlyLocation() {
        // Select All Query
        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + LOCATION_NAME + " as Location  FROM "
                + TABLE_EVENT_MASTER + " T1, " + LOCATION_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + " BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime') AND T1." +
                EVENT_LOC_ID + "= T2." + LOCATION_ID + " GROUP BY T1."
                + EVENT_LOC_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<CigCountLocation> cigcntmonthlylocation = new ArrayList<CigCountLocation>();
        System.out.println("QUERY Cig Count Location Monthly:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Monthly Location ..");
        if (cursor.moveToFirst()) {
            do {
                CigCountLocation cig = new CigCountLocation();
                cig.setCigCountLoc(Integer.parseInt(cursor.getString(0)));
                cig.setLocationName(cursor.getString(1));
                cigcntmonthlylocation.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntmonthlylocation;
    }

    public List<CigCountLocation> getCigConsumedYearlyLocation() {
        // Select All Query
        String selectQuery = "SELECT Count(T1." + EVENT_ID + ") as Cigarettecount, T2." + LOCATION_NAME + " as Location  FROM "
                + TABLE_EVENT_MASTER + " T1, " + LOCATION_TABLE + " T2 WHERE T1." +
                EVENT_DATE_TIME + " BETWEEN datetime('now', 'start of year') AND datetime('now', 'localtime') AND T1." +
                EVENT_LOC_ID + "= T2." + LOCATION_ID + " GROUP BY T1."
                + EVENT_LOC_ID + " ORDER BY Cigarettecount";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<CigCountLocation> cigcntyearlylocation = new ArrayList<CigCountLocation>();

        System.out.println("QUERY Cig Count Location Yearly:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Cig Consumed Yearly Location ..");
        if (cursor.moveToFirst()) {
            do {
                CigCountLocation cig = new CigCountLocation();
                cig.setCigCountLoc(Integer.parseInt(cursor.getString(0)));
                cig.setLocationName(cursor.getString(1));
                cigcntyearlylocation.add(cig);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cigcntyearlylocation;
    }

    public int getNumOfCigConsumed() { // Select All Query
        String selectQuery = "SELECT COUNT(" + EVENT_ID + ") as Cigarettecount FROM " + TABLE_EVENT_MASTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        System.out.println("QUERY TO GET NUMBER OF CIG CONSUMED :" + selectQuery);
        Log.d(String.valueOf((cursor.getCount())), "Selecting ..");
        int cigconsumed = 0;
        System.out.println("Cursor :" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            cigconsumed = Integer.parseInt(cursor.getString(0));
            System.out.println("Found :" + cursor.getString(0));
        }
        cursor.close();
        db.close();
        return cigconsumed;
    }

    public int getAvgCigConsumed() {
        // Select All Query
        String selectQuery = "SELECT " + CIG_DAILY_CURR + " as avg_cig_daily_earlier FROM " + TABLE_RECORD_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        System.out.println("QUERY TO GET AVG CIG CONSUMED EARLIER :" + selectQuery);
        Log.d(String.valueOf((cursor.getCount())), "Selecting ..");
        int avgcigconsumedearlier = 0;
        System.out.println("Cursor :" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            avgcigconsumedearlier = Integer.parseInt(cursor.getString(0));
            System.out.println("Found :" + cursor.getString(0));
        }
        cursor.close();
        db.close();
        return avgcigconsumedearlier;
    }
    public int getNumOfDays() {
        // Select All Query
        //String selectQuery = "SELECT " + CIG_DAILY_CURR + ") as avg_cig_daily_earlier FROM " + TABLE_RECORD_MASTER;
        String selectQuery = "Select Cast ((JulianDay(max(event_date_time)) - JulianDay(min(event_date_time))) As Integer) from EventMaster";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        System.out.println("QUERY TO GET NUMBER OF DAYS :" + selectQuery);
        Log.d(String.valueOf((cursor.getCount())), "Selecting ..");
        int numOfDay = 0;
        System.out.println("Cursor :" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(0) != null)
                numOfDay = Integer.parseInt(cursor.getString(0));
            System.out.println("Found :" + cursor.getString(0));
        }
        cursor.close();
        db.close();
        return numOfDay;
    }
    public int getGoal() {
        // Select All Query
        String selectQuery = "SELECT " + GOAL_CIG_RED_DAILY + " as goal FROM " + TABLE_RECORD_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        System.out.println("GOAL :" + selectQuery);
        Log.d(String.valueOf((cursor.getCount())), "Selecting ..");
        int goal = 0;
        System.out.println("Cursor :" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(0) != null)
                goal = Integer.parseInt(cursor.getString(0));
        }
        Log.i("GOAL: ", String.valueOf(goal));
        cursor.close();
        db.close();
        return goal;
    }
    public CostPerCig getCostPerCig() {
        // Select All Query
        String selectQuery = "SELECT " + COST_OF_PCK + "," + NUM_CIG_PKT + " FROM " + TABLE_RECORD_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        CostPerCig costpercigarette = new CostPerCig();
        System.out.println("QUERY TO Get Cost Per Cig:" + selectQuery);
        Log.d("FOUND", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(0) != null)
                costpercigarette.setCostOfPkt(Integer.parseInt(cursor.getString(0)));
            if (cursor.getString(1) != null)
                costpercigarette.setNumOfCigPkt(Integer.parseInt(cursor.getString(1)));
            Log.d("DEBUG1: ", String.valueOf(cursor.getString(0)));
            Log.d("DEBUG1: ", String.valueOf(cursor.getString(1)));
        }
        cursor.close();
        db.close();
        return costpercigarette;
    }
    public List<BarChartEntry> getWeeklyProgress()throws Exception {
        // Select All Query
        String selectQuery = "SELECT strftime('%m', " + EVENT_DATE_TIME + ") as month,  strftime('%d', " + EVENT_DATE_TIME
                + ") as day, date(" + EVENT_DATE_TIME + ") as date, count(" + EVENT_ID + ") FROM " +
                TABLE_EVENT_MASTER + " WHERE " + EVENT_DATE_TIME + " BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')" +
                " GROUP BY month, day" + " order by date Desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<BarChartEntry> weeklyData = new ArrayList<BarChartEntry>();

        System.out.println("QUERY Cigarette Count Weekly progress:" + selectQuery);

        Log.d(String.valueOf((cursor.getCount())), "Selecting Weekly Progress Data ..");
        if (cursor.moveToFirst()) {
            do {
                Date date =new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(2));
                BarChartEntry prog = new BarChartEntry();
                prog.setDate(date);
                prog.setValue(Integer.parseInt(cursor.getString(3)));
                prog.setMonth(cursor.getString(3));
                prog.setDay(cursor.getString(2));
                weeklyData.add(prog);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weeklyData;
    }


}
