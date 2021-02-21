package tpv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Pantalla_Carrito extends javax.swing.JFrame {

    private gestionPantallas gp;
    private button btn;
    
    Empleado empleadoLogeado;
    HashMap<Integer, Integer> productosCarrito;
    ArrayList productosCarritoArray;
    Ventas venta = new Ventas();
    
    private JButton volver;
    private JButton tarjeta;
    private JButton efectivo;
    private JButton informeVentas;
    
    
    public Pantalla_Carrito() {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        btn = new button();
        this.pintaBotones();
    }
    
    public Pantalla_Carrito(Empleado emp, HashMap productosCarrito) {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        btn = new button();
        this.empleadoLogeado = emp;
        this.productosCarrito = productosCarrito;
        this.cargaProducto();
        this.pintaBotones();
    }
    
    private void pintaBotones(){
        
        volver = btn.creaButton("src/assets/volver.png","ATRAS",19);
        
        panelMenuSelecProd.add(volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, 110));
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volver(evt);
            }
        });
        
        tarjeta = btn.creaButton("src/assets/tarjeta-de-credito.png","TARJETA",19);
        
        panelMenuSelecProd.add(tarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 120, 110));
        tarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagarTarjeta(evt);
            }
        });
        
        efectivo = btn.creaButton("src/assets/efectivo.png","EFECTIVO",19);
        
        panelMenuSelecProd.add(efectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 120, 110));
        efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagarEfectivo(evt);
            }
        });
        
        informeVentas = btn.creaButton(null,"<html><body>INFORME <br>VENTAS</body></html>",19);
        panelMenuSelecProd.add(informeVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 120, 110));
        informeVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Pantalla_Seleccion_Fecha_Informe().setVisible(true);
            }
        });
    }

    public void cargaProducto(){
        
        panelProductos_Carrito.removeAll();
        Almacen almacen = new Almacen();
        ArrayList <Producto> productos = almacen.mostrarProducto(productosCarrito);
        int posX = 10;
        int posY = 10;
        double precioTotalFinal = 0;
        int articulosTotales = 0;
        
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
            JLabel imgProducto = new JLabel(newImage);
            //Le seteamos de nombre el codigo de producto para que sea unico y no de fallos.
            imgProducto.setName(String.valueOf(productos.get(i).getCodigoProducto()));
            
            JLabel datosProducto = new JLabel("<HTML><b>Nombre:</b> "+ String.valueOf(productos.get(i).getNombre()) 
                    +" <br> <b>Codigo:</b> "+String.valueOf(productos.get(i).getCodigoProducto()) +" <br> <b>Precio:</b> "
                    +String.valueOf(productos.get(i).getPrecio())+" <br>"
                    + " <b>Cantidad:</b> "+String.valueOf(productosCarrito.get(productos.get(i).getCodigoProducto()))+" </HTML>");
            datosProducto.setForeground(Color.white);
            datosProducto.setFont(new Font("Calibri", Font.ITALIC, 15));

            
            //Mouse listener para cuando cliquemos.
            imgProducto.addMouseListener(new MouseAdapter() {
                boolean estaSeleccionado = false;
                public void mouseClicked(MouseEvent e){
                    if(e.getClickCount() == 2){
                        int cantidad = productosCarrito.get(Integer.parseInt(imgProducto.getName()));
                        int key = Integer.parseInt(imgProducto.getName());
                        productosCarrito.put(key, cantidad-1);
                        if(productosCarrito.get(key) == 0) productosCarrito.remove(key);
                        
                        cargaProducto();
                        System.out.println("Hashmap "+ productosCarrito );
                        System.out.println("Llave: " + key + " Cantidad: "+ cantidad);
                        
                    }
                    
                }
            });
            
            //agregamos la label a nuestro panel.
            panelProductos_Carrito.add(imgProducto,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY, 150, 150));
            panelProductos_Carrito.add(datosProducto,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY+150, 150, 90));
            posX += 170;
            //870 limite en X
            
            precioTotalFinal += productos.get(i).getPrecio() * Integer.parseInt(datosProducto.getText().split(" ")[datosProducto.getText().split(" ").length - 2]);
            articulosTotales += Integer.parseInt(datosProducto.getText().split(" ")[datosProducto.getText().split(" ").length - 2]);
            
            
        }
        lblPrecio_Carrito.setText(String.valueOf(precioTotalFinal) + "€");
        lblCantidadArticulos.setText(String.valueOf(articulosTotales));
        //revalidamos y repintamos el panel.
        panelProductos_Carrito.revalidate();
        panelProductos_Carrito.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelProductos_Carrito = new javax.swing.JPanel();
        panelMenuSelecProd = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblPrecio_Carrito = new javax.swing.JLabel();
        lblCantidadArticulos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);

        panelProductos_Carrito.setBackground(new java.awt.Color(30, 30, 30));
        panelProductos_Carrito.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane1.setViewportView(panelProductos_Carrito);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1040, 540));

        panelMenuSelecProd.setBackground(new java.awt.Color(30, 30, 30));
        panelMenuSelecProd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panelMenuSelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 130));

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Monotype Corsiva", 2, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PRECIO:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 16, 80, 70));

        jLabel2.setFont(new java.awt.Font("Monotype Corsiva", 2, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("<html><body>TOTAL DE <br>ARTICULOS:</body></html>");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 110, 70));

        lblPrecio_Carrito.setFont(new java.awt.Font("Monotype Corsiva", 2, 24)); // NOI18N
        lblPrecio_Carrito.setForeground(new java.awt.Color(255, 255, 255));
        lblPrecio_Carrito.setText("99999 €");
        jPanel1.add(lblPrecio_Carrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 570, 70));

        lblCantidadArticulos.setFont(new java.awt.Font("Monotype Corsiva", 2, 24)); // NOI18N
        lblCantidadArticulos.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadArticulos.setText("99999");
        jPanel1.add(lblCantidadArticulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 100, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 670, 1030, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void volver(java.awt.event.ActionEvent evt){
        gp.creaSeleccionProducto(empleadoLogeado, productosCarrito);
        dispose();;
    }
    
    private void pagarTarjeta(java.awt.event.ActionEvent evt){
        if(!productosCarrito.isEmpty()){
            venta.agregarVenta(productosCarrito, empleadoLogeado.getCorreo());
            productosCarrito.clear();
            gp.creaSeleccionProducto(empleadoLogeado, productosCarrito);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Tu carrito está vacío." ,"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void pagarEfectivo(java.awt.event.ActionEvent evt){
        if(!productosCarrito.isEmpty()){
            venta.agregarVenta(productosCarrito, empleadoLogeado.getCorreo());
            productosCarrito.clear();
            gp.creaSeleccionProducto(empleadoLogeado, productosCarrito);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Tu carrito está vacío." ,"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarInforme(java.awt.event.ActionEvent evt){
        
        gp.creaSeleccionProducto(empleadoLogeado, productosCarrito);
        dispose();
        
    }
    

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
            java.util.logging.Logger.getLogger(Pantalla_Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_Carrito().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidadArticulos;
    private javax.swing.JLabel lblPrecio_Carrito;
    private javax.swing.JPanel panelMenuSelecProd;
    private javax.swing.JPanel panelProductos_Carrito;
    // End of variables declaration//GEN-END:variables
}
