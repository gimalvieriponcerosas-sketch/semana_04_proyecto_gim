package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Documento;
import java.util.List;

@Dao
public interface DocumentoDao {
    @Insert
    void insert(Documento documento);

    @Query("SELECT * FROM documentos WHERE fiscalizacionId = :fiscalizacionId")
    List<Documento> getByFiscalizacion(int fiscalizacionId);
}
