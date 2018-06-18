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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosProyectoActivity extends AppCompatActivity {

    private ProyectoService service;
    private String idProyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_proyecto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
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
                Intent intent = new Intent(DatosProyectoActivity.this, EditarProyectoActivity.class);
                intent.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                intent.putExtra("EXTRA_NOMBRE", nombre);
                intent.putExtra("EXTRA_ESTADO", estado);
                intent.putExtra("EXTRA_FECHA_CREACION", fechaCreacion);
                intent.putExtra("EXTRA_DESCRIPCION", descripcion);
                intent.putExtra("EXTRA_FECHA_FIN", fechaFin);
                intent.putExtra("EXTRA_ID_PROPIETARIO", idPropietario);
                intent.putExtra("EXTRA_NOMBRE_PROPIETARIO", propietario);
                startActivity(intent);
            }
        });

        /*Button verHitosBtn = (Button) findViewById(R.id.ver_hitos_btn);
        verHitosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DatosProyectoActivity.this, ListaHitosActivity.class);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        });*/

        Button eliminarBtn = (Button) findViewById(R.id.button_eliminar);
        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageSuccess("Confirmar", "Esta seguro que desea eliminar el Proyecto ?");
            }
        });

        View.OnClickListener verHitosListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DatosProyectoActivity.this, ListaHitosActivity.class);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        };

        TextView verHitos = (TextView) findViewById(R.id.ver_hitos);
        verHitos.setOnClickListener(verHitosListener);
        ImageView linkHitos = (ImageView) findViewById(R.id.ver_hitos_link);
        linkHitos.setOnClickListener(verHitosListener);

        /*View.OnClickListener verTareasListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DatosProyectoActivity.this, ListaTareasActivity.class);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        };
        TextView verTareas = (TextView) findViewById(R.id.ver_tareas);
        verTareas.setOnClickListener(verTareasListener);
        ImageView linkTareas = (ImageView) findViewById(R.id.ver_tareas_link);
        linkTareas.setOnClickListener(verTareasListener);*/

        View.OnClickListener verSolicitudesListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DatosProyectoActivity.this, SolicitudesActivity.class);
                i.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                startActivity(i);
            }
        };

        TextView verSolicitudes = (TextView) findViewById(R.id.ver_solicitudes);
        verSolicitudes.setOnClickListener(verSolicitudesListener);
        ImageView linkSolicitudes = (ImageView) findViewById(R.id.ver_solicitudes_link);
        linkSolicitudes.setOnClickListener(verSolicitudesListener);

    }

    private void showMessageSuccess(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                UUID uuid = UUID.fromString(idProyecto);
                Call<ResponseBody> call = service.eliminar(uuid);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            showMessageSuccess("Exitoso", "Proyecto eliminado exitosamente");
                        } else {
                            showMessage("Error", "Ocurrio un error al realizar la operación");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showMessage("Error", "Ocurrio un error al realizar la operación");
                    }
                });
                System.err.println("Error editar proyecto, eliminar proyecto");
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.err.println("Error editar proyecto, NO eliminar proyecto");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMessage(String titulo, String mensaje) {
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

}
