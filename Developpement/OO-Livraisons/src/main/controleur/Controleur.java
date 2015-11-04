/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.*;
import vue.*;

/**
 * Controleur de l'application, qui fait le lien entre le modéle et la vue. Le 
 * contrôleur est implémenté en utilisant le pattern State. 
 * @author Kilian
 */

public class Controleur {

    /**
     * L'état courant du contrôleur.
     */
    private static Etat etatCourant; 
    
    
    protected static final EtatInitial etatInitial = new EtatInitial();
    protected static final EtatPlanCharge etatPlanCharge = new EtatPlanCharge();
    protected static final EtatLivraisonsChargees etatLivraisonChargee = new EtatLivraisonsChargees();
    protected static final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
    protected static final EtatPointLivraisonSelectionne etatPointLivraisonSelectionne = new EtatPointLivraisonSelectionne();
    protected static final EtatDeuxPointsLivraisonSelectionnes etatdeuxPointsLivraisonSelectionnes = new EtatDeuxPointsLivraisonSelectionnes();
    protected static final EtatIntersectionSelectionnee etatIntersectionSelectionnee = new EtatIntersectionSelectionnee();
    protected static final EtatRemplirInformations etatRemplirInformations = new EtatRemplirInformations();
    protected static final EtatChoixProchaineLivraison etatChoixProchaineLivraison = new EtatChoixProchaineLivraison();
    
    /**
     * Change l'état courant du contrôleur. 
     * @param etat Le nouvel état courant du contrôleur.
     */
    protected static void setEtatCourant(Etat etat){
        etatCourant = etat;
        etatCourant.activerEtDesactiverFonctionnalites();
    }
    
    /**
     * Le modèle de données lié au contrôleur. C'est le point d'entrée du contrôleur
     * dans le modèle. 
     */
    protected static ModeleManager modeleManager;
    
    /**
     * La fenêtre graphique de l'application.
     */
    protected static Fenetre fenetre;
    
    public static void main(String args[]){
        
        Controleur controleur = new Controleur();
    }
    
    /**
     * Constucteur du contrôleur.
     */
    public Controleur() {
       
        modeleManager = new ModeleManager();
        fenetre = new Fenetre(this);
        Controleur.setEtatCourant(etatInitial);
        
    }
   
    public synchronized Plan chargerPlan() {
        modeleManager.resetPlan();
        etatCourant.chargerPlan(modeleManager.getPlan());
        return modeleManager.getPlan();
    }
    
    public synchronized EnsembleLivraisons chargerLivraisons() {
        modeleManager.resetEnsembleLivraisons();
        etatCourant.chargerLivraisons(modeleManager.getPlan(),modeleManager.getEnsembleLivraisons());
        return modeleManager.getEnsembleLivraisons();
    }
    
    public synchronized Tournee calculerTournee() {
        modeleManager.resetTournee();
        etatCourant.calculerTournee();
        return modeleManager.getTournee();
    }
    
    public synchronized void undo() {
        
    }
    
    public synchronized void redo() {
        
    }
    
    public synchronized void ajouterLivraison(DemandeLivraison livraison) {
    
    }
    
    public synchronized void genererFeuilleRoute() {
        
    }
    
    public synchronized void echangerDeuxPoints() {
        
    }
}
