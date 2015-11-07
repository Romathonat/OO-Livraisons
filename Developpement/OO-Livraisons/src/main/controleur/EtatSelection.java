/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import static java.lang.Math.pow;
import java.util.Iterator;
import java.util.Map;
import modele.EnsembleLivraisons;
import modele.Intersection;
import vue.VueGraphique;

/**
 * Cette classe représente l'ensemble des Etats pouvant executer les opérations clic gauche sur une intersection, 
 * et clic gauche sur un point du plan qui n'est pas une intersection.
 * @author tfavrot
 */
public class EtatSelection extends EtatTournee{
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
    }
    
    public void clicPlan(int x, int y){
        Iterator<Map.Entry<Integer, Intersection>> itInter = Controleur.modeleManager.getPlan().getIntersections();
        //on cherche si on trouve un point qui correspond à l'endroit où on a cliqué
        int rayon = Controleur.fenetre.getVue().getVueGraphique().getRayonInter();
        
        boolean interTrouve = false;
        
        while(itInter.hasNext()){
            Intersection monInter = itInter.next().getValue();
            Point coord = Controleur.fenetre.getVue().getVueGraphique().getPointCoordEchelle(monInter.getX(), monInter.getY());
            
            if(pow(coord.x - x,2)+pow(coord.y - y,2) <= pow(rayon,2)){
                interTrouve = true;
                
                if(Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(monInter.getId()) != null){ //si c'est une demande de livraison
                    Controleur.fenetre.getVue().ajouterInterSelectionnee(monInter.getId(),true);
                    Controleur.setEtatCourant(Controleur.etatPointLivraisonSelectionne);
                }
                else{
                    Controleur.fenetre.getVue().ajouterInterSelectionnee(monInter.getId(),false);
                    Controleur.setEtatCourant(Controleur.etatIntersectionSelectionnee);
                }
                break;
            }
        }
        
        if(interTrouve == false){
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        }
    }
}
