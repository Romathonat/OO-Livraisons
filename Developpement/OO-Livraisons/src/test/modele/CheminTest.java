package modele;

import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author mgaillard
 */
public class CheminTest {
    @Test
    public void testConstructeur() {
        assertNotNull("Le constructeur de Chemin retourne null.", new Chemin());
    }
    
    @Test
    public void testDuree() {
        Chemin chemin = new Chemin();
        //Si le chemin ne contient aucun troncon, la duree retournée est 0.
        assertEquals("La duree n'est pas de 0.", 0.0, chemin.getDuree(), 0.0);
        //On ajoute des Troncons.
        Intersection intersection1 = new Intersection(1, 0, 0);
        Intersection intersection2 = new Intersection(2, 0, 10);
        Intersection intersection3 = new Intersection(3, 10, 10);
        Troncon troncon1 = new Troncon(intersection1, intersection2, "Troncon1", 10.0, 1.0);
        Troncon troncon2 = new Troncon(intersection2, intersection3, "Troncon2", 10.0, 1.0);
        chemin.ajouterTroncon(troncon1);
        chemin.ajouterTroncon(troncon2);
        //On teste si la duree retourne par le chemin est bien la somme des durees des deux troncons.
        assertEquals("La duree du chemin n'est pas bonne.", troncon1.getDuree() + troncon2.getDuree(), chemin.getDuree(), 0.0);
    }
    
    @Test
    public void testTroncon() {
        Chemin chemin = new Chemin();
        //On ajoute des Troncons.
        Intersection intersection1 = new Intersection(1, 0, 0);
        Intersection intersection2 = new Intersection(2, 0, 10);
        Intersection intersection3 = new Intersection(3, 10, 10);
        Troncon troncon1 = new Troncon(intersection1, intersection2, "Troncon1", 10.0, 1.0);
        Troncon troncon2 = new Troncon(intersection2, intersection3, "Troncon2", 10.0, 1.0);
        //Un troncon qui ne peut pas etre ajouté après le deuxième troncon.
        Troncon troncon3 = new Troncon(intersection1, intersection3, "Troncon3", 10.0, 1.0);
        assertTrue("Il doit etre possible d'ajouter un premier troncon.", chemin.ajouterTroncon(troncon1));
        assertTrue("Il doit etre possible d'ajouter un deuxieme troncon.", chemin.ajouterTroncon(troncon2));
        assertFalse("Il est impossible d'ajouter un troncon", chemin.ajouterTroncon(troncon3));
        //On verifie que les troncons sont bien ajoutes.
        Iterator<Troncon> itTroncon = chemin.getTroncons();
        assertEquals("Le premier troncon du chemin n'est pas le troncon 1.", troncon1, itTroncon.next());
        assertEquals("Le deuxieme troncon du chemin n'est pas le troncon 2.", troncon2, itTroncon.next());
        assertFalse("Il ne doit pas y avoir de troisieme troncon.", itTroncon.hasNext());
    }
    
    @Test
    public void testIntersections() {
        Chemin chemin = new Chemin();
        //Il ne peut pas y avoir d'Intersection de depart ni d'arrivee.
        assertNull("Il ne peut pas y avoir d'Intersection de depart.", chemin.getIntersectionDepart());
        assertNull("Il ne peut pas y avoir d'Intersection d'arrivee.", chemin.getIntersectionArrivee());
        
        //On ajoute un Troncon.
        Intersection intersection1 = new Intersection(1, 0, 0);
        Intersection intersection2 = new Intersection(2, 0, 10);
        Troncon troncon1 = new Troncon(intersection1, intersection2, "Troncon1", 10.0, 1.0);
        chemin.ajouterTroncon(troncon1);
        
        //On controle les intersections de depart et d'arrivee.
        assertEquals("Le depart du chemin n'est pas l'intersection 1.", intersection1, chemin.getIntersectionDepart());
        assertEquals("Le depart du chemin n'est pas l'intersection 2.", intersection2, chemin.getIntersectionArrivee());
    }
    
    @Test
    public void testLivraisonArrivee() {
        Chemin chemin = new Chemin();
        //On ajoute un Troncon.
        Intersection intersection1 = new Intersection(1, 0, 0);
        Intersection intersection2 = new Intersection(2, 0, 10);
        Intersection intersection3 = new Intersection(3, 10, 10);
        Troncon troncon1 = new Troncon(intersection1, intersection2, "Troncon1", 10.0, 1.0);
        Troncon troncon2 = new Troncon(intersection2, intersection3, "Troncon2", 10.0, 1.0);
        //On ajoute seulement le premier troncon.
        chemin.ajouterTroncon(troncon1);
        //On met une demande de livraison sur l'intersection 3.
        DemandeLivraison livraison = new DemandeLivraison(intersection3, null);
        //On essaye de definit la livraison.
        assertFalse("La livraison arrivee, doit etre sur la meme intersection que le dernier troncon du chemin.", chemin.setLivraisonArrivee(livraison));
        //On ajoute le troncon 2.
        chemin.ajouterTroncon(troncon2);
        //Cette fois, la livraison peut etre ajoutee.
        assertTrue("La livraison arrivee, doit pouvoir etre ajoute sur l'intersection d'arrivee du chemin.", chemin.setLivraisonArrivee(livraison));
        //On controle la livraison.
        assertEquals("La livraison arrivee n'est pas celle definie plus tot.", livraison, chemin.getLivraisonArrivee());
    }
}
