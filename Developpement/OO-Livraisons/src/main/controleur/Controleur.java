/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import modele.*;
import vue.*;

/**
 * Controleur de l'application, qui fait le lien entre le modéle et la vue. Le
 * contrôleur est implémenté en utilisant le pattern State.
 *
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
    protected static final EtatSelectionSecondeIntersection etatSelectionSecondeIntersection = new EtatSelectionSecondeIntersection();
    protected static final EtatIntersectionSelectionnee etatIntersectionSelectionnee = new EtatIntersectionSelectionnee();
    protected static final EtatRemplirInformations etatRemplirInformations = new EtatRemplirInformations();
    protected static final EtatChoixProchaineLivraison etatChoixProchaineLivraison = new EtatChoixProchaineLivraison();

    /**
     * Change l'état courant du contrôleur.
     *
     * @param etat Le nouvel état courant du contrôleur.
     */
    protected static void setEtatCourant(Etat etat) {
        etatCourant = etat;
        etatCourant.activerEtDesactiverFonctionnalites();
    }

    /**
     * Le modèle de données lié au contrôleur. C'est le point d'entrée du
     * contrôleur dans le modèle.
     */
    protected static ModeleManager modeleManager;

    /**
     * La fenêtre graphique de l'application.
     */
    protected static Fenetre fenetre;

    /**
     * La liste des commandes du pattern command
     */
    protected static ListeDeCmdes listeCommandes;

    public static void main(String args[]) {

        Controleur controleur = new Controleur();
    }

    /**
     * Constucteur du contrôleur.
     */
    public Controleur() {

        modeleManager = new ModeleManager();
        fenetre = new Fenetre(this);
        listeCommandes = new ListeDeCmdes();
        Controleur.setEtatCourant(etatInitial);
    }

    public boolean isEtatRemplirInformations() {
        return this.etatCourant == etatRemplirInformations;
    }

    /**
     * Appelle le chargement du plan depuis l'état courant
     * @param file le fichier à partir duquel on doit charger le plan.
     * @return Le plan chargé, null si le plan n'a pu être chargé.
     */
    public synchronized Plan chargerPlan(File file) {
        etatCourant.chargerPlan(file);
        return modeleManager.getPlan();
    }
    
    /**
     * Appelle le chargement de l'ensemble de livraison depuis l'état courant
     * @param file le fichier à partir duquel on doit charger l'ensemble de livraison.
     * @return l'ensemble de livraison chargé, null si l'ensemble de livraison n'a pu être chargé.
     */
    public synchronized EnsembleLivraisons chargerLivraisons(File file) {
        etatCourant.chargerLivraisons(file);
        return modeleManager.getEnsembleLivraisons();
    }

    /**
     * Appelle le chargement de la tournée depuis l'état courant
     * @return La tournée chargée, null si la tournée n'a pu être chargé.
     */
    public synchronized Tournee calculerTournee() {
        etatCourant.calculerTournee();
        return modeleManager.getTournee();
    }

    public synchronized void undo() {
        etatCourant.undo();
    }

    public synchronized void redo() {
        etatCourant.redo();
    }

    public synchronized void clicPlan(int x, int y) {
        etatCourant.clicPlan(x, y);
    }

    public synchronized void selectionerIntersection(Intersection inter) {
        etatCourant.selectionerIntersection(inter);
    }

    public synchronized void demandeAjoutPoint() {
        etatCourant.preparerAjouterPoint();
    }

    public synchronized void ajouterLivraison(DemandeLivraison livraison) {
        etatCourant.ajouterLivraison(livraison);
    }

    public synchronized void supprimerLivraison(DemandeLivraison livraison) {
        etatCourant.supprimerLivraison(livraison);
    }

    public synchronized void genererFeuilleRoute() {
        etatCourant.genererFeuilleRoute();
    }

    public synchronized void echangerDeuxLivraisons() {
        etatCourant.echangerDeuxLivraisons();
    }
}
