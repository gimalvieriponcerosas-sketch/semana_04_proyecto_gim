package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "documentos")
public class Documento {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @SerializedName("fiscalizacion_id")
    private int fiscalizacionId;
    
    private String nombre;
    private String path;

    public Documento(int fiscalizacionId, String nombre, String path) {
        this.fiscalizacionId = fiscalizacionId;
        this.nombre = nombre;
        this.path = path;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFiscalizacionId() { return fiscalizacionId; }
    public void setFiscalizacionId(int fiscalizacionId) { this.fiscalizacionId = fiscalizacionId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
