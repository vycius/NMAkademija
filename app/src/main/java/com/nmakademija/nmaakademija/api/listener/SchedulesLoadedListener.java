package com.nmakademija.nmaakademija.api.listener;


import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.util.ArrayList;

public interface SchedulesLoadedListener {

    void onSchedulesLoaded(ArrayList<ScheduleEvent> scheduleEvents);

    void onSchedulesLoadingFailed(Exception exception);

}
