/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.Iterator;
import modele.Chemin;
import modele.DemandeLivraison;

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

        // on récupère la première demande de livraison et on la stocke dans le buffer du modeleManager.
        DemandeLivraison demande1 = Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(
                Controleur.fenetre.getVue().getPremiereInterSelectionnee());
        Controleur.modeleManager.setBufferLivraison(demande1);

        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("En attente d'un second point de livraison à échanger.");
        Controleur.setEtatCourant(Controleur.etatdeuxPointsLivraisonSelectionnes);
    }
}
