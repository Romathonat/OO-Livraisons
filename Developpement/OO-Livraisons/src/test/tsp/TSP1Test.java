package tsp;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class TSP1Test {
    @Test
    public void quatreSommetsTest() {
        GrapheCreux graphe = new GrapheCreux(4);
        graphe.ajouterArc(0, 3, 11);
        graphe.ajouterArc(3, 1, 12);
        graphe.ajouterArc(3, 2, 13);
        graphe.ajouterArc(1, 0, 7);
        graphe.ajouterArc(1, 2, 15);
        graphe.ajouterArc(2, 0, 20);
        graphe.ajouterArc(2, 1, 15);
        
        TSP1 tsp = new TSP1();
        tsp.chercheSolution(60000, graphe);
        assertEquals("Le cout de la solution optimale doit etre de 46.", 46, tsp.getCoutSolution());
        assertEquals("Le premier sommet est 0.", 0, (int)tsp.getSolution(0));
        assertEquals("Le premier sommet est 3.", 3, (int)tsp.getSolution(1));
        assertEquals("Le premier sommet est 2.", 2, (int)tsp.getSolution(2));
        assertEquals("Le premier sommet est 1.", 1, (int)tsp.getSolution(3));
    }
}
