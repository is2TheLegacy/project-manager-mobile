package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerProyectoActivity extends AppCompatActivity {

    private ProyectoService service;
    private String idProyecto;
    final PreferenceUtils preferenceUtils = new PreferenceUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_proyecto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = (ProyectoService) ServiceBuilder.create(ProyectoService.class, preferenceUtils.getAuthToken());
        idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");
        final String nombre = getIntent().getStringExtra("EXTRA_NOMBRE");
        final String estado = getIntent().getStringExtra("EXTRA_ESTADO");
        final String categoria = getIntent().getStringExtra("EXTRA_CATEGORIA");
        final String descripcion = getIntent().getStringExtra("EXTRA_DESCRIPCION");
        final String fechaCreacion = getIntent().getStringExtra("EXTRA_FECHA_CREACION");
        final String fechaFin = getIntent().getStringExtra("EXTRA_FECHA_FIN");
        final String idPropietario = getIntent().getStringExtra("EXTRA_ID_PROPIETARIO");
        final String propietario = getIntent().getStringExtra("EXTRA_NOMBRE_PROPIETARIO");

        TextView nombreView = (TextView) findViewById(R.id.nombre);
        nombreView.setText(nombre);

        TextView propietarioView = (TextView) findViewById(R.id.propietario);
        propietarioView.setText(propietario);

        TextView descripcionView = (TextView) findViewById(R.id.descripcion);
        descripcionView.setText(descripcion);

        TextView estadoView = (TextView) findViewById(R.id.estado);
        estadoView.setText(estado);

        TextView categoriaView = (TextView) findViewById(R.id.categoria);
        categoriaView.setText(categoria);

        TextView fechaCreacionView = (TextView) findViewById(R.id.fechaCreacion);
        fechaCreacionView.setText(fechaCreacion);

        TextView fechaFinView = (TextView) findViewById(R.id.fechaFin);
        fechaFinView.setText(fechaFin);

        Button editarBtn = (Button) findViewById(R.id.button_editar);
        editarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerProyectoActivity.this, SolicitarColaborarActivity.class);
                intent.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                intent.putExtra("EXTRA_ID_PROPIETARIO", idPropietario);
                startActivity(intent);
            }
        });

    }

}
