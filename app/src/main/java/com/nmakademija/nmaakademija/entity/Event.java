package com.nmakademija.nmaakademija.entity;

import java.util.List;

public class Event {

    private int id;
    private String name;

    private long startTime;

    private long endTime;

    private List<Integer> lecturers;
    private int section;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public List<Integer> getLecturer() {
        return lecturers;
    }

    public int getSection() {
        return section;
    }


}
