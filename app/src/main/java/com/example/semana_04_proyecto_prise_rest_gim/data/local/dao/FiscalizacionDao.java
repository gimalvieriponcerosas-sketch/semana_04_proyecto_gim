package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Fiscalizacion;
import java.util.List;

@Dao
public interface FiscalizacionDao {
    @Insert
    long insert(Fiscalizacion fiscalizacion);
    @Update
    void update(Fiscalizacion fiscalizacion);
    @androidx.room.Delete
    void delete(Fiscalizacion fiscalizacion);
    @Query("SELECT * FROM fiscalizaciones ORDER BY id DESC")
    List<Fiscalizacion> getAll();
    @Query("SELECT * FROM fiscalizaciones WHERE isSynced = 0")
    List<Fiscalizacion> getUnsynced();
    @Query("SELECT * FROM fiscalizaciones WHERE id = :id")
    Fiscalizacion getById(int id);
}
