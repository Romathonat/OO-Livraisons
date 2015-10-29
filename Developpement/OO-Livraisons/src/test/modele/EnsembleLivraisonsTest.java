package modele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Guillaume Kheng
 */
public class EnsembleLivraisonsTest {

    @Test
    public void testConstructeur() {
        assertNotNull("Le constructeur de EnsembleLivraisons retourne null.", new EnsembleLivraisonsTest());
    }

    @Test
    public void testEntrepot() {
        EnsembleLivraisons ensembleLivraisons = new EnsembleLivraisons();
        Plan plan = new Plan();

        //On ajoute une intersection au plan.
        Intersection inter = plan.ajouterIntersection(1, 0, 0);

        //On tente de definir un mauvais entrepot.
        assertNull("L'entrepot ne peux pas se situer sur une Intersection qui n'existe pas.", ensembleLivraisons.setEntrepot(null));
        //On definit un vrai entrepot.
        assertNotNull("L'entrepot doit pouvoir se situer sur l'intersection 1.", ensembleLivraisons.setEntrepot(inter));
        //On verifie l'id de l'intersection de l'entrepot.
        assertEquals("L'entrepot doit se situer sur l'intersection 1.", 1, ensembleLivraisons.getEntrepot().getId());
    }

    @Test
    public void testajouteFenetreDeLivraison() {

        EnsembleLivraisons ensembleLivraisons = new EnsembleLivraisons();

        String str_date1 = "16:00:00"; // 16h00
        String str_date2 = "17:00:00"; // 17h00
        String str_date3 = "18:00:00"; // 18h00
        String str_date4 = "19:00:00"; // 19h00
        String str_date5 = "20:00:00"; // 20h00
        String str_date6 = "21:00:00"; // 21h00
        String str_date7 = "22:00:00"; // 22h00

        // Utilitaire pour parser la date.
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null, date6 = null, date7 = null;
        try {
            date1 = sdf.parse(str_date1);
            date2 = sdf.parse(str_date2);
            date3 = sdf.parse(str_date3);
            date4 = sdf.parse(str_date4);
            date5 = sdf.parse(str_date5);
            date6 = sdf.parse(str_date6);
            date7 = sdf.parse(str_date7);
        } catch (ParseException e) {
            assertNotNull("problème de parsing", null); // pas censé se produire.
        }

        FenetreLivraison fenetre = null;
        
        // Fenetre de livraison incohérente (DateDebut > DateFin).
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date2, date1);
        assertNull("La création d'une fenetre temporelle doit respecter dateDébut antérieure à dateFin", fenetre);
        // Fenetre de livraison incohérente (DateDebut = DateFin).
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date1, date1);
        assertNull("La création d'une fenetre temporelle doit respecter dateDébut antérieure à dateFin", fenetre);

        // Ajout d'une fenêtre.
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date2, date5);
        assertNotNull("La création d'une fenetre temporelle qui respecte les condition de cohérence est possible", fenetre);

        // Tentative de création de chevauchement de fenetres temporelles.
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date1, date3);
        assertNull("Deux fenetres temporelles ne peuvent se chevaucher / type BABA", fenetre);
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date1, date6);
        assertNull("Deux fenetres temporelles ne peuvent se chevaucher / type BAAB", fenetre);
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date4, date6);
        assertNull("Deux fenetres temporelles ne peuvent se chevaucher / type ABAB", fenetre);
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date3, date4);
        assertNull("Deux fenetres temporelles ne peuvent se chevaucher / type ABBA", fenetre);

        // ajout d'une seconde fenetre ne chevauchant pas la première.
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date1, date2);
        assertNotNull("On peut ajouter une fenetre temporelle qui n'en chevauche pas d'autre avant toutes les autres.", fenetre);
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date6, date7);
        assertNotNull("On peut ajouter une fenetre temporelle qui n'en chevauche pas d'autre après toutes les autres.", fenetre);
        fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(date5, date6);
        assertNotNull("On peut ajouter une fenetre temporelle qui n'en chevauche pas d'autre entre toutes les autres.", fenetre);
    }

}
