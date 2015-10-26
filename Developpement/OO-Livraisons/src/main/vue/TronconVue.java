/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author romain
 */
public class TronconVue extends JPanel{
    protected int xDebut;
    protected int yDebut;
    protected int xFin;
    protected int yFin;
    protected String name;
    
    public TronconVue(int x1, int y1, int x2, int y2, String name)
    {
        super();
        xDebut = x1;
        yDebut = y1;
        xFin = x2;
        yFin = y2;
        this.name = name;
        
                
    }
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2d.drawLine(xDebut, yDebut, xFin, yFin);
    }
}
