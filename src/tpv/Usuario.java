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
import javax.swing.JOptionPane;

/**
 *
 * @author Josito
 */
public class Usuario {
    private Connection conexion;
    private Statement sentencia;
    
    public Usuario(){
    
    }
    
    /**
     * @see Procedimiento para realizar la conexión con la base de datos
     */
    private void conexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Cargamos los drivers de la conexión
            //Conexion con la base de datos, pasando la ruta, el Usuario y la contraseña
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1/proyectotpv", "root", "");
            sentencia = conexion.createStatement();
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar el driver" ,"ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Debes abrir el servidor en XAMPP" ,"ERROR", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }
    
    /**
     * @see Procedimiento para realizar la desconexión con la base de datos
     */
    private void desconexion(){
        try {
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("ERROR DESCONEXION");
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * @see Metodo para un registro satisfactorio
     * @return Devolverá verdadero si se puede registrar o falso si no.
     * @param emp Empleado para poder obtener sus datos y ver si se puede registrar.
     */
    public boolean registro(Empleado emp){
        try {
            this.conexion();
            
            //Comprobar si existe ya el usuario
            boolean existeElUsuario = false;
            String todosLosUsuarios = "SELECT * FROM usuarios";
            ResultSet rs = sentencia.executeQuery(todosLosUsuarios);
            while(rs.next()){
                if(rs.getString("correo").equals(emp.getCorreo())){
                    existeElUsuario = true;
                }
            }
            
            if(!existeElUsuario){
                String introducirUsuario = String.format("INSERT INTO USUARIOS (nombre, apellidos, correo, password) VALUES ('%1$s','%2$s','%3$s','%4$s')",
                                                        emp.getNombre(),
                                                        emp.getApellidos(),
                                                        emp.getCorreo(),
                                                        emp.getPassword());
                
                sentencia.executeUpdate(introducirUsuario);
                this.desconexion();
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.desconexion();
        return false;
    }
    
    /**
     * @see Metodo para un login satisfactorio
     * @return Devolverá verdadero si se puede logear o falso si no.
     * @param emp Empleado para poder obtener sus datos y ver si se puede logear.
     */
    public boolean login(Empleado emp){
        try {
            this.conexion();
            
            //Comprobar si existe ya el usuario
            boolean existeElUsuario = false;
            String todosLosUsuarios = "SELECT * FROM usuarios";
            ResultSet rs = sentencia.executeQuery(todosLosUsuarios);
            
            while(rs.next()){
                if(rs.getString("correo").equals(emp.getCorreo()) && rs.getString("password").equals(emp.getPassword())){
                    existeElUsuario = true;
                }
            }
            
            if(existeElUsuario){
                this.desconexion();
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.desconexion();
        return false;
    }
    
    public ArrayList<Empleado> leerEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList();
        
        try {
            this.conexion();
            
            String sql = "SELECT * FROM usuarios";
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {       
                Empleado emp = new Empleado();
                emp.setNombre(rs.getString("nombre"));
                emp.setApellidos(rs.getString("apellidos"));
                emp.setEsAdmin(rs.getBoolean("esAdmin"));
                emp.setCorreo(rs.getString("correo"));
                empleados.add(emp);
            }
            this.desconexion();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }
    
    public boolean borrarEmpleado(String correo){
        try {
            this.conexion();
            
            String sql = "DELETE FROM usuarios WHERE correo = '" + correo + "'";
            sentencia.executeLargeUpdate(sql);
            this.desconexion();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean anadirAdmin(String correo){
        try {
            this.conexion();
            
            String sql = "UPDATE usuarios SET esAdmin=1 WHERE correo = '" + correo + "'";
            sentencia.executeLargeUpdate(sql);
            this.desconexion();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean eliminarAdmin(String correo){
        try {
            this.conexion();
            
            String sql = "UPDATE usuarios SET esAdmin=0 WHERE correo = '" + correo + "'";
            sentencia.executeLargeUpdate(sql);
            this.desconexion();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Empleado> leerEmpleados(String nombre){
        ArrayList<Empleado> empleados = new ArrayList();
        
        try {
            this.conexion();
            
            String sql = "SELECT * FROM usuarios WHERE nombre LIKE '" + nombre +"%'";
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {       
                Empleado emp = new Empleado();
                emp.setNombre(rs.getString("nombre"));
                emp.setApellidos(rs.getString("apellidos"));
                emp.setEsAdmin(rs.getBoolean("esAdmin"));
                emp.setCorreo(rs.getString("correo"));
                empleados.add(emp);
            }
            this.desconexion();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    } 
    
    public boolean comprobarAdmin(Empleado emp){
        boolean esAdmin = false;
        try {
            this.conexion();
            
            String sql = "SELECT * FROM usuarios WHERE correo = '" + emp.getCorreo() + "'";
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {       
                if(rs.getBoolean("esAdmin")){
                    esAdmin = true;
                }
            }
            this.desconexion();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esAdmin;
    }
    
}
