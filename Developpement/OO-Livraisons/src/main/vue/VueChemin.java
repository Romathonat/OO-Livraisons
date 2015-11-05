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
public class CheminVue {
    
    private Chemin chemin;
    private FenetreLivraisonVue fenetreLivraisonVue;

    public CheminVue(FenetreLivraisonVue fenetreVue, Chemin chemin) {
        this.chemin = chemin;
        this.fenetreLivraisonVue = fenetreVue;
    }

    public Chemin getChemin() {
        return chemin;
    }

    public FenetreLivraisonVue getFenetreLivraisonVue() {
        return fenetreLivraisonVue;
    }
    
}
