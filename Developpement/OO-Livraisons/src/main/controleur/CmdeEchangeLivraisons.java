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
    private Tournee tournee;
    
    public CmdeEchangeLivraisons(DemandeLivraison l1, DemandeLivraison l2, Tournee t) {
        livraison1 = l1;
        livraison2 = l2;
        
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
