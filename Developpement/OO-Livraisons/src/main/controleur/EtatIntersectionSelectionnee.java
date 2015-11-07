/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.List;
import modele.DemandeLivraison;
import modele.Tournee;

/**
 *
 * @author Kilian
 */
public class EtatIntersectionSelectionnee extends EtatSelection {
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
        Controleur.fenetre.activerAjouterLivraison(true);
    }
   
    
    @Override
    public void demandeAjoutPoint() {
        Controleur.setEtatCourant(Controleur.etatRemplirInformations);
    }
}
