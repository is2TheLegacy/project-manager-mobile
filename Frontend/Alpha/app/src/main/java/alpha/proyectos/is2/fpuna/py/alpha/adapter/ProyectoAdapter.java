package alpha.proyectos.is2.fpuna.py.alpha.adapter;

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
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;

public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ViewHolder> {

    private List<Proyecto> mDataset;
    
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
    public ProyectoAdapter(List<Proyecto> myDataset) {
        mDataset = myDataset;
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
        Proyecto proyecto = mDataset.get(position);
        holder.mTextView1.setText(proyecto.getNombre());

        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ;
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}