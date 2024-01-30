package org.example;

import java.sql.*;
import java.util.LinkedList;

public class LibroRepository {
    public Connection c = crearConexion();
    public Statement s = null;
    public LinkedList<Libro> libros = new LinkedList<>();

    public Connection crearConexion()  {
        String db = "biblioteca";
        String host = "localhost";
        String port = "3306";
        String urlConnection = "jdbc:mysql://"+host+":"+port+"/"+db;
        String user = "root";
        String pwd = "infobbdd";
        try{
            return DriverManager.getConnection(urlConnection, user, pwd);
        }catch (Exception e) {
            return null;
        }
    }

    public void cerrarConexion(){
        try {
            if (s!=null)
                s.close();
            if (c!=null)
                c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Libro> leerDatos(){
        libros = new LinkedList<>();
        String consulta = "SELECT * FROM libros";
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery(consulta);
            while (rs.next()) {
                int id = rs.getInt(1);
                String titulo = rs.getString(2);
                String autor = rs.getString(3);
                String genero = rs.getString(4);
                Boolean prestado = rs.getBoolean(5);
                libros.add(new Libro(id,titulo,autor,genero,prestado));
            }
            rs.close();
            return libros;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void crearLibro(String titulo, String autor, String genero){
        String query = "INSERT INTO libros (titulo, autor, genero, prestado) VALUES (?, ?, ?, false)";
        try  {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.setString(3, genero);
            ps.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void actualizarLibro(int id , String titulo, String autor, String genero){
        String query = "UPDATE libros SET titulo = ?, autor = ?, genero = ? where id = ?";
        try {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.setString(3, genero);
            ps.setInt(4, id);
            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void eliminarLibro(int id){
        String query = "DELETE FROM libros WHERE id = ?";
        try {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}




