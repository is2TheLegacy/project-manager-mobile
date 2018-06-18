package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.CrearTareaData;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;


/**
 * Pantalla de creacion/edicion de tareas.
 * @author federico.torres
 */
public class CrearTareaActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

	private Button cearTareaButton;
    private TareaService service;
    private UsuarioService usuarioService;
    private ProyectoService proyectoService;
    private UUID uuid;
    private final Activity mContext = this;
    private PreferenceUtils preferenceUtils;

    private EditText nombreView;
    private EditText descripcionView;
    private EditText fechaInicioView;
    private EditText fechaFinView;

    private Date fechaInicio;
    private Date fechaFin;
    private String datePicker;
    private Usuario usuarioAsignado;
    private Proyecto proyecto;
    private String prioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferenceUtils = new PreferenceUtils(CrearTareaActivity.this);

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaInicioView = (EditText) findViewById(R.id.fechaInicio);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);

        service = (TareaService) ServiceBuilder.create(TareaService.class);
        usuarioService = (UsuarioService) ServiceBuilder.create(UsuarioService.class);
        proyectoService = (ProyectoService) ServiceBuilder.create(ProyectoService.class);

        usuarioService.getAll().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<String> nombreUsuarios = new ArrayList<String>();
                final List<Usuario> usuarios = response.body();
                for (Usuario usuario : usuarios) {
                    nombreUsuarios.add(usuario.getNombre());
                }
                Spinner spinner = (Spinner) findViewById(R.id.usuarios_spinner);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        usuarioAsignado = usuarios.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_spinner_item, nombreUsuarios);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                ;
            }
        });

        proyectoService.listar().enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
                List<String> nombreProyectos = new ArrayList<String>();
                final List<Proyecto> proyectos = response.body();
                for (Proyecto proyecto : proyectos) {
                    nombreProyectos.add(proyecto.getNombre());
                }
                Spinner spinner = (Spinner) findViewById(R.id.proyectos_spinner);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        proyecto = proyectos.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_spinner_item, nombreProyectos);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                ;
            }
        });

        final List<String> prioridadList = new ArrayList<String>();
        prioridadList.add("NORMAL");
        prioridadList.add("BAJA");
        prioridadList.add("MEDIA");
        prioridadList.add("ALTA");
        prioridadList.add("MUY ALTA");

        Spinner spinner = (Spinner) findViewById(R.id.prioridad_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prioridad = prioridadList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, prioridadList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        cearTareaButton = (Button) findViewById(R.id.button_guardar);
        cearTareaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                crearTarea();
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

    private void crearTarea() {

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
            cearTareaButton.setEnabled(true);
            cearTareaButton.setText(R.string.action_guardar);
        } else {
            uuid = UUID.randomUUID();
            Usuario usuarioCreador = preferenceUtils.getUsuarioLogueado();
            CrearTareaData tarea = new CrearTareaData(uuid, nombre, descripcion,
                    fechaInicio, fechaFin, prioridad, usuarioAsignado, proyecto, null, usuarioCreador);
            Call<ResponseBody> call = service.crear(tarea);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        System.err.println("");
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Tarea creada exitosamente");
        } else {
            ResponseBody body = response.errorBody();
            String json = slurp(body.byteStream(), 1024);
            System.err.println("Error crear tarea : " + json);
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