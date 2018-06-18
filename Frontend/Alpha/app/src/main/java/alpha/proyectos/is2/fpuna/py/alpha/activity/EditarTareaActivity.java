package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
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
import alpha.proyectos.is2.fpuna.py.alpha.service.HitoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.CategoriaProyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;


/**
 * Pantalla de creacion/edicion de tareas.
 * @author federico.torres
 */
public class EditarTareaActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

	private Button guardarTareaButton;
    private TareaService service;
    private HitoService hitoService;
    private ProyectoService proyectoService;
    private UUID uuid;
    private final Activity mContext = this;

    private TextView nombreView;
    private TextView descripcionView;
    private EditText fechaInicioView;
    private EditText fechaFinView;

    private String idTarea;
    private String idProyecto;
    private String nombre;
    private String descripcion;
    private String estadoActual;

    private Date fechaInicio;
    private Date fechaFin;
    private String datePicker;
    private Usuario usuarioAsignado;
    private Proyecto proyecto;
    private String estado;
    private short porcentaje;
    private Hito hito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombreView = (TextView) findViewById(R.id.nombre);
        descripcionView = (TextView) findViewById(R.id.descripcion);
        fechaInicioView = (EditText) findViewById(R.id.fechaInicio);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);

        idTarea = getIntent().getStringExtra("EXTRA_ID_TAREA");
        idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");
        final String idHito = getIntent().getStringExtra("EXTRA_ID_HITO");
        nombre = getIntent().getStringExtra("EXTRA_NOMBRE");
        estadoActual = getIntent().getStringExtra("EXTRA_ESTADO");
        descripcion = getIntent().getStringExtra("EXTRA_DESCRIPCION");
        System.err.println("Id tarea : " + idTarea);

        nombreView.setText(nombre);
        descripcionView.setText(descripcion);

        service = (TareaService) ServiceBuilder.create(TareaService.class);
        hitoService = (HitoService) ServiceBuilder.create(HitoService.class);
        proyectoService = (ProyectoService) ServiceBuilder.create(ProyectoService.class);

        // Estados
        final List<String> estados = new ArrayList<String>();
        estados.add("ABIERTA");
        estados.add("PENDIENTE");
        estados.add("EN CURSO");
        estados.add("FINALIZADA");

        Spinner spinner = (Spinner) findViewById(R.id.estado_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                estado = estados.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, estados);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        // Porcentaje
        final List<String> porcentajes = new ArrayList<String>();
        porcentajes.add("0 %");
        porcentajes.add("10 %");
        porcentajes.add("20 %");
        porcentajes.add("30 %");
        porcentajes.add("40 %");
        porcentajes.add("50 %");
        porcentajes.add("60 %");
        porcentajes.add("70 %");
        porcentajes.add("80 %");
        porcentajes.add("90 %");
        porcentajes.add("100 %");

        Spinner spinnerPorcentaje = (Spinner) findViewById(R.id.porcentaje_spinner);
        spinnerPorcentaje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmp = porcentajes.get(i);
                tmp = tmp.substring(0, tmp.length() - 2);
                porcentaje = new Short(tmp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> dataAdapterPorcentaje = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, porcentajes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPorcentaje.setAdapter(dataAdapterPorcentaje);

        // Hitos
        final List<String> listaHitos = new ArrayList<>();

        UUID uuidProyecto = UUID.fromString(idProyecto);
        proyectoService.listarHitos(uuidProyecto).enqueue(new Callback<List<Hito>>() {
            @Override
            public void onResponse(Call<List<Hito>> call, Response<List<Hito>> response) {
                final List<Hito> hitos = response.body();
                int index = 0;
                int actual = 0;
                for (Hito hito : hitos) {
                    listaHitos.add(hito.getNombre());
                    if (idHito != null && hito.getIdHito().toString().equals(idHito)) {
                        actual = index;
                    }
                    index++;
                }

                Spinner hitoSpinner = (Spinner) findViewById(R.id.hito_spinner);
                ArrayAdapter<String> hitoDataAdapter = new ArrayAdapter<String>(EditarTareaActivity.this,
                        android.R.layout.simple_spinner_item, listaHitos);
                hitoDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hitoSpinner.setAdapter(hitoDataAdapter);
                hitoSpinner.setSelection(actual);
                hitoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        hito = hitos.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Hito>> call, Throwable t) {
                ;
            }
        });

        OnClickListener verComentariosListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarComentarios();
            }
        };

        final TextView verComentarios = (TextView) findViewById(R.id.ver_comentarios);
        ImageView linkComentarios = (ImageView) findViewById(R.id.link_comentarios);
        verComentarios.setOnClickListener(verComentariosListener);
        linkComentarios.setOnClickListener(verComentariosListener);

        guardarTareaButton = (Button) findViewById(R.id.button_guardar);
        guardarTareaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarTarea();
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

    public void mostrarComentarios() {
        Intent intent = new Intent(this, ComentariosActivity.class);
        intent.putExtra("EXTRA_ID_TAREA", idTarea);
        startActivity(intent);
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

    private void guardarTarea() {

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
        }/* else if (TextUtils.isEmpty(fin)) {
            fechaFinView.setError(getString(R.string.error_field_required));
            focusView = fechaFinView;
            cancel = true;
        }*/

        if (cancel) {
            focusView.requestFocus();
            guardarTareaButton.setEnabled(true);
            guardarTareaButton.setText(R.string.action_guardar);
        } else {
            System.err.println("Id tarea : " + idTarea);
            UUID id = UUID.fromString(idTarea);
            CrearTareaData tarea = new CrearTareaData(id, fechaInicio.getTime(), fechaFin.getTime(), estado, porcentaje, hito);
            Call<ResponseBody> call = service.editar(id, tarea);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        System.err.println("");
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Tarea guardada exitosamente");
        } else {
            ResponseBody body = response.errorBody();
            String json = slurp(body.byteStream(), 1024);
            System.err.println("Error crear tarea : " + json);
            showMessage("Error", "Ocurrio un error al realizar la operación");
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        showMessage("Error", "Ocurrio un error al realizar la operación");
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