package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Fiscalizacion;
import java.util.List;

public class FiscalizacionAdapter extends RecyclerView.Adapter<FiscalizacionAdapter.ViewHolder> {

    private List<Fiscalizacion> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Fiscalizacion item);
        void onItemLongClick(Fiscalizacion item);
    }

    public FiscalizacionAdapter(List<Fiscalizacion> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fiscalizacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fiscalizacion item = list.get(position);
        holder.tvExpediente.setText("Expediente: " + item.getExpediente());
        holder.tvFecha.setText("Fecha: " + item.getFechaDiligencia());
        holder.tvEstado.setText("Estado: " + item.getEstado());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(item);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpediente, tvFecha, tvEstado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpediente = itemView.findViewById(R.id.tvExpediente);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}
