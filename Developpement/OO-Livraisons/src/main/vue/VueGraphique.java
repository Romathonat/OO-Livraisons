/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
    LinkedList<TronconVue> mesTroncons;
    LinkedList<IntersectionVue> mesIntersection;
    
    private int maxX;
    private int maxY;
    
    private Date[] debutFenetre = new Date[3]; //que trois fenetre dans les specs
    private Color[] mesCouleurs = new Color[3];
    
    public VueGraphique()
    {
        this.setBackground(Color.white);
        mesTroncons = new LinkedList<TronconVue>();
        mesIntersection = new LinkedList<IntersectionVue>();
        maxX = this.getSize().width;
        maxY = this.getSize().height;
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
     * Dessine un plan dans la Vue Graphique à partir de plan
     * @param plan 
     */
    public void drawPlan(Plan plan) {
        //we create a test plan to see how it is working
        //Plan plan = new Plan();
        //creerPlanTest(plan);
        
        maxX = plan.getXmax();
        maxY = plan.getYMax();
        
        Iterator<Entry<Integer, Intersection>> itIntersections = plan.getIntersections();
        Iterator<Troncon> itTroncon = plan.getTroncons();
        int numberComponents = 0;
        
        while(itIntersections.hasNext())
        {
            Map.Entry pair = (Map.Entry)itIntersections.next();
            Intersection monInter = (Intersection) pair.getValue();//on cree une vue de l'intersection
            
            Point interEchelle = getCoordEchelle(monInter.getX(),monInter.getY());//on passe les coordonnees a l'echelle
            IntersectionVue interVue = new IntersectionVue((int)interEchelle.getX(),(int)interEchelle.getY(),monInter.getId(), Color.LIGHT_GRAY); 
            this.mesIntersection.add(interVue);
            this.add(interVue);//on l'ajoute à la vue graphique
            this.setComponentZOrder(interVue, numberComponents++); //on met un tool tip sur le front plus tard, donc on met ceci dans des plans plus profonds
        }
        
        while(itTroncon.hasNext())
        {
            Troncon monTroncon = (Troncon) itTroncon.next();//on cree une vue du troncon
            Point debutEchelle = getCoordEchelle(monTroncon.getIntersectionDepart().getX(),monTroncon.getIntersectionDepart().getY());//on passe les coordonnees a l'echelle
            Point finEchelle  = getCoordEchelle(monTroncon.getIntersectionArrivee().getX(),monTroncon.getIntersectionArrivee().getY());
            TronconVue tronconVue = new TronconVue((int)debutEchelle.getX(), (int)debutEchelle.getY(),(int)finEchelle.getX(),(int)finEchelle.getY(),monTroncon.getNom(), Color.LIGHT_GRAY); 
            
            this.mesTroncons.add(tronconVue);
            this.add(tronconVue);//on l'ajoute à la vue graphique
            this.setComponentZOrder(tronconVue,  numberComponents++);
        }
                
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Dessine l'ensemble des livraisons à partir de livraisons
     * @param livraisons 
     */
    void drawLivraisons(EnsembleLivraisons livraisons) throws Exception {
        Iterator<FenetreLivraison> it = livraisons.getFenetresLivraison();
        mesCouleurs[0] = Color.BLUE;
        mesCouleurs[1] = Color.MAGENTA;
        mesCouleurs[2] = Color.ORANGE;
        
        int k = 0;
        
        while(it.hasNext())//pour toutes les fenetres, on change la coloration
        {   
            Color CouleurCourante = mesCouleurs[k++%3];
            FenetreLivraison maFenetre = it.next();
            
            debutFenetre[k%3] = maFenetre.getHeureDebut();
                    
            Iterator<DemandeLivraison> itDemandes = maFenetre.getDemandesLivraison();
            while(itDemandes.hasNext())//pour toutes les demandes de cette fenetre
            {
                Intersection monInter = itDemandes.next().getIntersection();
                Iterator<IntersectionVue> itInterVue = mesIntersection.iterator();
                
                boolean ptTrouve = false;
                while(itInterVue.hasNext())//on cherche le point correspondant qui a déja du être dessiné
                {
                    IntersectionVue monInterVue = itInterVue.next();
                    if(monInterVue.getId() == monInter.getId())
                    {
                        monInterVue.setColor(CouleurCourante);
                        monInterVue.revalidate();
                        monInterVue.repaint();
                        ptTrouve = true;
                        break;
                    }
                }
                if(ptTrouve == false){
                    throw new Exception();
                }
            }
        }
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Dessine la tournée courante à partir de tournee
     * @param tournee 
     */
    void drawTournee(Tournee tournee) {
        Iterator<Chemin> itChemins = tournee.getChemins();
        
        while(itChemins.hasNext())//pour tous les chemins 
        {
            Chemin monChemin = itChemins.next();
            
            Iterator<Troncon> itTroncon = monChemin.getTroncons();
            FenetreLivraison horaire = monChemin.getLivraisonArrivee().getFenetreLivraison(); //n(arrive pas a voir la livraison arrivé!
            Color maCouleur = choixCouleur(horaire);
            while(itTroncon.hasNext())//pour tous les troncons de ce chemins
            {
                for(int i=0; i<mesTroncons.size();i++){//on cherche leTronconVue correspondant
                    if(mesTroncons.get(i).getName() == itTroncon.next().getNom()){
                        mesTroncons.get(i).setColor(maCouleur);
                    }
                }
            }
        }
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }

    private Color choixCouleur(FenetreLivraison horaire) {
        Color retour = Color.BLACK;
        for(int i=0; i<debutFenetre.length;i++){
            if(debutFenetre[i] == horaire.getHeureDebut()){
                retour = mesCouleurs[i];
            }
        }
        return retour;
    }
    
    private class AnnulerSelection implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            for(int i=0;i<mesIntersection.size();i++){
                mesIntersection.get(i).deselection();
            }
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
