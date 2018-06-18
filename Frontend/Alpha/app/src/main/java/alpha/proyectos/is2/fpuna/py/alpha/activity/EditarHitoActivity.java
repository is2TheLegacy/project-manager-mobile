package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.CrearHito;
import alpha.proyectos.is2.fpuna.py.alpha.service.HitoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
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

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;


/**
 * Pantalla de creacion de hitos.
 * @author federico.torres
 */
public class EditarHitoActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

	private Button guardarButton;
    private HitoService service;
    private UUID uuid;
    private final Activity mContext = this;
    private PreferenceUtils preferenceUtils;

    private String idHito;
    private String idProyecto;
    private String nombre;
    private String descripcion;

    private EditText nombreView;
    private EditText descripcionView;
    private EditText fechaInicioView;
    private EditText fechaFinView;

    private Date fechaInicio;
    private Date fechaFin;
    private String datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_hito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferenceUtils = new PreferenceUtils(EditarHitoActivity.this);
        idHito = getIntent().getStringExtra("EXTRA_ID_HITO");
        idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");
        System.err.println("Id hito : " + idHito);
        System.err.println("Id proyecto : " + idProyecto);
        nombre = getIntent().getStringExtra("EXTRA_NOMBRE");
        descripcion = getIntent().getStringExtra("EXTRA_DESCRIPCION");
        String fechaInicioString = getIntent().getStringExtra("EXTRA_FECHA_INICIO");
        String fechaFinString = getIntent().getStringExtra("EXTRA_FECHA_ESTIMADA_FIN");

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaInicioView = (EditText) findViewById(R.id.fechaInicio);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);

        nombreView.setText(nombre);
        descripcionView.setText(descripcion);
        fechaInicioView.setText(fechaInicioString);
        fechaFinView.setText(fechaFinString);

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA_2);
        try {
            fechaInicio = sdf.parse(fechaInicioString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            fechaFin = sdf.parse(fechaFinString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        service = (HitoService) ServiceBuilder.create(HitoService.class, preferenceUtils.getAuthToken());

        guardarButton = (Button) findViewById(R.id.button_guardar);
        guardarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

        Button cancelarCuentaButton = (Button) findViewById(R.id.button_cancelar);
        cancelarCuentaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        View.OnClickListener verTareasListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditarHitoActivity.this, TareasHitoActivity.class);
                i.putExtra("EXTRA_ID_HITO", idHito);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        };

        TextView verTareas = (TextView) findViewById(R.id.ver_tareas);
        verTareas.setOnClickListener(verTareasListener);
        ImageView linkTareas = (ImageView) findViewById(R.id.ver_tareas_link);
        linkTareas.setOnClickListener(verTareasListener);
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
            fechaFin = cal.getTime();
            ((TextView) findViewById(R.id.fechaFin)).setText(dateFormat.format(cal.getTime()));
        }
    }

    private void guardarDatos() {

        boolean cancel = false;
        View focusView = null;

        String nombre = nombreView.getText().toString();
        String descripcion = descripcionView.getText().toString();
        String inicio = fechaInicioView.getText().toString();
        String fin = fechaFinView.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            nombreView.setError(getString(R.string.error_field_required));
            focusView = nombreView;
            cancel = true;
        } else if (TextUtils.isEmpty(descripcion)) {
            descripcionView.setError(getString(R.string.error_field_required));
            focusView = descripcionView;
            cancel = true;
        } else if (TextUtils.isEmpty(inicio)) {
            fechaInicioView.setError(getString(R.string.error_field_required));
            focusView = fechaInicioView;
            cancel = true;
        } else if (TextUtils.isEmpty(fin)) {
            fechaFinView.setError(getString(R.string.error_field_required));
            focusView = fechaFinView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            guardarButton.setEnabled(true);
            guardarButton.setText(R.string.action_guardar);
        } else {
            UUID id = UUID.fromString(idHito);
            UUID uuidProyecto = UUID.fromString(idProyecto);
            Proyecto proyecto = new Proyecto(uuidProyecto);
            Usuario usuarioCreador = preferenceUtils.getUsuarioLogueado();
            CrearHito hito = new CrearHito(id, nombre, descripcion, fechaInicio.getTime(),
                    fechaFin.getTime(), usuarioCreador, proyecto);
            Call<ResponseBody> call = service.editar(id, hito);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Hito editado exitosamente");
        } else {
            ResponseBody body = response.errorBody();
            String json = slurp(body.byteStream(), 1024);
            System.err.println("Error crear hito : " + json);
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