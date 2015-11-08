/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.Iterator;
import javax.swing.JOptionPane;
import modele.DemandeLivraison;
import modele.Intersection;

/**
 *
 * @author Kilian
 */
public class EtatSelectionSecondeIntersection extends EtatSelection {

    @Override
    protected void activerFonctionnalites() {
        super.activerFonctionnalites();
    }

    @Override
    public void selectionerIntersection(Intersection inter) {

        if (Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(inter.getId()) != null) { //si c'est une demande de livraison
            // on récupère les deux demandes de livraison.
            Iterator<Integer> it = Controleur.fenetre.getVue().getInterSelectionne();
            DemandeLivraison demande1 = Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(it.next());
            DemandeLivraison demande2 = Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(it.next());
            if (demande1.getId() != demande2.getId()) {
                Controleur.modeleManager.echangerDeuxLivraisons(demande1,demande2);
                Controleur.fenetre.getVue().supprimerInterSelectionee();
                Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Points de livraisons échangés.");
                // TO DO update VUE
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
            } else {
                Controleur.fenetre.getVue().supprimerDerniereInterSelectionnee();
                Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Second point de livraison invalide");
                // TO DO update VUE
            }
        } else {
            Controleur.fenetre.getVue().supprimerDerniereInterSelectionnee();
            Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Second point de livraison invalide");
            // TO DO update VUE
        }
    }

}
