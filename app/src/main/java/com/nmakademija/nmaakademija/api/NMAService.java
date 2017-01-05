package com.nmakademija.nmaakademija.api;

import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;
import com.nmakademija.nmaakademija.entity.Academic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NMAService {

    @GET("api/events/")
    Call<List<ScheduleEvent>> getEvents();

    @GET("api/tts/")
    Call<TimeUntilSession> getTimeTillSession();

    @GET("api/users/")
    Call<List<Academic>> getUsers();

    @GET("api/news/")
    Call<List<Article>> getArticles();

    @GET("api/sections/")
    Call<List<Section>> getSections();
}
