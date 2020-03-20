package com.example.quitsmoke;



public class CigCountDayPeriod {
    private int cig_count_per;
    private String period_name;

    public CigCountDayPeriod() {    }

    public CigCountDayPeriod(int cig_count_per, String period_name)
    {
        System.out.println("RM1 - Cig Count: " + period_name.toString());
        this.cig_count_per          = cig_count_per;
        this.period_name            = period_name;
    }


    public void setCigCountPer(int cig_count_per)
    {this.cig_count_per = cig_count_per;}

    public void setPeriodName(String period_name)
    {this.period_name = period_name;}

    public int getCigCountPer()
    { return cig_count_per; }

    public String getPeriodName()
    { return period_name; }

}
