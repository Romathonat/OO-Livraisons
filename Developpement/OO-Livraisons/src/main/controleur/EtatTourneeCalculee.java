/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.Intersection;

/**
 *
 * @author Kilian
 */
public class EtatTourneeCalculee extends EtatSelection {
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
        Controleur.fenetre.activerGenererFeuilleRoute(true);
        Controleur.fenetre.activerAnnuler(true);
        Controleur.fenetre.activerRetablir(true);
    }
}
