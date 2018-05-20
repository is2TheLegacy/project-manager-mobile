package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de listado de proyectos
 * @author federico.torres
 */
public class ListaProyectoActivity extends BaseActivity implements Callback<List<Proyecto>> {

    private ListView misProyectosListView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void inint() {

        loadLayout(R.layout.activity_lista_proyectos, "Proyectos");
        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        misProyectosListView = (ListView) findViewById(R.id.mis_proyectos);//getRecyclerView(R.id.my_recycler_view);


        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };*/

        ProyectoService service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
        Call<List<Proyecto>> call = service.listar();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
        if (response.isSuccessful()) {
            List<Proyecto> proyectos = response.body();
            final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < proyectos.size(); ++i) {
                list.add(proyectos.get(i).getNombre());
            }
            final StableArrayAdapter adapter = new StableArrayAdapter(this,
                    android.R.layout.simple_list_item_1, list);
            misProyectosListView.setAdapter(adapter);
            misProyectosListView.setVisibility(View.VISIBLE);
            misProyectosListView.setMinimumHeight(proyectos.size() * 100);

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

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

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
