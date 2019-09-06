package obvious.assignment.nasaimagegallery.utility;

import android.content.SharedPreferences;

public class SharePrefUtil {

    private SharedPreferences mPref;

    public SharePrefUtil(SharedPreferences pref) {
        mPref = pref;
    }

    public void put(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public void put(String key, int value) {
        mPref.edit().putInt(key, value).apply();
    }

    public String getString(String key) {
        return mPref.getString(key, "");
    }

    public int getInteger(String key) {
        return mPref.getInt(key, 0);
    }

    public void put(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

}
