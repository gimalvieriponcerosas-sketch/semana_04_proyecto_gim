package com.example.semana_04_proyecto_prise_rest_gim.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUser, etPass, etFull, etDni;
    private Button btnReg;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = AppDatabase.getInstance(this);
        etUser = findViewById(R.id.etRegUsername);
        etPass = findViewById(R.id.etRegPassword);
        etFull = findViewById(R.id.etRegFullName);
        etDni = findViewById(R.id.etRegDni);
        btnReg = findViewById(R.id.btnDoRegister);

        btnReg.setOnClickListener(v -> register());
    }

    private void register() {
        String u = etUser.getText().toString().trim();
        String p = etPass.getText().toString().trim();
        String f = etFull.getText().toString().trim();
        String d = etDni.getText().toString().trim();

        if (u.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, "Usuario y contraseña requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario user = new Usuario();
        user.setNombreUsuario(u);
        user.setPassword(p);
        user.setNombreCompleto(f);
        user.setRol("FISCALIZADOR");
        user.setDni(d);

        Executors.newSingleThreadExecutor().execute(() -> {
            db.usuarioDao().insert(user);
            runOnUiThread(() -> {
                Toast.makeText(this, "Usuario registrado correctamente",
                        Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
