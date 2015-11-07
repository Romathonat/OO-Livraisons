/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.Tournee;

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
     * @param livraison
     * @param tournee 
     */
    public void ajouterLivraison(DemandeLivraison livraison) {
        if(Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(livraison.getId()) == null){
            //ajout de la demande dans la bonne fenetre de livraison (peut paraître bizarre ici c'est normal)
            livraison.getFenetreLivraison().ajouterDemandeLivraison(livraison.getId(), livraison.getIdClient(), livraison.getIntersection());
            Controleur.setEtatCourant(Controleur.etatChoixProchaineLivraison);
        }
        else{
            Controleur.fenetre.afficherErreurAjoutPoint();
        }
    }
}
