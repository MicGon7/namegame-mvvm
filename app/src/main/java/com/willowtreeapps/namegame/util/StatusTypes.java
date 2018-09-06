package com.willowtreeapps.namegame.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StatusTypes {
    // Build time safety + size and performance benefits of using int variables
    // https://guides.codepath.com/android/Replacing-Enums-with-Enumerated-Annotations

    // Constants
    private final String status;

    // Declare the @StringDef for these constants
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({OK, ERROR})
    private  @interface StatusCodes {}
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    public StatusTypes(@StatusCodes String gameMode) {
        status = gameMode;
    }
}
