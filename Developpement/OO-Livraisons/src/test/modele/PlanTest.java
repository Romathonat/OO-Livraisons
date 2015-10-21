package modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class PlanTest {
    @Test
    public void testConstructeur() {
        assertNotNull("Le constructeur de Plan retourne null.", new Plan());
    }
    
    @Test
    public void testIntersections() {
        Intersection intersection1 = new Intersection(1, 0, 0);
        Intersection intersection5 = new Intersection(5, 0, 1);
        Intersection intersection8 = new Intersection(8, 1, 0);
        Intersection intersection9 = new Intersection(9, 1, 1);
        
        Plan plan = new Plan();
        
        //On ajoute les intersections au plan.
        plan.ajouterIntersection(intersection1);
        plan.ajouterIntersection(intersection5);
        plan.ajouterIntersection(intersection8);
        plan.ajouterIntersection(intersection9);
        
        //On teste que les intersections sont dans le plan.
        assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection1, plan.getIntersection(1));
        assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection5, plan.getIntersection(5));
        assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection8, plan.getIntersection(8));
        assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection9, plan.getIntersection(9));
    }
}
