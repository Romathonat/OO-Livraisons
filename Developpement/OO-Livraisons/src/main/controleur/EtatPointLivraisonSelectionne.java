/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.Iterator;
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;

/**
 *
 * @author Kilian
 */
public class EtatPointLivraisonSelectionne extends EtatTourneeCalculee {

    @Override
    protected void activerFonctionnalites() {
        super.activerFonctionnalites();
        Controleur.fenetre.activerEchangerLivraison(true);
        Controleur.fenetre.activerSupprimerLivraison(true);
    }

    /**
     * Ajoute à la tournee cette Demande si elle est valide (pas de conflit
     * d'id)
     *
     * @param demandeLivraison
     */
    @Override
    public void supprimerLivraison(DemandeLivraison demandeLivraison) {
        
        Iterator<Chemin> itChemin = Controleur.modeleManager.getTournee().getChemins();
        DemandeLivraison nextDemandeLivraisonASupprimer = null;
        
        while (itChemin.hasNext()) {
            Chemin chemin = itChemin.next();
            if (chemin.getLivraisonArrivee() == demandeLivraison) {
                nextDemandeLivraisonASupprimer = itChemin.next().getLivraisonArrivee();
            }
        }
        Commande cmde = new CmdeSuppressionLivraison(demandeLivraison, nextDemandeLivraisonASupprimer);
        Controleur.listeCommandes.ajoute(cmde);
        
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);//on a fini ce use case, on revient à cet etat
    }
    
    @Override
    public void echangerDeuxLivraisons() {
        Controleur.setEtatCourant(Controleur.etatdeuxPointsLivraisonSelectionnes);
    }
}
