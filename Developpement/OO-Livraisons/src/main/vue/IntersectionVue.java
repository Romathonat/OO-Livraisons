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
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author romain
 */
class IntersectionVue extends JPanel{
    public int x;
    public int y;
    private int id;
    private boolean selectionne;
    public int radius = 10;
    JLabel infoBulle; //l'infobulle relative à cette intersection
    private Color couleur;
    
    public IntersectionVue(int x, int y, int id, Color c){
        super();
        this.x = x;
        this.y = y;
        offsetLocation();
        
        this.id = id;
        this.couleur = c;
        this.selectionne = false;
        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
        this.setBounds(this.x, this.y, radius, radius);//il faut definir la taille et la position de l'interectionVue courante
        this.setVisible(true);
        this.addMouseListener(new MyMouseManager(this));
    }
    
    /**
     * décale le point pour qu'il soit centre
     */
    private void offsetLocation(){
        this.x = this.x - (int)(radius/2);
        this.y = this.y - (int)(radius/2);
    }
    
    /**
     * remet la point à sa position avant offsetLocation()
     * Contrat: offsetLocation a été appellé une seule fois avant.
     */
    private void unoffsetLocation(){
        this.x = this.x + (int)(radius/2);
        this.y = this.y + (int)(radius/2);
    }
    
    public int getId(){
        return id;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public void setColor(Color c)    {
        couleur = c;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
                
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON );
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY );
        g2d.setColor(this.couleur);
        g2d.fillOval(0, 0, radius, radius);
    }
    
    public void setToolTip(String texte){
        this.infoBulle = new JLabel("<html>"+texte+"</html>");
        this.infoBulle.setBackground(Color.WHITE);
        this.infoBulle.setSize(70,40);
        this.infoBulle.setLocation(x,y);
    }
    
    public void deselection(){
        unoffsetLocation();
        this.radius = 10;
        selectionne = false;
        
        offsetLocation();
        setBounds(x, y, radius, radius);
        
        this.revalidate();
        this.repaint();
    }
    
    public class MyMouseManager implements MouseListener{
        IntersectionVue vue;
        public MyMouseManager(IntersectionVue vue){
            this.vue = vue;
        }
        
        /**
         * Permet de selectionner un point
         * @param e 
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(selectionne == false){
                unoffsetLocation();//on restaure l'etat precedent
                selectionne = true;
                radius = 15;
                
                offsetLocation();
                setBounds(x, y, radius, radius);
            }
            else{
                deselection();
            }
            
            revalidate();
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel vueGraphique = (JPanel)vue.getParent();
            
            if(vue.infoBulle == null){
                vue.setToolTip("id :"+vue.getId());
            }
            
            vueGraphique.add(vue.infoBulle);
            vueGraphique.setComponentZOrder(vue.infoBulle, 0);
            vueGraphique.revalidate();
            vueGraphique.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel vueGraphique = (JPanel)vue.getParent();
            vueGraphique.remove(vue.infoBulle);
            vueGraphique.revalidate();
            vueGraphique.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
        
    }
}
