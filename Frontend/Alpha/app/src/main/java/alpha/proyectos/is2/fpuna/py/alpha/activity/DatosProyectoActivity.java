package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import alpha.proyectos.is2.fpuna.py.alpha.R;

public class DatosProyectoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_proyecto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String idProyecto = getIntent().getStringExtra("EXTRA_ID_PROYECTO");
        String nombre = getIntent().getStringExtra("EXTRA_NOMBRE");
        String estado = getIntent().getStringExtra("EXTRA_ESTADO");
        String descripcion = getIntent().getStringExtra("EXTRA_DESCRIPCION");
        String fechaCreacion = getIntent().getStringExtra("EXTRA_FECHA_CREACION");
        String fechaFin = getIntent().getStringExtra("EXTRA_FECHA_FIN");
        String idPropietario = getIntent().getStringExtra("EXTRA_ID_PROPIETARIO");
        String propietario = getIntent().getStringExtra("EXTRA_NOMBRE_PROPIETARIO");

        TextView nombreView = (TextView) findViewById(R.id.nombre);
        nombreView.setText(nombre);

        TextView propietarioView = (TextView) findViewById(R.id.propietario);
        propietarioView.setText(propietario);

        TextView descripcionView = (TextView) findViewById(R.id.descripcion);
        descripcionView.setText(descripcion);

        TextView estadoView = (TextView) findViewById(R.id.estado);
        estadoView.setText(estado);

        TextView fechaCreacionView = (TextView) findViewById(R.id.fechaCreacion);
        fechaCreacionView.setText(fechaCreacion);

        TextView fechaFinView = (TextView) findViewById(R.id.fechaFin);
        fechaFinView.setText(fechaFin);

        Button verHitosBtn = (Button) findViewById(R.id.ver_hitos);
        verHitosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DatosProyectoActivity.this, ListaHitosActivity.class);
                startActivity(i);
            }
        });

    }

}
