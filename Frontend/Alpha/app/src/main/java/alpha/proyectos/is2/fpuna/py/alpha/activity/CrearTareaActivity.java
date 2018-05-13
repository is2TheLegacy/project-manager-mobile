package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Pantalla de creacion/edicion de tareas.
 * @author federico.torres
 */
public class CrearTareaActivity extends BaseActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody>, AdapterView.OnItemSelectedListener {

	private Button cearTareaButton;
    private TareaService service;
    private UUID uuid;

    private EditText nombreView;
    private EditText descripcionView;
    private EditText fechaInicioView;
    private EditText fechaFinView;

    private Date fechaInicio;
    private Date fechaFin;
    private String prioridad;

	@Override
    protected void inint() {

        loadLayout(R.layout.activity_crear_tarea, "Nueva Tarea");
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crear_tarea);

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaInicioView = (EditText) findViewById(R.id.fechaInicio);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);

        service = (TareaService) ServiceBuilder.create(TareaService.class);

        List<String> prioridadList = new ArrayList<String>();
        prioridadList.add("NORMAL");
        prioridadList.add("BAJA");
        prioridadList.add("MEDIA");
        prioridadList.add("ALTA");
        prioridadList.add("MUY ALTA");

        Spinner spinner = (Spinner) findViewById(R.id.prioridad_spinner);
        spinner.setOnItemSelectedListener(this);

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
        fragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        String datePicker = (String) view.getTag();
        if (datePicker.equals("inicio")) {
            fechaInicio = cal.getTime();
        } else {
            fechaFin = cal.getTime();
        }
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.fechaInicio)).setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.err.println("Seleccionado : " + i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ;
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
            Tarea tarea = new Tarea(uuid.toString(), nombre, descripcion, fechaInicio, fechaFin, "" +
                    "l");
            Call<ResponseBody> call = service.crear(tarea);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ;
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

}