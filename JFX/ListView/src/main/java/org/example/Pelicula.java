package org.example;

import javafx.fxml.FXML;

public class Pelicula {
    private String titulo;
    private String director;
    private int anioSalida;
    private String urlImagen;

    public Pelicula(String titulo, String director, int anioSalida, String urlImagen) {
        this.titulo = titulo;
        this.director = director;
        this.anioSalida = anioSalida;
        this.urlImagen = urlImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnioSalida() {
        return anioSalida;
    }

    public void setAnioSalida(int anioSalida) {
        this.anioSalida = anioSalida;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", anioSalida=" + anioSalida +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }
}
