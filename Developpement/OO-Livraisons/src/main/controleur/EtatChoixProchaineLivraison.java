/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

/**
 *
 * @author Kilian
 */
public class EtatChoixProchaineLivraison extends EtatDefaut {
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
        Controleur.fenetre.activerIntersectionsSelectionnables(true);
    }
}
