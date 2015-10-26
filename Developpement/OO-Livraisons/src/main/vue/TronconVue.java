/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author romain
 */
public class TronconVue extends JPanel{
    protected IntersectionVue debut;
    protected IntersectionVue fin;
    
    public TronconVue(IntersectionVue d, IntersectionVue f)
    {
        super();
        debut = d;
        fin = f;
    }
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(debut.x, debut.y, fin.x, fin.y);
    }
}
