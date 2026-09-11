package com.example.semana_04_proyecto_prise_rest_gim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "precios")
public class Precio {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @SerializedName("fiscalizacion_id")
    private int fiscalizacionId;
    
    @SerializedName("producto_id")
    private int productoId;
    
    @SerializedName("precio_price")
    private double precioPrice;
    
    @SerializedName("precio_publicado")
    private double precioPublicado;
    
    @SerializedName("precio_surtidor")
    private double precioSurtidor;
    
    @SerializedName("precio_descuento")
    private double precioDescuento;

    public Precio(int fiscalizacionId, int productoId, double precioPrice, double precioPublicado, double precioSurtidor, double precioDescuento) {
        this.fiscalizacionId = fiscalizacionId;
        this.productoId = productoId;
        this.precioPrice = precioPrice;
        this.precioPublicado = precioPublicado;
        this.precioSurtidor = precioSurtidor;
        this.precioDescuento = precioDescuento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFiscalizacionId() { return fiscalizacionId; }
    public void setFiscalizacionId(int fiscalizacionId) { this.fiscalizacionId = fiscalizacionId; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public double getPrecioPrice() { return precioPrice; }
    public void setPrecioPrice(double precioPrice) { this.precioPrice = precioPrice; }

    public double getPrecioPublicado() { return precioPublicado; }
    public void setPrecioPublicado(double precioPublicado) { this.precioPublicado = precioPublicado; }

    public double getPrecioSurtidor() { return precioSurtidor; }
    public void setPrecioSurtidor(double precioSurtidor) { this.precioSurtidor = precioSurtidor; }

    public double getPrecioDescuento() { return precioDescuento; }
    public void setPrecioDescuento(double precioDescuento) { this.precioDescuento = precioDescuento; }
}
