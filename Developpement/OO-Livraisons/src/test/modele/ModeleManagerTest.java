package modele;

import java.util.Date;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class ModeleManagerTest {
    @Test
    public void calculerTourneeTest() {
        ModeleManager modele = new ModeleManager();
        Plan plan = modele.getPlan();
        
        Intersection intersection1 = plan.ajouterIntersection(1, 0, 0);
        Intersection intersection2 = plan.ajouterIntersection(2, 0, 1);
        Intersection intersection3 = plan.ajouterIntersection(3, 0, 2);
        Intersection intersection4 = plan.ajouterIntersection(4, 1, 0);
        Intersection intersection5 = plan.ajouterIntersection(5, 1, 1);
        Intersection intersection6 = plan.ajouterIntersection(6, 1, 2);
        
        Troncon troncon1 = plan.ajouterTroncon(1, 2, "Troncon1", 7.0, 1.0);
        Troncon troncon2 = plan.ajouterTroncon(1, 3, "Troncon2", 9.0, 1.0);
        Troncon troncon3 = plan.ajouterTroncon(1, 6, "Troncon3", 14.0, 1.0);
        Troncon troncon4 = plan.ajouterTroncon(2, 4, "Troncon4", 15.0, 1.0);
        Troncon troncon5 = plan.ajouterTroncon(2, 3, "Troncon5", 10.0, 1.0);
        Troncon troncon6 = plan.ajouterTroncon(3, 4, "Troncon6", 11.0, 1.0);
        Troncon troncon7 = plan.ajouterTroncon(3, 6, "Troncon7", 2.0, 1.0);
        Troncon troncon8 = plan.ajouterTroncon(4, 5, "Troncon8", 6.0, 1.0);
        Troncon troncon9 = plan.ajouterTroncon(6, 5, "Troncon9", 9.0, 1.0);
        Troncon troncon10 = plan.ajouterTroncon(2, 1, "Troncon10", 7.0, 1.0);
        Troncon troncon11 = plan.ajouterTroncon(3, 1, "Troncon11", 9.0, 1.0);
        Troncon troncon12 = plan.ajouterTroncon(6, 1, "Troncon12", 14.0, 1.0);
        Troncon troncon13 = plan.ajouterTroncon(4, 2, "Troncon13", 15.0, 1.0);
        Troncon troncon14 = plan.ajouterTroncon(3, 2, "Troncon14", 10.0, 1.0);
        Troncon troncon15 = plan.ajouterTroncon(4, 3, "Troncon15", 11.0, 1.0);
        Troncon troncon16 = plan.ajouterTroncon(6, 3, "Troncon16", 2.0, 1.0);
        Troncon troncon17 = plan.ajouterTroncon(5, 4, "Troncon17", 6.0, 1.0);
        Troncon troncon18 = plan.ajouterTroncon(5, 6, "Troncon18", 9.0, 1.0);
        
        EnsembleLivraisons livraisons = new EnsembleLivraisons();
        modele.setEnsembleLivraisons(livraisons);
        //On fixe l'entrepot sur l'intersection 1.
        livraisons.setEntrepot(intersection1);
        //La fenetre debute le 1 novembre 2015 à 8h00 et se finit à 9h59.
        FenetreLivraison fenetre1 = livraisons.ajouteFenetreDeLivraison(new Date(1446361200), new Date(1446368399));
        //La fenetre debute le 1 novembre 2015 à 10h00 et se finit à 11h59.
        FenetreLivraison fenetre2 = livraisons.ajouteFenetreDeLivraison(new Date(1446368400), new Date(1446375599));
        //On ajoute une livraison a l'intersection 6 entre 8h et 10h.
        fenetre1.ajouterDemandeLivraison(1, 1, intersection6);
        //On ajoute une livraison a l'intersection 4 entre 10h et 12h.
        fenetre2.ajouterDemandeLivraison(2, 2, intersection4);
        //On ajoute une livraison a l'intersection 2 entre 10h et 12h.
        fenetre2.ajouterDemandeLivraison(3, 3, intersection2);
        
        //On calcule la tournee.
        modele.calculerTournee();
    }
}
