/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.Iterator;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;

/**
 *
 * @author Kilian
 */
public class EtatPointLivraisonSelectionne extends EtatSelection {

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
        /*Controleur.modeleManager.supprimerDemandeLivraison(demandeLivraison);
        Controleur.fenetre.getVue().supprimerInterSelectionee();
        Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
        Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Point de livraison supprimé");*/
        
        DemandeLivraison prevDemandeLivraisonASupprimer = null;
        
        //on cherche la livraison precedant celle à supprimer
        
        EnsembleLivraisons ensembleLivraisons = Controleur.modeleManager.getEnsembleLivraisons();
        if(ensembleLivraisons != null){
            Iterator<FenetreLivraison> it_fenetre = ensembleLivraisons.getFenetresLivraison();
            Iterator<DemandeLivraison> it_demande = null;
            while (it_fenetre.hasNext()) {
                it_demande = it_fenetre.next().getDemandesLivraison();
                while (it_demande.hasNext()) {
                    DemandeLivraison currDemande =  it_demande.next();
                    if(currDemande == demandeLivraison)
                        continue;
                    prevDemandeLivraisonASupprimer = currDemande;
                }
            }
        }
        
        System.out.println(prevDemandeLivraisonASupprimer);
        
        Commande cmde = new CmdeSuppressionLivraison(demandeLivraison, prevDemandeLivraisonASupprimer);
        Controleur.listeCommandes.ajoute(cmde);
        
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);//on a fini ce use case, on revient à cet etat
    }
    
    @Override
    public void echangerDeuxLivraisons() {
        Controleur.setEtatCourant(Controleur.etatdeuxPointsLivraisonSelectionnes);
    }
}
