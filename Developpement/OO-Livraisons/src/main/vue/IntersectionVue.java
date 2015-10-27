/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author romain
 */
class IntersectionVue extends JPanel{
    public int x;
    public int y;
    static int radius = 10;
    
    public IntersectionVue(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
	this.setSize(new Dimension(radius,radius)); //il faut definir la taille du JPanel
        //!!!!!!!!!METTRE EN TRANSPARENT !!!!!!!!!!!
        this.setLocation(x, y);
        this.setVisible(true);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, radius, radius);
    }
}
