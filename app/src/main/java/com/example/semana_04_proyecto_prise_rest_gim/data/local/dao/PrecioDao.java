package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Precio;
import java.util.List;

@Dao
public interface PrecioDao {
    @Insert
    void insert(Precio precio);

    @Query("SELECT * FROM precios WHERE fiscalizacionId = :fiscalizacionId")
    List<Precio> getByFiscalizacion(int fiscalizacionId);
}
