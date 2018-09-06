package com.willowtreeapps.namegame.network;

import com.willowtreeapps.namegame.model.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NameGameApi {

    @GET("/api/v1.0/profiles")
    Call<List<Profile>> getProfiles();

}
