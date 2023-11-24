package org.example;

public class Producto {
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public boolean contieneNombre(String filtro) {
        return nombre.toLowerCase().contains(filtro.toLowerCase());
    }
    public boolean contieneStoc(int filtroStock) {
        return stock > filtroStock;
    }

    @Override
    public String toString() {
        return  nombre + "\n" +
                "Precio: " + precio +
                "  Stock: " + stock ;
    }
}
