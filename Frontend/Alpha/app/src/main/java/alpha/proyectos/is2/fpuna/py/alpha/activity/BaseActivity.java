package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.login.LoginService;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author federico.torres
 */

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private PreferenceUtils preferenceUtils;

    protected abstract void inint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        preferenceUtils = new PreferenceUtils(BaseActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View hView =  navigationView.getHeaderView(0);

        SharedPreferences prefs = getSharedPreferences(Constantes.PROYECTOS_ALPHA_PREFS_NAME, 0);

        TextView nombre = (TextView) hView.findViewById(R.id.header_nombre);
        nombre.setText(prefs.getString(Constantes.SESSION_NOMBRE, "") + " "+prefs.getString(Constantes.SESSION_APELLIDO, ""));
        inint();
    }

    protected void loadLayout(int layoutId, String title) {

        View C = findViewById(R.id.content_activity);
        ViewGroup parent = (ViewGroup) C.getParent();
        int index = parent.indexOfChild(C);
        parent.removeView(C);
        C = getLayoutInflater().inflate(layoutId, parent, false);
        parent.addView(C, index);
        setTitle(title);
    }

    protected RecyclerView getRecyclerView(int idRecyclerView) {
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

    @Override
    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {

            final SharedPreferences prefs = getSharedPreferences(Constantes.PROYECTOS_ALPHA_PREFS_NAME,0);

            LoginService service = (LoginService) ServiceBuilder.create(LoginService.class);
            Call<ResponseBody> call = service.logout(prefs.getString(Constantes.SESSION_AUTH_TOKEN,""));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(BaseActivity.this, R.string.msg_error_logout, Toast.LENGTH_SHORT).show();
                }
            });

            preferenceUtils.logout();
            
        } else if (id == R.id.nav_tareas) {
            Intent i = new Intent(BaseActivity.this, ListaTareasActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_proyectos) {
            Intent i = new Intent(BaseActivity.this, ListaProyectoActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void showMessageSuccess(String titulo, String mensaje) {
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

    protected void showMessage(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
