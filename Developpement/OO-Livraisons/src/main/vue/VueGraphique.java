/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JPanel;
import modele.Intersection;
import modele.Plan;
import modele.Troncon;

/**
 *
 * @author romain
 */
public class VueGraphique extends JPanel{
    Collection<TronconVue> mesTroncons;
    Collection<IntersectionVue> mesIntersection;
    
    private int maxX;
    private int maxY;
    
    public VueGraphique()
    {
        this.setBackground(Color.white);
        mesTroncons = new LinkedList<TronconVue>();
        mesIntersection = new LinkedList<IntersectionVue>();
        maxX = this.getSize().width;
        maxY = this.getSize().height;
    }
    
    /**
     * Retourne un point dont les coordonnes x et y sont à l'échelle de la fenêtre
     * @param x
     * @param y
     * @return 
     */
    public Point getCoordEchelle(int x, int y)
    {
        Point monPoint = new Point(x*this.getWidth()/maxX,y*this.getHeight()/maxY);
        return monPoint;
    }
    
    public void creerPlanTest(Plan plan)
    {
        plan.ajouterIntersection(1, 10, 27);
        plan.ajouterIntersection(2, 458, 78);
        plan.ajouterIntersection(3, 100, 80);
        plan.ajouterIntersection(4, 21, 400);
        plan.ajouterIntersection(5, 245, 366);
        plan.ajouterIntersection(6, 458, 150);
        
        //plan.ajouterTroncon(idDepart, idArrivee, nomRue, longueur, vitesse)
        plan.ajouterTroncon(1,2,"rue1",3,5);
        plan.ajouterTroncon(3,4,"rue2",3,5);
        plan.ajouterTroncon(4,5,"rue2",3,5);
        plan.ajouterTroncon(5,6,"rue2",3,5);
        plan.ajouterTroncon(6,1,"rue2",3,5);
        plan.ajouterTroncon(3,1,"rue2",3,5);
    }
    
    //attend un plan en parametre dans la version finale, ainsi que maxX et maxY
    public void drawPlan() {
        //we create a test plan to see how it is working
        Plan plan = new Plan();
        creerPlanTest(plan);
        
        maxX = 458;
        maxY = 400;
        
        Iterator<Entry<Integer, Intersection>> itIntersections = plan.getIntersections();
        Iterator<Troncon> itTroncon = plan.getTroncons();
        
        while(itIntersections.hasNext())
        {
            Map.Entry pair = (Map.Entry)itIntersections.next();
            Intersection monInter = (Intersection) pair.getValue();//on cree une vue de l'intersection
            
            Point interEchelle = getCoordEchelle(monInter.getX(),monInter.getY());//on passe les coordonnees a l'echelle
            IntersectionVue interVue = new IntersectionVue((int)interEchelle.getX(),(int)interEchelle.getY(), Color.LIGHT_GRAY); 
            this.mesIntersection.add(interVue);
            this.add(interVue);//on l'ajoute à la vue graphique
        }
        
        while(itTroncon.hasNext())
        {
            Troncon monTroncon = (Troncon) itTroncon.next();//on cree une vue du troncon
            Point debutEchelle = getCoordEchelle(monTroncon.getIntersectionDepart().getX(),monTroncon.getIntersectionDepart().getY());//on passe les coordonnees a l'echelle
            Point finEchelle  = getCoordEchelle(monTroncon.getIntersectionArrivee().getX(),monTroncon.getIntersectionArrivee().getY());
            TronconVue tronconVue = new TronconVue((int)debutEchelle.getX(), (int)debutEchelle.getY(),(int)finEchelle.getX(),(int)finEchelle.getY(),monTroncon.getNom(), Color.LIGHT_GRAY); 
            
            this.mesTroncons.add(tronconVue);
            this.add(tronconVue);//on l'ajoute à la vue graphique
        }
        this.revalidate();
        this.repaint();
    }
    
}
