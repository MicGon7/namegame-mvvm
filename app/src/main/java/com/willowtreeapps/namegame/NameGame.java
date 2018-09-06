package com.willowtreeapps.namegame;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import com.willowtreeapps.namegame.model.Profile;
import com.willowtreeapps.namegame.model.ProfilesRepository;
import com.willowtreeapps.namegame.util.NameGameUtil;
import com.willowtreeapps.namegame.util.RoundTypes;

import java.util.List;

import javax.inject.Inject;

public class NameGame implements Parcelable {
    private static final String SPECIAL_NAME = "Mat";
    private static final int ROUND_LIMIT = 10;
    private static final int HINT_MODE_START_ROUND = 5;

    private ProfilesRepository mProfilesRepository;
    private LiveData<List<Profile>> mProfilesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Profile>> mGameProfilesLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mSelectedProfileLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mGameTimeLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mHintModeLiveData = new MutableLiveData<>();

    private List<Profile> mGameProfiles;
    private int mCorrectProfilePosition;
    private int mCorrectCount;
    private int mIncorrectCount;
    private int mRoundTime;
    private int mRound;
    private int mRoundLimit = ROUND_LIMIT;
    private int mRoundType;
    private int mMattModeRound;
    private int mFlipModeRound;
    private double mTimeStamp;
    private double mAnswerAverage;
    private boolean mGameOver;
    private boolean mFlipMode;
    private boolean mHintMode;
    private boolean mSpecialRound;

    private Handler mTimerHandler = new Handler();
    private Runnable mTimerRunnable = this::initializeTimer;


    @Inject
    public NameGame(ProfilesRepository repository) {
        mProfilesRepository = repository;
    }

    public void loadData() {
        if (mProfilesLiveData.getValue() == null) {
            mProfilesLiveData = mProfilesRepository.load();
        }
    }

    public void startGame() {
        startTimer();
        mMattModeRound = NameGameUtil.getRandomRound(ROUND_LIMIT);
        mFlipModeRound = NameGameUtil.getRandomRound(ROUND_LIMIT);

        startRound();

    }

    public void onProfileSelected(int position) {
        if (position == mCorrectProfilePosition) {
            stopTimer();
            mCorrectCount++;
            mTimeStamp += mRoundTime;
            mAnswerAverage = mTimeStamp / mRound;
            mRoundTime = 0;

            if (mRound == ROUND_LIMIT) {
                mGameOver = true;
                mSpecialRound = false;
            } else {
                startRound();
            }

        } else {
            mIncorrectCount++;
        }

        mSelectedProfileLiveData.setValue(position);

    }

    public void resumeGame() {
        stopTimer();
        if (!isGameOver() && mGameProfiles != null) {
            startTimer();
        }
    }

    private void startRound() {
        stopTimer();
        mRound++;
        mHintMode = false;
        mHintModeLiveData.setValue(false);

        if (mRound == mMattModeRound) {
            mRoundType = RoundTypes.MATT_MODE;
            mGameProfiles = NameGameUtil.getSpecificProfiles(mProfilesLiveData.getValue(), SPECIAL_NAME);
            mSpecialRound = true;
        } else if (mRound == mFlipModeRound) {
            mRoundType = RoundTypes.FLIP_MODE;
            mGameProfiles = NameGameUtil.getRandomProfiles(mProfilesLiveData.getValue());
            mFlipMode = true;
            mSpecialRound = true;
        } else {
            mGameProfiles = NameGameUtil.getRandomProfiles(mProfilesLiveData.getValue());
            mRoundType = RoundTypes.DEFAULT_MODE;
            mFlipMode = false;
            mSpecialRound = false;

        }

        mCorrectProfilePosition = NameGameUtil.getRandomPosition(mGameProfiles);
        mGameProfilesLiveData.setValue(mGameProfiles);

        startTimer();

    }

    public void resetGame() {
        startTimer();
        mMattModeRound = NameGameUtil.getRandomRound(ROUND_LIMIT);
        mFlipModeRound = NameGameUtil.getRandomRound(ROUND_LIMIT);
        mSpecialRound = false;
        mGameOver = false;
        mFlipMode = false;
        mHintMode = false;
        mHintModeLiveData.setValue(false);
        mRound = 1;
        mRoundTime = 0;
        mCorrectCount = 0;
        mIncorrectCount = 0;
        mAnswerAverage = 0;
        mTimeStamp = 0;
        mGameProfiles = NameGameUtil.getRandomProfiles(mProfilesLiveData.getValue());
        mGameProfilesLiveData.setValue(mGameProfiles);

    }

    public MutableLiveData<String> getStatusResponseLiveData() {
        return mProfilesRepository.getStatusMutableLiveData();
    }

    public MutableLiveData<List<Profile>> getGameProfilesLiveData() {
        return mGameProfilesLiveData;
    }

    public MutableLiveData<Integer> getSelectedProfileLiveData() {
        return mSelectedProfileLiveData;
    }

    public MutableLiveData<Integer> getGameTimeLiveData() {
        return mGameTimeLiveData;
    }

    public MutableLiveData<Boolean> getHintModeLiveData() {
        return mHintModeLiveData;
    }

    public int getCorrectProfilePosition() {
        return mCorrectProfilePosition;
    }

    public int getCorrectCount() {
        return mCorrectCount;
    }

    public int getIncorrectCount() {
        return mIncorrectCount;
    }

    public int getRound() {
        return mRound;
    }

    public int getRoundLimit() {
        return mRoundLimit;
    }

    public int getRoundType() {
        return mRoundType;
    }

    public double getAnswerAverage() {
        return mAnswerAverage;
    }

    public boolean isGameOver() {
        return mGameOver;
    }

    public boolean isFlipMode() {
        return mFlipMode;
    }

    public boolean isSpecialRound() {
        return mSpecialRound;
    }

    private void initializeTimer() {
        int tickSpeed = 1000;
        if (mRoundTime == HINT_MODE_START_ROUND) {
            mHintMode = true;
            mHintModeLiveData.setValue(true);
        }
        mRoundTime++;
        mGameTimeLiveData.setValue(mRoundTime);

        mTimerHandler.postDelayed(mTimerRunnable, tickSpeed);
    }

    private void startTimer() {
        mTimerHandler.postDelayed(mTimerRunnable, 0);
    }

    private void stopTimer() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
    }

    protected NameGame(Parcel in) {
        mGameProfiles = in.createTypedArrayList(Profile.CREATOR);
        mCorrectProfilePosition = in.readInt();
        mCorrectCount = in.readInt();
        mIncorrectCount = in.readInt();
        mRoundTime = in.readInt();
        mRound = in.readInt();
        mRoundLimit = in.readInt();
        mRoundType = in.readInt();
        mMattModeRound = in.readInt();
        mFlipModeRound = in.readInt();
        mTimeStamp = in.readDouble();
        mAnswerAverage = in.readDouble();
        mGameOver = in.readByte() != 0;
        mFlipMode = in.readByte() != 0;
        mHintMode = in.readByte() != 0;
        mSpecialRound = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mGameProfiles);
        dest.writeInt(mCorrectProfilePosition);
        dest.writeInt(mCorrectCount);
        dest.writeInt(mIncorrectCount);
        dest.writeInt(mRoundTime);
        dest.writeInt(mRound);
        dest.writeInt(mRoundLimit);
        dest.writeInt(mRoundType);
        dest.writeInt(mMattModeRound);
        dest.writeInt(mFlipModeRound);
        dest.writeDouble(mTimeStamp);
        dest.writeDouble(mAnswerAverage);
        dest.writeByte((byte) (mGameOver ? 1 : 0));
        dest.writeByte((byte) (mFlipMode ? 1 : 0));
        dest.writeByte((byte) (mHintMode ? 1 : 0));
        dest.writeByte((byte) (mSpecialRound ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameGame> CREATOR = new Creator<NameGame>() {
        @Override
        public NameGame createFromParcel(Parcel in) {
            return new NameGame(in);
        }

        @Override
        public NameGame[] newArray(int size) {
            return new NameGame[size];
        }
    };

}


