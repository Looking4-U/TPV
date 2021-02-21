
package tpv;





public class Pantalla_LogIn extends javax.swing.JFrame {
    
    Empleado emp = new Empleado();
    Usuario us = new Usuario();
    Encriptacion md5 = new Encriptacion();
    private gestionPantallas gp;
    
    
    public Pantalla_LogIn() {
        initComponents();
        this.setLocationRelativeTo(null);
        gp = new gestionPantallas();
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Entrar_LogIn = new javax.swing.JButton();
        lbl_Icono = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPasswd_Login = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Entrar_LogIn.setBackground(new java.awt.Color(255, 179, 71));
        btn_Entrar_LogIn.setFont(new java.awt.Font("Monotype Corsiva", 3, 24)); // NOI18N
        btn_Entrar_LogIn.setForeground(new java.awt.Color(51, 51, 51));
        btn_Entrar_LogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/usuario.png"))); // NOI18N
        btn_Entrar_LogIn.setText("ENTRAR");
        btn_Entrar_LogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Entrar_LogInActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Entrar_LogIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, 210, 80));

        lbl_Icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/genero.png"))); // NOI18N
        getContentPane().add(lbl_Icono, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 50, 50));

        lblInicio.setBackground(new java.awt.Color(255, 127, 15));
        lblInicio.setFont(new java.awt.Font("Monotype Corsiva", 2, 32)); // NOI18N
        lblInicio.setForeground(new java.awt.Color(255, 127, 15));
        lblInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInicio.setText("INICIO SESIÓN");
        getContentPane().add(lblInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 250, 40));

        txtEmail.setBackground(new java.awt.Color(255, 179, 71));
        txtEmail.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(51, 51, 51));
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 230, 40));

        txtPasswd_Login.setBackground(new java.awt.Color(255, 179, 71));
        txtPasswd_Login.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtPasswd_Login.setForeground(new java.awt.Color(51, 51, 51));
        txtPasswd_Login.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txtPasswd_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 230, 40));

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 127, 15));
        jLabel2.setText("Correo:\n");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 210, 160, 40));

        jLabel3.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 127, 15));
        jLabel3.setText("Contraseña: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 160, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Entrar_LogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Entrar_LogInActionPerformed
            String email = txtEmail.getText();
            String password = new String(txtPasswd_Login.getPassword());
            password = md5.getMD5String(password);
            emp.setCorreo(email);
            emp.setPassword(password);
            if(us.login(emp)){
                System.out.println("Usuario y contraseña correcto");
                gp.creaSeleccionProducto(emp);
                dispose();
            }else{
                System.err.println("Usuario y contraseña incorrecto");
            }
    }//GEN-LAST:event_btn_Entrar_LogInActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

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
            java.util.logging.Logger.getLogger(Pantalla_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_LogIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Entrar_LogIn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lbl_Icono;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPasswd_Login;
    // End of variables declaration//GEN-END:variables
}
