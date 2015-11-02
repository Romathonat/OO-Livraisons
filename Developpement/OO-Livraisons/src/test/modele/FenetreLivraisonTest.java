package modele;

import java.util.Iterator;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class FenetreLivraisonTest {
    @Test
    public void getIdIntersectionsLivraisonsTest() {
        FenetreLivraison fenetre = new FenetreLivraison(null, null);
        
        Plan plan = new Plan();
        Intersection intersection1 = plan.ajouterIntersection(1, 1, 1);
        Intersection intersection2 = plan.ajouterIntersection(2, 2, 2);
        Intersection intersection3 = plan.ajouterIntersection(3, 3, 3);
        
        fenetre.ajouterDemandeLivraison(1, 1, intersection1);
        fenetre.ajouterDemandeLivraison(2, 2, intersection2);
        fenetre.ajouterDemandeLivraison(3, 3, intersection3);
        
        Set<Integer> idIntersections = fenetre.getIdIntersectionsLivraisons();
        assertEquals("L'ensemble des id des intersections ne contient pas 3 elements.", 3, idIntersections.size());
        assertTrue("L'ensemble des id des intersections ne contient pas 1.", idIntersections.contains(1));
        assertTrue("L'ensemble des id des intersections ne contient pas 2.", idIntersections.contains(2));
        assertTrue("L'ensemble des id des intersections ne contient pas 3.", idIntersections.contains(3));
    }
}
