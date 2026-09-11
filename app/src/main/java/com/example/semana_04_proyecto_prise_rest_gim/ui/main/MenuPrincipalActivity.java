package com.example.semana_04_proyecto_prise_rest_gim.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.AppDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Establecimiento;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Fiscalizacion;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Usuario;
import com.example.semana_04_proyecto_prise_rest_gim.data.remote.ApiClient;
import com.example.semana_04_proyecto_prise_rest_gim.data.remote.ApiService;
import com.example.semana_04_proyecto_prise_rest_gim.ui.establecimiento.EstablecimientoListActivity;
import com.example.semana_04_proyecto_prise_rest_gim.ui.fiscalizacion.FiscalizacionHistorialActivity;
import com.example.semana_04_proyecto_prise_rest_gim.ui.login.LoginActivity;
import com.example.semana_04_proyecto_prise_rest_gim.ui.settings.SettingsActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        MaterialCardView cardEstablecimientos = findViewById(R.id.cardEstablecimientos);
        MaterialCardView cardNuevaFiscalizacion = findViewById(R.id.cardNuevaFiscalizacion);
        MaterialCardView cardHistorial = findViewById(R.id.cardHistorial);
        MaterialCardView cardConfiguracion = findViewById(R.id.cardConfiguracion);
        MaterialCardView cardSincronizar = findViewById(R.id.cardSincronizar);
        Button btnLogout = findViewById(R.id.btnLogout);

        int userId = getIntent().getIntExtra("userId", -1);

        cardEstablecimientos.setOnClickListener(v -> {
            startActivity(new Intent(this, EstablecimientoListActivity.class));
        });

        cardNuevaFiscalizacion.setOnClickListener(v -> {
            Intent intent = new Intent(this, EstablecimientoListActivity.class);
            intent.putExtra("mode", "select");
            startActivity(intent);
        });

        cardHistorial.setOnClickListener(v -> {
            startActivity(new Intent(this, FiscalizacionHistorialActivity.class));
        });

        cardConfiguracion.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        cardSincronizar.setOnClickListener(v -> {
            Toast.makeText(this, "Iniciando sincronización...", Toast.LENGTH_SHORT).show();
            sincronizarTodo();
        });

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void sincronizarTodo() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            ApiService api = ApiClient.getApiService();

            // 0. Sincronizar Usuarios
            List<Usuario> users = db.usuarioDao().getUnsynced();
            if (!users.isEmpty()) {
                api.createUsuario(users).enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        if (response.isSuccessful()) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                for (Usuario u : users) {
                                    u.setSynced(true);
                                    db.usuarioDao().update(u);
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {}
                });
            }

            // 1. Sincronizar Establecimientos
            List<Establecimiento> ests = db.establecimientoDao().getUnsynced();
            if (!ests.isEmpty()) {
                api.createEstablecimiento(ests).enqueue(new Callback<List<Establecimiento>>() {
                    @Override
                    public void onResponse(Call<List<Establecimiento>> call, Response<List<Establecimiento>> response) {
                        if (response.isSuccessful()) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                for (Establecimiento e : ests) {
                                    e.setSynced(true);
                                    db.establecimientoDao().update(e);
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Establecimiento>> call, Throwable t) {}
                });
            }

            // 2. Sincronizar Fiscalizaciones
            List<Fiscalizacion> fiscs = db.fiscalizacionDao().getUnsynced();
            if (!fiscs.isEmpty()) {
                api.createFiscalizacion(fiscs).enqueue(new Callback<List<Fiscalizacion>>() {
                    @Override
                    public void onResponse(Call<List<Fiscalizacion>> call, Response<List<Fiscalizacion>> response) {
                        if (response.isSuccessful()) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                for (Fiscalizacion f : fiscs) {
                                    f.setSynced(true);
                                    db.fiscalizacionDao().update(f);
                                }
                                runOnUiThread(() -> Toast.makeText(MenuPrincipalActivity.this, "Sincronización completa", Toast.LENGTH_LONG).show());
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Fiscalizacion>> call, Throwable t) {
                        runOnUiThread(() -> Toast.makeText(MenuPrincipalActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show());
                    }
                });
            } else {
                runOnUiThread(() -> Toast.makeText(MenuPrincipalActivity.this, "Sin datos pendientes", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
