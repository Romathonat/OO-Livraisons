/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.DemandeLivraison;
import modele.*;
import vue.*;

/**
 *
 * @author Kilian
 */

public class Controleur {
    
    // - Etats
    
    private Etat etatCourant; 
    protected static final EtatInitial etatInitial = new EtatInitial();
    protected static final EtatPlanCharge etatPlanCharge = new EtatPlanCharge();
    protected static final EtatLivraisonsChargees etatLivraisonChargee = new EtatLivraisonsChargees();
    protected static final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
    protected static final EtatPointLivraisonSelectionne etatPointLivraisonSelectionne = new EtatPointLivraisonSelectionne();
    protected static final EtatDeuxPointsLivraisonSelectionnes etatdeuxPointsLivraisonSelectionnes = new EtatDeuxPointsLivraisonSelectionnes();
    protected static final EtatIntersectionSelectionnee etatIntersectionSelectionnee = new EtatIntersectionSelectionnee();
    protected static final EtatRemplirInformations etatRemplirInformations = new EtatRemplirInformations();
    protected static final EtatChoixProchaineLivraison etatChoixProchaineLivraison = new EtatChoixProchaineLivraison();
    
    private ModeleManager modeleManager;
    private Fenetre fenetre;
    
    public static void main(String args[]){
        
        Controleur controleur;
        
        controleur = new Controleur();
    }
    
    public Controleur() {
        etatCourant = etatInitial;
        
        modeleManager = new ModeleManager();
        fenetre = new Fenetre(this);
    }
    
    public void chargerPlan() {
        etatCourant.chargerPlan(modeleManager.getPlan());
    }
    
    public void chargerTournee() {
        etatCourant.chargerPlan(modeleManager.getPlan());
    }
    
    public void clicGauche() {
        
    }
    
    public void undo() {
        
    }
    
    public void redo() {
        
    }
    
    public void ajouterLivraison(DemandeLivraison livraison) {
    
    }
    
    public void genererFeuilleRoute() {
        
    }
    
    public void echangerDeuxPoints() {
        
    }
    
    public void clicOk() {
        
    }
    
    public void clicAnnuler() {
        
    }
}
