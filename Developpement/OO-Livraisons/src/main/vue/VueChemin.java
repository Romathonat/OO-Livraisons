/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Chemin;

/**
 *
 * @author Guillaume Kheng
 */
public class VueChemin {
    
    private Chemin chemin;
    private VueFenetreLivraison fenetreLivraisonVue;

    public VueChemin(VueFenetreLivraison fenetreVue, Chemin chemin) {
        this.chemin = chemin;
        this.fenetreLivraisonVue = fenetreVue;
    }

    public Chemin getChemin() {
        return chemin;
    }

    public VueFenetreLivraison getFenetreLivraisonVue() {
        return fenetreLivraisonVue;
    }
    
}
