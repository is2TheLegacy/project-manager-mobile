package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import alpha.proyectos.is2.fpuna.py.alpha.service.CategoriaProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.CategoriaProyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;

public class EditarProyectoActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, Callback<ResponseBody> {

    private String idProyecto;
    private Button guardarButton;
    private ProyectoService service;
    private CategoriaProyectoService categoriasService;
    private UsuarioService usuarioService;
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
        setContentView(R.layout.activity_editar_proyecto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");
        String nombre = getIntent().getStringExtra("EXTRA_NOMBRE");
        String estado = getIntent().getStringExtra("EXTRA_ESTADO");
        String descripcion = getIntent().getStringExtra("EXTRA_DESCRIPCION");
        String fechaCreacion = getIntent().getStringExtra("EXTRA_FECHA_CREACION");
        String fechaFinProyecto = getIntent().getStringExtra("EXTRA_FECHA_FIN");
        final String idPropietario = getIntent().getStringExtra("EXTRA_ID_PROPIETARIO");

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        try {
            fechaFin = sdf.parse(fechaFinProyecto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nombreView = (EditText) findViewById(R.id.nombre);
        descripcionView = (EditText) findViewById(R.id.descripcion);
        fechaFinView = (EditText) findViewById(R.id.fechaFin);
        propietarioView = (Spinner) findViewById(R.id.usuarios_spinner);
        categoriaView = (Spinner) findViewById(R.id.categoria_spinner);

        nombreView.setText(nombre);
        descripcionView.setText(descripcion);
        fechaFinView.setText(fechaFinProyecto);

        service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
        categoriasService = (CategoriaProyectoService) ServiceBuilder.create(CategoriaProyectoService.class);
        usuarioService = (UsuarioService) ServiceBuilder.create(UsuarioService.class);

        // Listar usuarios
        usuarioService.getAll().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                List<String> nombreUsuarios = new ArrayList<String>();
                final List<Usuario> usuarios = response.body();
                int index = 0;
                int actual = 0;
                for (Usuario usuario : usuarios) {
                    nombreUsuarios.add(usuario.getNombre());
                    if (usuario.getIdUsuario().equals(idPropietario)) {
                        actual = index;
                    }
                    index++;
                }

                Spinner spinner = (Spinner) findViewById(R.id.usuarios_spinner);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_spinner_item, nombreUsuarios);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(actual);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        propietario = usuarios.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

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

        guardarButton = (Button) findViewById(R.id.button_guardar);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarProyecto();
            }
        });

        Button cancelarCuentaButton = (Button) findViewById(R.id.button_cancelar);
        cancelarCuentaButton.setOnClickListener(new View.OnClickListener() {
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

    private void editarProyecto() {

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
        } else if (TextUtils.isEmpty(fin) || fechaFin == null) {
            fechaFinView.setError(getString(R.string.error_field_required));
            focusView = fechaFinView;
            cancel = true;
        }  else if (propietario == null) {
            Toast.makeText(EditarProyectoActivity.this, "Debe seleccionar el propietario del proyecto",
                    Toast.LENGTH_SHORT).show();
            focusView = propietarioView;
            cancel = true;
        }  else if (categoria == null) {
            Toast.makeText(EditarProyectoActivity.this, "Debe seleccionar la categoria del proyecto",
                    Toast.LENGTH_SHORT).show();
            focusView = categoriaView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            guardarButton.setEnabled(true);
            guardarButton.setText(R.string.action_guardar);
        } else {
            UUID id = UUID.fromString(idProyecto);
            Proyecto proyecto = new Proyecto(id, nombre, descripcion, fechaFin.getTime(), propietario, categoria);
            Call<ResponseBody> call = service.editar(id, proyecto);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Proyecto editado exitosamente");
        } else {
            ResponseBody body = response.errorBody();
            String json = slurp(body.byteStream(), 1024);
            System.err.println("Error - editar proyecto : " + json);
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
