package com.example.scoreboard;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GameData {
    private String player_name;
    private String date;
    private String player_amount;
    private int profile_pic_name_id;

    public int getProfile_pic_name_id() {
        return profile_pic_name_id;
    }

    public GameData(String player_name, String date, String player_amount, int profile_pic_name) {
        this.player_name = player_name;
        this.player_amount = player_amount;
        this.date=date;
        this.profile_pic_name_id =profile_pic_name;
    }

    public void setProfile_pic_name_id(int profile_pic_name_id) {
        this.profile_pic_name_id = profile_pic_name_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public String getPlayer_amount() {
        return player_amount;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setPlayer_amount(String player_amount) {
        this.player_amount = player_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /* This Function return week number of given date*/
    public String number_of_week(String time)
    {
        Calendar date = java.util.Calendar.getInstance();

        try {
            date.setTime((new java.text.SimpleDateFormat("dd-MM-yyyy")).parse(time));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.get(java.util.Calendar.WEEK_OF_YEAR)+"";
    }

    /* This function returns true if palyer date and current date are on the same week. otherwise it return false*/
    public boolean is_same_week()
    {
        String current_date_week= number_of_week(get_current_date());
        String player_date_week=number_of_week(date);
        if(current_date_week.equals(player_date_week))return true;
        else return false;
    }

    /* This function returns system's current date*/
    String get_current_date()
    {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}
