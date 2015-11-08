/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.DemandeLivraison;
import modele.Intersection;

/**
 *
 * @author Kilian
 */
public class EtatChoixProchaineLivraison extends EtatDefaut {
    @Override
    protected void activerFonctionnalites(){
    }
    
    @Override
    public void clicPlan(int x, int y){
        Intersection intersection = Controleur.fenetre.getVue().getVueGraphique().getIntersection(x, y);
        if( intersection == null){

        } else {
            DemandeLivraison demandeLivraisonArrivee = Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(intersection.getId());
            if(demandeLivraisonArrivee == null){ //si ce n'est pas une demande de livraison
                Controleur.fenetre.afficherMessage("Le point selectionné n'est pas valide");
            } else{
                Controleur.modeleManager.ajouterNouvelleLivraison(demandeLivraisonArrivee);

                Controleur.fenetre.getVue().supprimerInterSelectionee();
                Controleur.fenetre.getVue().updateVueEnsembleLivraisons();
                Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Point de livraison ajouté");
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);//on a fini ce use case, on revient à cet etat
            }
        }        
    }
}
