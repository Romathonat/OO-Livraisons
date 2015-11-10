/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.Intersection;

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
        if (intersection != null) {
            selectionerIntersection(intersection);
        } else {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        }
    }

    @Override
    public void selectionerIntersection(Intersection inter) {
        if (Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(inter.getId()) != null) {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), true);
            Controleur.setEtatCourant(Controleur.etatPointLivraisonSelectionne);
        } else if (Controleur.modeleManager.getEnsembleLivraisons().getEntrepot().getId() == inter.getId()) {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), false);
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        } else {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().ajouterInterSelectionnee(inter.getId(), false);
            Controleur.setEtatCourant(Controleur.etatIntersectionSelectionnee);
        }
    }
}
