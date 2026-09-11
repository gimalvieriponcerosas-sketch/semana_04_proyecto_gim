package com.example.semana_04_proyecto_prise_rest_gim.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Usuario;
import com.example.semana_04_proyecto_prise_rest_gim.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executors;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText etName, etRole;
    private SwitchCompat switchTheme;
    private Button btnSave, btnDelete;
    private AppDatabase db;
    private SharedPreferences sharedPreferences;
    private int userId;
    private Usuario currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userId = getIntent().getIntExtra("userId", -1);
        db = AppDatabase.getInstance(this);
        sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);

        etName = findViewById(R.id.etSettingsName);
        etRole = findViewById(R.id.etSettingsRole);
        switchTheme = findViewById(R.id.switchTheme);
        btnSave = findViewById(R.id.btnSaveSettings);
        btnDelete = findViewById(R.id.btnDeleteAccount);

        // Cargar estado del tema
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        switchTheme.setChecked(isDarkMode);

        loadUserData();

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        btnSave.setOnClickListener(v -> saveChanges());
        btnDelete.setOnClickListener(v -> confirmDelete());
    }

    private void loadUserData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            currentUser = db.usuarioDao().getById(userId); // Necesitaré agregar este método al DAO
            if (currentUser != null) {
                runOnUiThread(() -> {
                    etName.setText(currentUser.getNombreCompleto());
                    etRole.setText(currentUser.getRol());
                });
            }
        });
    }

    private void saveChanges() {
        String newName = etName.getText().toString().trim();
        String newRole = etRole.getText().toString().trim();

        if (newName.isEmpty()) {
            Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setNombreCompleto(newName);
        currentUser.setRol(newRole);

        Executors.newSingleThreadExecutor().execute(() -> {
            db.usuarioDao().update(currentUser); // Necesitaré agregar este método al DAO
            runOnUiThread(() -> {
                Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cuenta")
                .setMessage("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.")
                .setPositiveButton("ELIMINAR", (dialog, which) -> deleteAccount())
                .setNegativeButton("CANCELAR", null)
                .show();
    }

    private void deleteAccount() {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.usuarioDao().delete(currentUser); // Necesitaré agregar este método al DAO
            runOnUiThread(() -> {
                Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });
        });
    }
}
