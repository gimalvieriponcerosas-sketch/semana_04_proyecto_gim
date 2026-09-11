package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.*;
import com.example.semana_04_proyecto_prise_rest_gim.util.PdfGenerator;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

public class FiscalizacionFirmasActivity extends AppCompatActivity {

    private TextInputEditText etNombreRecibe, etDniRecibe, etRelacionRecibe;
    private Button btnFinalize;
    private AppDatabase db;
    private int fiscalizacionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscalizacion_firmas);

        fiscalizacionId = getIntent().getIntExtra("fiscalizacionId", -1);
        db = AppDatabase.getInstance(this);

        etNombreRecibe = findViewById(R.id.etNombreRecibe);
        etDniRecibe = findViewById(R.id.etDniRecibe);
        etRelacionRecibe = findViewById(R.id.etRelacionRecibe);
        btnFinalize = findViewById(R.id.btnFinalize);

        btnFinalize.setOnClickListener(v -> finalizeFiscalizacion());
    }

    private void finalizeFiscalizacion() {
        String nombre = etNombreRecibe.getText().toString().trim();
        String dni = etDniRecibe.getText().toString().trim();
        String relacion = etRelacionRecibe.getText().toString().trim();

        if (nombre.isEmpty() || dni.isEmpty()) {
            Toast.makeText(this, "Debe ingresar el nombre y DNI de quien recibe", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            Fiscalizacion fis = db.fiscalizacionDao().getById(fiscalizacionId);
            Establecimiento est = db.establecimientoDao().getById(fis.getEstablecimientoId());
            List<Precio> precios = db.precioDao().getByFiscalizacion(fiscalizacionId);
            List<Verificacion> verifs = db.verificacionDao().getByFiscalizacion(fiscalizacionId);
            List<HechoVerificado> hechos = db.hechoVerificadoDao().getByFiscalizacion(fiscalizacionId);
            
            // Simular firmas con los datos manuales
            Firma f1 = new Firma(fiscalizacionId, "RECIBE", nombre, dni, relacion, "FIRMA_MANUAL");
            List<Firma> firmas = List.of(f1);

            String pdfPath = PdfGenerator.generateActa(this, fis, est, precios, verifs, hechos, firmas);

            if (pdfPath != null) {
                fis.setPdfPath(pdfPath);
                fis.setEstado("ACTA_GENERADA");
                db.fiscalizacionDao().update(fis);
            }

            runOnUiThread(() -> {
                if (pdfPath != null) {
                    Toast.makeText(this, "Acta generada: " + pdfPath, Toast.LENGTH_LONG).show();
                    openPdf(pdfPath);
                } else {
                    Toast.makeText(this, "Error al generar PDF", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void openPdf(String path) {
        File file = new File(path);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
