package modele;

import java.util.Date;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        
        EnsembleLivraisons livraisons = modele.getEnsembleLivraisons();
        //On fixe l'entrepot sur l'intersection 1.
        livraisons.setEntrepot(intersection1);
        //La fenetre debute le 1 novembre 2015 à 8h00 et se finit à 9h59.
        FenetreLivraison fenetre1 = livraisons.ajouteFenetreDeLivraison(new Date(1446361200000L), new Date(1446368399000L));
        //La fenetre debute le 1 novembre 2015 à 10h00 et se finit à 11h59.
        FenetreLivraison fenetre2 = livraisons.ajouteFenetreDeLivraison(new Date(1446368400000L), new Date(1446375599000L));
        //On ajoute une livraison a l'intersection 6 entre 8h et 10h.
        fenetre1.ajouterDemandeLivraison(1, 1, intersection6);
        //On ajoute une livraison a l'intersection 4 entre 10h et 12h.
        fenetre2.ajouterDemandeLivraison(2, 2, intersection4);
        //On ajoute une livraison a l'intersection 2 entre 10h et 12h.
        fenetre2.ajouterDemandeLivraison(3, 3, intersection2);
        
        //On calcule la tournee.
        modele.calculerTournee();
        
        assertEquals("La tournee doit faire une duree de 46.0.", 46.0, modele.getTournee().getTempsDeLivraison(), 0.1);
        Iterator<Chemin> itChemin = modele.getTournee().getChemins();
        assertNotNull("L'iterateur de chemin de la tournee doit au moins avoir un element.", itChemin.hasNext());
        Chemin chemin1 = itChemin.next();
        assertEquals("Le premier chemin doit partir de 1.", 1, chemin1.getIntersectionDepart().getId());
        assertEquals("Le premier chemin doit finir en 6.", 6, chemin1.getIntersectionArrivee().getId());
        
        assertNotNull("L'iterateur de chemin de la tournee doit au moins avoir deux elements.", itChemin.hasNext());
        Chemin chemin2 = itChemin.next();
        assertEquals("Le deuxieme chemin doit partir de 6.", 6, chemin2.getIntersectionDepart().getId());
        assertEquals("Le deuxieme chemin doit finir en 4.", 4, chemin2.getIntersectionArrivee().getId());
        
        assertNotNull("L'iterateur de chemin de la tournee doit au moins avoir trois elements.", itChemin.hasNext());
        Chemin chemin3 = itChemin.next();
        assertEquals("Le troisieme chemin doit partir de 4.", 4, chemin3.getIntersectionDepart().getId());
        assertEquals("Le troisieme chemin doit finir en 2.", 2, chemin3.getIntersectionArrivee().getId());
        
        assertNotNull("L'iterateur de chemin de la tournee doit au moins avoir quatre elements.", itChemin.hasNext());
        Chemin chemin4 = itChemin.next();
        assertEquals("Le quatrieme chemin doit partir de 2.", 2, chemin4.getIntersectionDepart().getId());
        assertEquals("Le quatrieme chemin doit finir en 1.", 1, chemin4.getIntersectionArrivee().getId());
        
        assertFalse("L'iterateur de chemin de la tournee ne doit pas avoir cinq elements.", itChemin.hasNext());
    }
}
