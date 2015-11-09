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
            // on récupère la seconde demande de livraison.
            DemandeLivraison demande2 = Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(
                    inter.getId());

            if (Controleur.modeleManager.getBufferLivraison().getId() != demande2.getId()) {
                Controleur.modeleManager.echangerDeuxLivraisons(demande2);
                Controleur.fenetre.getVue().supprimerInterSelectionee();
                Controleur.fenetre.getVue().updateVueTournee();
                Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Points de livraisons échangés.");
                // TO DO update VUE
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
            } else {
                Controleur.fenetre.getVue().supprimerInterSelectionee();
                Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Second point de livraison invalide");
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
            }
        } else {
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Second point de livraison invalide");
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        }
    }

}
