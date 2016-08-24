package com.nmakademija.nmaakademija.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static Retrofit retrofit;

    public static NMAService nmaService;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://dak.lt:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nmaService = retrofit.create(NMAService.class);
    }

}
