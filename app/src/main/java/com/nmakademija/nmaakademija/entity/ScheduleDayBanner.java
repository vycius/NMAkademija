package com.nmakademija.nmaakademija.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleDayBanner {
    private Date date;

    private SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public ScheduleDayBanner(Date date) {
        this.date = date;
    }

    //region Getters
    public String getTime() {
        return dateFormater.format(date);
    }
    //endregion

    public Date getDate() {
        return this.date;
    }
}
