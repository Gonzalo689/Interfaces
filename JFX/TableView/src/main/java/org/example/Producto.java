package org.example;

import java.util.Date;

public class Producto {
    private String nombre;
    private Double precio;
    private int stock;
    private Double peso;
    private Date fechaVencimiento;
    private String color;
    private String material;
    private String numMaterial;

    public Producto(String nombre, Double precio, int stock, Double peso, Date fechaVencimiento, String color, String material, String numMaterial) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.peso = peso;
        this.fechaVencimiento = fechaVencimiento;
        this.color = color;
        this.material = material;
        this.numMaterial = numMaterial;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNumMaterial() {
        return numMaterial;
    }

    public void setNumMaterial(String numMaterial) {
        this.numMaterial = numMaterial;
    }
    public boolean contieneNombre(String filtro) {
        return nombre.toLowerCase().contains(filtro.toLowerCase());
    }
}
