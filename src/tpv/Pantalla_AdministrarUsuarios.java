package tpv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Pantalla_AdministrarUsuarios extends javax.swing.JFrame {

    private gestionPantallas gp;
    private button btn;
    
    ArrayList empleadosParaEliminar = new ArrayList();
    boolean buscadorVisible = false;
    Usuario usuario = new Usuario();
    ArrayList <Empleado> empleados = usuario.leerEmpleados();
    Empleado empleadoLogeado;
    
    private JButton addEmpleado;
    private JButton remvEmpleado;
    private JButton addAdmin;
    private JButton remvAdmin;
    private JButton buscarEmp;
    private JButton volver;
    
    public Pantalla_AdministrarUsuarios() {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        btn = new button();
        txtBuscador.setVisible(buscadorVisible);
        this.cargarEmpleados();
        this.pintaBotones();
    }
    
    public Pantalla_AdministrarUsuarios(Empleado emp) {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        btn = new button();
        txtBuscador.setVisible(buscadorVisible);
        this.cargarEmpleados();
        this.empleadoLogeado = emp;
        this.pintaBotones();
    }
    
    private void pintaBotones(){
        
        addEmpleado = btn.creaButton("src/assets/add-empleado.png","<html><body>AÑADIR <br>EMPLEADO</body></html>",19);
        
        panelMenuSelecProd.add(addEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, 110));
        addEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpleado(evt);
            }
        });
        
        remvEmpleado = btn.creaButton("src/assets/remove-empleado.png","<html><body>ELIMINAR <br>EMPLEADO</body></html>",19);
        
        panelMenuSelecProd.add(remvEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 120, 110));
        remvEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEmpleado(evt);
            }
        });
        
        addAdmin = btn.creaButton("src/assets/add-admin.png","<html><body>AÑADIR <br>ADMIN</body></html>",19);
        
        panelMenuSelecProd.add(addAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 120, 110));
        addAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAdmin(evt);
            }
        });
        
        remvAdmin = btn.creaButton("src/assets/remove-admin.png","<html><body>QUITAR <br>ADMIN</body></html>",19);
        
        panelMenuSelecProd.add(remvAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 120, 110));
        remvAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAdmin(evt);
            }
        });
        
        buscarEmp = btn.creaButton("src/assets/buscar.png","<html><body>BUSCAR <br>EMPLEADO</body></html>",19);
        
        panelMenuSelecProd.add(buscarEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 120, 110));
        buscarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarEmpleado(evt);
            }
        });
        
        volver = btn.creaButton("src/assets/volver.png","VOLVER",19);
        
        panelMenuSelecProd.add(volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 120, 110));
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volver(evt);
            }
        });
        
        JButton informeVendedores = btn.creaButton(null,"VEND.",19);
        
        panelMenuSelecProd.add(informeVendedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 130, 55));
        informeVendedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    Informes informe = new Informes();
                    PantallaInforme pInforme = new PantallaInforme(informe.crearInformeVendedores());
                    pInforme.setVisible(true);
                } catch (SQLException ex) {
                    System.out.println("ERROOOOOR");
                    //Logger.getLogger(Pantalla_AdministrarUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        JButton informeCompras = btn.creaButton(null,"COMPRAS",19);
        
        panelMenuSelecProd.add(informeCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 65, 130, 55));
        informeCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Pantalla_Seleccion_Cantidad_Compras().setVisible(true);
            }
        });
    }

    /**
     * @see Metodo para cargar los productos que tenemos en nuestra base de datos.
     */
    public void cargarEmpleados(){
        //Primero eliminamos todos los productos que tenemos para eliminar de nuestro
        //array de productosParaEliminar.
        empleadosParaEliminar.clear();
        
        //Eliminamos todos los componentes de nuestro panel de productos, por si acaso tenemos alguno
        panelProductos_Administrador.removeAll();
        
        
        
        int posX = 10;
        int posY = 10;
        Color colorBorde = new Color(253,126,14);
        
        for (int i = 0; i < empleados.size(); i++) {
            
            //Para dar un salto a la siguiente linea al mostrar nuestros productos.
            if(posX >= 870){
                posY += 170;
                posX = 10;
            }
            
            Empleado emp = empleados.get(i);
            String esAdmin = emp.getEsAdmin() ? "Si" : "No";
            //Por cada producto vamos a crear un Label para añadirlo al panel.
            JLabel datosEmpleado = new JLabel("<HTML><b>Nombre:</b> "+emp.getNombre() +" "+ emp.getApellidos()+" <br> <b>Administrado:</b> "+ esAdmin +"</HTML>");
            datosEmpleado.setForeground(Color.white);
            datosEmpleado.setFont(new Font("Calibri", Font.ITALIC, 15));
            //Le seteamos de nombre el codigo de producto para que sea unico y no de fallos.
            datosEmpleado.setName(String.valueOf(empleados.get(i).getCorreo()));
            datosEmpleado.setBorder(new EmptyBorder(0,5,0,0));
            //Mouse listener para cuando cliquemos.
            datosEmpleado.addMouseListener(new MouseAdapter() {
                boolean estaSeleccionado = false;
                public void mouseClicked(MouseEvent e){
                    //si no ha sido clickado le ponemos un borde y lo añadimos al array de eliminar producto
                    if(!estaSeleccionado){
                        Border border = BorderFactory.createLineBorder(colorBorde, 5);
                        datosEmpleado.setBorder(border);
                        empleadosParaEliminar.add(datosEmpleado.getName());
                        estaSeleccionado = true;
                    }else{
                        //Si ya habiamos clickado, le quitamos el borde y eliminamos ese producto de nuestro producto
                        //Como el nombre es un Integer, obtenemos el nombre, lo convertimos en numero y buscamos
                        //la posicion de ese indice para borrar esa posicion del array.
                        datosEmpleado.setBorder(new EmptyBorder(0,5,0,0));
                        empleadosParaEliminar.remove(empleadosParaEliminar.indexOf(datosEmpleado.getName()));
                        estaSeleccionado = false;
                    }
                }
            });
            
            //agregamos la label a nuestro panel.
            panelProductos_Administrador.add(datosEmpleado,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY, 150, 150));
            posX += 170;
            //870 limite en X
            
            
        }
        //revalidamos y repintamos el panel.
        panelProductos_Administrador.revalidate();
        panelProductos_Administrador.repaint();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscador = new javax.swing.JTextField();
        panelMenuSelecProd = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelProductos_Administrador = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 769));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscador.setBackground(new java.awt.Color(255, 179, 71));
        txtBuscador.setFont(new java.awt.Font("Monotype Corsiva", 0, 17)); // NOI18N
        txtBuscador.setForeground(new java.awt.Color(51, 51, 51));
        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 120, 40));

        panelMenuSelecProd.setBackground(new java.awt.Color(30, 30, 30));
        panelMenuSelecProd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panelMenuSelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 130));

        jScrollPane1.setBorder(null);

        panelProductos_Administrador.setBackground(new java.awt.Color(30, 30, 30));
        panelProductos_Administrador.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane1.setViewportView(panelProductos_Administrador);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1030, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void addEmpleado(java.awt.event.ActionEvent evt){
        gp.creaRegistro(empleadoLogeado);
        dispose();
    }
    private void removeEmpleado(java.awt.event.ActionEvent evt){
        Usuario us = new Usuario();
        for (int i = 0; i < empleadosParaEliminar.size(); i++) {
            us.borrarEmpleado((String) empleadosParaEliminar.get(i));
        }
        empleados = us.leerEmpleados();
        this.cargarEmpleados();
    }
    private void addAdmin(java.awt.event.ActionEvent evt){
        Usuario us = new Usuario();
        for (int i = 0; i < empleadosParaEliminar.size(); i++) {
            us.anadirAdmin((String) empleadosParaEliminar.get(i));
        }
        empleados = usuario.leerEmpleados();
        empleados = us.leerEmpleados();
        this.cargarEmpleados();
    }
    
    private void removeAdmin(java.awt.event.ActionEvent evt){
        Usuario us = new Usuario();
        for (int i = 0; i < empleadosParaEliminar.size(); i++) {
            us.eliminarAdmin((String) empleadosParaEliminar.get(i));
        }
        empleados = usuario.leerEmpleados();
        empleados = us.leerEmpleados();
        this.cargarEmpleados();
    }
    
    private void buscarEmpleado(java.awt.event.ActionEvent evt){
        buscadorVisible = !buscadorVisible;
        if(buscadorVisible) buscarEmp.setSize(new Dimension(120,70));
        else buscarEmp.setSize(new Dimension(120,110));
        txtBuscador.setVisible(buscadorVisible);
    }
    private void volver(java.awt.event.ActionEvent evt){
        gp.creaAdministrador(empleadoLogeado);
        dispose();
    }
    
    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscadorActionPerformed

    private void txtBuscadorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyTyped
        
    }//GEN-LAST:event_txtBuscadorKeyTyped

    private void txtBuscadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyPressed
        
    }//GEN-LAST:event_txtBuscadorKeyPressed

    private void txtBuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyReleased
        if(txtBuscador.getText().isEmpty()){
            empleados = usuario.leerEmpleados();
        }else{
            empleados = usuario.leerEmpleados(txtBuscador.getText());
        }
        this.cargarEmpleados();
    }//GEN-LAST:event_txtBuscadorKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla_AdministrarUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_AdministrarUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_AdministrarUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_AdministrarUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_AdministrarUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelMenuSelecProd;
    private javax.swing.JPanel panelProductos_Administrador;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
