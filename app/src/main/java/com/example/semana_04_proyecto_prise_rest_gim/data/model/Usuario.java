package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @SerializedName("nombre_usuario")
    private String nombreUsuario;
    
    private String password;
    
    @SerializedName("nombre_completo")
    private String nombreCompleto;
    
    private String rol; // ADMIN, FISCALIZADOR, CONSULTA
    private String dni;
    
    private boolean isSynced = false;

    public Usuario() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }
}
