package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.HitosAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.HitoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de listado de Hitos
 * @author federico.torres
 */
public class ListaHitosActivity extends AppCompatActivity implements Callback<List<Hito>> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBar;
    private LinearLayout sinDatos;
    final PreferenceUtils preferenceUtils = new PreferenceUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_hitos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");
        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        mRecyclerView = getRecyclerView(R.id.my_recycler_view);
        sinDatos = (LinearLayout) findViewById(R.id.sin_datos_content);

        Button nuevoHitoBtn = (Button) findViewById(R.id.nuevo_hito);
        nuevoHitoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaHitosActivity.this, CrearHitoActivity.class);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        });

        UUID uuidProyecto = UUID.fromString(idProyecto);
        ProyectoService service = (ProyectoService) ServiceBuilder.create(ProyectoService.class, preferenceUtils.getAuthToken());
        Call<List<Hito>> call = service.listarHitos(uuidProyecto);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Hito>> call, Response<List<Hito>> response) {
        System.err.println("Status code : " + response.code());
        if (response.isSuccessful()) {
            List<Hito> hitos = response.body();
            if (hitos.size() == 0) {
                sinDatos.setVisibility(View.VISIBLE);
            } else {
                System.err.println("Hitos : " + hitos.size());
                mAdapter = new HitosAdapter(hitos, ListaHitosActivity.this);
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
    public void onFailure(Call<List<Hito>> call, Throwable t) {
        showProgress(false);
        System.err.println("Error - listar hitos : " + t.getMessage());
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
