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
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {

    private List<Tarea> mDataset;
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public CheckBox cbSelect;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mTextView1 = (TextView) view.findViewById(R.id.titulo);
            mTextView2 = (TextView) view.findViewById(R.id.estado);
            cbSelect = (CheckBox) v.findViewById(R.id.checkbox_tarea);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TareasAdapter(List<Tarea> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TareasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_tareas, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
		Tarea tarea = mDataset.get(position);
        holder.mTextView1.setText(tarea.getNombre());
        //holder.mTextView2.setText(tarea.getEstado());

        holder.cbSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.err.println("Seleccionado");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
