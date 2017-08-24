package com.nmakademija.nmaakademija.api.controllers;


import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.api.listener.SchedulesLoadedListener;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SchedulesController extends FirebaseController {
    private final int sectionId;

    public SchedulesController(SchedulesLoadedListener listener, int section) {
        WeakReference<SchedulesLoadedListener> weakListener = new WeakReference<>(listener);
        this.sectionId = section;
        databaseReference = FirebaseDatabase.getInstance().getReference("schedules");

        eventListener = new ApiReferenceListener<ScheduleEvent, SchedulesLoadedListener>(ScheduleEvent.class,
                weakListener) {

            @Override
            public void onFailed(SchedulesLoadedListener listener, Exception ex) {
                listener.onSchedulesLoadingFailed(ex);
            }

            @Override
            public void onLoaded(ArrayList<ScheduleEvent> items, SchedulesLoadedListener listener) {
                listener.onSchedulesLoaded(items);
            }

            @Override
            public boolean where(ScheduleEvent item) {
                return item.getSection() < 0 || sectionId == item.getSection();
            }

            @Override
            public void onUpdated(ArrayList<ScheduleEvent> items, SchedulesLoadedListener listener) {
                listener.onSchedulesUpdated(items);
            }

            @Override
            public ArrayList<ScheduleEvent> order(ArrayList<ScheduleEvent> items) {
                return items;
            }
        };
    }

}
