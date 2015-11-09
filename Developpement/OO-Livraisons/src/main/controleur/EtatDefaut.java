/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import java.util.List;
import modele.DemandeLivraison;
import modele.*;

/**
 *
 * @author Kilian
 */
public class EtatDefaut implements Etat{

    @Override
    public void chargerPlan(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void chargerLivraisons(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void calculerTournee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void redo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ajouterLivraison(DemandeLivraison livraison/*, List<Commande> listeCmde*/) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void genererFeuilleRoute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void echangerDeuxLivraisons(/*List<Commande> listeCmde*/) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerLivraison(DemandeLivraison livraison/*, Tournee tournee, List<Commande> listeCmde*/) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fermer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valider() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    protected void activerFonctionnalites(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public final void activerEtDesactiverFonctionnalites(){
        Controleur.fenetre.toutDesactiver();
        this.activerFonctionnalites();
    }

    @Override
    public void clicPlan(int x, int y) {}

    

    @Override
    public void preparerAjouterPoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void selectionerIntersection(Intersection inter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}