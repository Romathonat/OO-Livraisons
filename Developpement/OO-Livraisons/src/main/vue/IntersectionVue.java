/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author romain
 */
class IntersectionVue extends JPanel{
    public int x;
    public int y;
    static int radius = 5;
    
    public IntersectionVue(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillOval(x, y, radius, radius);
    }
}
