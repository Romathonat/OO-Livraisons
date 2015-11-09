/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import java.util.List;
import modele.*;

/**
 * Interface définissant l'ensemble des actions possibles du contrôleur.
 * @author Kilian
 */
public interface Etat {
    public void chargerPlan(File file);
    public void chargerLivraisons(File file);
    public void calculerTournee();
    public void clicPlan(int x, int y);
    public void undo();
    public void redo();
    public void ajouterLivraison(DemandeLivraison livraison/*, List<Commande> listeCmde*/);
    public void genererFeuilleRoute();
    public void echangerDeuxLivraisons(/* List<Commande> listeCmde*/);
    public void supprimerLivraison(DemandeLivraison livraison/*, Tournee tournee, List<Commande> listeCmde*/);
    public void fermer();
    public void valider();
    public void activerEtDesactiverFonctionnalites();
    public void preparerAjouterPoint();
    public void selectionerIntersection(Intersection inter);
}
