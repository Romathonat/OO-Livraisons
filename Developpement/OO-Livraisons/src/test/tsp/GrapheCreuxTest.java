package tsp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class GrapheCreuxTest {
    @Test
    public void aretesTest() {
        GrapheCreux graphe = new GrapheCreux(3);
        graphe.ajouterArc(0, 1, 7.5);
        graphe.ajouterArc(1, 0, 7);
        graphe.ajouterArc(1, 2, 10.645);
        graphe.ajouterArc(2, 1, 10.8);
        graphe.ajouterArc(2, 0, 9.96);
        
        //Teste getNbSommets.
        assertEquals("Le nombre de sommet doit etre egal a 3.", 3, graphe.getNbSommets());
        
        //Teste estArc.
        assertTrue("Il doit y avoir un arc entre 0 et 1.", graphe.estArc(0, 1));
        assertTrue("Il doit y avoir un arc entre 1 et 0.", graphe.estArc(1, 0));
        assertTrue("Il doit y avoir un arc entre 1 et 2.", graphe.estArc(1, 2));
        assertTrue("Il doit y avoir un arc entre 2 et 1.", graphe.estArc(2, 1));
        assertTrue("Il doit y avoir un arc entre 2 et 0.", graphe.estArc(2, 0));
        assertFalse("Il ne doit pas y avoir un arc entre 0 et 2.", graphe.estArc(0, 2));
        
        //Teste getCout.
        assertEquals("Le cout entre 0 et 1 doit etre de 7.5", 7.5, graphe.getCout(0, 1), 0);
        assertEquals("Le cout entre 1 et 0 doit etre de 7.0", 7, graphe.getCout(1, 0), 0);
        assertEquals("Le cout entre 1 et 2 doit etre de 10.645", 10.645, graphe.getCout(1, 2), 0);
        assertEquals("Le cout entre 2 et 1 doit etre de 10.8", 10.8, graphe.getCout(2, 1), 0);
        assertEquals("Le cout entre 2 et 0 doit etre de 9.96", 9.96, graphe.getCout(2, 0), 0);
        assertEquals("Le cout entre 0 et 2 doit etre de -1", -1, graphe.getCout(0, 2), 0);
    }
}
