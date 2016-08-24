package com.nmakademija.nmaakademija.api;

import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.TimeTillSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NMAService {

    @GET("api/events")
    Call<List<ScheduleEvent>> getEvents();

    @GET("api/tts")
    Call<TimeTillSession> getTimeTillSession();

}
