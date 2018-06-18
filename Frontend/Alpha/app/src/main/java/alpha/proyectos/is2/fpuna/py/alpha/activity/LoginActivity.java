package alpha.proyectos.is2.fpuna.py.alpha.activity;

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

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.login.LoginService;
import alpha.proyectos.is2.fpuna.py.alpha.service.login.RespuestaLogin;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de Login
 * @author federico.torres
 */
public class LoginActivity extends AppCompatActivity implements Callback<RespuestaLogin> {

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private LoginService service;
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceUtils = new PreferenceUtils(LoginActivity.this);
        service = (LoginService) ServiceBuilder.create(LoginService.class);

        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        Button cearCuentaButton = (Button) findViewById(R.id.button_cear_cuenta);
        cearCuentaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CrearUsuarioActivity.class);
                startActivity(i);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void login() {
        String userName = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userName)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            Call<RespuestaLogin> call = service.login(userName, password);
            call.enqueue(this);
        }

    }

    @Override
    public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {

        if (response.isSuccessful()) {
            RespuestaLogin res = response.body();
            if (res.getStatus() == 200) {
                String authToken = res.getAuthToken().toString();
                /*SharedPreferences prefs = getSharedPreferences(Constantes.PROYECTOS_ALPHA_PREFS_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constantes.SESSION_AUTH_TOKEN, authToken);
                System.err.println("Login : " + res.getUser().getIdUsuario().toString());
                editor.putString(Constantes.SESSION_ID_USUARIO, res.getUser().getIdUsuario().toString());
                editor.putString(Constantes.SESSION_ALIAS, res.getUser().getAlias());
                editor.putString(Constantes.SESSION_NOMBRE, res.getUser().getNombre());
                editor.putString(Constantes.SESSION_APELLIDO, res.getUser().getApellido());
                editor.putString(Constantes.SESSION_EMAIL, res.getUser().getEmail());
                editor.commit();*/
                preferenceUtils.guardarDatosUsuario(res.getUser().getIdUsuario().toString(),
                        res.getUser().getNombre(), res.getUser().getApellido(), authToken);

                // Registrar token del firebase
                String tokenFirebase = preferenceUtils.getTokenFirebase();
                if (tokenFirebase != null) {
                    Call<ResponseBody> responseRegistrationId = service.registrationid(authToken, tokenFirebase);
                    responseRegistrationId.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            showProgress(false);
                            System.err.println("Response code : " + response.code());
                            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(i);
                            preferenceUtils.login();
                            finish();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            showProgress(false);
                            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(i);
                            preferenceUtils.login();
                            finish();
                        }
                    });
                } else {
                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(i);
                    preferenceUtils.login();
                    finish();
                }

            } else {
                showProgress(false);
                if (response.body() != null && response.body().getMessages() != null) {
                    String msg = response.body().getMessages().get(0);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al llamar al servicio", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            showProgress(false);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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

