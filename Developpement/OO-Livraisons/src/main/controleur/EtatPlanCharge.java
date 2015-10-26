/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.EnsembleLivraisons;

/**
 *
 * @author Kilian
 */
public class EtatPlanCharge extends EtatDefaut {

    @Override
    public void chargerLivraisons(EnsembleLivraisons ensembleLivraisons){
        Controleur.setEtatCourant(Controleur.etatLivraisonChargee);
    }
}
