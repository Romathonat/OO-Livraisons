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
public class CmdeEchangeLivraisons implements Commande {

    private DemandeLivraison livraison1, livraison2;

    /**
     * Constructeur d'une CmdeEchangeLivraisons.
     *
     * @param l1 Une livraison à échanger.
     * @param l2 Une autre livraison à échanger avec la première.
     */
    public CmdeEchangeLivraisons(DemandeLivraison l1, DemandeLivraison l2) {
        livraison1 = l1;
        livraison2 = l2;
    }

    @Override
    public void doCommande() {
        Controleur.modeleManager.setBufferLivraison(livraison1);
        Controleur.modeleManager.echangerDeuxLivraisons(livraison2);
        Controleur.fenetre.getVue().updateVueTournee();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Echangé: points de livraisons.");
    }

    @Override
    public void undoCommande() {
        Controleur.modeleManager.setBufferLivraison(livraison2);
        Controleur.modeleManager.echangerDeuxLivraisons(livraison1);
        Controleur.fenetre.getVue().updateVueTournee();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Echangé: points de livraisons.");

    }
}
