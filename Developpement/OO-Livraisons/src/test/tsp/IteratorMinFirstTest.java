package tsp;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class IteratorMinFirstTest {
    @Test
    public void iteratorTest() {
        GrapheCreux graphe = new GrapheCreux(4);
        graphe.ajouterArc(0, 1, 1.2);
        graphe.ajouterArc(0, 2, 5.8);
        graphe.ajouterArc(0, 3, 0.8);
        
        ArrayList<Integer> nonVus = new ArrayList<>(3);
        nonVus.add(1);
        nonVus.add(2);
        nonVus.add(3);
        IteratorMinFirst it = new IteratorMinFirst(nonVus, 0, graphe);
        
        assertTrue("L'iterateur doit retourner un sommet au moins.", it.hasNext());
        assertEquals("le sommet le plus proche est 3.", 3, (int)it.next());
        assertEquals("le deuxieme sommet le plus proche est 1.", 1, (int)it.next());
        assertEquals("le troisieme sommet le plus proche est 2.", 2, (int)it.next());
    }
}
