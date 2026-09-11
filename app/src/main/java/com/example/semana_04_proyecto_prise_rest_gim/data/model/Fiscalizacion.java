package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "fiscalizaciones")
public class Fiscalizacion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String expediente;
    
    @SerializedName("usuario_id")
    private int usuarioId;
    
    @SerializedName("establecimiento_id")
    private int establecimientoId;
    
    @SerializedName("fecha_diligencia")
    private String fechaDiligencia;
    
    @SerializedName("hora_apertura")
    private String horaApertura;
    
    @SerializedName("hora_cierre")
    private String horaCierre;
    
    @SerializedName("fiscalizador_responsable")
    private String fiscalizadorResponsable;
    
    private String estado; // BORRADOR, EN_PROCESO, FINALIZADA, ACTA_GENERADA
    
    @SerializedName("pdf_path")
    private String pdfPath;

    private boolean isSynced = false;

    public Fiscalizacion() {}

    @androidx.room.Ignore
    public Fiscalizacion(String expediente, int usuarioId, int establecimientoId, String fechaDiligencia, String horaApertura, String horaCierre, String fiscalizadorResponsable, String estado) {
        this.expediente = expediente;
        this.usuarioId = usuarioId;
        this.establecimientoId = establecimientoId;
        this.fechaDiligencia = fechaDiligencia;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.fiscalizadorResponsable = fiscalizadorResponsable;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getEstablecimientoId() { return establecimientoId; }
    public void setEstablecimientoId(int establecimientoId) { this.establecimientoId = establecimientoId; }

    public String getFechaDiligencia() { return fechaDiligencia; }
    public void setFechaDiligencia(String fechaDiligencia) { this.fechaDiligencia = fechaDiligencia; }

    public String getHoraApertura() { return horaApertura; }
    public void setHoraApertura(String horaApertura) { this.horaApertura = horaApertura; }

    public String getHoraCierre() { return horaCierre; }
    public void setHoraCierre(String horaCierre) { this.horaCierre = horaCierre; }

    public String getFiscalizadorResponsable() { return fiscalizadorResponsable; }
    public void setFiscalizadorResponsable(String fiscalizadorResponsable) { this.fiscalizadorResponsable = fiscalizadorResponsable; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }
}
