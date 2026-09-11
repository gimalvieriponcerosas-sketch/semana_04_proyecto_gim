package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "hechos_verificados")
public class HechoVerificado {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @SerializedName("fiscalizacion_id")
    private int fiscalizacionId;
    
    @SerializedName("incumplimiento_id")
    private int incumplimientoId;
    
    @SerializedName("descripcion_hecho")
    private String descripcionHecho;

    public HechoVerificado(int fiscalizacionId, int incumplimientoId, String descripcionHecho) {
        this.fiscalizacionId = fiscalizacionId;
        this.incumplimientoId = incumplimientoId;
        this.descripcionHecho = descripcionHecho;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFiscalizacionId() { return fiscalizacionId; }
    public void setFiscalizacionId(int fiscalizacionId) { this.fiscalizacionId = fiscalizacionId; }

    public int getIncumplimientoId() { return incumplimientoId; }
    public void setIncumplimientoId(int incumplimientoId) { this.incumplimientoId = incumplimientoId; }

    public String getDescripcionHecho() { return descripcionHecho; }
    public void setDescripcionHecho(String descripcionHecho) { this.descripcionHecho = descripcionHecho; }
}
