package com.example.semana_04_proyecto_prise_rest_gim.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.semana_04_proyecto_prise_rest_gim.data.local.dao.*;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.*;

@Database(entities = {
    Usuario.class, Establecimiento.class, Fiscalizacion.class,
    Producto.class, Precio.class, Verificacion.class,
    IncumplimientoCat.class, HechoVerificado.class, Firma.class, Documento.class
}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract UsuarioDao usuarioDao();
    public abstract EstablecimientoDao establecimientoDao();
    public abstract FiscalizacionDao fiscalizacionDao();
    public abstract ProductoDao productoDao();
    public abstract PrecioDao precioDao();
    public abstract VerificacionDao verificacionDao();
    public abstract IncumplimientoCatDao incumplimientoCatDao();
    public abstract HechoVerificadoDao hechoVerificadoDao();
    public abstract FirmaDao firmaDao();
    public abstract DocumentoDao documentoDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "fiscalizacion_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
