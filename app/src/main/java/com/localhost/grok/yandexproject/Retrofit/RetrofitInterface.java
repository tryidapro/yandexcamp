package com.localhost.grok.yandexproject.Retrofit;

import com.google.gson.JsonParser;

import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by grok on 4/12/16.
 */
public interface RetrofitInterface {
    @GET("/download.cdn.yandex.net/mobilization-2016/artists.json")
    Call <List<ArtistGson>> loadArtists();

}