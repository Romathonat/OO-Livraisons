/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;


/**
 * Cette classe représente l'ensemble des Etats pouvant effectuer l'opération "calculerTournee".
 * @author tfavrot
 */
public class EtatTournee extends EtatLivraisons{
    
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
        Controleur.fenetre.activerCalculerTournee(true);
    }
    
    @Override
    public void calculerTournee(){
        Controleur.modeleManager.calculerTournee();
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
    }
}
