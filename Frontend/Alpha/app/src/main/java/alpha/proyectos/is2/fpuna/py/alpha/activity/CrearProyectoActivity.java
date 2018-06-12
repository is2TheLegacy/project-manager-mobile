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
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.CategoriaProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.CategoriaProyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;


/**
 * Pantalla de creacion/edicion de proyectos.
 * @author federico.torres
 */
public class CrearProyectoActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

	private Button cearProyectoButton;
    private ProyectoService service;
    private CategoriaProyectoService categoriasService;
    private UsuarioService usuarioService;
    private UUID uuid;
    private final Activity mContext = this;

    private EditText nombreView;
    private EditText descripcionView;
    private EditText fechaFinView;
    private Spinner propietarioView;
    private Spinner categoriaView;

    private Date fechaFin;
    private CategoriaProyecto categoria;
    private Usuario propietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);
        propietarioView = (Spinner) findViewById(R.id.usuarios_spinner);
        categoriaView = (Spinner) findViewById(R.id.categoria_spinner);

        service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
        categoriasService = (CategoriaProyectoService) ServiceBuilder.create(CategoriaProyectoService.class);
        usuarioService = (UsuarioService) ServiceBuilder.create(UsuarioService.class);

        // Listar usuarios
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

        // Listar categorias
        categoriasService.listar().enqueue(new Callback<List<CategoriaProyecto>>() {
            @Override
            public void onResponse(Call<List<CategoriaProyecto>> call, Response<List<CategoriaProyecto>> response) {
                final List<String> nombreCategorias = new ArrayList<String>();
                final List<CategoriaProyecto> categorias = response.body();
                for (CategoriaProyecto categoria : categorias) {
                    nombreCategorias.add(categoria.getNombre());
                }
                Spinner spinner = (Spinner) findViewById(R.id.categoria_spinner);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        categoria = categorias.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_spinner_item, nombreCategorias);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoriaProyecto>> call, Throwable t) {
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
        }  else if (propietario == null) {
            Toast.makeText(CrearProyectoActivity.this, "Debe seleccionar el propietario del proyecto",
                    Toast.LENGTH_SHORT).show();
            focusView = propietarioView;
            cancel = true;
        }  else if (categoria == null) {
            Toast.makeText(CrearProyectoActivity.this, "Debe seleccionar la categoria del proyecto",
                    Toast.LENGTH_SHORT).show();
            focusView = categoriaView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            cearProyectoButton.setEnabled(true);
            cearProyectoButton.setText(R.string.action_guardar);
        } else {
            uuid = UUID.randomUUID();
            Proyecto proyecto = new Proyecto(uuid, nombre, descripcion, fechaFin.getTime(), propietario, categoria);
            Call<ResponseBody> call = service.crear(proyecto);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Proyecto creada exitosamente");
        } else {
            ResponseBody body = response.errorBody();
            String json = slurp(body.byteStream(), 1024);
            System.err.println("Error crear proyecto : " + json);
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