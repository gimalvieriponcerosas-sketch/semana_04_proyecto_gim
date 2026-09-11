package com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Fiscalizacion;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;

public class FiscalizacionDataActivity extends AppCompatActivity {

    private TextInputEditText etExpediente, etFecha, etHoraApertura, etHoraCierre, etFiscalizador;
    private Button btnNext;
    private AppDatabase db;
    private int establishmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscalizacion_data);

        establishmentId = getIntent().getIntExtra("establishmentId", -1);
        db = AppDatabase.getInstance(this);

        initViews();
        setupPickers();

        btnNext.setOnClickListener(v -> saveAndNext());
    }

    private void initViews() {
        etExpediente = findViewById(R.id.etExpediente);
        etFecha = findViewById(R.id.etFecha);
        etHoraApertura = findViewById(R.id.etHoraApertura);
        etHoraCierre = findViewById(R.id.etHoraCierre);
        etFiscalizador = findViewById(R.id.etFiscalizador);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupPickers() {
        etFecha.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                etFecha.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year));
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        etHoraApertura.setOnClickListener(v -> showTimePicker(etHoraApertura));
        etHoraCierre.setOnClickListener(v -> showTimePicker(etHoraCierre));
    }

    private void showTimePicker(TextInputEditText et) {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            et.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    private void saveAndNext() {
        String exp = etExpediente.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String aperture = etHoraApertura.getText().toString().trim();
        String closing = etHoraCierre.getText().toString().trim();
        String fiscalizador = etFiscalizador.getText().toString().trim();

        if (exp.isEmpty() || fecha.isEmpty() || aperture.isEmpty()) {
            Toast.makeText(this, "Complete los datos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Fiscalizacion fis = new Fiscalizacion(exp, 1, establishmentId, fecha, aperture, closing, fiscalizador, "BORRADOR");

        Executors.newSingleThreadExecutor().execute(() -> {
            long id = db.fiscalizacionDao().insert(fis);
            runOnUiThread(() -> {
                Intent intent = new Intent(this, FiscalizacionPreciosActivity.class);
                intent.putExtra("fiscalizacionId", (int) id);
                intent.putExtra("establishmentId", establishmentId);
                startActivity(intent);
                finish();
            });
        });
    }
}
