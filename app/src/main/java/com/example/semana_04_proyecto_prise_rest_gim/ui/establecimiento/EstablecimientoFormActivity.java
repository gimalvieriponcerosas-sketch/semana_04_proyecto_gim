package com.example.semana_04_proyecto_prise_rest_gim.ui.establecimiento;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Establecimiento;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executors;

public class EstablecimientoFormActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etDireccion, etDistrito, etProvincia, etRuc, etTelefono, etCodigoOsinergmin, etRegistroHidrocarburos;
    private Button btnSave;
    private AppDatabase db;
    private int establishmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento_form);

        db = AppDatabase.getInstance(this);
        initViews();

        if (getIntent().hasExtra("id")) {
            establishmentId = getIntent().getIntExtra("id", -1);
            loadEstablishment();
        }

        btnSave.setOnClickListener(v -> save());
    }

    private void initViews() {
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        etDistrito = findViewById(R.id.etDistrito);
        etProvincia = findViewById(R.id.etProvincia);
        etRuc = findViewById(R.id.etRuc);
        etTelefono = findViewById(R.id.etTelefono);
        etCodigoOsinergmin = findViewById(R.id.etCodigoOsinergmin);
        etRegistroHidrocarburos = findViewById(R.id.etRegistroHidrocarburos);
        btnSave = findViewById(R.id.btnSave);
    }

    private void loadEstablishment() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Establecimiento est = db.establecimientoDao().getById(establishmentId);
            if (est != null) {
                runOnUiThread(() -> {
                    etNombre.setText(est.getNombre());
                    etDireccion.setText(est.getDireccion());
                    etDistrito.setText(est.getDistrito());
                    etProvincia.setText(est.getProvincia());
                    etRuc.setText(est.getRuc());
                    etTelefono.setText(est.getTelefono());
                    etCodigoOsinergmin.setText(est.getCodigoOsinergmin());
                    etRegistroHidrocarburos.setText(est.getRegistroHidrocarburos());
                });
            }
        });
    }

    private void save() {
        String nombre = etNombre.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String distrito = etDistrito.getText().toString().trim();
        String provincia = etProvincia.getText().toString().trim();
        String ruc = etRuc.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String codigo = etCodigoOsinergmin.getText().toString().trim();
        String registro = etRegistroHidrocarburos.getText().toString().trim();

        if (nombre.isEmpty() || ruc.isEmpty()) {
            Toast.makeText(this, "Nombre y RUC son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Establecimiento est = new Establecimiento(nombre, direccion, distrito, provincia, "HUANUCO", ruc, telefono, codigo, registro);
        
        Executors.newSingleThreadExecutor().execute(() -> {
            if (establishmentId == -1) {
                db.establecimientoDao().insert(est);
            } else {
                est.setId(establishmentId);
                db.establecimientoDao().update(est);
            }
            runOnUiThread(() -> {
                finish();
            });
        });
    }
}
