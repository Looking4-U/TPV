/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jose
 */
public class Informes {
    public Informes(){
    
    }
    
    public JRViewer crearInformeVentas(String fecha, String fecha2) throws SQLException{
        //Impresión del informe
        JasperPrint jp = null;
        //El visor del informe
        JRViewer viewer;
        //Ruta de la plantilla del reporte
        String ficheroReporte = "reports\\InformeVentas.jrxml";
        //Parametros Map
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        //conexión con la BD
        Connection conexion;
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Establecemos conexion con la BD
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyectotpv?serverTimezone=UTC", "root", "");
            //Compilamos el fichero de reporte
            JasperReport reporteCompilado = JasperCompileManager.compileReport(ficheroReporte);
            //Establecemos los parametros del reporte
            
            parameterMap.put("fechaIngresada1", fecha);
            parameterMap.put("fechaIngresada2", fecha2);
            
            //Rellenamos el reporte con los parametros,conexion and the stream reader
            jp = JasperFillManager.fillReport(reporteCompilado, parameterMap,conexion);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            System.out.println(ex);
        } 
        viewer = new JRViewer(jp);
        return viewer;
    }
    
    public JRViewer crearInformeVendedores() throws SQLException{
        //Impresión del informe
        JasperPrint jp = null;
        //El visor del informe
        JRViewer viewer;
        //Ruta de la plantilla del reporte
        String ficheroReporte = "reports\\InformeVendedores.jrxml";
        
        //conexión con la BD
        Connection conexion;
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Establecemos conexion con la BD
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyectoTPV?serverTimezone=UTC", "root", "");
            //Compilamos el fichero de reporte
            JasperReport reporteCompilado = JasperCompileManager.compileReport(ficheroReporte);
            
            //Rellenamos el reporte con los parametros,conexion and the stream reader
            jp = JasperFillManager.fillReport(reporteCompilado,null,conexion);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            System.out.println(ex);
        }
        viewer = new JRViewer(jp);
        return viewer;
    }
    
    public JRViewer crearInformeCompras(int stockMin) throws SQLException{
        //Impresión del informe
        JasperPrint jp = null;
        //El visor del informe
        JRViewer viewer;
        //Ruta de la plantilla del reporte
        String ficheroReporte = "reports\\InformeCompras.jrxml";
        //Parametros Map
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        //conexión con la BD
        Connection conexion;
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Establecemos conexion con la BD
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyectoTPV?serverTimezone=UTC", "root", "");
            //Compilamos el fichero de reporte
            JasperReport reporteCompilado = JasperCompileManager.compileReport(ficheroReporte);
            //Establecemos los parametros del reporte
            
            parameterMap.put("stockIngresado", stockMin);
            
            //Rellenamos el reporte con los parametros,conexion and the stream reader
            jp = JasperFillManager.fillReport(reporteCompilado, parameterMap,conexion);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            System.out.println(ex);
        }
        viewer = new JRViewer(jp);
        return viewer;
    }
}
