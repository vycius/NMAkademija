package com.nmakademija.nmaakademija.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dekedro on 16.8.23.
 */
public class ScheduleDayBanner implements ScheduleItem {
    private Date date;

    private SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

    //region Getters
    public String getTime() {
        return dateFormater.format(date);
    }
    public Date getDate(){
        return this.date;
    }
    //endregion

    public ScheduleDayBanner (Date date){
        this.date = date;
    }
}
