/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import static java.lang.Math.pow;
import java.util.Iterator;
import java.util.Map;
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
        Iterator<Map.Entry<Integer, Intersection>> itInter = Controleur.modeleManager.getPlan().getIntersections();
        //on cherche si on trouve un point qui correspond à l'endroit où on a cliqué
        int rayon = Controleur.fenetre.getVue().getVueGraphique().getRayonInter();
        boolean interTrouve = false;
        
        while(itInter.hasNext()){
            Intersection monInter = itInter.next().getValue();
            Point coord = Controleur.fenetre.getVue().getVueGraphique().getPointCoordEchelle(monInter.getX(), monInter.getY());
            
            if(pow(coord.x - x,2)+pow(coord.y - y,2) <= pow(rayon,2)){
                interTrouve = true;
                
                if(Controleur.modeleManager.getEnsembleLivraisons().getDemandeLivraison(monInter.getId()) == null){ //si ce n'est pas une demande de livraison
                    Controleur.fenetre.afficherErreurAjoutPoint();
                }
                else{
                    //ici on a deux points selectionnes, on definit la nouvelle tournee et on sera bon
                    
                    Intersection interDepart;
                    Intersection interArrive = monInter;
                    Intersection nouvelleDemande;
                    
                    //on cherche l'interDepart en trouvant la demande de livraison qui precede interArrive
                    //Iterator<Chemin> itChemin = Controleur.fenetre.ge
                    //ATTENTION A CHANGER APRES
                    Controleur.modeleManager.calculerTournee();
                    Controleur.fenetre.getVue().updateTournee(Controleur.modeleManager.getTournee());
                    
                    Controleur.fenetre.getVue().supprimerInterSelectionee();
                    Controleur.setEtatCourant(Controleur.etatTourneeCalculee);//on a fini ce use case, on revient à cet etat
                }
                break;
            }
        }        
    }
    
    
}
