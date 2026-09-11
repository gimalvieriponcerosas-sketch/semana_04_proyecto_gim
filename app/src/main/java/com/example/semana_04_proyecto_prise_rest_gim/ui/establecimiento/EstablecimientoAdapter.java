package com.example.semana_04_proyecto_prise_rest_gim.ui.establecimiento;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Establecimiento;
import java.util.ArrayList;
import java.util.List;

public class EstablecimientoAdapter extends RecyclerView.Adapter<EstablecimientoAdapter.ViewHolder> implements Filterable {

    private List<Establecimiento> list;
    private List<Establecimiento> listFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Establecimiento item);
        void onItemLongClick(Establecimiento item);
    }

    public EstablecimientoAdapter(List<Establecimiento> list, OnItemClickListener listener) {
        this.list = new ArrayList<>(list);
        this.listFull = new ArrayList<>(list);
        this.listener = listener;
    }

    public void updateData(List<Establecimiento> newList) {
        this.list.clear();
        this.list.addAll(newList);
        this.listFull.clear();
        this.listFull.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_establecimiento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Establecimiento item = list.get(position);
        holder.tvNombre.setText(item.getNombre());
        holder.tvDireccion.setText(item.getDireccion());
        holder.tvRuc.setText("RUC: " + item.getRuc());
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Establecimiento> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(listFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Establecimiento item : listFull) {
                        if (item.getNombre().toLowerCase().contains(filterPattern) || 
                            item.getRuc().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                if (results.values != null) {
                    list.addAll((List<Establecimiento>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDireccion, tvRuc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvRuc = itemView.findViewById(R.id.tvRuc);
        }
    }
}
