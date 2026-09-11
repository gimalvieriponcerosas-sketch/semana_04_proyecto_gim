package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Firma;
import java.util.List;

@Dao
public interface FirmaDao {
    @Insert
    void insert(Firma firma);

    @Query("SELECT * FROM firmas WHERE fiscalizacionId = :fiscalizacionId")
    List<Firma> getByFiscalizacion(int fiscalizacionId);
}
