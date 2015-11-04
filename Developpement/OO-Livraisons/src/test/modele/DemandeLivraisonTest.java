/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Guillaume Kheng
 */
public class DemandeLivraisonTest {

    @Test
    public void RespecteFenetreLivraisonTest() {

        String str_date1 = "16:00:00"; // 16h00
        String str_date2 = "17:00:00"; // 17h00
        String str_date3 = "18:00:00"; // 18h00
        String str_date4 = "19:00:00"; // 19h00
        String str_date5 = "20:00:00"; // 20h00
        String str_date6 = "21:00:00"; // 21h00
        String str_date7 = "22:00:00"; // 22h00

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

        FenetreLivraison fenetre1 = new FenetreLivraison(date2, date4);

        DemandeLivraison demande1 = new DemandeLivraison(0, 0, null, fenetre1);
        DemandeLivraison demande2 = new DemandeLivraison(0, 0, null, fenetre1);
        DemandeLivraison demande3 = new DemandeLivraison(0, 0, null, fenetre1);
        DemandeLivraison demande4 = new DemandeLivraison(0, 0, null, fenetre1);
        DemandeLivraison demande5 = new DemandeLivraison(0, 0, null, fenetre1);

        // on a crée 5 demandes de livraison.
        // les demandes 1 et 5 ne respectent pas leur demandes de livraison.
        demande1.setHeureLivraison(date1);
        demande2.setHeureLivraison(date2);
        demande3.setHeureLivraison(date3);
        demande4.setHeureLivraison(date4);
        demande5.setHeureLivraison(date5);

        assertFalse("Erreur concernant le test du respect des fenêtre de livraison - test 1", demande1.RespecteFenetreLivraison());
        assertTrue("Erreur concernant le test du respect des fenêtre de livraison - test 2", demande2.RespecteFenetreLivraison());
        assertTrue("Erreur concernant le test du respect des fenêtre de livraison - test 3", demande3.RespecteFenetreLivraison());
        assertTrue("Erreur concernant le test du respect des fenêtre de livraison - test 4", demande4.RespecteFenetreLivraison());
        assertFalse("Erreur concernant le test du respect des fenêtre de livraison - test 5", demande5.RespecteFenetreLivraison());

    }
}
