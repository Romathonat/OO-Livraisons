/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
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
    
    public VueGraphique()
    {
        this.setBackground(Color.white);
        mesTroncons = new LinkedList<TronconVue>();
        mesIntersection = new LinkedList<IntersectionVue>();
    }
    
    public void creerPlanTest(Plan plan)
    {
        plan.ajouterIntersection(1, 1, 2);
        plan.ajouterIntersection(2, 4, 5);
        plan.ajouterIntersection(3, 2, 5);
        plan.ajouterIntersection(4, 1, 7);
        plan.ajouterIntersection(5, 5, 1);
        plan.ajouterIntersection(6, 3, 2);
        
        //plan.ajouterTroncon(idDepart, idArrivee, nomRue, longueur, vitesse)
        plan.ajouterTroncon(1,2,"rue1",3,5);
        plan.ajouterTroncon(3,4,"rue2",3,5);
        plan.ajouterTroncon(4,5,"rue2",3,5);
        plan.ajouterTroncon(5,6,"rue2",3,5);
        plan.ajouterTroncon(6,1,"rue2",3,5);
        plan.ajouterTroncon(3,1,"rue2",3,5);
    }
    //attend un plan en parametre dans la version finale
    public void drawPlan() {
        //we create a test plan to see how it is working
        Plan plan = new Plan();
        creerPlanTest(plan);
        
        Iterator<Entry<Integer, Intersection>> itIntersections = plan.getIntersections();
        Iterator<Troncon> itTroncon = plan.getTroncons();
        
        while(itIntersections.hasNext())
        {
            Map.Entry pair = (Map.Entry)itIntersections.next();
            Intersection monInter = (Intersection) pair.getValue();//on cree une vue de l'intersection
            
            IntersectionVue interVue = new IntersectionVue(monInter.getX(),monInter.getY()); 
            this.mesIntersection.add(interVue);
            this.add(interVue);//on l'ajoute à la vue graphique
        }
        
        while(itTroncon.hasNext())
        {
            Troncon monTroncon = (Troncon) itTroncon.next();//on cree une vue de l'intersection
            TronconVue tronconVue = new TronconVue(monTroncon.getIntersectionDepart().getX(),monTroncon.getIntersectionDepart().getY(), monTroncon.getIntersectionArrivee().getX(),monTroncon.getIntersectionArrivee().getY(),monTroncon.getNom()); 
            
            this.mesTroncons.add(tronconVue);
            this.add(tronconVue);//on l'ajoute à la vue graphique
        }
        repaint();
    }
}
