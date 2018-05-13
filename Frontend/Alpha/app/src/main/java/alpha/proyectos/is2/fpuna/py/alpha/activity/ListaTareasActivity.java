package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.TareasAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de listado de tareas
 * @author federico.torres
 */
public class ListaTareasActivity extends BaseActivity implements Callback<List<Tarea>> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void inint() {

        loadLayout(R.layout.activity_lista_tareas, "Tareas");
        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        mRecyclerView = getRecyclerView(R.id.my_recycler_view);

        Button agregarBtn = (Button) findViewById(R.id.action_agregar);
        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaTareasActivity.this, CrearTareaActivity.class);
                startActivity(i);
            }
        });

        TareaService service = (TareaService) ServiceBuilder.create(TareaService.class);
        Call<List<Tarea>> call = service.listar();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
        System.err.println("Status code : " + response.code());
        if (response.isSuccessful()) {
            List<Tarea> ofertas = response.body();
            mAdapter = new TareasAdapter(ofertas);
            mRecyclerView.setAdapter(mAdapter);
        } else {
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
