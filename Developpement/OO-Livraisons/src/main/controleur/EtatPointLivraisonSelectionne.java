/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.DemandeLivraison;

/**
 *
 * @author Kilian
 */
public class EtatPointLivraisonSelectionne extends EtatSelection {

    @Override
    protected void activerFonctionnalites() {
        super.activerFonctionnalites();
        Controleur.fenetre.activerEchangerLivraison(true);
        Controleur.fenetre.activerSupprimerLivraison(true);
    }

    /**
     * Ajoute à la tournee cette Demande si elle est valide (pas de conflit
     * d'id)
     *
     * @param demandeLivraison
     */
    @Override
    public void supprimerLivraison(DemandeLivraison demandeLivraison) {
        Controleur.modeleManager.supprimerDemandeLivraison(demandeLivraison);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Point de livraison supprimé");
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);//on a fini ce use case, on revient à cet etat
    }
    
    @Override
    public void echangerDeuxLivraisons() {
        Controleur.setEtatCourant(Controleur.etatdeuxPointsLivraisonSelectionnes);
    }
}
