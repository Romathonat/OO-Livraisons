/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.List;
import modele.*;

/**
 * Interface définissant l'ensemble des actions possibles du contrôleur.
 * @author Kilian
 */
public interface Etat {
    public void chargerPlan();
    public void chargerLivraisons();
    public void calculerTournee();
    public void clicPlan(int x, int y);
    public void clicIntersection(Intersection intersection);
    public void clicPointLivraison(Intersection intersection);
    public void clicVide();
    public void undo(List<Commande> listeCmde);
    public void redo(List<Commande> listeCmde);
    public void ajouterLivraison(DemandeLivraison livraison, Tournee tournee, List<Commande> listeCmde);
    public void genererFeuilleRoute();
    public void echangerDeuxLivraisons(DemandeLivraison livraison1, DemandeLivraison livraison2, Tournee tournee, List<Commande> listeCmde);
    public void supprimerLivraison(DemandeLivraison livraison, Tournee tournee, List<Commande> listeCmde);
    public void fermer();
    public void valider();
    public void activerEtDesactiverFonctionnalites();
}
