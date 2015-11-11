/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import modele.*;

/**
 * Interface définissant l'ensemble des actions possibles du contrôleur.
 *
 * @author Kilian
 */
public interface Etat {

    public void chargerPlan(File file);

    public void chargerLivraisons(File file);

    public void calculerTournee();

    public void clicPlan(int x, int y);

    public void undo();

    public void redo();

    public void ajouterLivraison(DemandeLivraison livraison);

    public void genererFeuilleRoute();

    public void echangerDeuxLivraisons();

    public void supprimerLivraison(DemandeLivraison livraison);

    public void activerEtDesactiverFonctionnalites();

    public void preparerAjouterPoint();

    public void selectionerIntersection(Intersection inter);
}
