package com.example.semana_04_proyecto_prise_rest_gim.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.Usuario;
import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    long insert(Usuario usuario);
    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :username AND password = :password LIMIT 1")
    Usuario login(String username, String password);
    @androidx.room.Update
    void update(Usuario usuario);
    @androidx.room.Delete
    void delete(Usuario usuario);
    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario getById(int id);
    @Query("SELECT * FROM usuarios WHERE isSynced = 0")
    List<Usuario> getUnsynced();
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAll();
}
