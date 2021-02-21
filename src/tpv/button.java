package tpv;


import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;


public class button extends JButton{
    
    public button(){
        
    }
    
    public JButton creaButton(String rutaIco,String nombreBtn,int tamLetra){
        
       JButton btn = new JButton(nombreBtn);
       JLabel label = new JLabel(new javax.swing.ImageIcon(rutaIco));
        
       
       Color bg = new Color(255,179,71);
       Color fg = new Color(51,51,51);
       

       btn.setBackground(bg);
       btn.setForeground(fg);
       btn.setFont(new Font("Monotype Corsiva", Font.BOLD, tamLetra));
       
       ComponentBorder cb = new ComponentBorder( label );
       cb.setEdge( ComponentBorder.Edge.TOP );
       cb.install( btn );
       
       return btn;
        
    }

    
    
}
