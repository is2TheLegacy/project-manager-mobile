package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
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
public class ListaTareasActivity extends BaseActivity implements Callback<List<Tarea>> {

    private RecyclerView mRecyclerView;
    private TareasAdapter mAdapter;
    private ProgressBar progressBar;
    private LinearLayout sinDatos;
    private List<Tarea> tareas;
    private TareaService service;
    final PreferenceUtils preferenceUtils = new PreferenceUtils(this);

    @Override
    protected void inint() {

        loadLayout(R.layout.activity_lista_tareas, "Tareas");
        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        mRecyclerView = getRecyclerView(R.id.my_recycler_view);
        sinDatos = (LinearLayout) findViewById(R.id.sin_datos_content);
        service = (TareaService) ServiceBuilder.create(TareaService.class, preferenceUtils.getAuthToken());

        Button agregarBtn = (Button) findViewById(R.id.action_agregar);
        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaTareasActivity.this, CrearTareaActivity.class);
                startActivity(i);
            }
        });

        Button eliminarBtn = (Button) findViewById(R.id.action_eliminar);
        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> seleccionadas = mAdapter.getTareasSeleccionadas();
                System.err.println("Seleccionados : " + seleccionadas.size());
                for (int i = 0; i < seleccionadas.size(); i++) {
                    UUID id = tareas.get(seleccionadas.get(i)).getIdTarea();
                    Call<ResponseBody> call = service.eliminar(id);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                System.err.println("Eliminar tarea funciono");
                                showMessageSuccess("Exitoso", "Tarea eliminada exitosamente");
                            } else {
                                ResponseBody body = response.errorBody();
                                String json = slurp(body.byteStream(), 1024);
                                System.err.println("Eliminar tarea error : " + json);
                                showMessageSuccess("Error", "Ocurrio un error al realizar la operacion");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.err.println("Quitar tarea error : " + t.getMessage());
                        }
                    });
                    System.err.println("Seleccionados : " + tareas.get(seleccionadas.get(i)).getNombre());
                }
            }
        });

        Usuario usuario = preferenceUtils.getUsuarioLogueado();
        UsuarioService service = (UsuarioService) ServiceBuilder.create(UsuarioService.class, preferenceUtils.getAuthToken());
        Call<List<Tarea>> call = service.getTareas(usuario.getIdUsuario());
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
                mAdapter = new TareasAdapter(tareas, ListaTareasActivity.this);
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

}
