package com.example.semana_04_proyecto_prise_rest_gim.ui.establecimiento;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Establecimiento;
import com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion.FiscalizacionDataActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class EstablecimientoListActivity extends AppCompatActivity {

    private RecyclerView rvEstablecimientos;
    private SearchView searchView;
    private EstablecimientoAdapter adapter;
    private List<Establecimiento> list = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento_list);

        rvEstablecimientos = findViewById(R.id.rvEstablecimientos);
        searchView = findViewById(R.id.searchView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        TextView tvTitle = findViewById(R.id.tvTitle);
        db = AppDatabase.getInstance(this);

        String mode = getIntent().getStringExtra("mode");

        if ("select".equals(mode)) {
            tvTitle.setText("SELECCIONAR ESTABLECIMIENTO");
            fabAdd.setVisibility(View.GONE);
        } else {
            tvTitle.setText("GESTIÓN DE ESTABLECIMIENTOS");
            fabAdd.setVisibility(View.VISIBLE);
        }

        adapter = new EstablecimientoAdapter(list, new EstablecimientoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Establecimiento item) {
                if ("select".equals(mode)) {
                    // Modo Selección: Ir directo a Fiscalización
                    Intent intent = new Intent(EstablecimientoListActivity.this, FiscalizacionDataActivity.class);
                    intent.putExtra("establishmentId", item.getId());
                    startActivity(intent);
                    finish();
                } else {
                    // Modo CRUD: Opciones de Edición/Eliminación
                    String[] options = {"Editar Datos", "Eliminar Establecimiento"};
                    new AlertDialog.Builder(EstablecimientoListActivity.this)
                            .setTitle(item.getNombre())
                            .setItems(options, (dialog, which) -> {
                                if (which == 0) {
                                    Intent intent = new Intent(EstablecimientoListActivity.this, EstablecimientoFormActivity.class);
                                    intent.putExtra("id", item.getId());
                                    startActivity(intent);
                                } else {
                                    deleteItem(item);
                                }
                            }).show();
                }
            }

            @Override
            public void onItemLongClick(Establecimiento item) {
                if (!"select".equals(mode)) {
                    new AlertDialog.Builder(EstablecimientoListActivity.this)
                            .setTitle("Eliminar")
                            .setMessage("¿Desea eliminar este establecimiento?")
                            .setPositiveButton("Sí", (dialog, which) -> deleteItem(item))
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });

        rvEstablecimientos.setLayoutManager(new LinearLayoutManager(this));
        rvEstablecimientos.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, EstablecimientoFormActivity.class));
        });

        loadData();
    }

    private void deleteItem(Establecimiento item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.establecimientoDao().delete(item);
            loadData();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Establecimiento> data = db.establecimientoDao().getAll();
            runOnUiThread(() -> {
                adapter.updateData(data);
            });
        });
    }
}
