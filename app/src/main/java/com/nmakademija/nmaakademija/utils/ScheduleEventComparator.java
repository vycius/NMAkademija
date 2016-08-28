package com.nmakademija.nmaakademija.utils;

import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.util.Comparator;

public class ScheduleEventComparator implements Comparator<ScheduleEvent> {
    @Override
    public int compare(ScheduleEvent e1, ScheduleEvent e2) {
        return e1.getDate().compareTo(e2.getDate());
    }
}