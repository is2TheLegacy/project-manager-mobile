package alpha.proyectos.is2.fpuna.py.alpha;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

import alpha.proyectos.is2.fpuna.py.alpha.service.login.LoginService;
import alpha.proyectos.is2.fpuna.py.alpha.service.login.RespuestaLogin;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Callback<RespuestaLogin> {

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(Constantes.PROYECTOS_ALPHA_PREFS_NAME, 0);
        if(prefs.getString(Constantes.SESSION_AUTH_TOKEN, null) == null) {
            setContentView(R.layout.activity_login);

            mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
            mPasswordView = (EditText) findViewById(R.id.password);

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);
        } else {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    private void login() {
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            LoginService service = (LoginService) ServiceBuilder.create(LoginService.class);
            Call<RespuestaLogin> call = service.login(email, password);
            call.enqueue(this);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Implement a real email validation
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
        showProgress(false);
        if (response.isSuccessful()) {
            RespuestaLogin res = response.body();
            if (res.getStatus() == 200) {
                SharedPreferences prefs = getSharedPreferences(Constantes.PROYECTOS_ALPHA_PREFS_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constantes.SESSION_AUTH_TOKEN, res.getAuthToken().toString());
                editor.putString(Constantes.SESSION_ID_USUARIO, res.getUser().getIdUsuario().toString());
                editor.putString(Constantes.SESSION_ALIAS, res.getUser().getAlias());
                editor.putString(Constantes.SESSION_NOMBRE, res.getUser().getNombre());
                editor.putString(Constantes.SESSION_APELLIDO, res.getUser().getApellido());
                editor.putString(Constantes.SESSION_EMAIL, res.getUser().getEmail());
                editor.commit();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            } else {
                if (response.body() != null && response.body().getMessages() != null) {
                    String msg = response.body().getMessages().get(0);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al llamar al servicio", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Gson gson = new Gson();
            JsonReader jsonReader = gson.newJsonReader(response.errorBody().charStream());
            RespuestaLogin errResp = gson.fromJson(jsonReader, RespuestaLogin.class);
            String msg = errResp.getMessages().get(0);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<RespuestaLogin> call, Throwable t) {
        showProgress(false);
        Toast.makeText(this, R.string.msg_error_login, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}

