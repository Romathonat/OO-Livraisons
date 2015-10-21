package modele;

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
}
