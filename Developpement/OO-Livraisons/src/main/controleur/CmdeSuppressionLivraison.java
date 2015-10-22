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
    
    private DemandeLivraison livraison;
    private Tournee tournee;
    
    public CmdeSuppressionLivraison(DemandeLivraison l, Tournee t) {
        livraison = l;
        tournee = t;
    }
    
    @Override
    public void doCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undoCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
