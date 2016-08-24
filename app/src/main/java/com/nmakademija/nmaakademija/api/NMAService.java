package com.nmakademija.nmaakademija.api;

import com.nmakademija.nmaakademija.entity.Event;
import com.nmakademija.nmaakademija.entity.TimeTillSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NMAService {

    @GET("api/events")
    Call<List<Event>> getEvents();

    @GET("api/tts")
    Call<TimeTillSession> getTimeTillSession();

}
