package alpha.proyectos.is2.fpuna.py.alpha.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

import static alpha.proyectos.is2.fpuna.py.alpha.Constantes.SESSION_AUTH_TOKEN;

/**
 *
 * @author federico.torres
 */

public class PreferenceUtils {

    /** Boolean para indicar si el usuario esta logueado o no */
    public static final String IS_LOGGED_IN = "is_logged_in";

    /** Token del Firebase */
    public static final String TOKEN_FIREBASE = "token_firebase";

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

    public void guardarTokenFirebase(String token) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(TOKEN_FIREBASE, token);
        editor.commit();
    }

    public Usuario getUsuarioLogueado() {
        String idUsuario = getPreferences().getString(Constantes.SESSION_ID_USUARIO, null);
        if (idUsuario != null) {
            return new Usuario(idUsuario);
        }
        return null;
    }

    public String getTokenFirebase() {
        return getPreferences().getString(TOKEN_FIREBASE, null);
    }

    public String getAuthToken() {
        return getPreferences().getString(SESSION_AUTH_TOKEN, null);
    }

    public void logout() {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.commit();
    }
}
