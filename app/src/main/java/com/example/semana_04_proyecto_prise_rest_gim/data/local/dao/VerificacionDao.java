package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Verificacion;
import java.util.List;

@Dao
public interface VerificacionDao {
    @Insert
    void insert(Verificacion verificacion);

    @Query("SELECT * FROM verificaciones WHERE fiscalizacionId = :fiscalizacionId")
    List<Verificacion> getByFiscalizacion(int fiscalizacionId);
}
