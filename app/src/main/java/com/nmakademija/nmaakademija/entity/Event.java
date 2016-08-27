package com.nmakademija.nmaakademija.entity;

import java.util.Date;
import java.util.List;

public class Event {

    private int id;
    private String name;

    private Date startTime;
    private Date endTime;

    private List<Integer> lecturers;
    private int section;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<Integer> getLecturer() {
        return lecturers;
    }

    public int getSection() {
        return section;
    }


}
