package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Producto;
import java.util.List;

@Dao
public interface ProductoDao {
    @Insert
    void insertAll(List<Producto> productos);

    @Query("SELECT * FROM productos")
    List<Producto> getAll();
}
