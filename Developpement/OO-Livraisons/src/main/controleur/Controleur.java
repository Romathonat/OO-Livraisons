/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.*;
import vue.*;

/**
 *
 * @author Kilian
 */

public class Controleur {
    
    // - Etats
    
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
    
    protected static void setEtatCourant(Etat etat){
        etatCourant = etat;
    }
        
    protected static ModeleManager modeleManager;
    private Fenetre fenetre;
    
    public static void main(String args[]){
        
        Controleur controleur;
        Fenetre fenetre;
        
        controleur = new Controleur();
    }
    
    public Controleur() {
        etatCourant = etatInitial;
        
        modeleManager = new ModeleManager();
        fenetre = new Fenetre(this);
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
        etatCourant.calculerTournee();
        return modeleManager.getTournee();
    }
    
    public synchronized void clicGauche() {
        
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
    
    public synchronized void clicOk() {
        
    }
    
    public synchronized void clicAnnuler() {
        
    }
}
