package alpha.proyectos.is2.fpuna.py.alpha.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *
 * @author federico.torres
 */

public class PreferenceUtils {

    /** Boolean para indicar si el usuario esta logueado o no */
    public static final String IS_LOGGED_IN = "is_logged_in";

    private Context mContext;

    public PreferenceUtils(Context context) {
        mContext = context;
    }

    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public boolean isLoggedIn() {
        return getPreferences().getBoolean(IS_LOGGED_IN, false);
    }

    public SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public void login() {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.commit();
    }

    public void logout() {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.commit();
    }
}
