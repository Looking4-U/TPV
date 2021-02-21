package tpv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Pantalla_Seleccion_Producto extends javax.swing.JFrame {

    private gestionPantallas gp;
    private button btn;

    private Usuario us = new Usuario();
    Empleado empleadoLogeado = new Empleado();
    Almacen almc = new Almacen();
    HashMap<Integer, Integer> productosCarrito = new HashMap();
    ArrayList <Producto> productos = almc.mostrarProducto();
    
    
    private JButton carrito;
    private JButton categoria;
    private JButton modoAdmin;
    private JButton salir;
    
    
    public Pantalla_Seleccion_Producto() {
        initComponents();
        this.setLocationRelativeTo(null);
        panelCategorias.setVisible(false);
        gp = new gestionPantallas();
        btn = new button();
        this.pintaBotones();
        this.cargarProductos();
    }
    
        public Pantalla_Seleccion_Producto(Empleado emp) {
        initComponents();
        this.setLocationRelativeTo(null);
        panelCategorias.setVisible(false);
        gp = new gestionPantallas();
        btn = new button();
        this.empleadoLogeado=emp; 
        System.out.println(empleadoLogeado.getCorreo());
        this.cargarProductos();
        this.pintaBotones();
    }
    
    public Pantalla_Seleccion_Producto(Empleado emp, HashMap productosCarrito) {
        initComponents();
        this.setLocationRelativeTo(null);
        panelCategorias.setVisible(false);
        gp = new gestionPantallas();
        btn = new button();
        this.empleadoLogeado=emp; 
        System.out.println(empleadoLogeado.getCorreo());
        this.productosCarrito = productosCarrito;
        this.cargarProductos();
        this.pintaBotones();
        
    }
    
    private void pintaBotones(){
        carrito = btn.creaButton("src/assets/carro-de-la-compra.png","CARRITO",19);
               
        panelMenuSelecProd.add(carrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, 110));
        carrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carrito(evt);
            }
        });
        
        categoria = btn.creaButton("src/assets/lista.png","CATEGORIA",17);
        
        panelMenuSelecProd.add(categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 120, 110));
        categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoria(evt);
            }
        });
        
        modoAdmin = btn.creaButton("src/assets/modo-admin.png","<html><body>MODO <br>ADMIN</body></html>",17);
        
        panelMenuSelecProd.add(modoAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 120, 110));
        modoAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modoAdmin(evt);
            }
        });
        
        salir = btn.creaButton("src/assets/salir.png","SALIR",19);
        
        panelMenuSelecProd.add(salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 120, 110));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salir(evt);
            }
        });
        
    }
    
    public void cargarProductos(){
        
        //Eliminamos todos los componentes de nuestro panel de productos, por si acaso tenemos alguno
        panelProductos_SelecProd.removeAll();
        
        int posX = 10;
        int posY = 10;
        
        
        for (int i = 0; i < productos.size(); i++) {
            
            //Para dar un salto a la siguiente linea al mostrar nuestros productos.
            if(posX >= 870){
                posY += 250;
                posX = 10;
            }
            
            //Obtenemos las imagenes de nuestros productos
            ImageIcon imgen = productos.get(i).getImagen();

            //Para reescalar la imagen al tamaño del label
            Image img = imgen.getImage();
            Image myImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon newImage = new ImageIcon(myImg);
            //----------------------------------------------------
            
            //Por cada producto vamos a crear un Label para añadirlo al panel.
            JLabel imgProducto = new JLabel(newImage, SwingConstants.RIGHT);
            imgProducto.setVerticalAlignment(SwingConstants.TOP);
            //Le seteamos de nombre el codigo de producto para que sea unico y no de fallos.
            imgProducto.setName(String.valueOf(productos.get(i).getCodigoProducto()));
            
            Producto producto = productos.get(i);
            
            JLabel datosProducto = new JLabel("<HTML><b>Nombre:</b> "+ String.valueOf(productos.get(i).getNombre()) +
                    " <br> <b>Cantidad:</b> "+ productosCarrito.get(Integer.parseInt(imgProducto.getName())) + 
                    " <br> <b>Stock:</b>"+ producto.getStock() +"</HTML>");
            datosProducto.setForeground(Color.white);
            datosProducto.setFont(new Font("Calibri", Font.ITALIC, 15));
            //Mouse listener para cuando cliquemos.
            imgProducto.addMouseListener(new MouseAdapter() {
                JLabel cantidad = new JLabel("0");
                int cantidadProductos;
                
                public void mouseClicked(MouseEvent e){
                    
                    if(producto.getStock() != 0){
                        producto.setStock(producto.getStock()-1);
                        if (!productosCarrito.containsKey(Integer.parseInt(imgProducto.getName()))) {
                            productosCarrito.put(Integer.parseInt(imgProducto.getName()), 1);
                        }else{
                            cantidadProductos = productosCarrito.get(Integer.parseInt(imgProducto.getName()));
                            productosCarrito.put(Integer.parseInt(imgProducto.getName()), cantidadProductos+1);
                        } 
                        cargarProductos();
                    }else{
                        JOptionPane.showMessageDialog(null,"No quedan mas productos en Stock." ,"ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
            });
            
            
            
            //agregamos la label a nuestro panel.
            panelProductos_SelecProd.add(imgProducto,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY, 150, 150));
            panelProductos_SelecProd.add(datosProducto,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY+150, 150, 90));
            posX += 170;
            //870 limite en X
            
        }
        //revalidamos y repintamos el panel.
        panelProductos_SelecProd.revalidate();
        panelProductos_SelecProd.repaint();
        
    }
    
 
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCategorias = new javax.swing.JPanel();
        btnCategoriaS_SelecProd = new javax.swing.JButton();
        btnCategoriaH_SelecProd = new javax.swing.JButton();
        btnCategoriaM_SelecProd = new javax.swing.JButton();
        btnCategoriaU_SelecProd = new javax.swing.JButton();
        btnCategoriaP_SelecProd = new javax.swing.JButton();
        btnCategoriaA_SelecProd = new javax.swing.JButton();
        btnCategoriaTodo_SelecProd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelProductos_SelecProd = new javax.swing.JPanel();
        panelMenuSelecProd = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 769));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCategorias.setBackground(new java.awt.Color(30, 30, 30));
        panelCategorias.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(0, 0, 0)));
        panelCategorias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCategoriaS_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaS_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 20)); // NOI18N
        btnCategoriaS_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaS_SelecProd.setText("STOCK");
        btnCategoriaS_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaS_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaS_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 110, 110));

        btnCategoriaH_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaH_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 19)); // NOI18N
        btnCategoriaH_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaH_SelecProd.setText("HOMBRE");
        btnCategoriaH_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaH_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaH_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 110, 110));

        btnCategoriaM_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaM_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 20)); // NOI18N
        btnCategoriaM_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaM_SelecProd.setText("MUJER");
        btnCategoriaM_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaM_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaM_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 110, 110));

        btnCategoriaU_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaU_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 20)); // NOI18N
        btnCategoriaU_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaU_SelecProd.setText("UNISEX");
        btnCategoriaU_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaU_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaU_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 110, 110));

        btnCategoriaP_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaP_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 20)); // NOI18N
        btnCategoriaP_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaP_SelecProd.setText("PRECIO");
        btnCategoriaP_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaP_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaP_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 110, 110));

        btnCategoriaA_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaA_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 19)); // NOI18N
        btnCategoriaA_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaA_SelecProd.setText("NOMBRE");
        btnCategoriaA_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaA_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaA_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 110, 110));

        btnCategoriaTodo_SelecProd.setBackground(new java.awt.Color(255, 179, 71));
        btnCategoriaTodo_SelecProd.setFont(new java.awt.Font("Monotype Corsiva", 3, 17)); // NOI18N
        btnCategoriaTodo_SelecProd.setForeground(new java.awt.Color(51, 51, 51));
        btnCategoriaTodo_SelecProd.setText("VER TODO");
        btnCategoriaTodo_SelecProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaTodo_SelecProdActionPerformed(evt);
            }
        });
        panelCategorias.add(btnCategoriaTodo_SelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 110, 110));

        getContentPane().add(panelCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 220, 690, 380));

        jScrollPane1.setBorder(null);

        panelProductos_SelecProd.setBackground(new java.awt.Color(30, 30, 30));
        panelProductos_SelecProd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane1.setViewportView(panelProductos_SelecProd);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1030, 640));

        panelMenuSelecProd.setBackground(new java.awt.Color(30, 30, 30));
        panelMenuSelecProd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panelMenuSelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    

    private void carrito(java.awt.event.ActionEvent evt){
        System.out.println("Carrito creado por mi");
        gp.creaCarrito(empleadoLogeado, productosCarrito);
        dispose();
    }
    
    private void categoria(java.awt.event.ActionEvent evt){
        panelCategorias.setVisible(true);
    }
    
    private void modoAdmin(java.awt.event.ActionEvent evt){
        if (us.comprobarAdmin(empleadoLogeado)) {
            gp.creaAdministrador(empleadoLogeado);
            dispose();
        } else {
            System.out.println("Debes de ser admin");
        }
    }
    
    private void salir(java.awt.event.ActionEvent evt){
        gp.creaLogin();
        dispose();
    }
    
        
    private void btnCategoriaS_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaS_SelecProdActionPerformed
        // TODO add your handling code here:
        productos = almc.mostrarProducto("stock");
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaS_SelecProdActionPerformed

    private void btnCategoriaM_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaM_SelecProdActionPerformed
        // TODO add your handling code here:
        productos = almc.mostrarProductoGenero("m");
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaM_SelecProdActionPerformed

    private void btnCategoriaU_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaU_SelecProdActionPerformed
        // TODO add your handling code here:
        productos = almc.mostrarProductoGenero("u");
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaU_SelecProdActionPerformed

    private void btnCategoriaP_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaP_SelecProdActionPerformed
        // TODO add your handling code here:
        productos = almc.mostrarProducto("precio");
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaP_SelecProdActionPerformed

    private void btnCategoriaA_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaA_SelecProdActionPerformed
        // TODO add your handling code here:
        productos = almc.mostrarProducto("nombre");
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaA_SelecProdActionPerformed

    private void btnCategoriaH_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaH_SelecProdActionPerformed
        // TODO add your handling code here:
        productos = almc.mostrarProductoGenero("h");
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaH_SelecProdActionPerformed

    private void btnCategoriaTodo_SelecProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaTodo_SelecProdActionPerformed
        productos = almc.mostrarProducto();
        this.cargarProductos();
        panelCategorias.setVisible(false);
    }//GEN-LAST:event_btnCategoriaTodo_SelecProdActionPerformed


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
            java.util.logging.Logger.getLogger(Pantalla_Seleccion_Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Seleccion_Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Seleccion_Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Seleccion_Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new Pantalla_Seleccion_Producto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategoriaA_SelecProd;
    private javax.swing.JButton btnCategoriaH_SelecProd;
    private javax.swing.JButton btnCategoriaM_SelecProd;
    private javax.swing.JButton btnCategoriaP_SelecProd;
    private javax.swing.JButton btnCategoriaS_SelecProd;
    private javax.swing.JButton btnCategoriaTodo_SelecProd;
    private javax.swing.JButton btnCategoriaU_SelecProd;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCategorias;
    private javax.swing.JPanel panelMenuSelecProd;
    private javax.swing.JPanel panelProductos_SelecProd;
    // End of variables declaration//GEN-END:variables
}
