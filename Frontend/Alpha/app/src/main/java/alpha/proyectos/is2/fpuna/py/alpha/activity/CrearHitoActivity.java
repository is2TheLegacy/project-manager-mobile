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
import alpha.proyectos.is2.fpuna.py.alpha.service.HitoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Pantalla de creacion de hitos.
 * @author federico.torres
 */
public class CrearHitoActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

	private Button cearHitoButton;
    private HitoService service;
    private UsuarioService usuarioService;
    private UUID uuid;
    private final Activity mContext = this;

    private EditText nombreView;
    private EditText descripcionView;
    private EditText fechaFinView;

    private Date fechaInicio;
    private Date fechaFin;
    private String datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_hito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);

        service = (HitoService) ServiceBuilder.create(HitoService.class);
        usuarioService = (UsuarioService) ServiceBuilder.create(UsuarioService.class);

        cearHitoButton = (Button) findViewById(R.id.button_guardar);
        cearHitoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cearHito();
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

    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        datePicker = (String) view.getTag();
        fragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        if (datePicker.equals("inicio")) {
            fechaInicio = cal.getTime();
            ((TextView) findViewById(R.id.fechaInicio)).setText(dateFormat.format(cal.getTime()));
        } else {
            fechaFin =cal.getTime();
            ((TextView) findViewById(R.id.fechaFin)).setText(dateFormat.format(cal.getTime()));
        }
    }

    private void cearHito() {

        boolean cancel = false;
        View focusView = null;

        String nombre = nombreView.getText().toString();
        String descripcion = descripcionView.getText().toString();
        String fin = fechaFinView.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            nombreView.setError(getString(R.string.error_field_required));
            focusView = nombreView;
            cancel = true;
        } else if (TextUtils.isEmpty(descripcion)) {
            descripcionView.setError(getString(R.string.error_field_required));
            focusView = descripcionView;
            cancel = true;
        } else if (TextUtils.isEmpty(fin)) {
            fechaFinView.setError(getString(R.string.error_field_required));
            focusView = fechaFinView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            cearHitoButton.setEnabled(true);
            cearHitoButton.setText(R.string.action_guardar);
        } else {
            uuid = UUID.randomUUID();
            /*Hito hito = new Hito(uuid, nombre, descripcion, fechaFin.getTime(), propietario);
            Call<ResponseBody> call = service.crear(hito);
            call.enqueue(this);*/
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Hito creado exitosamente");
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