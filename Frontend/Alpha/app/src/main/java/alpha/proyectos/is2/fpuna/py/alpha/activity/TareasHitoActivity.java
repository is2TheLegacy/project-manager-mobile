package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.TareasAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.TareasHitoAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.service.CrearTareaData;
import alpha.proyectos.is2.fpuna.py.alpha.service.HitoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;

/**
 * Pantalla de listado de tareas
 * @author federico.torres
 */
public class TareasHitoActivity extends AppCompatActivity implements Callback<List<Tarea>> {

    private RecyclerView mRecyclerView;
    private TareasHitoAdapter mAdapter;
    private ProgressBar progressBar;
    private LinearLayout sinDatos;
    private List<Tarea> tareas;
    private TareaService tareaService;
    private String idHito;
    private String idProyecto;
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_hito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferenceUtils = new PreferenceUtils(TareasHitoActivity.this);

        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        mRecyclerView = getRecyclerView(R.id.my_recycler_view);
        sinDatos = (LinearLayout) findViewById(R.id.sin_datos_content);
        tareaService = (TareaService) ServiceBuilder.create(TareaService.class, preferenceUtils.getAuthToken());
        idHito = getIntent().getStringExtra("EXTRA_ID_HITO");
        idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");

        Button agregarBtn = (Button) findViewById(R.id.action_agregar);
        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TareasHitoActivity.this, CrearTareaHitoActivity.class);
                i.putExtra("EXTRA_ID_HITO", idHito);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        });

        Button quitarBtn = (Button) findViewById(R.id.action_quitar);
        quitarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter != null && mAdapter.getTareasSeleccionadas() != null) {
                    List<Integer> seleccionadas = mAdapter.getTareasSeleccionadas();
                    System.err.println("Quitar tarea Seleccionados : " + seleccionadas.size());
                    for (int i = 0; i < seleccionadas.size(); i++) {
                        Tarea tarea = tareas.get(seleccionadas.get(i));
                        UUID id = tarea.getIdTarea();
                        CrearTareaData datos = new CrearTareaData(tarea);
                        datos.setHito(null);
                        Call<ResponseBody> call = tareaService.editar(id, datos);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    System.err.println("Quitar tarea funciono");
                                    showMessageSuccess("Exitoso", "Operacion realizada exitosamente");
                                } else {
                                    ResponseBody body = response.errorBody();
                                    String json = slurp(body.byteStream(), 1024);
                                    System.err.println("Quitar tarea error : " + json);
                                    showMessageSuccess("Error", "Ocurrio un error al realizar la operacion");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                System.err.println("Quitar tarea error : " + t.getMessage());
                            }
                        });
                        System.err.println("Quitar tarea Seleccionados : " + tareas.get(seleccionadas.get(i)).getNombre());
                    }
                }
            }
        });

        HitoService service = (HitoService) ServiceBuilder.create(HitoService.class, preferenceUtils.getAuthToken());
        UUID uuidHito = UUID.fromString(idHito);
        Call<List<Tarea>> call = service.listarTareas(uuidHito);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
        System.err.println("Status code : " + response.code());
        if (response.isSuccessful()) {
            tareas = response.body();
            if (tareas.size() == 0) {
                sinDatos.setVisibility(View.VISIBLE);
            } else {
                mAdapter = new TareasHitoAdapter(tareas, TareasHitoActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            System.err.println("Status code : " + response.message());
            Toast.makeText(this, "Ocurrio un error al procesar la respuesta del Servidor", Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }

    @Override
    public void onFailure(Call<List<Tarea>> call, Throwable t) {
        showProgress(false);
        Toast.makeText(this, "Ocurrio un error al invocar al servicio : " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void showProgress(final boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(show? View.GONE: View.VISIBLE);
    }

    private RecyclerView getRecyclerView(int idRecyclerView) {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(idRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_separator));
        mRecyclerView.addItemDecoration(divider);
        return mRecyclerView;
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
