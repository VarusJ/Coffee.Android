package studio.xmatrix.coffee.data.store;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpUtil {
    private static String key_user_id = "id";

    public enum SpKey {
        UserId
    }

    public static String getItem(Activity activity, SpKey key) {
        SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(activity);

        switch (key) {
            case UserId:
                return manager.getString(key_user_id, "");
        }
        return "";
    }

    public static void setItem(Activity activity, SpKey key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(activity).edit();
        switch (key) {
            case UserId:
                editor.putString(key_user_id, value);
                break;
        }
        editor.apply();
    }
}
