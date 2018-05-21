package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.adapter.ProyectoAdapter;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author federico.torres
 */
public class DashboardActivity extends BaseActivity implements Callback<List<Proyecto>> {

    private CardView cardView;

    @Override
    protected void inint() {
        loadLayout(R.layout.activity_dashboard, "Dashboard");

        PreferenceUtils preferenceUtils = new PreferenceUtils(DashboardActivity.this);
        String tokenFirebase = preferenceUtils.getTokenFirebase();
        System.err.println("Token firebase : " + tokenFirebase);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, CrearProyectoActivity.class);
                startActivity(i);
            }
        });

        cardView = (CardView) findViewById(R.id.mis_proyectos_card);

        ProyectoService service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
        Call<List<Proyecto>> call = service.listar();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {

        if (response.isSuccessful()) {

            final List<Proyecto> proyectos = response.body();
            final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < proyectos.size(); ++i) {
                list.add(proyectos.get(i).getNombre());
            }
            final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.list_item, list);

            cardView.setMinimumHeight(proyectos.size() * 100);
            ListView misProyectosListView = new ListView(this);
            misProyectosListView.setMinimumHeight(proyectos.size() * 100);
            misProyectosListView.setAdapter(adapter);
            misProyectosListView.setDivider(null);
            misProyectosListView.setPadding(10,60,0,0);
            cardView.addView(misProyectosListView);

            misProyectosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Proyecto datosProyecto = proyectos.get(i);
                    Intent intent = new Intent(DashboardActivity.this, DatosProyectoActivity.class);
                    intent.putExtra("EXTRA_ID_PROYECTO", datosProyecto.getIdProyecto().toString());
                    intent.putExtra("EXTRA_NOMBRE", datosProyecto.getNombre());
                    intent.putExtra("EXTRA_ESTADO", datosProyecto.getEstado());
                    intent.putExtra("EXTRA_FECHA_CREACION", sdf.format(datosProyecto.getFechaCreacion()));
                    intent.putExtra("EXTRA_DESCRIPCION", datosProyecto.getDescripcion());
                    if (datosProyecto.getFechaFinalizacion() != null) {
                        intent.putExtra("EXTRA_FECHA_FIN", sdf.format(datosProyecto.getFechaFinalizacion()));
                    }
                    if (datosProyecto.getPropietario() != null) {
                        intent.putExtra("EXTRA_ID_PROPIETARIO", datosProyecto.getPropietario().getIdUsuario().toString());
                        String propietario = datosProyecto.getPropietario().getNombre()
                                + " " + datosProyecto.getPropietario().getApellido();
                        intent.putExtra("EXTRA_NOMBRE_PROPIETARIO", propietario);
                    }

                    startActivity(intent);
                }
            });

        } else {
            Toast.makeText(this, "Ocurrio un error al procesar la respuesta del Servidor", Toast.LENGTH_SHORT).show();
        }
        //showProgress(false);
    }

    @Override
    public void onFailure(Call<List<Proyecto>> call, Throwable t) {
        //showProgress(false);
        Toast.makeText(this, "Ocurrio un error al invocar al servicio : " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
