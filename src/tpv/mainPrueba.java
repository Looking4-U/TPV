/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Josito
 */
public class mainPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Empleado emp = new Empleado();
//        emp.setNombre("Jose");
//        emp.setApellidos("Rodriguez");
//        emp.setCorreo("joserr0210@gmail.com");
//        emp.setPassword("1234");
//        
//        Usuario us = new Usuario();
//        if(us.registro(emp)){
//            System.out.println("Correcto");
//        }else{
//            System.out.println("ERROR USUARIO EXISTE");
//        }
        
        /*if(us.login(emp)){
            System.out.println("LOGIN CORRECTO");
        }else{
            System.out.println("LOGIN INCORRECTO");
        }*/
        
        Producto pr = new Producto();
        Almacen al = new Almacen();
        
       
        
        pr.setNombre("Diva");
        pr.setDescripcion("Masajeador de clitoris");
        pr.setGenero("M");
        pr.setPrecio(35.99);
        pr.setStock(150);
        pr.setImagenFile("C:\\Users\\thebe\\OneDrive\\Escritorio\\TPV_IMGS\\imgM7.png");
        pr.setCodigoProducto(7);
        
        al.insertarProducto(pr);
        
        
        

        //al.eliminarProducto(2134);
        
//        Map productosVentas = new HashMap();
//        productosVentas.put(1234,26);
//        productosVentas.put(21123,38);
//        
//        Ventas venta = new Ventas();
//        
//        venta.agregarVenta(productosVentas);
    }
    
}
