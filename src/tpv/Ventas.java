/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Josito
 */
public class Ventas {
    private Connection conection;
    private Statement sentencia;
    
    public Ventas(){
    
    }
    
    /**
     * @see Procedimiento para realizar la conexión con la base de datos
     */
    private void conexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Cargamos los drivers de la conexión
            //Conexion con la base de datos, pasando la ruta, el Usuario y la contraseña
            conection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/proyectotpv", "root", "");
            sentencia = conection.createStatement();
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar el driver" ,"ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Debes abrir el servidor en XAMPP" ,"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * @see Procedimiento para realizar la desconexión con la base de datos
     */
    private void desconexion(){
        try {
            sentencia.close();
            conection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * @see Agregar una venta a la DB
     */
    public void agregarVenta(Map <Integer,Integer> productos, String correoUsuario){
        try{
            int codigoVenta = this.generateCode();
            this.conexion();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            for (int codProd : productos.keySet()) {
                
                sentencia.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
                
                String sqlVenta = String.format("INSERT INTO ventas (codigoVenta, productos, ventasProductos, fechaVenta, correoEmpleado) VALUES (%1$d,%2$d,%3$d,'"+dateFormat.format(date)+"', '%4$s')",
                        codigoVenta,
                        codProd,
                        productos.get(codProd),
                        correoUsuario);
                sentencia.executeUpdate(sqlVenta);
                
                String sqlEditarStockAlmacen =  String.format("UPDATE almacen SET stock = stock - %1$d WHERE codigoProducto = %2$d",
                        productos.get(codProd),
                        codProd);
                
                sentencia.executeUpdate(sqlEditarStockAlmacen);
                
                sentencia.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
                
            }
            
            this.desconexion();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @see Generar un codigo unico para nuestra venta.
     */
    public int generateCode() throws SQLException{
        this.conexion();
        int code = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        try{
            String sql = "SELECT * FROM ventas";
            ResultSet rs = sentencia.executeQuery(sql);
            ArrayList codigosVentas = new ArrayList();
            
            while(rs.next()){
                codigosVentas.add(rs.getInt("codigoVenta"));
            }
            
            for(int i = 0; i<codigosVentas.size(); i++){
                if(codigosVentas.get(i).equals(code)){
                    code = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
                    i=-1;
                } 
            }
        } catch (SQLException e) {
            System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
            System.out.printf("Mensaje : %s %n", e.getMessage());
            System.out.printf("SQL estado: %s %n", e.getSQLState());
            System.out.printf("Cód error : %s %n", e.getErrorCode());
        }
        this.desconexion();
        return code;
    }
}
