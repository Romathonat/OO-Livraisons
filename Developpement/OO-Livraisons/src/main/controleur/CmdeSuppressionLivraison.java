/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.*;

/**
 *
 * @author Kilian
 */
public class CmdeSuppressionLivraison implements Commande {

    /**
     * Demande de livraison a supprimer de la tournee
     */
    private DemandeLivraison demandeLivraisonASuppr;

    /**
     * Demande de livraison suivant celle a supprimer de la tournee
     */
    private DemandeLivraison nextDemandeLivraisonASuppr;

    public CmdeSuppressionLivraison(DemandeLivraison l, DemandeLivraison pl) {
        this.demandeLivraisonASuppr = l;
        this.nextDemandeLivraisonASuppr = pl;
    }

    @Override
    public void doCommande() {
        Controleur.modeleManager.supprimerDemandeLivraison(this.demandeLivraisonASuppr);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Suppression: Point de livraison");
    }

    @Override
    public void undoCommande() {
        Controleur.modeleManager.setBufferLivraison(this.demandeLivraisonASuppr);
        Controleur.modeleManager.ajouterNouvelleLivraison(this.nextDemandeLivraisonASuppr);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Ajout: Point de livraison");
    }

}
