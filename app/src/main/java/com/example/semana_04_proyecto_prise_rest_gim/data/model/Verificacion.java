package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "verificaciones")
public class Verificacion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @SerializedName("fiscalizacion_id")
    private int fiscalizacionId;
    
    private String pregunta;
    private boolean respuesta;

    public Verificacion(int fiscalizacionId, String pregunta, boolean respuesta) {
        this.fiscalizacionId = fiscalizacionId;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFiscalizacionId() { return fiscalizacionId; }
    public void setFiscalizacionId(int fiscalizacionId) { this.fiscalizacionId = fiscalizacionId; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public boolean isRespuesta() { return respuesta; }
    public void setRespuesta(boolean respuesta) { this.respuesta = respuesta; }
}
