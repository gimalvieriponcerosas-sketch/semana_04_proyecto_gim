package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "incumplimientos_catalogo")
public class IncumplimientoCat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String descripcion;
    
    @SerializedName("base_legal")
    private String baseLegal;

    public IncumplimientoCat(String descripcion, String baseLegal) {
        this.descripcion = descripcion;
        this.baseLegal = baseLegal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getBaseLegal() { return baseLegal; }
    public void setBaseLegal(String baseLegal) { this.baseLegal = baseLegal; }
}
