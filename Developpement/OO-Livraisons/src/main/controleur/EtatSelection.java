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
 * Cette classe représente l'ensemble des Etats pouvant executer les opérations
 * clic gauche sur une intersection, et clic gauche sur un point du plan qui
 * n'est pas une intersection.
 *
 * @author tfavrot
 */
public class EtatSelection extends EtatTournee {

    @Override
    protected void activerFonctionnalites() {
        super.activerFonctionnalites();
    }

    @Override
    public void clicPlan(int x, int y) {

        Intersection intersection = Controleur.fenetre.getVue().getVueGraphique().getIntersection(x, y);
        if (intersection != null){
            selectionerIntersection(intersection);
        } else {
            //on a cliqué dans le vide
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        }
    }

    @Override
    public void selectionerIntersection(Intersection inter) {
        if (Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(inter.getId()) != null) { //si c'est une demande de livraison
            Controleur.fenetre.getVue().supprimerInterSelectionee();//on supprime d'apres le schema etat-transition
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), true);
            Controleur.setEtatCourant(Controleur.etatPointLivraisonSelectionne);
        } 
        else {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), false);
            Controleur.setEtatCourant(Controleur.etatIntersectionSelectionnee);
        }
    }
}
