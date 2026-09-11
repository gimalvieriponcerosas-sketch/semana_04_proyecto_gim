package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Establecimiento;
import java.util.List;

@Dao
public interface EstablecimientoDao {
    @Insert
    long insert(Establecimiento establecimiento);
    @Update
    void update(Establecimiento establecimiento);
    @Delete
    void delete(Establecimiento establecimiento);
    @Query("SELECT * FROM establecimientos")
    List<Establecimiento> getAll();
    @Query("SELECT * FROM establecimientos WHERE isSynced = 0")
    List<Establecimiento> getUnsynced();
    @Query("SELECT * FROM establecimientos WHERE id = :id")
    Establecimiento getById(int id);
}
