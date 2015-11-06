/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Intersection;
import modele.Tournee;
import modele.Troncon;

/**
 *
 * @author romain
 */
public class VueGraphique extends JPanel implements Observer{
 
    private Vue vue;
    private int maxX;
    private int maxY;
    private int rayonInter = 10;
    private int rayonSelection = 15;
    
    /**
     * Constucteur d'une vue graphique
     * @param vue 
     */
    public VueGraphique(Vue vue) {
        this.vue = vue;
        this.setBackground(Color.white);
        this.maxX = this.getSize().width;
        this.maxY = this.getSize().height;
        this.addMouseListener(new ecouteurSouris(this.vue.fenetre));       
    }
    public int getRayonInter(){
        return this.rayonInter;
    }
    /**
     * Retourne un point dont les coordonnes x et y sont à l'échelle de la fenêtre
     * @param x
     * @param y
     * @return 
     */
    public Point getCoordEchelle(int x, int y)
    {
        Point monPoint = new Point(x*(this.getWidth()-10)/maxX,y*(this.getHeight()-10)/maxY);
        return monPoint;
    }
    
    /**
     * Permet à la fenetre de se rafraichir correctement 
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        initialiserGraphics2d(g2D); //on initialise le pinceau pour qu'il dessine bien
        
        if(this.vue.vuePlan.plan != null){
            dessinerPlan(g2D);
            if(this.vue.vueEnsembleLivraisons.ensembleLivraison != null){
                dessinerLivraisons(g2D);
                if(this.vue.vueTournee.tournee != null){
                    dessinerTournee(g2D);
                }
            }
        }
    }
    
    /**
     * Ajoute la reference du plan, et le dessine via repaint()
     * @param plan 
     */
    public void drawPlan() {
        
        maxX = this.vue.vuePlan.plan.getXMax();
        maxY = this.vue.vuePlan.plan.getYMax();
        
        repaint();
    }
    
    /**
     * Dessine le plan à partir du plan courant avec le pinceau g2D
     * @param g2D 
     */
    public void dessinerPlan(Graphics2D g2D){
        g2D.setColor(Color.LIGHT_GRAY);
        dessinerInterNeutre(g2D);
        dessinerTronconNeutre(g2D);
    }
    
    /**
     * Dessine les intersections avec g2D
     * @param g2D 
     */
    public void dessinerInterNeutre(Graphics2D g2D){
        Iterator<Entry<Integer, Intersection>> itInter = this.vue.vuePlan.plan.getIntersections();
        
        while(itInter.hasNext()){
            Intersection monInter = itInter.next().getValue();
            dessinerUneIntersection(monInter, g2D);
        }
    }
    
    public void dessinerUneIntersection(Intersection monInter, Graphics2D g2D){
        Point coordonnesInter = getCoordEchelle(monInter.getX(),monInter.getY());
        
        Iterator<Integer> itInterSelectionne = this.vue.getInterSelectionne();
        int rayonDessin = rayonInter;
        
        while(itInterSelectionne.hasNext()){ //on verifie que l'intersection qu'on dessine a été selectionnée ou pas
            if(itInterSelectionne.next() == monInter.getId()){
                rayonDessin = this.rayonSelection;
            }
        }
        
        g2D.fillOval(coordonnesInter.x-(int)rayonDessin/2, coordonnesInter.y-(int)rayonDessin/2, rayonDessin, rayonDessin);
    }
    /**
     * Dessine les troncons avec g2D
     * @param g2D 
     */
    public void dessinerTronconNeutre(Graphics2D g2D){
        Iterator<Troncon> itTroncon = this.vue.vuePlan.plan.getTroncons();
        
        while(itTroncon.hasNext()){
            Troncon monTroncon = itTroncon.next();
            dessinerUnTroncon(monTroncon,g2D);
        }
    }
    
    public void dessinerUnTroncon(Troncon monTroncon, Graphics2D g2D){
        Point coordonnesTronconDepart = getCoordEchelle(monTroncon.getIntersectionDepart().getX(),monTroncon.getIntersectionDepart().getY());
        Point coordonnesTronconArrivee = getCoordEchelle(monTroncon.getIntersectionArrivee().getX(),monTroncon.getIntersectionArrivee().getY());

        g2D.drawLine(coordonnesTronconDepart.x, coordonnesTronconDepart.y, coordonnesTronconArrivee.x, coordonnesTronconArrivee.y);
    }
    
    /**
     * Initialise le Graphics2D pour dessiner
     */
    public void initialiserGraphics2d(Graphics2D g2D){
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY );
    }
    
    /**
     * Ajoute la reference de livraison, et la dessine via repaint()
     * @param livraisons 
     */
    void drawLivraisons()  {
        this.repaint();
    }
    
    
    /**
     * Dessine les demandes de livraison avec g2D
     * @param g2D 
     */
    void dessinerLivraisons(Graphics2D g2D){
        
        //on dessine d'abord l'entrepôt
        Intersection entrepot = this.vue.vueEnsembleLivraisons.ensembleLivraison.getEntrepot();
        g2D.setColor(GenerateurCouleur.getCouleurEntrepot());
        dessinerUneIntersection(entrepot, g2D);
        
        Iterator<VueFenetreLivraison> itFenetres = this.vue.vueEnsembleLivraisons.listFenetresLivraisonVue.iterator();
        
        while(itFenetres.hasNext()){           
            VueFenetreLivraison fenetreLivraisonVue = itFenetres.next();
            
            Iterator<VueDemandeLivraison> itDemandes = fenetreLivraisonVue.listDemandesLivraisonVue.iterator();
            
            while(itDemandes.hasNext())//pour toutes les demandes de cette fenetre
            {
                VueDemandeLivraison vueDemandeLivraison = itDemandes.next();
                g2D.setColor(vueDemandeLivraison.getCouleur());
                Intersection monIntersection = vueDemandeLivraison.demandeLivraison.getIntersection();
                dessinerUneIntersection(monIntersection, g2D);
            }
        }
    }
    /**
     * Dessine la tournée courante à partir de tournee
     * @param tournee 
     */
    void drawTournee() {
        this.repaint();
    }
    
    private void dessinerTournee(Graphics2D g2D) {
        Iterator<VueChemin> itChemins = this.vue.vueTournee.listCheminVue.iterator();
        
        while(itChemins.hasNext()){
            VueChemin vueChemin = itChemins.next();
            g2D.setColor(vueChemin.getFenetreLivraisonVue().getCouleur());
            Iterator<Troncon> itTroncon = vueChemin.getChemin().getTroncons();
            while(itTroncon.hasNext()){
                dessinerUnTroncon(itTroncon.next(),g2D);
            }
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }

    // --- Activation/Desactivation ---
    

    private class ecouteurSouris implements MouseListener{
        
        Fenetre fenetre;

        public ecouteurSouris(JFrame frameParent) {
            this.fenetre = (Fenetre) frameParent;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            this.fenetre.controleur.clicPlan(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
}
