package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.HechoVerificado;
import java.util.List;

@Dao
public interface HechoVerificadoDao {
    @Insert
    void insert(HechoVerificado hecho);

    @Query("SELECT * FROM hechos_verificados WHERE fiscalizacionId = :fiscalizacionId")
    List<HechoVerificado> getByFiscalizacion(int fiscalizacionId);
}
