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

import java.util.List;

import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.activity.EditarHitoActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.EditarTareaActivity;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;

public class HitosAdapter extends RecyclerView.Adapter<HitosAdapter.ViewHolder> {

    private List<Hito> mDataset;
    private Activity mContext;
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView mTextView1;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mTextView1 = (TextView) view.findViewById(R.id.nombre);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HitosAdapter(List<Hito> myDataset, Activity mContext) {
        mDataset = myDataset;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HitosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_hitos, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
		final Hito hito = mDataset.get(position);
        holder.mTextView1.setText(hito.getNombre());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditarHitoActivity.class);
                System.err.println("Id hito : " + hito.getIdHito());
                intent.putExtra("EXTRA_ID_HITO", hito.getIdHito().toString());
                String idProyecto = hito.getProyecto().getIdProyecto().toString();
                System.err.println("Id proyecto : " + idProyecto);
                intent.putExtra("EXTRA_ID_PROYECTO", idProyecto);
                intent.putExtra("EXTRA_NOMBRE", hito.getNombre());
                intent.putExtra("EXTRA_DESCRIPCION", hito.getDescripcion());
                intent.putExtra("EXTRA_FECHA_INICIO", hito.getFechaInicio());
                intent.putExtra("EXTRA_FECHA_ESTIMADA_FIN", hito.getFechaEstimadaFin());
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
