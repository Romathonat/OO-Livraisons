/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.Iterator;
import modele.Chemin;
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
        this.selectionerIntersection(intersection);
       
    }
    
    @Override
    public void selectionerIntersection(Intersection intersection){
        if( intersection == null){

        } else {
            DemandeLivraison demandeLivraisonArrivee = Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(intersection.getId());
            if(demandeLivraisonArrivee == null){ //si ce n'est pas une demande de livraison
                if (intersection.getId() == Controleur.modeleManager.getEnsembleLivraisons().getEntrepot().getId()){
                    demandeLivraisonArrivee = Controleur.modeleManager.getTournee().getLivraisonEntrepotFictive();
                }
                else{
                    Controleur.fenetre.afficherMessage("Le point selectionné n'est pas valide");
                    return;
                }
            }
            
            Iterator<Chemin> itChemin = Controleur.modeleManager.getTournee().getChemins();
            DemandeLivraison demandeLivraisonAAjouter = Controleur.modeleManager.getBufferLivraison();

            Commande cmde = new CmdeAjoutLivraison(demandeLivraisonAAjouter, demandeLivraisonArrivee);
            Controleur.listeCommandes.ajoute(cmde);
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
            
        } 
    }
}
