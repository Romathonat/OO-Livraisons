/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.Intersection;

/**
 *
 * @author Kilian
 */
public class EtatChoixProchaineLivraison extends EtatDefaut {
    @Override
    protected void activerFonctionnalites(){
    }
    
    public void clicPlan(int x, int y){
        Intersection intersection = Controleur.fenetre.getVue().getVueGraphique().getIntersection(x, y);
        if( intersection == null){

        } else {
            if(Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(intersection.getId()) == null){ //si ce n'est pas une demande de livraison
                Controleur.fenetre.afficherMessage("Le point selectionné n'est pas valide");
            } else{
                Controleur.modeleManager.ajouterNouvelleLivraison(intersection,
                        Controleur.modeleManager.getPlan().getIntersection(Controleur.fenetre.getVue().getPremiereInterSelectionnee()));

                //ATTENTION A CHANGER APRES
                //Controleur.modeleManager.calculerTournee();
                //Controleur.fenetre.getVue().updateTournee(Controleur.modeleManager.getTournee());

                Controleur.fenetre.getVue().supprimerInterSelectionee();
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);//on a fini ce use case, on revient à cet etat
            }
        }        
    }
}
