/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import static vue.IntersectionVue.radius;

/**
 *
 * @author romain
 */
public class ElementLegende extends JPanel{
    private Color couleur;
    private String texte;
    private JLabel label;
    
    public ElementLegende (Color c, String s){
        this.couleur = c;
        this.texte = s;
        this.setPreferredSize(new Dimension(20,200));
        label = new JLabel(s);
        label.setBorder(new EmptyBorder(0, 20, 0, 0));
        label.setFont(new Font("Verdana",1,10));
        this.add(label);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
                
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON );
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY );
        g2d.setColor(this.couleur);
        g2d.fillOval(5, 5, 10, 10);
    }
}
