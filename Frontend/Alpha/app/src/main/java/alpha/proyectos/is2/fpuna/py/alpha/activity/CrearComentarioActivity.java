package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Comentario;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Pantalla para agregar comentarios a una tarea.
 * @author federico.torres
 */
public class CrearComentarioActivity extends AppCompatActivity implements Callback<ResponseBody> {

	private Button crearButton;
    private TareaService service;
    private UUID uuid;
    final PreferenceUtils preferenceUtils = new PreferenceUtils(this);

    private EditText contenidoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comentario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String idTarea = getIntent().getStringExtra("EXTRA_ID_TAREA");

        contenidoView = (EditText) findViewById(R.id.contenido);
        service = (TareaService) ServiceBuilder.create(TareaService.class, preferenceUtils.getAuthToken());

        crearButton = (Button) findViewById(R.id.button_guardar);
        crearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                crearComentario(idTarea);
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

    private void crearComentario(String idTarea) {

        boolean cancel = false;
        View focusView = null;

        String contenido = contenidoView.getText().toString();

        if (TextUtils.isEmpty(contenido)) {
            contenidoView.setError(getString(R.string.error_field_required));
            focusView = contenidoView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            crearButton.setEnabled(true);
            crearButton.setText(R.string.action_guardar);
        } else {
            uuid = UUID.randomUUID();
            Comentario comentario = new Comentario(uuid, contenido);
            Call<ResponseBody> call = service.crearComentario(idTarea, comentario);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Comentario agregado exitosamente");
        } else {
            showMessage("Error", "Ocurrio un error al realizar la operación");
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        ;
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

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

}