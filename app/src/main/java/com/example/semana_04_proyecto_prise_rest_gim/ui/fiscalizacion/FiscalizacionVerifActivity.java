package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Verificacion;
import java.util.concurrent.Executors;

public class FiscalizacionVerifActivity extends AppCompatActivity {

    private RadioButton rbTelPubSi, rbTelPriceSi, rbHorarioSi;
    private Button btnNext;
    private AppDatabase db;
    private int fiscalizacionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscalizacion_verif);

        fiscalizacionId = getIntent().getIntExtra("fiscalizacionId", -1);
        db = AppDatabase.getInstance(this);

        rbTelPubSi = findViewById(R.id.rbTelPubSi);
        rbTelPriceSi = findViewById(R.id.rbTelPriceSi);
        rbHorarioSi = findViewById(R.id.rbHorarioSi);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> saveAndNext());
    }

    private void saveAndNext() {
        Verificacion v1 = new Verificacion(fiscalizacionId, "¿Teléfono publicado?", rbTelPubSi.isChecked());
        Verificacion v2 = new Verificacion(fiscalizacionId, "¿Teléfono en PRICE?", rbTelPriceSi.isChecked());
        Verificacion v3 = new Verificacion(fiscalizacionId, "¿Horario publicado?", rbHorarioSi.isChecked());

        Executors.newSingleThreadExecutor().execute(() -> {
            db.verificacionDao().insert(v1);
            db.verificacionDao().insert(v2);
            db.verificacionDao().insert(v3);
            runOnUiThread(() -> {
                Intent intent = new Intent(this, FiscalizacionHechosActivity.class);
                intent.putExtra("fiscalizacionId", fiscalizacionId);
                startActivity(intent);
                finish();
            });
        });
    }
}
