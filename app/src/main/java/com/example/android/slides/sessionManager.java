package com.example.android.slides;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by athulnair on 04/06/17.
 */

class sessionManager {

    SharedPreferences session;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "session";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public sessionManager(Context context) {
        this._context = context;
        session = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = session.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return session.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
