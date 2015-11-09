/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.DemandeLivraison;

/**
 *
 * @author Kilian
 */
public class EtatRemplirInformations extends EtatDefaut {
    @Override
    protected void activerFonctionnalites(){
    }
    
    /** 
     * Ajoute à la tournee cette Demande si elle est valide (pas de conflit d'id)
     * @param demandeLivraison 
     */
    @Override
    public void ajouterLivraison(DemandeLivraison demandeLivraison) {
        if(Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(demandeLivraison.getId()) == null){
            Controleur.modeleManager.setBufferLivraison(demandeLivraison);
            Controleur.fenetre.getVue().getVueStatus().updateStatusDroit("Selectionnez une demande de livraison");
            Controleur.setEtatCourant(Controleur.etatChoixProchaineLivraison);
        }
        else{
            Controleur.fenetre.afficherMessage("Le point selectionné n'est pas valide");
        }
    }
}
