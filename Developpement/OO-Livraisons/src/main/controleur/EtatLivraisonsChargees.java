/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.EnsembleLivraisons;
import modele.Intersection;

/**
 *
 * @author Kilian
 */
public class EtatLivraisonsChargees extends EtatTournee {
    @Override
    protected void activerFonctionnalites(){
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
        }
    }

    @Override
    public void selectionerIntersection(Intersection inter) {
        if (Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(inter.getId()) != null) { //si c'est une demande de livraison
            Controleur.fenetre.getVue().supprimerInterSelectionee();//on supprime d'apres le schema etat-transition
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), true);
        }
        else if (Controleur.modeleManager.getEnsembleLivraisons().getEntrepot().getId() == inter.getId()){
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), false);
        }
        else {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), false);
        }
    }
}
