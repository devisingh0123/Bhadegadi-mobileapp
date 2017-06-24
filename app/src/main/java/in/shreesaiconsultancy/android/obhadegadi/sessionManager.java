package in.shreesaiconsultancy.android.obhadegadi;

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
    private static final String USER_ID = "UserID";
    private static final String USER_Type = "UserType";
    private static final String USER_NO = "UserNumber";

    public sessionManager(Context context) {
        this._context = context;
        session = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = session.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }


    public void setUserId(String uid) {
        editor.putString(USER_ID, uid);
        editor.commit();
    }

    public void setUserType(String utype) {
        editor.putString(USER_Type, utype);
        editor.commit();
    }

    public void setUserNo(String num) {
        editor.putString(USER_Type, num);
        editor.commit();
    }



    public boolean isFirstTimeLaunch() {
        return session.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    public String getUserId() { return session.getString(USER_ID, "-1");}
    public String getUSER_Type() { return session.getString(USER_Type, "NO");}
    public String getUserNo() { return session.getString(USER_NO, "0");}

}
