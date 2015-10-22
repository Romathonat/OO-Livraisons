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
        
        Plan plan = new Plan();
        
        //On ajoute les intersections au plan.
        plan.ajouterIntersection(1, 0, 0);
        plan.ajouterIntersection(5, 0, 1);
        plan.ajouterIntersection(8, 1, 0);
        plan.ajouterIntersection(9, 1, 1);
        
        //On teste que les intersections sont dans le plan. TEST A REECRIRE
        //assertEquals("L'intersection 1 n'a pas ete trouvee.", Intersection(1,0,0), plan.getIntersection(1));
        //assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection5, plan.getIntersection(5));
        //assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection8, plan.getIntersection(8));
        //assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection9, plan.getIntersection(9));
    }
}
