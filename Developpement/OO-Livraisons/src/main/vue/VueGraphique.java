/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.QuadCurve2D;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modele.Intersection;
import modele.Troncon;

/**
 * Élement graphique de dessin d'une vue.
 * @author romain
 */
public class VueGraphique extends JPanel{
 
    /**
     * La vue dans laquelle s'inscrit la VueGraphique.
     */
    private Vue vue;
    
    /**
     * La coordonnée maximale en abcisses des intersection à dessiner. 
     */
    private int maxX;
    
    /**
     * La coordonnée maximale en ordonnée des intersection à dessiner. 
     */
    private int maxY;
    
    /**
     * Le rayon du cercle représentant une intersection. 
     */
    private final int rayonInter = 10;
    
    /**
     * Le rayon du cercle représentant une intersection selectionnée. 
     */
    private final int rayonSelection = 15;
    
    /**
     * Le decalage entre les troncons qui se chevauchent lors du dessin de la tournée
     */
    private final int decalageTronconChevauchement = 20;
    
    /**
     * L'epaisseur du trait du dessin des troncons
     */
    private final int epaisseurTrait = 2;
    /**
     * Constucteur d'une VueGraphique
     * @param vue La vue dans laquelle s'inscrit la VueGraphique.
     */
    protected VueGraphique(Vue vue) {
        this.vue = vue;
        this.setBackground(Color.white);
        this.maxX = this.getSize().width;
        this.maxY = this.getSize().height;
        this.addMouseListener(new ecouteurSouris(this.vue.fenetre));       
    }
    
    /**
     * Retourne un point dont les coordonnes x et y sont à l'échelle de la fenêtre
     * @param x La coordonnée en abscisse du point à retourner, avant mise à l'échelle. 
     * @param y La coordonnée en ordonnée du point à retourner, avant mise à l'échelle.
     * @return Le point, avec les coordonnées mise à l'échelle.
     */
    public Point getPointCoordEchelle(int x, int y)
    {
        Point monPoint = new Point(x*(this.getWidth()-10)/this.maxX,y*(this.getHeight()-10)/this.maxY);
        return monPoint;
    }
    
    /**
     * Permet à la vue graphique de se dessiner correctement. 
     * @param g L'objet graphics à utiliser pour dessiner.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        initialiserGraphics2d(g2D);
        
        if(this.vue.getVuePlan().getPlan() != null){
            dessinerPlan(g2D);
            if(this.vue.getVueEnsembleLivraisons().getEnsembleLivraison() != null){
                dessinerLivraisons(g2D);
                if(this.vue.getVueTournee().getTournee() != null){
                    dessinerTournee(g2D);
                }
            }
        }
    }
    
    /**
     * Dessine le plan à partir du plan de la vue.
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void dessinerPlan(Graphics2D g2D){
        maxX = this.vue.getVuePlan().getPlan().getXMax();
        maxY = this.vue.getVuePlan().getPlan().getYMax();
        g2D.setColor(Color.LIGHT_GRAY);
        dessinerIntersectionsNeutre(g2D);
        dessinerTronconsNeutre(g2D);
    }
    
    /**
     * Dessine l'ensemble des intersections du plan de la vue.
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void dessinerIntersectionsNeutre(Graphics2D g2D){
        Iterator<Entry<Integer, Intersection>> itInter = this.vue.getVuePlan().getPlan().getIntersections();
        
        while(itInter.hasNext()){
            Intersection monInter = itInter.next().getValue();
            dessinerIntersection(monInter, g2D);
        }
    }
    
    /**
     * Dessine l'ensemble des troncons du plan de la vue. 
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void dessinerTronconsNeutre(Graphics2D g2D){
        Iterator<Troncon> itTroncon = this.vue.getVuePlan().getPlan().getTroncons();
        
        while(itTroncon.hasNext()){
            Troncon monTroncon = itTroncon.next();
            dessinerTroncon(monTroncon,g2D,0);
        }
    }
    
    /**
     * Dessine une intercection. 
     * @param intercection L'intercection à dessiner.
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void dessinerIntersection(Intersection intercection, Graphics2D g2D){
        Point coordonnesInter = getPointCoordEchelle(intercection.getX(),intercection.getY());
        
        Iterator<Integer> itInterSelectionne = this.vue.getInterSelectionne();
        int rayonDessin = rayonInter;
        
        while(itInterSelectionne.hasNext()){ 
            if(itInterSelectionne.next() == intercection.getId()){
                rayonDessin = this.rayonSelection;
            }
        }
        
        g2D.fillOval(coordonnesInter.x-(int)rayonDessin/2, coordonnesInter.y-(int)rayonDessin/2, rayonDessin, rayonDessin);
    }

    
    /**
     * Dessine un tronçon. 
     * @param troncon Le troncon à dessiner.
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     * @param offset le decalage à appliquer (chevauchement de troncons)
     */
    private void dessinerTroncon(Troncon troncon, Graphics2D g2D, int offset){
        Point coordonnesTronconDepart = getPointCoordEchelle(troncon.getIntersectionDepart().getX(),troncon.getIntersectionDepart().getY());
        Point coordonnesTronconArrivee = getPointCoordEchelle(troncon.getIntersectionArrivee().getX(),troncon.getIntersectionArrivee().getY());
        
        //on calcule le vecteur formé par ces deux coordonnees
        int xVecteur = coordonnesTronconArrivee.x - coordonnesTronconDepart.x;
        int yVecteur = coordonnesTronconArrivee.y - coordonnesTronconDepart.y;
        
        //on trouve l'offset (demo math sur papier basé sur produit scalaire)
        double xOffset = sqrt(pow(yVecteur,2)/(pow(xVecteur,2)+pow(yVecteur,2))) * offset * decalageTronconChevauchement;
        double yOffset = sqrt(pow(xVecteur,2)/(pow(xVecteur,2)+pow(yVecteur,2))) * offset * decalageTronconChevauchement;
        
        g2D.setStroke(new BasicStroke(epaisseurTrait, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); //on change l'epaisseur du trait
        
        if(offset != 0){ //si ls troncons se chevauchent, on les dessine "curvé"
            QuadCurve2D q = new QuadCurve2D.Float();
            int decalage = 0;
            
            q.setCurve(coordonnesTronconDepart.x, coordonnesTronconDepart.y, (coordonnesTronconDepart.x+coordonnesTronconArrivee.x+xOffset)/2, (coordonnesTronconDepart.y+coordonnesTronconArrivee.y+yOffset)/2, coordonnesTronconArrivee.x, coordonnesTronconArrivee.y);

            g2D.draw(q);
        }
        else{
            g2D.drawLine(coordonnesTronconDepart.x, coordonnesTronconDepart.y, coordonnesTronconArrivee.x, coordonnesTronconArrivee.y);
        }
    }
    
    /**
     * Initialise le Graphics2D pour dessiner.
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void initialiserGraphics2d(Graphics2D g2D){
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY );
    } 
    
    /**
     * Dessine l'ensemble des demandes de livraison.
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void dessinerLivraisons(Graphics2D g2D){
        
        //on dessine d'abord l'entrepôt
        Intersection entrepot = this.vue.getVueEnsembleLivraisons().getEnsembleLivraison().getEntrepot();
        g2D.setColor(GenerateurCouleur.getCouleurEntrepot());
        dessinerIntersection(entrepot, g2D);
        
        Iterator<VueFenetreLivraison> itFenetres = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
        
        while(itFenetres.hasNext()){           
            VueFenetreLivraison fenetreLivraisonVue = itFenetres.next();
            
            Iterator<VueDemandeLivraison> itDemandes = fenetreLivraisonVue.getVueDemandeLivraisonList();
            
            while(itDemandes.hasNext())//pour toutes les demandes de cette fenetre
            {
                VueDemandeLivraison vueDemandeLivraison = itDemandes.next();
                g2D.setColor(vueDemandeLivraison.getCouleur());
                Intersection monIntersection = vueDemandeLivraison.getDemandeLivraison().getIntersection();
                dessinerIntersection(monIntersection, g2D);
            }
        }
    }
    
    /**
     * Dessine la tournée. 
     * @param g2D L'objet Graphics2D à utiliser pour dessiner.
     */
    private void dessinerTournee(Graphics2D g2D) {
        Iterator<VueChemin> itChemins = this.vue.getVueTournee().getListVueChemin();
        
        List<Troncon> tronconsDejaDessines = new LinkedList<Troncon>(); //cache pour se souvenir de ce qu'on a dessiné
        
        
        while(itChemins.hasNext()){
            VueChemin vueChemin = itChemins.next();
            g2D.setColor(vueChemin.getVueFenetreLivraison().getCouleur());
            Iterator<Troncon> itTroncon = vueChemin.getChemin().getTroncons();
            
            while(itTroncon.hasNext()){
                Troncon tronconCourant = itTroncon.next();
                int nbTronconsDejaDessines = nombreTronconDejaDessine(tronconsDejaDessines, tronconCourant);
                dessinerTroncon(tronconCourant,g2D,nbTronconsDejaDessines);
                tronconsDejaDessines.add(tronconCourant); //on ajoute ce troncon apres l'avoir dessine
            }
        }
    }
    
    public int nombreTronconDejaDessine(List<Troncon> listeTroncons, Troncon t){
        int total = 0;
        for(int i=0; i<listeTroncons.size();i++){
            if(listeTroncons.get(i).getIntersectionDepart() == t.getIntersectionDepart() && listeTroncons.get(i).getIntersectionArrivee()== t.getIntersectionArrivee()){
                total += 1;
            }
            
            else if(listeTroncons.get(i).getIntersectionDepart() == t.getIntersectionArrivee() && listeTroncons.get(i).getIntersectionArrivee()== t.getIntersectionDepart()){
                total += 1;
            }
        }
        return total;
    }
    /**
     * Vérifie si une intersection se trouve aux coordonnées spécifiées,et 
     * renvoie l'intersection si elle a été trouvée. 
     * @param x
     * @param y
     * @return 
     */
    public Intersection getIntersection(int x, int y){
        Iterator<Map.Entry<Integer, Intersection>> itInter = this.vue.getVuePlan().getPlan().getIntersections();
        //on cherche si on trouve un point qui correspond à l'endroit où on a cliqué

        while (itInter.hasNext()) {
            Intersection intersection = itInter.next().getValue();
            Point coord = this.getPointCoordEchelle(intersection.getX(), intersection.getY());

            if (pow(coord.x - x, 2) + pow(coord.y - y, 2) <= pow(this.rayonInter, 2)) {
                return intersection;
            }
        }
        return null;
    } 

    

    private class ecouteurSouris implements MouseListener{
        
        Fenetre fenetre;

        public ecouteurSouris(JFrame frameParent) {
            this.fenetre = (Fenetre) frameParent;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            Fenetre.controleur.clicPlan(e.getX(), e.getY());
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
