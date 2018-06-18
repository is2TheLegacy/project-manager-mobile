package alpha.proyectos.is2.fpuna.py.alpha.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.text.SimpleDateFormat;
import java.util.List;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.activity.EditarHitoActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.VerProyectoActivity;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;

public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ViewHolder> {

    private List<Proyecto> mDataset;
    private Activity mContext;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView mTextView1;
        public CheckBox cbSelect;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mTextView1 = (TextView) view.findViewById(R.id.nombre);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProyectoAdapter(List<Proyecto> myDataset, Activity mContext) {
        this.mDataset = myDataset;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProyectoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_proyecto, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Proyecto proyecto = mDataset.get(position);
        holder.mTextView1.setText(proyecto.getNombre());

        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VerProyectoActivity.class);
                System.err.println("Id hito : " + proyecto.getIdProyecto());
                intent.putExtra("EXTRA_ID_PROYECTO", proyecto.getIdProyecto().toString());
                intent.putExtra("EXTRA_NOMBRE", proyecto.getNombre());
                intent.putExtra("EXTRA_ESTADO", proyecto.getEstado());
                intent.putExtra("EXTRA_FECHA_CREACION", sdf.format(proyecto.getFechaCreacion()));
                intent.putExtra("EXTRA_DESCRIPCION", proyecto.getDescripcion());
                if (proyecto.getFechaFinalizacion() != null) {
                    intent.putExtra("EXTRA_FECHA_FIN", sdf.format(proyecto.getFechaFinalizacion()));
                }
                if (proyecto.getCategoria() != null) {
                    intent.putExtra("EXTRA_CATEGORIA", proyecto.getCategoria().getNombre());
                }
                if (proyecto.getPropietario() != null) {
                    intent.putExtra("EXTRA_ID_PROPIETARIO", proyecto.getPropietario().getIdUsuario().toString());
                    String propietario = proyecto.getPropietario().getNombre()
                            + " " + proyecto.getPropietario().getApellido();
                    intent.putExtra("EXTRA_NOMBRE_PROPIETARIO", propietario);
                }
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
