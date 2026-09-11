package com.example.semana_04_proyecto_prise_rest_gim;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Establecimiento;
import com.example.semana_04_proyecto_prise_rest_gim.data.remote.ApiClient;
import com.example.semana_04_proyecto_prise_rest_gim.data.remote.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "API_PRUEBA_REAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EJECUTAR PRUEBA DE ENDPOINT (PASO 5)
        probarServidorReal();
    }

    private void probarServidorReal() {
        ApiService api = ApiClient.getApiService();
        Call<List<Establecimiento>> call = api.getEstablecimientos();

        Log.d(TAG, "Conectando a SUPABASE: https://qxsuiutnoagglmawgpcl.supabase.co");

        call.enqueue(new Callback<List<Establecimiento>>() {
            @Override
            public void onResponse(Call<List<Establecimiento>> call, Response<List<Establecimiento>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "¡CONEXIÓN EXITOSA CON SUPABASE! Código: " + response.code());
                    if (response.body() != null) {
                        Log.d(TAG, "Establecimientos en la nube: " + response.body().size());
                    }
                } else {
                    Log.e(TAG, "Error de Supabase: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Establecimiento>> call, Throwable t) {
                Log.e(TAG, "FALLO CRÍTICO: No se pudo contactar al servidor.");
                Log.e(TAG, "Detalle: " + t.getMessage());
            }
        });
    }
}
