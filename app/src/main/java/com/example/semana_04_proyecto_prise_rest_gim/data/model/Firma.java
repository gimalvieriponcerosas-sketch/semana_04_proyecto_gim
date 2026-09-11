package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "firmas")
public class Firma {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @SerializedName("fiscalizacion_id")
    private int fiscalizacionId;
    
    @SerializedName("tipo_firma")
    private String tipoFirma; // FISCALIZADOR, RECIBE
    
    private String nombre;
    private String dni;
    private String relacion;
    
    @SerializedName("firma_path")
    private String firmaPath;

    public Firma(int fiscalizacionId, String tipoFirma, String nombre, String dni, String relacion, String firmaPath) {
        this.fiscalizacionId = fiscalizacionId;
        this.tipoFirma = tipoFirma;
        this.nombre = nombre;
        this.dni = dni;
        this.relacion = relacion;
        this.firmaPath = firmaPath;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFiscalizacionId() { return fiscalizacionId; }
    public void setFiscalizacionId(int fiscalizacionId) { this.fiscalizacionId = fiscalizacionId; }

    public String getTipoFirma() { return tipoFirma; }
    public void setTipoFirma(String tipoFirma) { this.tipoFirma = tipoFirma; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getRelacion() { return relacion; }
    public void setRelacion(String relacion) { this.relacion = relacion; }

    public String getFirmaPath() { return firmaPath; }
    public void setFirmaPath(String firmaPath) { this.firmaPath = firmaPath; }
}
