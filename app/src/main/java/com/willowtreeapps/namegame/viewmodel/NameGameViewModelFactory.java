package com.willowtreeapps.namegame.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.willowtreeapps.namegame.NameGame;

import javax.inject.Inject;

public class NameGameViewModelFactory implements ViewModelProvider.Factory {

    private final NameGame mNameGame;


    @Inject
    public NameGameViewModelFactory(NameGame nameGame) {
        mNameGame = nameGame;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NameGameViewModel.class)) {
            //noinspection unchecked
            return (T) new NameGameViewModel(mNameGame);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
