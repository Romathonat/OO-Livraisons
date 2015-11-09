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
    
    private DemandeLivraison livraison;
    private Tournee tournee;
    
    public CmdeAjoutLivraison(DemandeLivraison l, Tournee t) {
        livraison = l;
        tournee = t;
    }
    
    @Override
    public void doCommande() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undoCommande() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
