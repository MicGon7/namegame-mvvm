package com.willowtreeapps.namegame.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;

import com.willowtreeapps.namegame.BR;
import com.willowtreeapps.namegame.NameGame;
import com.willowtreeapps.namegame.model.Profile;

import java.util.List;

import javax.inject.Inject;

public class NameGameViewModel extends ObservableViewModel {

    private NameGame mNameGame;
    private boolean mLoading;

    public NameGameViewModel() {
    }

    @Inject
    public NameGameViewModel(NameGame nameGame) {
        mNameGame = nameGame;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public void loadData() {
        mNameGame.loadData();
    }

    public void startGame() {
        mNameGame.startGame();
        notifyPropertyChanged(BR._all);
    }

    public void onProfileSelected(int position) {
        mNameGame.onProfileSelected(position);
        notifyPropertyChanged(BR.currentRound);
        notifyPropertyChanged(BR.roundType);
        notifyPropertyChanged(BR.roundTimeText);
        notifyPropertyChanged(BR.correctCount);
        notifyPropertyChanged(BR.incorrectCount);
        notifyPropertyChanged(BR.averageAnswerTime);
        notifyPropertyChanged(BR.specialRound);
        notifyPropertyChanged(BR.flipMode);
        notifyPropertyChanged(BR.gameOver);

    }

    public void resumeGame() {
        mNameGame.resumeGame();
        notifyPropertyChanged(BR.specialRound);
        mNameGame.getHintModeLiveData().setValue(isHintMode().getValue());

    }

    public void resetGame() {
        mNameGame.resetGame();
        notifyPropertyChanged(BR._all);
    }

    public MutableLiveData<String> getLiveResponseStatus() {
        return mNameGame.getStatusResponseLiveData();
    }

    public MutableLiveData<Integer> getSelectedProfileLiveData() {
        return mNameGame.getSelectedProfileLiveData();
    }

    public LiveData<List<Profile>> getGameProfiles() {
        return mNameGame.getGameProfilesLiveData();
    }

    public LiveData<Boolean> isHintMode() {
        return mNameGame.getHintModeLiveData();
    }

    @Bindable
    public int getCurrentRound() {
        return mNameGame.getRound();
    }

    @Bindable
    public int getCorrectCount() {
        return mNameGame.getCorrectCount();
    }

    @Bindable
    public int getIncorrectCount() {
        return mNameGame.getIncorrectCount();
    }

    @Bindable
    public LiveData<Integer> getRoundTimeText() {
        return mNameGame.getGameTimeLiveData();
    }

    @Bindable
    public double getAverageAnswerTime() {
        return mNameGame.getAnswerAverage();
    }

    @Bindable
    public boolean isGameOver() {
        return mNameGame.isGameOver();
    }

    @Bindable
    public boolean isFlipMode() {
        return mNameGame.isFlipMode();
    }

    @Bindable
    public int getCorrectProfilePosition() {
        return mNameGame.getCorrectProfilePosition();
    }

    @Bindable
    public boolean isSpecialRound() {
        return mNameGame.isSpecialRound();
    }

    @Bindable
    public int getRoundType() {
        return mNameGame.getRoundType();
    }

    @Bindable
    public int getRoundLimit() {
        return mNameGame.getRoundLimit();
    }

    @Bindable
    public boolean isLoading() {
        return mLoading;
    }
}




