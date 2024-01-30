package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import javafx.fxml.FXML;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class PrimaryController {


    public void initialize() {

    }
    @FXML
    private void switchToSecondary() throws IOException {
        InputStream reportFile = getClass().getResourceAsStream("Techmart.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, crearConexion());
            JasperViewer.viewReport(jasperPrint, false);
//            String pdfFilePath = "D:\\Informatica2\\Desarrollo interfaces\\ProyectoInterfaces\\JFX\\PruebaJasper\\src\\main\\resources";
//            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public Connection crearConexion() {
        String db = "techmart";
        String host = "localhost";
        String port = "3306";
        String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + db;
        String user = "root";
        String pwd = "infobbdd";
        try {
            return DriverManager.getConnection(urlConnection, user, pwd);
        } catch (Exception e) {
            return null;
        }
    }

    }
