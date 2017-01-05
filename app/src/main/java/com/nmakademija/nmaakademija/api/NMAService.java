package com.nmakademija.nmaakademija.api;

import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NMAService {

    @GET("api/tts/")
    Call<TimeUntilSession> getTimeTillSession();

    @GET("api/news/")
    Call<List<Article>> getArticles();

}
