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
        Intersection intersection1 = plan.ajouterIntersection(1, 0, 0);
        Intersection intersection5 = plan.ajouterIntersection(5, 0, 1);
        Intersection intersection8 = plan.ajouterIntersection(8, 1, 0);
        Intersection intersection9 = plan.ajouterIntersection(9, 1, 1);
        
        //On teste que les intersections sont dans le plan.
        assertEquals("L'intersection 1 n'a pas ete trouvee.", intersection1, plan.getIntersection(1));
        assertEquals("L'intersection 5 n'a pas ete trouvee.", intersection5, plan.getIntersection(5));
        assertEquals("L'intersection 8 n'a pas ete trouvee.", intersection8, plan.getIntersection(8));
        assertEquals("L'intersection 9 n'a pas ete trouvee.", intersection9, plan.getIntersection(9));
        
        //On teste qu'il n'est pas possible d'ajouter une intersection dont l'id est nul.
        assertNull("Il est impossible d'ajouter une intersection dont l'id est nul.", plan.ajouterIntersection(-1, 0, 0));
    }
    
    @Test
    public void testAjouterTroncon() {
        Plan plan = new Plan();
        
        //On ajoute les intersections au plan.
        plan.ajouterIntersection(1, 0, 0);
        plan.ajouterIntersection(2, 0, 1);
        plan.ajouterIntersection(3, 1, 0);
        
        //On ajoute des troncons au plan.
        assertNotNull("Impossible d'ajouter le troncon 1 au plan.", plan.ajouterTroncon(1, 2, "Troncon1", 10.0, 1.0));
        assertNotNull("Impossible d'ajouter le troncon 2 au plan.", plan.ajouterTroncon(1, 3, "Troncon2", 10.0, 1.0));
        //On ajoute des intersections qui ne peuvent pas exister.
        assertNull("On ne doit pas pouvoir ajouter un troncon arrivant sur une Intersection qui n'existe pas", plan.ajouterTroncon(1, 4, "FauxTroncon1", 10.0, 1.0));
        assertNull("On ne doit pas pouvoir ajouter un troncon partant d'une Intersection qui n'existe pas", plan.ajouterTroncon(4, 1, "FauxTroncon2", 10.0, 1.0));
        assertNull("On ne doit pas pouvoir ajouter un troncon entre deux Intersections qui n'existent pas", plan.ajouterTroncon(4, 5, "FauxTroncon3", 10.0, 1.0));
        
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
    
    @Test
    public void testCalculerPlusCourtChemin() {
        Plan plan = new Plan();
        
        Intersection intersection1 = plan.ajouterIntersection(1, 0, 0);
        Intersection intersection2 = plan.ajouterIntersection(2, 0, 1);
        Intersection intersection3 = plan.ajouterIntersection(3, 0, 2);
        Intersection intersection4 = plan.ajouterIntersection(4, 1, 0);
        Intersection intersection5 = plan.ajouterIntersection(5, 1, 1);
        Intersection intersection6 = plan.ajouterIntersection(6, 1, 2);
        
        Troncon troncon1 = plan.ajouterTroncon(1, 2, "Troncon1", 7.0, 1.0);
        Troncon troncon2 = plan.ajouterTroncon(1, 3, "Troncon2", 9.0, 1.0);
        Troncon troncon3 = plan.ajouterTroncon(1, 6, "Troncon3", 14.0, 1.0);
        Troncon troncon4 = plan.ajouterTroncon(2, 4, "Troncon4", 15.0, 1.0);
        Troncon troncon5 = plan.ajouterTroncon(2, 3, "Troncon5", 10.0, 1.0);
        Troncon troncon6 = plan.ajouterTroncon(3, 4, "Troncon6", 11.0, 1.0);
        Troncon troncon7 = plan.ajouterTroncon(3, 6, "Troncon7", 2.0, 1.0);
        Troncon troncon8 = plan.ajouterTroncon(4, 5, "Troncon8", 6.0, 1.0);
        Troncon troncon9 = plan.ajouterTroncon(6, 5, "Troncon9", 9.0, 1.0);
        
        Chemin chemin = plan.calculerPlusCourtChemin(intersection1, intersection5);
        
        System.out.println("Chemin de 1 vers 5 : ");
        Iterator<Troncon> itTroncon = chemin.getTroncons();
        
        assertEquals("Le premier troncon du chemin le plus court est le troncon 2.", troncon2, itTroncon.next());
        assertEquals("Le deuxieme troncon du chemin le plus court est le troncon 7.", troncon7, itTroncon.next());
        assertEquals("Le troisieme troncon du chemin le plus court est le troncon 9.", troncon9, itTroncon.next());
        assertFalse("Le chemin se finit apres le troisieme troncon.", itTroncon.hasNext());
    }
}
