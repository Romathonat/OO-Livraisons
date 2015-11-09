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
public class EtatIntersectionSelectionnee extends EtatTourneeCalculee {
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
        Controleur.fenetre.activerAjouterLivraison(true);
    }
   
    
    @Override
    public void preparerAjouterPoint() {
        Controleur.setEtatCourant(Controleur.etatRemplirInformations);
    }
}
