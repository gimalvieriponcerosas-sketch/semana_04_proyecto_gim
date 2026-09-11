package com.example.semana_04_proyecto_prise_rest_gim.data.remote;

import com.example.semana_04_proyecto_prise_rest_gim.data.model.*;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("usuarios")
    Call<List<Usuario>> login(@Query("nombre_usuario") String username, @Query("password") String password);

    @POST("usuarios")
    Call<List<Usuario>> createUsuario(@Body List<Usuario> usuarios);

    @GET("establecimientos")
    Call<List<Establecimiento>> getEstablecimientos();

    @POST("establecimientos")
    Call<List<Establecimiento>> createEstablecimiento(@Body List<Establecimiento> establecimientos);

    @GET("fiscalizaciones")
    Call<List<Fiscalizacion>> getFiscalizaciones();

    @POST("fiscalizaciones")
    Call<List<Fiscalizacion>> createFiscalizacion(@Body List<Fiscalizacion> fiscalizaciones);

    @GET("incumplimientos_catalogo")
    Call<List<IncumplimientoCat>> getIncumplimientos();
}
