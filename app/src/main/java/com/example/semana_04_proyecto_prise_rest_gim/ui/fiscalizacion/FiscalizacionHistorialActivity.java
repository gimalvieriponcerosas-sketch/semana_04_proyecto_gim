package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Fiscalizacion;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FiscalizacionHistorialActivity extends AppCompatActivity {

    private RecyclerView rvHistorial;
    private FiscalizacionAdapter adapter;
    private List<Fiscalizacion> list = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscalizacion_historial);

        rvHistorial = findViewById(R.id.rvHistorial);
        db = AppDatabase.getInstance(this);

        adapter = new FiscalizacionAdapter(list, new FiscalizacionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Fiscalizacion item) {
                if (item.getPdfPath() != null) {
                    openPdf(item.getPdfPath());
                } else {
                    Toast.makeText(FiscalizacionHistorialActivity.this, "El PDF aún no ha sido generado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(Fiscalizacion item) {
                new AlertDialog.Builder(FiscalizacionHistorialActivity.this)
                        .setTitle("Gestión de Acta")
                        .setItems(new String[]{"Eliminar"}, (dialog, which) -> {
                            deleteItem(item);
                        }).show();
            }
        });

        rvHistorial.setLayoutManager(new LinearLayoutManager(this));
        rvHistorial.setAdapter(adapter);

        loadData();
    }

    private void deleteItem(Fiscalizacion item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.fiscalizacionDao().delete(item);
            loadData();
        });
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Fiscalizacion> data = db.fiscalizacionDao().getAll();
            runOnUiThread(() -> {
                list.clear();
                list.addAll(data);
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void openPdf(String path) {
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "El archivo ya no existe", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
