package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.ProyectoAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.TareasAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.TareaService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de listado de proyectos
 * @author federico.torres
 */
public class ListaProyectoActivity extends AppCompatActivity implements Callback<List<Proyecto>> {

    private RecyclerView mRecyclerView;
    private ProyectoAdapter mAdapter;
    private ProgressBar progressBar;
    private LinearLayout sinDatos;
    final PreferenceUtils preferenceUtils = new PreferenceUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proyectos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        mRecyclerView = getRecyclerView(R.id.my_recycler_view);
        sinDatos = (LinearLayout) findViewById(R.id.sin_datos_content);

        ProyectoService service = (ProyectoService) ServiceBuilder.create(ProyectoService.class, preferenceUtils.getAuthToken());
        Call<List<Proyecto>> call = service.listar();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
        if (response.isSuccessful()) {
            List<Proyecto> proyectos = response.body();
            if (proyectos.size() == 0) {
                sinDatos.setVisibility(View.VISIBLE);
            } else {
                mAdapter = new ProyectoAdapter(proyectos, ListaProyectoActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
            /*final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < proyectos.size(); ++i) {
                list.add(proyectos.get(i).getNombre());
            }
            final StableArrayAdapter adapter = new StableArrayAdapter(this,
                    android.R.layout.simple_list_item_1, list);
            misProyectosListView.setAdapter(adapter);
            misProyectosListView.setVisibility(View.VISIBLE);
            misProyectosListView.setMinimumHeight(proyectos.size() * 100);*/

        } else {
            Toast.makeText(this, "Ocurrio un error al procesar la respuesta del Servidor", Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }

    @Override
    public void onFailure(Call<List<Proyecto>> call, Throwable t) {
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

}
