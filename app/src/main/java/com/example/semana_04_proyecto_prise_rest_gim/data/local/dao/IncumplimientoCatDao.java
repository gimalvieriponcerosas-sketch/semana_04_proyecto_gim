package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.IncumplimientoCat;
import java.util.List;

@Dao
public interface IncumplimientoCatDao {
    @Insert
    void insertAll(List<IncumplimientoCat> items);

    @Query("SELECT * FROM incumplimientos_catalogo")
    List<IncumplimientoCat> getAll();
}
