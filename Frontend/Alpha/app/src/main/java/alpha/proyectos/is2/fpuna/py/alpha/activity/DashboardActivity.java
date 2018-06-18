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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.UsuarioService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla Principal de la app
 * @author federico.torres
 */
public class DashboardActivity extends BaseActivity {

    private CardView misProyectosCardView;
    private CardView proyectosParticipandoCardView;
    private LinearLayout sinProyectosContent1;
    private LinearLayout sinProyectosContent2;
    private TextView sinProyectosText1;
    private TextView sinProyectosText2;
    private PreferenceUtils preferenceUtils;

    private UsuarioService service;
    public static boolean recargar = false;

    @Override
    protected void inint() {
        loadLayout(R.layout.activity_dashboard, "Dashboard");

        preferenceUtils = new PreferenceUtils(DashboardActivity.this);
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

        misProyectosCardView = (CardView) findViewById(R.id.mis_proyectos_card);
        proyectosParticipandoCardView = (CardView) findViewById(R.id.proyectos_participando_card);
        sinProyectosContent1 = (LinearLayout) findViewById(R.id.sin_proyectos_content_2);
        sinProyectosContent2 = (LinearLayout) findViewById(R.id.sin_proyectos_content_2);
        sinProyectosText1 = (TextView) findViewById(R.id.sin_proyectos_text_1);
        sinProyectosText2 = (TextView) findViewById(R.id.sin_proyectos_text_2);
        service = (UsuarioService) ServiceBuilder.create(UsuarioService.class);
        cargarDatos();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (recargar) {
            System.err.println("Reiniciando pantalla del Dashoard");
            cargarDatos();
        }
    }

    private void cargarDatos() {

        Usuario usuario = preferenceUtils.getUsuarioLogueado();
        String idUsuario = usuario.getIdUsuario();

        Call<List<Proyecto>> call1 = service.getMisProyectos(idUsuario);
        call1.enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
                if (response.isSuccessful()) {

                    final List<Proyecto> proyectos = response.body();

                    if (proyectos.size() == 0) {

                        sinProyectosContent1.setVisibility(View.VISIBLE);

                    } else {
                        System.err.println("Reiniciando pantalla cargando datos");
                        final ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < proyectos.size(); ++i) {
                            list.add(proyectos.get(i).getNombre());
                        }
                        final StableArrayAdapter adapter = new StableArrayAdapter(DashboardActivity.this,
                                R.layout.list_item, list);

                        misProyectosCardView.setMinimumHeight(proyectos.size() * 80);
                        ListView misProyectosListView = new ListView(DashboardActivity.this);
                        misProyectosListView.setMinimumHeight(proyectos.size() * 100);
                        misProyectosListView.setAdapter(adapter);
                        misProyectosListView.setDivider(null);
                        misProyectosListView.setPadding(10, 60, 0, 0);
                        misProyectosCardView.addView(misProyectosListView);

                        misProyectosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Proyecto datosProyecto = proyectos.get(i);
                                startActivity(datosProyecto);
                            }
                        });
                    }

                } else {
                    sinProyectosText1.setText("Ocurrio un error al recupear los datos");
                    Toast.makeText(DashboardActivity.this,
                            "Ocurrio un error al procesar la respuesta del Servidor",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                sinProyectosText1.setText("Ocurrio un error al recupear los datos");
                Toast.makeText(DashboardActivity.this,
                        "Ocurrio un error al procesar la respuesta del Servidor",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Proyecto>> call2 = service.getProyectosColaborando(idUsuario);
        call2.enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
                if (response.isSuccessful()) {

                    final List<Proyecto> proyectos = response.body();

                    if (proyectos.size() == 0) {

                        sinProyectosContent2.setVisibility(View.VISIBLE);

                    } else {

                        final ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < proyectos.size(); ++i) {
                            list.add(proyectos.get(i).getNombre());
                        }
                        final StableArrayAdapter adapter = new StableArrayAdapter(DashboardActivity.this,
                                R.layout.list_item, list);

                        proyectosParticipandoCardView.setMinimumHeight(proyectos.size() * 80);
                        ListView misProyectosListView = new ListView(DashboardActivity.this);
                        misProyectosListView.setMinimumHeight(proyectos.size() * 100);
                        misProyectosListView.setAdapter(adapter);
                        misProyectosListView.setDivider(null);
                        misProyectosListView.setPadding(10, 60, 0, 0);
                        proyectosParticipandoCardView.addView(misProyectosListView);

                        misProyectosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Proyecto datosProyecto = proyectos.get(i);
                                startActivity(datosProyecto);
                            }
                        });
                    }

                } else {
                    sinProyectosText2.setText("Ocurrio un error al recupear los datos");
                    Toast.makeText(DashboardActivity.this,
                            "Ocurrio un error al procesar la respuesta del Servidor",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                sinProyectosText2.setText("Ocurrio un error al recupear los datos");
                Toast.makeText(DashboardActivity.this,
                        "Ocurrio un error al procesar la respuesta del Servidor",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivity(Proyecto datosProyecto) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        Intent intent = new Intent(DashboardActivity.this, DatosProyectoActivity.class);
        intent.putExtra("EXTRA_ID_PROYECTO", datosProyecto.getIdProyecto().toString());
        intent.putExtra("EXTRA_NOMBRE", datosProyecto.getNombre());
        intent.putExtra("EXTRA_ESTADO", datosProyecto.getEstado());
        intent.putExtra("EXTRA_FECHA_CREACION", sdf.format(datosProyecto.getFechaCreacion()));
        intent.putExtra("EXTRA_DESCRIPCION", datosProyecto.getDescripcion());
        if (datosProyecto.getFechaFinalizacion() != null) {
            intent.putExtra("EXTRA_FECHA_FIN", sdf.format(datosProyecto.getFechaFinalizacion()));
        }
        if (datosProyecto.getCategoria() != null) {
            intent.putExtra("EXTRA_CATEGORIA", datosProyecto.getCategoria().getNombre());
        }
        if (datosProyecto.getPropietario() != null) {
            intent.putExtra("EXTRA_ID_PROPIETARIO", datosProyecto.getPropietario().getIdUsuario().toString());
            String propietario = datosProyecto.getPropietario().getNombre()
                    + " " + datosProyecto.getPropietario().getApellido();
            intent.putExtra("EXTRA_NOMBRE_PROPIETARIO", propietario);
        }

        startActivity(intent);

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
