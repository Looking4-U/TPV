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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*; 
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Josito
 */
public class Almacen {
    private Connection conection;
    private Statement sentencia;
    
    public Almacen(){
    
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
     * @see Agregar un producto a la DB
     */
    public boolean insertarProducto(Producto prod){
        try {
            this.conexion();
            
            //Comprobamos si el producto existe
            boolean existeElProducto = false;
            
            String recibirTodosProductos = "SELECT * FROM almacen";
            ResultSet rs = sentencia.executeQuery(recibirTodosProductos);
            
            while(rs.next()) {
                if(rs.getInt("codigoProducto") == prod.getCodigoProducto()){
                    existeElProducto = true;
                }
            }
            
            if(!existeElProducto){
                
                PreparedStatement ps = conection.prepareStatement("INSERT INTO almacen (codigoProducto,precio,stock,nombre,descripcion,imagen,genero) VALUES (?,?,?,?,?,?,?)");
                ps.setInt(1, prod.getCodigoProducto());
                ps.setDouble(2, prod.getPrecio());
                ps.setInt(3, prod.getStock());
                ps.setString(4, prod.getNombre());
                ps.setString(5, prod.getDescripcion());
                ps.setBinaryStream(6, prod.getImagenFile());
                ps.setString(7, prod.getGenero());
                ps.executeUpdate();
                this.desconexion();
                return true;
            }
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * @see Editar un producto de la DB
     */
    public boolean editarProducto(Producto prod){
        try{
            this.conexion();
            //Comprobamos si el producto existe
            boolean existeElProducto = false;
            
            String recibirTodosProductos = "SELECT * FROM almacen";
            ResultSet rs = sentencia.executeQuery(recibirTodosProductos);
            
            while(rs.next()) {
                if(rs.getInt("codigoProducto") == prod.getCodigoProducto()){
                    existeElProducto = true;
                }
            }
            
            if(existeElProducto){
                
                PreparedStatement ps = conection.prepareStatement("UPDATE almacen SET precio = ?, stock = ?, nombre = ?, descripcion = ?, imagen = ?, genero = ? WHERE codigoProducto = ?");
            
                ps.setDouble(1, prod.getPrecio());
                ps.setInt(2, prod.getStock());
                ps.setString(3, prod.getNombre());
                ps.setString(4, prod.getDescripcion());
                ps.setBinaryStream(5, prod.getImagenFile());
                ps.setString(6, prod.getGenero());
                ps.setInt(7,prod.getCodigoProducto());
                ps.executeUpdate();
                return true;
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * @see Eliminar un producto a la DB
     */
    public void eliminarProducto(int codProducto){
        try{
            this.conexion();
            sentencia.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            String eliminarProd = "DELETE FROM almacen WHERE codigoProducto = " + codProducto;
            sentencia.executeUpdate(eliminarProd);
            sentencia.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            this.desconexion();
            
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @see Obtener todos los productos de la DB
     */
    public ArrayList<Producto> mostrarProducto(){
        try {
            this.conexion();
            
            String recibirTodosProductos = "SELECT * FROM almacen";
            ResultSet rs = sentencia.executeQuery(recibirTodosProductos);
            ArrayList<Producto> productos = new ArrayList();
            
            while(rs.next()) {
                Producto prod = new Producto();
                byte[] blob = rs.getBytes("imagen");
                ImageIcon imageIcon = new ImageIcon(blob);
                
                prod.setCodigoProducto(rs.getInt("codigoProducto"));
                prod.setPrecio(rs.getInt("precio"));
                prod.setStock(rs.getInt("stock"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setImagen(imageIcon);
                prod.setGenero(rs.getString("genero"));
                
                productos.add(prod);
            }
            
            this.desconexion();
            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Producto> mostrarProducto(String order){
        try {
            this.conexion();
            
            String recibirTodosProductos = "SELECT * FROM almacen ORDER BY " + order + " ASC";
            ResultSet rs = sentencia.executeQuery(recibirTodosProductos);
            ArrayList<Producto> productos = new ArrayList();
            
            while(rs.next()) {
                Producto prod = new Producto();
                byte[] blob = rs.getBytes("imagen");
                ImageIcon imageIcon = new ImageIcon(blob);
                
                prod.setCodigoProducto(rs.getInt("codigoProducto"));
                prod.setPrecio(rs.getInt("precio"));
                prod.setStock(rs.getInt("stock"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setImagen(imageIcon);
                prod.setGenero(rs.getString("genero"));
                
                productos.add(prod);
            }
            
            this.desconexion();
            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Producto> mostrarProductoGenero(String genero){
        try {
            this.conexion();
            
            String recibirTodosProductos = "SELECT * FROM almacen WHERE genero = '" + genero +"'";
            ResultSet rs = sentencia.executeQuery(recibirTodosProductos);
            ArrayList<Producto> productos = new ArrayList();
            
            while(rs.next()) {
                Producto prod = new Producto();
                byte[] blob = rs.getBytes("imagen");
                ImageIcon imageIcon = new ImageIcon(blob);
                
                prod.setCodigoProducto(rs.getInt("codigoProducto"));
                prod.setPrecio(rs.getInt("precio"));
                prod.setStock(rs.getInt("stock"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setImagen(imageIcon);
                prod.setGenero(rs.getString("genero"));
                
                productos.add(prod);
            }
            
            this.desconexion();
            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    /**
     * @see Obtener X productos de la DB
     */
    public ArrayList mostrarProducto(HashMap<Integer,Integer> carritoProductos){
        try {
            this.conexion();
            ArrayList<Producto> productos = new ArrayList();
            
            for(Integer k : carritoProductos.keySet()){
                String recibirTodosProductos = "SELECT * FROM almacen WHERE codigoProducto = " + k;
                ResultSet rs = sentencia.executeQuery(recibirTodosProductos);
                

                while(rs.next()) {
                    Producto prod = new Producto();
                    byte[] blob = rs.getBytes("imagen");
                    ImageIcon imageIcon = new ImageIcon(blob);

                    prod.setCodigoProducto(rs.getInt("codigoProducto"));
                    prod.setPrecio(rs.getInt("precio"));
                    prod.setStock(rs.getInt("stock"));
                    prod.setNombre(rs.getString("nombre"));
                    prod.setDescripcion(rs.getString("descripcion"));
                    prod.setImagen(imageIcon);
                    prod.setGenero(rs.getString("genero"));

                    productos.add(prod);
                }
            }
            
            
            this.desconexion();
            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
