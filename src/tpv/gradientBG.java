/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class gradientBG extends JPanel{
    

    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
    
        Color color1 = new Color(225,68,152);
        Color color2 = new Color(92,28,62);
        GradientPaint gp = new GradientPaint(0,0,color1,180,height,color2);

        g2d.setPaint(gp);
        g2d.fillRect(0,0,width,height);
        
    }
    
    
}
