/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Chemin;

/**
 * Représente la vue graphique d'un chemin.
 *
 * @author Guillaume Kheng
 */
public class VueChemin {

    /**
     * Le chemin associé à la VueChemin.
     */
    private Chemin chemin;

    /**
     * La vue de la fenêtre de livraison associé à la VueChemin.
     */
    private VueFenetreLivraison VueFenetreLivraisonVue;

    /**
     * Constucteur d'une VueChemin.
     *
     * @param vueFenetreLivraison La vue de la fenêtre de livraison associé à la
     * VueChemin
     * @param chemin Le chemin que représente la VueChemin.
     */
    protected VueChemin(VueFenetreLivraison vueFenetreLivraison, Chemin chemin) {
        this.chemin = chemin;
        this.VueFenetreLivraisonVue = vueFenetreLivraison;
    }

    /**
     * Retourne le chemin associé à la VueChemin.
     *
     * @return Le chemin associé à la VueChemin.
     */
    protected Chemin getChemin() {
        return chemin;
    }

    /**
     * Retourne la vue de la fenêtre de livraison associé au chemin.
     *
     * @return La vue de la fenêtre de livraison associé au chemin.
     */
    protected VueFenetreLivraison getVueFenetreLivraison() {
        return VueFenetreLivraisonVue;
    }

}
