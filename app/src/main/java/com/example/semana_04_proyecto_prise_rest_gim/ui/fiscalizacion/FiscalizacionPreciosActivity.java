package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Precio;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executors;

public class FiscalizacionPreciosActivity extends AppCompatActivity {

    private TextInputEditText etDieselPrice, etDieselPub, etG84Price, etG84Pub;
    private Button btnNext;
    private AppDatabase db;
    private int fiscalizacionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscalizacion_precios);

        fiscalizacionId = getIntent().getIntExtra("fiscalizacionId", -1);
        db = AppDatabase.getInstance(this);

        initViews();

        btnNext.setOnClickListener(v -> saveAndNext());
    }

    private void initViews() {
        etDieselPrice = findViewById(R.id.etDieselPrice);
        etDieselPub = findViewById(R.id.etDieselPub);
        etG84Price = findViewById(R.id.etG84Price);
        etG84Pub = findViewById(R.id.etG84Pub);
        btnNext = findViewById(R.id.btnNext);
    }

    private void saveAndNext() {
        // En un caso real, validaríamos y guardaríamos todos los precios
        // Aquí simulamos guardar los dos primeros
        double dieselP = parseDouble(etDieselPrice.getText().toString());
        double dieselPub = parseDouble(etDieselPub.getText().toString());

        Precio p1 = new Precio(fiscalizacionId, 1, dieselP, dieselPub, 0, 0);

        Executors.newSingleThreadExecutor().execute(() -> {
            db.precioDao().insert(p1);
            runOnUiThread(() -> {
                Intent intent = new Intent(this, FiscalizacionVerifActivity.class);
                intent.putExtra("fiscalizacionId", fiscalizacionId);
                startActivity(intent);
                finish();
            });
        });
    }

    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }
}
