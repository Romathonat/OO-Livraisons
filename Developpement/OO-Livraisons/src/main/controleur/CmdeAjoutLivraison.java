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
public class CmdeAjoutLivraison implements Commande {
    
    /**
     * Livraison a ajouter a la tournee
     */
    private DemandeLivraison livraisonAAjouter;
    
    /**
     * Demande de livraison precedent celle a ajouter a la tournee
     */
    private DemandeLivraison demandeLivraisonArrivee;
    
    public CmdeAjoutLivraison(DemandeLivraison l, DemandeLivraison la) {
        livraisonAAjouter = l;
        demandeLivraisonArrivee = la;
    }
    
    @Override
    public void doCommande() {
        Controleur.modeleManager.setBufferLivraison(this.livraisonAAjouter);
        Controleur.modeleManager.ajouterNouvelleLivraison(this.demandeLivraisonArrivee);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Point de livraison ajouté");
    }

    @Override
    public void undoCommande() {
        Controleur.modeleManager.supprimerDemandeLivraison(this.livraisonAAjouter);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Point de livraison supprimé");
    }
}
