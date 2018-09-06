package com.willowtreeapps.namegame.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RoundTypes {
    // Build time safety + size and performance benefits of using int variables
    // https://guides.codepath.com/android/Replacing-Enums-with-Enumerated-Annotations

    // Constants
    //private final int mGameMode;


    // Declare the @IntDef for these constants
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MATT_MODE, FLIP_MODE})
    private  @interface GameMode {}
    public static final int DEFAULT_MODE = 0;
    public static final int MATT_MODE = 1;
    public static final int FLIP_MODE = 2;

    public RoundTypes(@GameMode int gameMode) {

    }
}
