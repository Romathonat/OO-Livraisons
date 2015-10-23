package modele;

import java.util.Iterator;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
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
        Intersection intersection1 = plan.getIntersection(1);
        assertNotNull("L'intersection 1 n'a pas ete trouvee.", intersection1);
        assertEquals("L'id de l'intersection 1 n'est pas bonne.", 1, intersection1.getId());
        assertEquals("La coordonnée X de l'intersection 1 n'est pas bonne.", 0, intersection1.getX());
        assertEquals("La coordonnée Y de l'intersection 1 n'est pas bonne.", 0, intersection1.getY());
        
        Intersection intersection5 = plan.getIntersection(5);
        assertNotNull("L'intersection 5 n'a pas ete trouvee.", intersection5);
        assertEquals("L'id de l'intersection 5 n'est pas bonne.", 5, intersection5.getId());
        assertEquals("La coordonnée X de l'intersection 5 n'est pas bonne.", 0, intersection5.getX());
        assertEquals("La coordonnée Y de l'intersection 5 n'est pas bonne.", 1, intersection5.getY());
        
        Intersection intersection8 = plan.getIntersection(8);
        assertNotNull("L'intersection 8 n'a pas ete trouvee.", intersection8);
        assertEquals("L'id de l'intersection 8 n'est pas bonne.", 8, intersection8.getId());
        assertEquals("La coordonnée X de l'intersection 8 n'est pas bonne.", 1, intersection8.getX());
        assertEquals("La coordonnée Y de l'intersection 8 n'est pas bonne.", 0, intersection8.getY());
        
        Intersection intersection9 = plan.getIntersection(9);
        assertNotNull("L'intersection 9 n'a pas ete trouvee.", intersection9);
        assertEquals("L'id de l'intersection 9 n'est pas bonne.", 9, intersection9.getId());
        assertEquals("La coordonnée X de l'intersection 9 n'est pas bonne.", 1, intersection9.getX());
        assertEquals("La coordonnée Y de l'intersection 9 n'est pas bonne.", 1, intersection9.getY());
    }
    
    @Test
    public void testAjouterTroncon() {
        Plan plan = new Plan();
        
        //On ajoute les intersections au plan.
        plan.ajouterIntersection(1, 0, 0);
        plan.ajouterIntersection(2, 0, 1);
        plan.ajouterIntersection(3, 1, 0);
        
        //On ajoute des troncons au plan.
        assertTrue("Impossible d'ajouter le troncon 1 au plan.", plan.ajouterTroncon(1, 2, "Troncon1", 10.0, 1.0));
        assertTrue("Impossible d'ajouter le troncon 2 au plan.", plan.ajouterTroncon(1, 3, "Troncon2", 10.0, 1.0));
        //On ajoute des intersections qui ne peuvent pas exister.
        assertFalse("On ne doit pas pouvoir ajouter un troncon arrivant sur une Intersection qui n'existe pas", plan.ajouterTroncon(1, 4, "FauxTroncon1", 10.0, 1.0));
        assertFalse("On ne doit pas pouvoir ajouter un troncon partant d'une Intersection qui n'existe pas", plan.ajouterTroncon(4, 1, "FauxTroncon2", 10.0, 1.0));
        assertFalse("On ne doit pas pouvoir ajouter un troncon entre deux Intersections qui n'existent pas", plan.ajouterTroncon(4, 5, "FauxTroncon3", 10.0, 1.0));
        
        //On teste que l'intersection1 a des troncons successeurs.
        Intersection intersection = plan.getIntersection(1);
        Iterator<Troncon> itTroncon = intersection.getTronconsSortants();
        Troncon troncon1 = itTroncon.next();
        assertEquals("Le nom du premier troncon sortant de l'intersection 1 n'est pas bon.", "Troncon1", troncon1.getNom());
        Troncon troncon2 = itTroncon.next();
        assertEquals("Le nom du premier troncon sortant de l'intersection 1 n'est pas bon.", "Troncon2", troncon2.getNom());
        //On verifie qu'il n'y a pas d'autre troncon.
        assertFalse("Il ne devrait pas y avoir de troncon sortant de l'intersection 1 en plus.", itTroncon.hasNext());
    }
    
    @Test
    public void testEntrepot() {
        Plan plan = new Plan();
        
        //On ajoute une intersection au plan.
        plan.ajouterIntersection(1, 0, 0);
        
        //On tente de definir un mauvais entrepot.
        assertNull("L'entrepot ne peux pas se situer sur une Intersection qui n'existe pas.", plan.setEntrepot(2));
        //On definit un vrai entrepot.
        assertNotNull("L'entrepot doit pouvoir se situer sur l'intersection 1.", plan.setEntrepot(1));
        //On verifie l'id de l'intersection de l'entrepot.
        assertEquals("L'entrepot doit se situer sur l'intersection 1.", 1, plan.getEntrepot().getId());
    }
}
