/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Etat;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Intersection;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;

/**
 *
 * @author romain
 */
public class VueGraphique extends JPanel implements Observer{
 
    private int maxX;
    private int maxY;
    private int rayonInter = 10;
    private Plan planCourant; //il nous faut garder ces references pour redessiner le plan quand repaint est appelle
    private EnsembleLivraisons livraisonsCourantes;//attention doivent être mise à null si on recharge juste le plan !
    private Tournee tourneeCourante;
    
    private boolean intersectionsSelectionnables;
    
    private Date[] mesDebutsFenetre = new Date[3]; //que trois fenetres dans les specs
    private Color[] mesCouleurs = new Color[3];//ces deux tableaux correspondent
    private Color couleurEntrepot = new Color(165,233,224);
    
    public VueGraphique()
    {
        this.setBackground(Color.white);
        
        maxX = this.getSize().width;
        maxY = this.getSize().height;
        intersectionsSelectionnables = false;
        this.addMouseListener(new AnnulerSelection());
        
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
        
              
        if(this.planCourant != null){
            dessinerPlan(g2D);
            if(this.livraisonsCourantes != null){
                dessinerLivraisons(g2D);
                if(this.tourneeCourante != null){
                    dessinerTournee(g2D);
                }
            }
        }
    }
    
    /**
     * Ajoute la reference du plan, et le dessine via repaint()
     * @param plan 
     */
    public void drawPlan(Plan plan) {
        this.planCourant = plan; 
        this.tourneeCourante = null; //on s'assure que ces deux references sont à null quand on charge le plan
        this.livraisonsCourantes = null;
        
        maxX = plan.getXmax();
        maxY = plan.getYMax();
        
        repaint();//on repaint, en prenant en compte le plan
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
        Iterator<Entry<Integer, Intersection>> itInter = this.planCourant.getIntersections();
        
        while(itInter.hasNext()){
            Intersection monInter = itInter.next().getValue();
            dessinerUneIntersection(monInter, g2D);
        }
    }
    
    public void dessinerUneIntersection(Intersection monInter, Graphics2D g2D){
        Point coordonnesInter = getCoordEchelle(monInter.getX(),monInter.getY());
        g2D.fillOval(coordonnesInter.x-(int)rayonInter/2, coordonnesInter.y-(int)rayonInter/2, rayonInter, rayonInter);
    }
    /**
     * Dessine les troncons avec g2D
     * @param g2D 
     */
    public void dessinerTronconNeutre(Graphics2D g2D){
        Iterator<Troncon> itTroncon = this.planCourant.getTroncons();
        
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
    void drawLivraisons(EnsembleLivraisons livraisons)  {
        this.livraisonsCourantes = livraisons; 
        this.tourneeCourante = null; //on s'assure que cette reference esst à null quand on charge le plan
        initialiserCouleurEtFenetre();
        this.repaint();
    }
    
    
    /**
     * Dessine les demandes de livraison avec g2D
     * @param g2D 
     */
    void dessinerLivraisons(Graphics2D g2D){
        
        //on dessine d'abord l'entrepôt
        Intersection entrepot = this.livraisonsCourantes.getEntrepot();
        g2D.setColor(this.couleurEntrepot);
        dessinerUneIntersection(entrepot, g2D);
        
        Iterator<FenetreLivraison> itFenetres = this.livraisonsCourantes.getFenetresLivraison();
        
        int k = 0; //indice des couleurs
        
        while(itFenetres.hasNext()){
            Color CouleurCourante = mesCouleurs[k++%3];
            
            g2D.setColor(CouleurCourante);
            FenetreLivraison maFenetre = itFenetres.next();
            Iterator<DemandeLivraison> itDemandes = maFenetre.getDemandesLivraison();
            
            while(itDemandes.hasNext())//pour toutes les demandes de cette fenetre
            {
                Intersection monIntersection = itDemandes.next().getIntersection();
                dessinerUneIntersection(monIntersection, g2D);
            }
        }
    }
    /**
     * Dessine la tournée courante à partir de tournee
     * @param tournee 
     */
    void drawTournee(Tournee tournee) {
        this.tourneeCourante = tournee;
        
        this.repaint();
    }
    
    private void dessinerTournee(Graphics2D g2D) {
        Iterator<Chemin> itChemins = this.tourneeCourante.getChemins();
        
        while(itChemins.hasNext()){
            Chemin monChemin = itChemins.next();
            g2D.setColor(choixCouleur(monChemin.getLivraisonArrivee().getFenetreLivraison()));
            Iterator<Troncon> itTroncon = monChemin.getTroncons();
            while(itTroncon.hasNext()){
                dessinerUnTroncon(itTroncon.next(),g2D);
            }
        }
    }
    
    /**
     * Initialise le tableau des fenetres (correspondance avec les couleurs)
     */
    public void initialiserCouleurEtFenetre(){
        if(this.livraisonsCourantes != null){
            Iterator<FenetreLivraison> itFenetres = this.livraisonsCourantes.getFenetresLivraison();
            mesCouleurs[0] = Color.BLUE;
            mesCouleurs[1] = Color.MAGENTA;
            mesCouleurs[2] = Color.ORANGE;
            
            int k = 0; //indice des couleurs
            while(itFenetres.hasNext()){
                mesDebutsFenetre[k++%3] = itFenetres.next().getHeureDebut();
            }
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }

    private Color choixCouleur(FenetreLivraison horaire) {
        Color retour = Color.BLACK;
        for(int i=0; i<mesDebutsFenetre.length;i++){
            if(mesDebutsFenetre[i] == horaire.getHeureDebut()){
                retour = mesCouleurs[i];
            }
        }
        return retour;
    }

    // --- Activation/Desactivation ---
    
    public void activerIntersectionsSelectionnables(boolean activer){
        intersectionsSelectionnables = activer;
    }

    private class AnnulerSelection implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            
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
