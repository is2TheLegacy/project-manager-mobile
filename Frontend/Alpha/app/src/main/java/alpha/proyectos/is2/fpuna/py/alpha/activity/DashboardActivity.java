package alpha.proyectos.is2.fpuna.py.alpha.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import alpha.proyectos.is2.fpuna.py.alpha.R;

/**
 *
 * @author federico.torres
 */
public class DashboardActivity extends BaseActivity {

    @Override
    protected void inint() {
        loadLayout(R.layout.activity_dashboard, "Dashboard");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, CrearProyectoActivity.class);
                startActivity(i);
            }
        });
    }
}
