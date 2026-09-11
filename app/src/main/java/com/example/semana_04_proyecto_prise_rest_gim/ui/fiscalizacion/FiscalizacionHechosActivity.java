package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.HechoVerificado;
import java.util.concurrent.Executors;

public class FiscalizacionHechosActivity extends AppCompatActivity {

    private CheckBox cbInc1, cbInc2, cbInc3;
    private EditText etHecho1, etHecho2, etHecho3;
    private Button btnNext;
    private AppDatabase db;
    private int fiscalizacionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscalizacion_hechos);

        fiscalizacionId = getIntent().getIntExtra("fiscalizacionId", -1);
        db = AppDatabase.getInstance(this);

        cbInc1 = findViewById(R.id.cbInc1);
        cbInc2 = findViewById(R.id.cbInc2);
        cbInc3 = findViewById(R.id.cbInc3);
        etHecho1 = findViewById(R.id.etHecho1);
        etHecho2 = findViewById(R.id.etHecho2);
        etHecho3 = findViewById(R.id.etHecho3);
        btnNext = findViewById(R.id.btnNext);

        cbInc1.setOnCheckedChangeListener((v, isChecked) -> etHecho1.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        cbInc2.setOnCheckedChangeListener((v, isChecked) -> etHecho2.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        cbInc3.setOnCheckedChangeListener((v, isChecked) -> etHecho3.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        btnNext.setOnClickListener(v -> saveAndNext());
    }

    private void saveAndNext() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (cbInc1.isChecked()) db.hechoVerificadoDao().insert(new HechoVerificado(fiscalizacionId, 1, etHecho1.getText().toString()));
            if (cbInc2.isChecked()) db.hechoVerificadoDao().insert(new HechoVerificado(fiscalizacionId, 2, etHecho2.getText().toString()));
            if (cbInc3.isChecked()) db.hechoVerificadoDao().insert(new HechoVerificado(fiscalizacionId, 3, etHecho3.getText().toString()));
            
            runOnUiThread(() -> {
                Intent intent = new Intent(this, FiscalizacionFirmasActivity.class);
                intent.putExtra("fiscalizacionId", fiscalizacionId);
                startActivity(intent);
                finish();
            });
        });
    }
}
