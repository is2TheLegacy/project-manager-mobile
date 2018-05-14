package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
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
 * Pantalla de creacion/edicion de proyectos.
 * @author federico.torres
 */
public class CrearProyectoActivity extends BaseActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

	private Button cearProyectoButton;
    private ProyectoService service;
    private UsuarioService usuarioService;
    private UUID uuid;
    private final Activity mContext = this;

    private EditText nombreView;
    private EditText descripcionView;
    private EditText fechaFinView;

    private Date fechaFin;
    private String categoria;
    private Usuario propietario;

	@Override
    protected void inint() {

        loadLayout(R.layout.activity_crear_proyecto, "Nuevo Proyecto");

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);

        service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
        usuarioService = (UsuarioService) ServiceBuilder.create(UsuarioService.class);

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
                        propietario = usuarios.get(i);
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

        cearProyectoButton = (Button) findViewById(R.id.button_guardar);
        cearProyectoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                crearProyecto();
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
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        fechaFin = cal.getTime();
        ((TextView) findViewById(R.id.fechaFin)).setText(dateFormat.format(cal.getTime()));
    }

    private void crearProyecto() {

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
            cearProyectoButton.setEnabled(true);
            cearProyectoButton.setText(R.string.action_guardar);
        } else {
            uuid = UUID.randomUUID();
            Proyecto proyecto = new Proyecto(uuid, nombre, descripcion, fechaFin.getTime(), propietario);
            Call<ResponseBody> call = service.crear(proyecto);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Proyecto creada exitosamente");
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

}