package tpv;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTextField;


public class Pantalla_Registro extends javax.swing.JFrame {
    
    private gestionPantallas gp;
    private button btn;
        
    Empleado empleadoLogeado;
    
    private JButton volver;
    
    public Pantalla_Registro() {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        btn = new button();
        this.pintaBotones();
    }
    
    public Pantalla_Registro(Empleado emp) {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        btn = new button();
        this.empleadoLogeado = emp;
        this.pintaBotones();
    }
    
    private void pintaBotones(){
        volver = btn.creaButton("src/assets/volver.png","VOLVER",19);
        
        jPanel1.add(volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 120, 110));
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volver(evt);
            }
        });
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblRegistro = new javax.swing.JLabel();
        txtNombre_Registrar = new javax.swing.JTextField();
        txtApellidos_Registrar = new javax.swing.JTextField();
        txtEmail_Registrar = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        txtPasswd_Reagistrar = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        errorCampos = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/genero.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 175, 50, 50));

        lblRegistro.setFont(new java.awt.Font("Monotype Corsiva", 2, 30)); // NOI18N
        lblRegistro.setForeground(new java.awt.Color(253, 126, 14));
        lblRegistro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegistro.setText("REGISTRAR USUARIO");
        getContentPane().add(lblRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 290, 40));

        txtNombre_Registrar.setBackground(new java.awt.Color(255, 179, 71));
        txtNombre_Registrar.setFont(new java.awt.Font("Monotype Corsiva", 0, 17)); // NOI18N
        txtNombre_Registrar.setForeground(new java.awt.Color(51, 51, 51));
        txtNombre_Registrar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre_Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre_RegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(txtNombre_Registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 230, 40));

        txtApellidos_Registrar.setBackground(new java.awt.Color(255, 179, 71));
        txtApellidos_Registrar.setFont(new java.awt.Font("Monotype Corsiva", 0, 17)); // NOI18N
        txtApellidos_Registrar.setForeground(new java.awt.Color(51, 51, 51));
        txtApellidos_Registrar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txtApellidos_Registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 230, 40));

        txtEmail_Registrar.setBackground(new java.awt.Color(255, 179, 71));
        txtEmail_Registrar.setFont(new java.awt.Font("Monotype Corsiva", 0, 17)); // NOI18N
        txtEmail_Registrar.setForeground(new java.awt.Color(51, 51, 51));
        txtEmail_Registrar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail_Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmail_RegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(txtEmail_Registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, 230, 40));

        btnRegistrar.setBackground(new java.awt.Color(255, 179, 71));
        btnRegistrar.setFont(new java.awt.Font("Monotype Corsiva", 3, 22)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(51, 51, 51));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/comprobado.png"))); // NOI18N
        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 590, 210, 80));

        txtPasswd_Reagistrar.setBackground(new java.awt.Color(255, 179, 71));
        txtPasswd_Reagistrar.setFont(new java.awt.Font("Monotype Corsiva", 0, 17)); // NOI18N
        txtPasswd_Reagistrar.setForeground(new java.awt.Color(51, 51, 51));
        txtPasswd_Reagistrar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPasswd_Reagistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswd_ReagistrarActionPerformed(evt);
            }
        });
        getContentPane().add(txtPasswd_Reagistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 520, 230, 40));

        jLabel2.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(253, 126, 14));
        jLabel2.setText("Contraseña:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 480, 160, 30));

        jLabel3.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(253, 126, 14));
        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 120, 30));

        jLabel4.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(253, 126, 14));
        jLabel4.setText("Apellidos:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 310, 140, 30));

        jLabel5.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(253, 126, 14));
        jLabel5.setText("Correo:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, 160, 30));

        errorCampos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        errorCampos.setForeground(new java.awt.Color(255, 0, 51));
        getContentPane().add(errorCampos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 630, -1, -1));

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void volver(java.awt.event.ActionEvent evt){
        gp.creaAdministrarUsuario(empleadoLogeado);
        dispose();
    }
    
    
    
    private void txtEmail_RegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmail_RegistrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmail_RegistrarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Empleado emp = new Empleado();
        Encriptacion encriptar = new Encriptacion();
        Usuario us = new Usuario();
        boolean estanVacio = false;
        
        for(Component c : this.getComponents()){
            if(c instanceof JTextField){
                JTextField txt = (JTextField) c;
                if(txt.getText().isEmpty()){
                    estanVacio = true;
                }
            }
        }
        
        if(!new String(txtPasswd_Reagistrar.getPassword()).isEmpty() && !estanVacio){
            emp.setNombre(txtNombre_Registrar.getText());
            emp.setApellidos(txtApellidos_Registrar.getText());
            emp.setCorreo(txtEmail_Registrar.getText());
            emp.setPassword(encriptar.getMD5String(new String(txtPasswd_Reagistrar.getPassword())));

            if (us.registro(emp)) {
                gp.creaAdministrarUsuario(empleadoLogeado);
                dispose();
            }else{
                errorCampos.setText("ERROR USUARIO EXISTENTE");
            }
        
        }else{
            errorCampos.setText("Rellena todos los campos");
        }
        
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void txtPasswd_ReagistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswd_ReagistrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswd_ReagistrarActionPerformed

    private void txtNombre_RegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre_RegistrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre_RegistrarActionPerformed

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
            java.util.logging.Logger.getLogger(Pantalla_Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_Registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel errorCampos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblRegistro;
    private javax.swing.JTextField txtApellidos_Registrar;
    private javax.swing.JTextField txtEmail_Registrar;
    private javax.swing.JTextField txtNombre_Registrar;
    private javax.swing.JPasswordField txtPasswd_Reagistrar;
    // End of variables declaration//GEN-END:variables
}
