package alpha.proyectos.is2.fpuna.py.alpha;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alpha.proyectos.is2.fpuna.py.alpha.service.LoginService;
import alpha.proyectos.is2.fpuna.py.alpha.service.RespuestaLogin;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author federico.torres
 */
public class LoginActivity extends AppCompatActivity implements Callback<RespuestaLogin> {

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceUtils = new PreferenceUtils(LoginActivity.this);

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
            LoginService service = (LoginService) ServiceBuilder.create(LoginService.class);
            Call<RespuestaLogin> call = service.login(userName, password);
            call.enqueue(this);
        }

    }

    @Override
    public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
        showProgress(false);
        if (response.isSuccessful()) {
            RespuestaLogin res = response.body();
            if (response.code() == 200) {
                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(i);
                preferenceUtils.login();
                finish();
            } else {
                if (response.body() != null && response.body().getMessages() != null) {
                    String msg = response.body().getMessages().get(0);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al iniciar session", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (response.body() != null && response.body().getMessages() != null) {
                String msg = response.body().getMessages().get(0);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al llamar al servicio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<RespuestaLogin> call, Throwable t) {
        showProgress(false);
        Toast.makeText(this, "Ocurrio un error al llamar al Servidor", Toast.LENGTH_SHORT).show();
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
}

