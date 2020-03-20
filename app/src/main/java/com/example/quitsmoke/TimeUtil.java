package com.example.quitsmoke;

import java.util.Calendar;

public class TimeUtil {

    public static int getDayPeriod(){
        Calendar c = Calendar.getInstance();
        int Hr24=c.get(Calendar.HOUR_OF_DAY);
        if(Hr24 >= 4 && Hr24<=9)
            return 1;
        else if(Hr24 > 9 && Hr24<=12)
            return 2;
        else if(Hr24 > 12 && Hr24<=16)
            return 3;
        else if(Hr24 > 16 && Hr24<=20)
            return 4;
        else
            return 5;

    }
}
