package com.nmakademija.nmaakademija.utils;

import com.nmakademija.nmaakademija.entity.ScheduleItem;

import java.util.Comparator;

/**
 * Created by dekedro on 16.8.23.
 */

public class ScheduleItemComparator implements Comparator<ScheduleItem> {
    @Override
    public int compare(ScheduleItem e1, ScheduleItem e2) {
        return e1.getDate().compareTo(e2.getDate());
    }
}