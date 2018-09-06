package com.willowtreeapps.namegame.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.willowtreeapps.namegame.network.NameGameApi;
import com.willowtreeapps.namegame.util.StatusTypes;

import java.util.List;

import javax.inject.Inject;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilesRepository {


    @Inject
    NameGameApi mNameGameApi;

    @Inject
    Cache mCache;

    private MutableLiveData<String> mStatusMutableLiveData;

    @Inject
    public ProfilesRepository(@NonNull NameGameApi mNameGameApi) {
        this.mNameGameApi = mNameGameApi;
        mStatusMutableLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<List<Profile>> load() {
        final MutableLiveData<List<Profile>> data = new MutableLiveData<>();

        mNameGameApi.getProfiles().enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(@NonNull Call<List<Profile>> call,
                                   @NonNull Response<List<Profile>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    mStatusMutableLiveData.setValue(StatusTypes.OK);

                } else {
                    mStatusMutableLiveData.setValue(StatusTypes.ERROR);
                }

            }


            @Override
            public void onFailure(@NonNull Call<List<Profile>> call, @NonNull Throwable t) {
                mStatusMutableLiveData.setValue(StatusTypes.ERROR);
            }
        });

        return data;
    }

    public MutableLiveData<String> getStatusMutableLiveData() {
        return mStatusMutableLiveData;
    }

}
