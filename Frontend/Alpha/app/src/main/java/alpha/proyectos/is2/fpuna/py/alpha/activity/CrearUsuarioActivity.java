package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de registro de usuario
 * @author federico.torres
 */
public class CrearUsuarioActivity extends AppCompatActivity implements Callback<ResponseBody> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBar;
    private final Activity mContext = this;

    private Button cearCuentaButton;
    private UsuarioService service;
    private boolean mandarCredencial = true;
    private boolean callbackEliminarUsuario = false;
    private UUID uuid;

    private EditText mNombreView;
    private EditText mApellidoView;
    private EditText mEmailView;
    private EditText mAliasView;
    private EditText mPasswordView;
    private EditText mConfirmarPasswordView;
    private RadioGroup mSexoView;
    private String sexo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        mNombreView = (EditText) findViewById(R.id.nombre);
        mApellidoView = (EditText) findViewById(R.id.apellido);
        mEmailView = (EditText) findViewById(R.id.email);
        mAliasView = (EditText) findViewById(R.id.alias);
        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmarPasswordView = (EditText) findViewById(R.id.confirmar_password);
        mSexoView = (RadioGroup) findViewById(R.id.sexo);

        service = (UsuarioService) ServiceBuilder.create(UsuarioService.class);

        cearCuentaButton = (Button) findViewById(R.id.button_cear_cuenta);
        cearCuentaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                crearUsuario();
            }
        });

        Button cancelarCuentaButton = (Button) findViewById(R.id.button_cancelar);
        cancelarCuentaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void crearUsuario() {

        cearCuentaButton.setEnabled(false);
        cearCuentaButton.setText("Procesando");
        boolean cancel = false;
        View focusView = null;

        String nombre = mNombreView.getText().toString();
        String apellido = mApellidoView.getText().toString();
        String email = mEmailView.getText().toString();
        String alias = mAliasView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmarPasswordView = mConfirmarPasswordView.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            mNombreView.setError(getString(R.string.error_field_required));
            focusView = mNombreView;
            cancel = true;
        } else if (TextUtils.isEmpty(apellido)) {
            mApellidoView.setError(getString(R.string.error_field_required));
            focusView = mApellidoView;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValido(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(alias)) {
            mAliasView.setError(getString(R.string.error_field_required));
            focusView = mAliasView;
            cancel = true;
        } else if (TextUtils.isEmpty(sexo)) {
            focusView = mSexoView;
            cancel = true;
            showMessage("Datos incompletos", "Seleccione el sexo");
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(confirmarPasswordView)) {
            mConfirmarPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmarPasswordView;
            cancel = true;
        } else if (!password.equals(confirmarPasswordView)) {
            mConfirmarPasswordView.setError(getString(R.string.error_password_no_coincide));
            focusView = mConfirmarPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            cearCuentaButton.setEnabled(true);
            cearCuentaButton.setText(R.string.action_registrar_usuario);
        } else {
            uuid = UUID.randomUUID();
            Usuario user = new Usuario(uuid.toString(), alias, email, nombre, apellido, sexo);
            Call<ResponseBody> call = service.create(user);
            call.enqueue(this);
        }

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.opcion_hombre:
                if (checked) {
                    sexo = "M";
                }
                break;
            case R.id.opcion_mujer:
                if (checked) {
                    sexo = "F";
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (callbackEliminarUsuario) {
            cearCuentaButton.setEnabled(true);
            cearCuentaButton.setText(R.string.action_registrar_usuario);
            callbackEliminarUsuario = false;
            showMessage("Error", "Contraseña muy débil");
            return;
        }
        if (response.isSuccessful()) {
            if (mandarCredencial) {
                mandarCredencial = false;
                Call<ResponseBody> callback = service.credential(uuid.toString(), mPasswordView.getText().toString());
                callback.enqueue(this);
            } else {
                cearCuentaButton.setEnabled(true);
                cearCuentaButton.setText(R.string.action_registrar_usuario);
                showMessageSuccess("Exitoso", "Usuario creado exitosamente");
            }
        } else {
            // Si fallo el segundo servicio(credenciales), eliminar el usuario creado
            if (!mandarCredencial) {
                mandarCredencial = true;
                callbackEliminarUsuario = true;
                Call<ResponseBody> callback = service.delete(uuid.toString());
                callback.enqueue(this);
            } else {
                cearCuentaButton.setEnabled(true);
                cearCuentaButton.setText(R.string.action_registrar_usuario);
                showMessage("Error", "Ocurrio un error al realizar la operacion");
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (callbackEliminarUsuario) {
            cearCuentaButton.setEnabled(true);
            cearCuentaButton.setText(R.string.action_registrar_usuario);
            callbackEliminarUsuario = false;
            showMessage("Error", "Contraseña muy débil");
            return;
        }
        // Si fallo el segundo servicio(credenciales), eliminar el usuario creado
        if (!mandarCredencial) {
            mandarCredencial = true;
            callbackEliminarUsuario = true;
            Call<ResponseBody> callback = service.delete(uuid.toString());
            callback.enqueue(this);
        } else {
            cearCuentaButton.setEnabled(true);
            cearCuentaButton.setText(R.string.action_registrar_usuario);
            showMessage("Error", "Ocurrio un error al realizar la operacion");
        }
    }

    private boolean isEmailValido(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showMessageSuccess(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMessage(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    class CallBackCredenciales implements Callback<ResponseBody> {

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    }
}

