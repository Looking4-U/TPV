package tpv;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Pantalla_Administrador extends javax.swing.JFrame {

    Producto prod;
    private gestionPantallas gp;
    private button btn;
    
    File fichero;
    ArrayList productosParaEliminar = new ArrayList();
    Border bordePorDefecto;
    
    Empleado empleadoLogeado;
    
    private JButton salir;
    private JButton gestionarProducto;
    private JButton quitarProducto;
    private JButton administrarUsuarios;
    private JButton modoEmpleado;
    
    private MouseAdapter ma;
    
    public Pantalla_Administrador() {
        initComponents();
        this.setLocationRelativeTo(null);
        panelAddEditProd_Administrador.setVisible(false);
        bordePorDefecto = txtAreaDescripProd_panelAdministrarProd.getBorder();
        prod = new Producto();
        gp = new gestionPantallas();
        btn = new button();
        this.cargarProductos();
        this.pintaBotones();
        

    }
    
    public Pantalla_Administrador(Empleado emp) {
        initComponents();
        this.setLocationRelativeTo(null);
        panelAddEditProd_Administrador.setVisible(false);
        bordePorDefecto = txtAreaDescripProd_panelAdministrarProd.getBorder();
        prod = new Producto();
        gp = new gestionPantallas();
        btn = new button();
        this.cargarProductos();
        this.empleadoLogeado=emp;
        this.pintaBotones();

    }
    
    private void pintaBotones(){
        
        gestionarProducto = btn.creaButton("src/assets/gestionar-producto.png","<html><body>GESTIONAR <br>PRODUCTO</body></html>",19);
        
        panelMenuSelecProd.add(gestionarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, 110));
        gestionarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionarProducto(evt);
            }
        });
        
        quitarProducto = btn.creaButton("src/assets/quitar-producto.png","<html><body>QUITAR<br>PRODUCTO</body></html>",19);
        
        panelMenuSelecProd.add(quitarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 120, 110));
        quitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitarProducto(evt);
            }
        });
        
        administrarUsuarios = btn.creaButton("src/assets/admin-usuarios.png","<html><body>ADMINISTRAR<br>USUARIOS</body></html>",15);
        
        panelMenuSelecProd.add(administrarUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 120, 110));
        administrarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administrarUsuarios(evt);
            }
        });
        
        modoEmpleado = btn.creaButton("src/assets/modo-empleado.png","<html><body>MODO<br>EMPLEADO</body></html>",17);
        
        panelMenuSelecProd.add(modoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 120, 110));
        modoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modoEmpleado(evt);
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
    

    /**
     * @see Metodo para cargar los productos que tenemos en nuestra base de datos.
     */
    public void cargarProductos(){
        //Primero eliminamos todos los productos que tenemos para eliminar de nuestro
        //array de productosParaEliminar.
        productosParaEliminar.clear();
        
        //Eliminamos todos los componentes de nuestro panel de productos, por si acaso tenemos alguno
        panelProductos_Administrador.removeAll();
        
        Almacen almacen = new Almacen();
        ArrayList <Producto> productos = almacen.mostrarProducto();
        int posX = 10;
        int posY = 10;
        Color colorBorde = new Color(253,126,14);
        
        
        for (int i = 0; i < productos.size(); i++) {
            
            //Para dar un salto a la siguiente linea al mostrar nuestros productos.
            if(posX >= 870){
                posY += 230;
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
            
            JLabel datosProducto = new JLabel("<HTML>Codigo: "+String.valueOf(productos.get(i).getCodigoProducto()) +" <br> "+ String.valueOf(productos.get(i).getNombre()) +"</HTML>");
            datosProducto.setForeground(Color.white);
            datosProducto.setFont(new Font("Calibri", Font.ITALIC, 15));
            //Mouse listener para cuando cliquemos.
            ma = new MouseAdapter() {
                boolean estaSeleccionado = false;
                public void mouseClicked(MouseEvent e){
                    //si no ha sido clickado le ponemos un borde y lo añadimos al array de eliminar producto
                    if(!estaSeleccionado){
                        Border border = BorderFactory.createLineBorder(colorBorde, 5);
                        imgProducto.setBorder(border);
                        productosParaEliminar.add(Integer.parseInt(imgProducto.getName()));
                        estaSeleccionado = true;
                    }else{
                        //Si ya habiamos clickado, le quitamos el borde y eliminamos ese producto de nuestro producto
                        //Como el nombre es un Integer, obtenemos el nombre, lo convertimos en numero y buscamos
                        //la posicion de ese indice para borrar esa posicion del array.
                        imgProducto.setBorder(null);
                        productosParaEliminar.remove(productosParaEliminar.indexOf(Integer.parseInt(imgProducto.getName())));
                        estaSeleccionado = false;
                    }
                }
            };
            imgProducto.addMouseListener(ma);
            
            //agregamos la label a nuestro panel.
            panelProductos_Administrador.add(imgProducto,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY, 150, 150));
            panelProductos_Administrador.add(datosProducto,new org.netbeans.lib.awtextra.AbsoluteConstraints(posX, posY+150, 150, 70));
            posX += 170;
            //870 limite en X
            
        }
        System.out.println(panelProductos_Administrador.getComponentCount());
        //revalidamos y repintamos el panel.
        panelProductos_Administrador.revalidate();
        panelProductos_Administrador.repaint();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAddEditProd_Administrador = new javax.swing.JPanel();
        mostrarImagenProd = new javax.swing.JLabel();
        txtCodProd_panelAdministrarProd = new javax.swing.JTextField();
        txtNombreProd_panelAdministrarProd = new javax.swing.JTextField();
        txtPrecioProd_panelAdministrarProd = new javax.swing.JTextField();
        txtStockProd_panelAdministrarProd = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaDescripProd_panelAdministrarProd = new javax.swing.JTextArea();
        btnCancelProd_Administrador = new javax.swing.JButton();
        btnAddProd_Administrador = new javax.swing.JButton();
        btnEditProd_Administrador = new javax.swing.JButton();
        lblNombreProd_panelAdministrarProd = new javax.swing.JLabel();
        lblPrecProd_panelAdministrarProd = new javax.swing.JLabel();
        lblStockProd_panelAdministrarProd = new javax.swing.JLabel();
        lblCategProd_panelAdministrarProd = new javax.swing.JLabel();
        lblCodProd_panelAdministrarProd = new javax.swing.JLabel();
        lblDescripProd_panelAdministrarProd = new javax.swing.JLabel();
        btnAgregarImagen = new javax.swing.JButton();
        comboBoxCategoria = new javax.swing.JComboBox<>();
        mostrarTodosProductos = new javax.swing.JScrollPane();
        panelProductos_Administrador = new javax.swing.JPanel();
        panelMenuSelecProd = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 769));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelAddEditProd_Administrador.setBackground(new java.awt.Color(30, 30, 30));
        panelAddEditProd_Administrador.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 127, 15), new java.awt.Color(255, 127, 15), new java.awt.Color(255, 127, 15), new java.awt.Color(255, 127, 15)));
        panelAddEditProd_Administrador.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelAddEditProd_Administrador.add(mostrarImagenProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 160, 160));

        txtCodProd_panelAdministrarProd.setBackground(new java.awt.Color(255, 179, 71));
        txtCodProd_panelAdministrarProd.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtCodProd_panelAdministrarProd.setForeground(new java.awt.Color(51, 51, 51));
        panelAddEditProd_Administrador.add(txtCodProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 530, 30));

        txtNombreProd_panelAdministrarProd.setBackground(new java.awt.Color(255, 179, 71));
        txtNombreProd_panelAdministrarProd.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtNombreProd_panelAdministrarProd.setForeground(new java.awt.Color(51, 51, 51));
        txtNombreProd_panelAdministrarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProd_panelAdministrarProdActionPerformed(evt);
            }
        });
        panelAddEditProd_Administrador.add(txtNombreProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 530, 30));

        txtPrecioProd_panelAdministrarProd.setBackground(new java.awt.Color(255, 179, 71));
        txtPrecioProd_panelAdministrarProd.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtPrecioProd_panelAdministrarProd.setForeground(new java.awt.Color(51, 51, 51));
        panelAddEditProd_Administrador.add(txtPrecioProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 530, 30));

        txtStockProd_panelAdministrarProd.setBackground(new java.awt.Color(255, 179, 71));
        txtStockProd_panelAdministrarProd.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtStockProd_panelAdministrarProd.setForeground(new java.awt.Color(51, 51, 51));
        panelAddEditProd_Administrador.add(txtStockProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 530, 30));

        txtAreaDescripProd_panelAdministrarProd.setBackground(new java.awt.Color(255, 179, 71));
        txtAreaDescripProd_panelAdministrarProd.setColumns(20);
        txtAreaDescripProd_panelAdministrarProd.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtAreaDescripProd_panelAdministrarProd.setForeground(new java.awt.Color(51, 51, 51));
        txtAreaDescripProd_panelAdministrarProd.setRows(5);
        txtAreaDescripProd_panelAdministrarProd.setBorder(null);
        jScrollPane2.setViewportView(txtAreaDescripProd_panelAdministrarProd);

        panelAddEditProd_Administrador.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 450, 120));

        btnCancelProd_Administrador.setBackground(new java.awt.Color(255, 179, 71));
        btnCancelProd_Administrador.setFont(new java.awt.Font("Monotype Corsiva", 3, 14)); // NOI18N
        btnCancelProd_Administrador.setForeground(new java.awt.Color(51, 51, 51));
        btnCancelProd_Administrador.setText("CANCELAR");
        btnCancelProd_Administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelProd_AdministradorActionPerformed(evt);
            }
        });
        panelAddEditProd_Administrador.add(btnCancelProd_Administrador, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 350, 100, 110));

        btnAddProd_Administrador.setBackground(new java.awt.Color(255, 179, 71));
        btnAddProd_Administrador.setFont(new java.awt.Font("Monotype Corsiva", 3, 19)); // NOI18N
        btnAddProd_Administrador.setForeground(new java.awt.Color(51, 51, 51));
        btnAddProd_Administrador.setText("AÑADIR");
        btnAddProd_Administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProd_AdministradorActionPerformed(evt);
            }
        });
        panelAddEditProd_Administrador.add(btnAddProd_Administrador, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, 100, 110));

        btnEditProd_Administrador.setBackground(new java.awt.Color(255, 179, 71));
        btnEditProd_Administrador.setFont(new java.awt.Font("Monotype Corsiva", 3, 19)); // NOI18N
        btnEditProd_Administrador.setForeground(new java.awt.Color(51, 51, 51));
        btnEditProd_Administrador.setText("EDITAR");
        btnEditProd_Administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProd_AdministradorActionPerformed(evt);
            }
        });
        panelAddEditProd_Administrador.add(btnEditProd_Administrador, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 350, 100, 110));

        lblNombreProd_panelAdministrarProd.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreProd_panelAdministrarProd.setText("NOMBRE DEL PRODUCTO:");
        panelAddEditProd_Administrador.add(lblNombreProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 170, 20));

        lblPrecProd_panelAdministrarProd.setForeground(new java.awt.Color(255, 255, 255));
        lblPrecProd_panelAdministrarProd.setText("PRECIO DEL PRODUCTO:");
        panelAddEditProd_Administrador.add(lblPrecProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 86, 180, 20));

        lblStockProd_panelAdministrarProd.setForeground(new java.awt.Color(255, 255, 255));
        lblStockProd_panelAdministrarProd.setText("STOCK DEL PRODUCTO:");
        panelAddEditProd_Administrador.add(lblStockProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 146, 180, 20));

        lblCategProd_panelAdministrarProd.setForeground(new java.awt.Color(255, 255, 255));
        lblCategProd_panelAdministrarProd.setText("CATEGORÍA DEL PRODUCTO:");
        panelAddEditProd_Administrador.add(lblCategProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 206, 200, 20));

        lblCodProd_panelAdministrarProd.setForeground(new java.awt.Color(255, 255, 255));
        lblCodProd_panelAdministrarProd.setText("CÓDIGO DEL PRODUCTO:");
        panelAddEditProd_Administrador.add(lblCodProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 266, 180, 20));

        lblDescripProd_panelAdministrarProd.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripProd_panelAdministrarProd.setText("DESRIPCIÓN DEL PRODUCTO:");
        panelAddEditProd_Administrador.add(lblDescripProd_panelAdministrarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 200, 30));

        btnAgregarImagen.setBackground(new java.awt.Color(255, 179, 71));
        btnAgregarImagen.setFont(new java.awt.Font("Monotype Corsiva", 3, 20)); // NOI18N
        btnAgregarImagen.setForeground(new java.awt.Color(51, 51, 51));
        btnAgregarImagen.setText("Agregar Imagen");
        btnAgregarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarImagenActionPerformed(evt);
            }
        });
        panelAddEditProd_Administrador.add(btnAgregarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, 80));

        comboBoxCategoria.setBackground(new java.awt.Color(255, 179, 71));
        comboBoxCategoria.setFont(new java.awt.Font("Monotype Corsiva", 2, 18)); // NOI18N
        comboBoxCategoria.setForeground(new java.awt.Color(51, 51, 51));
        comboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "H", "M", "U" }));
        panelAddEditProd_Administrador.add(comboBoxCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 230, 530, -1));

        getContentPane().add(panelAddEditProd_Administrador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 850, 500));

        mostrarTodosProductos.setBorder(null);

        panelProductos_Administrador.setBackground(new java.awt.Color(30, 30, 30));
        panelProductos_Administrador.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        mostrarTodosProductos.setViewportView(panelProductos_Administrador);

        getContentPane().add(mostrarTodosProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1030, 640));

        panelMenuSelecProd.setBackground(new java.awt.Color(30, 30, 30));
        panelMenuSelecProd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panelMenuSelecProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @see Metodo para agregar abrir un nuevo panel para agregar productos
     */
    private void gestionarProducto(java.awt.event.ActionEvent evt){
        //Ponemos todos los TextField a su borde original (variable global almacenada anteriormente)
        txtNombreProd_panelAdministrarProd.setBorder(bordePorDefecto);
        txtAreaDescripProd_panelAdministrarProd.setBorder(bordePorDefecto);
        txtPrecioProd_panelAdministrarProd.setBorder(bordePorDefecto);
        txtStockProd_panelAdministrarProd.setBorder(bordePorDefecto);
        txtCodProd_panelAdministrarProd.setBorder(bordePorDefecto);  
        mostrarImagenProd.setBorder(bordePorDefecto);
        
        panelAddEditProd_Administrador.setVisible(true);
        
        //Eliminamos el mouseClick event para que no podamos hacer click una vez abrimos el nuevo panel
        if (panelProductos_Administrador.getComponentCount()>= 0) {
            Component[] c = panelProductos_Administrador.getComponents();
            for (int i = 0; i < c.length; i++) {
                JLabel component = (JLabel) c[i];
                
                component.removeMouseListener(ma);
            }
        }
    }
    /**
     * @see Metodo para eliminar un producto de la base de datos.
     */
    private void quitarProducto(java.awt.event.ActionEvent evt){
        Almacen almacen = new Almacen();
        for (int i = 0; i < productosParaEliminar.size(); i++) {
            almacen.eliminarProducto((int) productosParaEliminar.get(i));
        }  
        
        this.cargarProductos();
    }
    
    private void administrarUsuarios(java.awt.event.ActionEvent evt){
        gp.creaAdministrarUsuario(empleadoLogeado);
        dispose();
    }
    
    private void modoEmpleado(java.awt.event.ActionEvent evt){
        gp.creaSeleccionProducto(empleadoLogeado);
        dispose();
    }
    
    /**
     * 
     * @see crea una nueva pestaña Login y elimina la actual 
     */
    private void salir(java.awt.event.ActionEvent evt){
        gp.creaLogin();
        dispose();
    }
    
        
    
    private void txtNombreProd_panelAdministrarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProd_panelAdministrarProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProd_panelAdministrarProdActionPerformed

    /**
     * @see Metodo para agregar a la base de datos un nuevo producto
     */
    private void btnAddProd_AdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProd_AdministradorActionPerformed
        
        Almacen almacen = new Almacen();
        
        boolean estaVacio = false;
        Border borderRojo = BorderFactory.createLineBorder(Color.RED, 1);
        //Comprobar que los campos de los TextField no están vacíos y asi alteraremos nuestra variable estaVacio
        for (Component c : panelAddEditProd_Administrador.getComponents()) {
            if(c instanceof JTextField){
                JTextField label = (JTextField) c;
                if(label.getText().isEmpty()){
                    estaVacio = true;
                }
            }
            
        }
        
        //Si los TesxtField están vacíos nos mostrara un msg de error y los bordes se pondran en rojo.
        if(prod.getImagenFile()== null || estaVacio){
            txtNombreProd_panelAdministrarProd.setBorder(borderRojo);
            txtAreaDescripProd_panelAdministrarProd.setBorder(borderRojo);
            txtPrecioProd_panelAdministrarProd.setBorder(borderRojo);
            txtStockProd_panelAdministrarProd.setBorder(borderRojo);
            txtCodProd_panelAdministrarProd.setBorder(borderRojo);   
            mostrarImagenProd.setBorder(borderRojo);
            JLabel mensaje = new JLabel("<html>Debes de rellenar todos los campos.</html>");
            JOptionPane.showMessageDialog(null,mensaje,"ERROR",JOptionPane.ERROR_MESSAGE);
            
        }else{
            //Si no estan vacios, almacenaremos esos datos en variables para agregarlo al obj producto 
            //y luego agregarlo a nuesto almacen.
            String nombre = txtNombreProd_panelAdministrarProd.getText();
            String descripcion = txtAreaDescripProd_panelAdministrarProd.getText();
            double precio = Double.parseDouble(txtPrecioProd_panelAdministrarProd.getText());
            int stock = Integer.parseInt(txtStockProd_panelAdministrarProd.getText());
            String categoria = comboBoxCategoria.getItemAt(comboBoxCategoria.getSelectedIndex());
            int codigoProducto = Integer.parseInt(txtCodProd_panelAdministrarProd.getText());
            
            prod.setNombre(nombre);
            prod.setDescripcion(descripcion);
            prod.setPrecio(precio);
            prod.setStock(stock);
            prod.setGenero(categoria);
            prod.setCodigoProducto(codigoProducto);
            prod.setImagenFile(fichero.getAbsolutePath());
            
            //Si nuestro almacen devuelve verdadero (el producto no existe) se insertará en la BD
            //y se cerrará este panel actualizando los productos
            if (almacen.insertarProducto(prod)) {
                panelProductos_Administrador.removeAll();
                panelProductos_Administrador.repaint();
                this.cargarProductos();
                panelAddEditProd_Administrador.setVisible(false);
            }else{
                //Si no se inserta el borde del codigo se pone en rojo, porque no puedes insertar 
                //un producto con el mismo codigo
                txtNombreProd_panelAdministrarProd.setBorder(bordePorDefecto);
                txtAreaDescripProd_panelAdministrarProd.setBorder(bordePorDefecto);
                txtPrecioProd_panelAdministrarProd.setBorder(bordePorDefecto);
                txtStockProd_panelAdministrarProd.setBorder(bordePorDefecto); 
                mostrarImagenProd.setBorder(bordePorDefecto);
                txtCodProd_panelAdministrarProd.setBorder(borderRojo);  
                JLabel mensaje = new JLabel("<html>Producto ya existente, comprueba que el<br><b>Codigo del producto</b> sea distinto.</html>");
                JOptionPane.showMessageDialog(null,mensaje,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
        
        for (Component c : panelAddEditProd_Administrador.getComponents()) {
            if(c instanceof JTextField){
                JTextField label = (JTextField) c;
                label.setText("");
            }
            
        }
        mostrarImagenProd.setIcon(null);
    }//GEN-LAST:event_btnAddProd_AdministradorActionPerformed

    private void btnEditProd_AdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProd_AdministradorActionPerformed
        // TODO add your handling code here:
        Almacen almacen = new Almacen();
        
        boolean estaVacio = false;
        Border borderRojo = BorderFactory.createLineBorder(Color.RED, 1);
        //Comprobar que los campos de los TextField no están vacíos
        for (Component c : panelAddEditProd_Administrador.getComponents()) {
            if(c instanceof JTextField){
                JTextField label = (JTextField) c;
                if(label.getText().isEmpty()){
                    estaVacio = true;
                }
            }
            
        }
        
        //Si los TesxtField están vacíos nos mostrara un msg de error y los bordes se pondran en rojo.
        if(prod.getImagenFile()== null || estaVacio){
            
            txtNombreProd_panelAdministrarProd.setBorder(borderRojo);
            txtAreaDescripProd_panelAdministrarProd.setBorder(borderRojo);
            txtPrecioProd_panelAdministrarProd.setBorder(borderRojo);
            txtStockProd_panelAdministrarProd.setBorder(borderRojo);
            txtCodProd_panelAdministrarProd.setBorder(borderRojo);   
            mostrarImagenProd.setBorder(borderRojo);
            JLabel mensaje = new JLabel("<html>Debes de rellenar todos los campos.</html>");
            JOptionPane.showMessageDialog(null,mensaje,"ERROR",JOptionPane.ERROR_MESSAGE);
            
        }else{
            //Si no estan vacios, almacenaremos esos datos en variables para agregarlo al obj producto 
            //y luego editarlo en nuesto almacen.
            String nombre = txtNombreProd_panelAdministrarProd.getText();
            String descripcion = txtAreaDescripProd_panelAdministrarProd.getText();
            double precio = Double.parseDouble(txtPrecioProd_panelAdministrarProd.getText());
            int stock = Integer.parseInt(txtStockProd_panelAdministrarProd.getText());
            String categoria = comboBoxCategoria.getItemAt(comboBoxCategoria.getSelectedIndex());
            int codigoProducto = Integer.parseInt(txtCodProd_panelAdministrarProd.getText());
            
            prod.setNombre(nombre);
            prod.setDescripcion(descripcion);
            prod.setPrecio(precio);
            prod.setStock(stock);
            prod.setGenero(categoria);
            prod.setCodigoProducto(codigoProducto);
            prod.setImagenFile(fichero.getAbsolutePath());
            
            if (almacen.editarProducto(prod)) {
                panelProductos_Administrador.removeAll();
                panelProductos_Administrador.repaint();
                this.cargarProductos();
                panelAddEditProd_Administrador.setVisible(false);
            }else{
                txtNombreProd_panelAdministrarProd.setBorder(bordePorDefecto);
                txtAreaDescripProd_panelAdministrarProd.setBorder(bordePorDefecto);
                txtPrecioProd_panelAdministrarProd.setBorder(bordePorDefecto);
                txtStockProd_panelAdministrarProd.setBorder(bordePorDefecto); 
                txtCodProd_panelAdministrarProd.setBorder(borderRojo);  
                mostrarImagenProd.setBorder(bordePorDefecto);
                JLabel mensaje = new JLabel("<html>Producto no existente, comprueba el<br><b>Codigo del producto</b>.</html>");
                JOptionPane.showMessageDialog(null,mensaje,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
    }//GEN-LAST:event_btnEditProd_AdministradorActionPerformed

    private void btnCancelProd_AdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelProd_AdministradorActionPerformed
        // TODO add your handling code here:
        panelAddEditProd_Administrador.setVisible(false);
        this.cargarProductos();
    }//GEN-LAST:event_btnCancelProd_AdministradorActionPerformed

    /**
     * @see Metodo para obtener una imagen para agregar al objeto producto y visualizarla en nuestro panel
     */
    private void btnAgregarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarImagenActionPerformed
        //Creamos un nuevo fileChooser para agregar una imagen
        JFileChooser fc = new JFileChooser();
        //Agregamos un filtro de imagenes.
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));;
        int seleccion = fc.showOpenDialog(this);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                //Variable globar File para poder obtener el path absoluto y agregarlo al producto.
                fichero = fc.getSelectedFile();
                prod.setImagenFile(fichero.getAbsolutePath());
                
                //Obtenemos la imagen del producto
                Image image = ImageIO.read(prod.getImagenFile());
                ImageIcon ic = new ImageIcon(image);
                
                //Para reescalar la imagen al tamaño del label
                Image img = ic.getImage();
                Image myImg = img.getScaledInstance(mostrarImagenProd.getWidth(), mostrarImagenProd.getHeight(), Image.SCALE_SMOOTH);
                ic = new ImageIcon(myImg);
                //----------------------------------------------------
                //La mostramos en nuestro label.
                mostrarImagenProd.setIcon(ic);
            } catch (IOException ex) {
                Logger.getLogger(Pantalla_Administrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAgregarImagenActionPerformed

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
            java.util.logging.Logger.getLogger(Pantalla_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_Administrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProd_Administrador;
    private javax.swing.JButton btnAgregarImagen;
    private javax.swing.JButton btnCancelProd_Administrador;
    private javax.swing.JButton btnEditProd_Administrador;
    private javax.swing.JComboBox<String> comboBoxCategoria;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCategProd_panelAdministrarProd;
    private javax.swing.JLabel lblCodProd_panelAdministrarProd;
    private javax.swing.JLabel lblDescripProd_panelAdministrarProd;
    private javax.swing.JLabel lblNombreProd_panelAdministrarProd;
    private javax.swing.JLabel lblPrecProd_panelAdministrarProd;
    private javax.swing.JLabel lblStockProd_panelAdministrarProd;
    private javax.swing.JLabel mostrarImagenProd;
    private javax.swing.JScrollPane mostrarTodosProductos;
    private javax.swing.JPanel panelAddEditProd_Administrador;
    private javax.swing.JPanel panelMenuSelecProd;
    private javax.swing.JPanel panelProductos_Administrador;
    private javax.swing.JTextArea txtAreaDescripProd_panelAdministrarProd;
    private javax.swing.JTextField txtCodProd_panelAdministrarProd;
    private javax.swing.JTextField txtNombreProd_panelAdministrarProd;
    private javax.swing.JTextField txtPrecioProd_panelAdministrarProd;
    private javax.swing.JTextField txtStockProd_panelAdministrarProd;
    // End of variables declaration//GEN-END:variables
}
