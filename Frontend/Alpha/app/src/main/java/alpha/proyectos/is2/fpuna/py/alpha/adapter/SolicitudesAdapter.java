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
import alpha.proyectos.is2.fpuna.py.alpha.activity.DatosSolicitudActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.EditarHitoActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.EditarTareaActivity;
import alpha.proyectos.is2.fpuna.py.alpha.service.SolicitudesColaboracion;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.ViewHolder> {

    private List<SolicitudesColaboracion> mDataset;
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
    public SolicitudesAdapter(List<SolicitudesColaboracion> myDataset, Activity mContext) {
        mDataset = myDataset;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SolicitudesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_hitos, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
		final SolicitudesColaboracion solicitud = mDataset.get(position);
        Usuario usuario = solicitud.getUsuarioOrigen();
        final String datosUsuario = usuario.getNombre() + " " + usuario.getApellido();
        holder.mTextView1.setText(datosUsuario);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DatosSolicitudActivity.class);
                intent.putExtra("EXTRA_ID_SOLICITUD", solicitud.getIdSolicitudColaboracion().toString());
                intent.putExtra("EXTRA_MENSAJE", solicitud.getMensaje());
                intent.putExtra("EXTRA_DATOS_USUARIO", datosUsuario);
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
