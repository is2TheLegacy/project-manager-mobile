package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.ProyectoService;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alpha.proyectos.is2.fpuna.py.alpha.utils.StringUtils.slurp;

public class DatosSolicitudActivity extends AppCompatActivity implements Callback<ResponseBody> {

    private ProyectoService service;
    private String idSolicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_solicitud);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = (ProyectoService) ServiceBuilder.create(ProyectoService.class);
        idSolicitud = getIntent().getStringExtra("EXTRA_ID_SOLICITUD");
        final String mensaje = getIntent().getStringExtra("EXTRA_MENSAJE");
        final String datosUsuario = getIntent().getStringExtra("EXTRA_DATOS_USUARIO");

        TextView usuarioView = (TextView) findViewById(R.id.usuario);
        usuarioView.setText(datosUsuario);

        TextView mensajeView = (TextView) findViewById(R.id.mensaje);
        mensajeView.setText(mensaje);

        Button aceptarBtn = (Button) findViewById(R.id.button_aceptar);
        aceptarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID id = UUID.fromString(idSolicitud);
                Call<ResponseBody> call = service.acpetarSolicitud(id);
                call.enqueue(DatosSolicitudActivity.this);
            }
        });

        Button rechazarBtn = (Button) findViewById(R.id.button_rechazar);
        rechazarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID id = UUID.fromString(idSolicitud);
                Call<ResponseBody> call = service.rechazarSolicitud(id);
                call.enqueue(DatosSolicitudActivity.this);
            }
        });

    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            showMessageSuccess("Exitoso", "Operación realizada exitosamente");
        } else {
            ResponseBody body = response.errorBody();
            String json = slurp(body.byteStream(), 1024);
            System.err.println("Error crear hito : " + json);
            showMessage("Error", "Ocurrio un error al realizar la operación");
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        ;
    }

    private void showMessageSuccess(String titulo, String mensaje) {
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

    private void showMessage(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
