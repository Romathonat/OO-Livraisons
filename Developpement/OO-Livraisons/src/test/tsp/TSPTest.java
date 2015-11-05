package tsp;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class TSPTest {
    @Test
    public void quatreSommetsTest() {
        GrapheCreux graphe = new GrapheCreux(4);
        graphe.ajouterArc(0, 3, 11.56);
        graphe.ajouterArc(3, 1, 12.56);
        graphe.ajouterArc(3, 2, 13.56);
        graphe.ajouterArc(1, 0, 7.56);
        graphe.ajouterArc(1, 2, 15.56);
        graphe.ajouterArc(2, 0, 20.56);
        graphe.ajouterArc(2, 1, 15.56);
        
        TSP1 tsp = new TSP1();
        tsp.chercheSolution(60000, graphe);
        assertEquals("Le cout de la solution optimale doit etre de 48.24.", 48.24, tsp.getCoutSolution(), 0);
        assertEquals("Le premier sommet est 0.", 0, (int)tsp.getSolution(0));
        assertEquals("Le deuxième sommet est 3.", 3, (int)tsp.getSolution(1));
        assertEquals("Le troisième sommet est 2.", 2, (int)tsp.getSolution(2));
        assertEquals("Le quatrième sommet est 1.", 1, (int)tsp.getSolution(3));
    }
    
    @Test
    public void PerformanceTest() {
        TemplateTSP tsp1 = new TSP1();
        TemplateTSP tsp2 = new TSP2();
        TemplateTSP tsp3 = new TSP2();
        
        TemplateTSP tsp = tsp3;
        
        for (int nbSommets = 8; nbSommets <= 14; nbSommets += 2)
        {
            System.out.println("Graphes de "+nbSommets+" sommets :");
            Graphe g = new GrapheComplet(nbSommets);
            long tempsDebut = System.currentTimeMillis();
            tsp.chercheSolution(60000, g);
            System.out.print("Solution de longueur "+tsp.getCoutSolution()+" trouvee en "
                            +(System.currentTimeMillis() - tempsDebut)+"ms : ");
            for (int i=0; i<nbSommets; i++)
                    System.out.print(tsp.getSolution(i)+" ");
            System.out.println();
        }
    }
}
