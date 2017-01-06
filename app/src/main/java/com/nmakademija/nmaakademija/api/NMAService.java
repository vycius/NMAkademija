package com.nmakademija.nmaakademija.api;

import com.nmakademija.nmaakademija.entity.TimeUntilSession;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NMAService {

    @GET("api/tts/")
    Call<TimeUntilSession> getTimeTillSession();

}
