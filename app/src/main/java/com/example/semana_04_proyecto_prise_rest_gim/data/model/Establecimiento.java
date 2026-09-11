package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "establecimientos")
public class Establecimiento {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private String ruc;
    private String telefono;
    
    @SerializedName("codigo_osinergmin")
    private String codigoOsinergmin;
    
    @SerializedName("registro_hidrocarburos")
    private String registroHidrocarburos;

    private boolean isSynced = false;

    public Establecimiento() {}

    @androidx.room.Ignore
    public Establecimiento(String nombre, String direccion, String distrito, String provincia, String departamento, String ruc, String telefono, String codigoOsinergmin, String registroHidrocarburos) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
        this.ruc = ruc;
        this.telefono = telefono;
        this.codigoOsinergmin = codigoOsinergmin;
        this.registroHidrocarburos = registroHidrocarburos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCodigoOsinergmin() { return codigoOsinergmin; }
    public void setCodigoOsinergmin(String codigoOsinergmin) { this.codigoOsinergmin = codigoOsinergmin; }

    public String getRegistroHidrocarburos() { return registroHidrocarburos; }
    public void setRegistroHidrocarburos(String registroHidrocarburos) { this.registroHidrocarburos = registroHidrocarburos; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }
}
