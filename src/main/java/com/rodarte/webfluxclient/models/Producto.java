package com.rodarte.webfluxclient.models;

import java.util.Date;

public class Producto {

    private String id;
    private String nombre;
    private Double precio;
    private Date createdAt;
    private String foto;
    private Categoria categoria;

    public Producto(String id, String nombre, Double precio, Date createdAt, String foto, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.createdAt = createdAt;
        this.foto = foto;
        this.categoria = categoria;
    }

    public Producto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
