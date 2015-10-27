/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author romain
 */
class IntersectionVue extends JPanel{
    public int x;
    public int y;
    public static int radius = 10;
    public Color couleur;
    
    public IntersectionVue(int x, int y, Color c)
    {
        super();
        this.x = x-5;
        this.y = y-5;
        this.couleur = c;
	this.setSize(new Dimension(radius,radius)); //il faut definir la taille du JPanel
        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
        this.setLocation(this.x, this.y);
        this.setVisible(true);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.couleur);
        g.fillOval(0, 0, radius, radius);
    }
}
