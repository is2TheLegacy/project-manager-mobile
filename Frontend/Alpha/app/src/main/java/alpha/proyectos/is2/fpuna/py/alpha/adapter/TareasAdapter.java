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
import java.util.ArrayList;
import java.util.List;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import alpha.proyectos.is2.fpuna.py.alpha.R;
import alpha.proyectos.is2.fpuna.py.alpha.activity.DashboardActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.DatosProyectoActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.EditarTareaActivity;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {

    private List<Tarea> mDataset;
    private List<Integer> tareasSeleccionadas;
    private Activity mContext;
    SimpleDateFormat sdf;
    
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
    public TareasAdapter(List<Tarea> myDataset, Activity mContext) {
        mDataset = myDataset;
        this.mContext = mContext;
        sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        tareasSeleccionadas = new ArrayList<>();
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
		final Tarea tarea = mDataset.get(position);
        holder.mTextView1.setText(tarea.getNombre());
        //holder.mTextView2.setText(tarea.getEstado());

        holder.cbSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tareasSeleccionadas.add(position);
                }
                System.err.println("Seleccionado : " + position);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditarTareaActivity.class);
                intent.putExtra("EXTRA_ID_TAREA", tarea.getIdTarea());
                intent.putExtra("EXTRA_NOMBRE", tarea.getNombre());
                intent.putExtra("EXTRA_DESCRIPCION", tarea.getDescripcion());
                intent.putExtra("EXTRA_ESTADO", tarea.getEstado());
                //intent.putExtra("EXTRA_FECHA_ESTIMADA_FIN", sdf.format(tarea.getFechaEstimadaFin()));
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public List<Integer> getTareasSeleccionadas() {
        return tareasSeleccionadas;
    }

}
