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

    /**
     * Constructeur d'une CmdeAjoutLivraison
     *
     * @param l La demande de livraison à ajouter
     * @param la La demande de livraison d'arrivée.
     */
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
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Ajout: Point de livraison");
    }

    @Override
    public void undoCommande() {
        Controleur.modeleManager.supprimerDemandeLivraison(this.livraisonAAjouter);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Suppression: Point de livraison");
    }
}
