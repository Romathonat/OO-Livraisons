package modele;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author mgaillard
 */
public class DepartArriveeCheminTest {
    @Test
    public void compareToTest() {
        DepartArriveeChemin chemin1 = new DepartArriveeChemin(0, 0);
        DepartArriveeChemin chemin2 = new DepartArriveeChemin(1, 0);
        DepartArriveeChemin chemin3 = new DepartArriveeChemin(1, 1);
        DepartArriveeChemin chemin4 = new DepartArriveeChemin(1, 1);
        
        assertEquals("Le chemin 1 est plus petit que le chemin 2.", -1, chemin1.compareTo(chemin2));
        assertEquals("Le chemin 2 est plus grand que le chemin 1.", 1, chemin2.compareTo(chemin1));
        assertEquals("Le chemin 2 est plus petit que le chemin 3.", -1, chemin2.compareTo(chemin3));
        assertEquals("Le chemin 3 est egal au chemin 4", 0, chemin3.compareTo(chemin4));
        assertEquals("Le chemin 4 est egal au chemin 3", 0, chemin4.compareTo(chemin3));
    }
}
