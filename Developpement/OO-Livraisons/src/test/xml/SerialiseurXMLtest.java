/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Plan;
import modele.Tournee;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Guillaume Kheng
 */
public class SerialiseurXMLtest {

    @Test
    public void testSerialisation() {

        // Creation du plan.
        Plan plan = new Plan();
        plan.ajouterIntersection(0, 1, 1);
        plan.ajouterIntersection(1, 2, 2);
        plan.ajouterIntersection(2, 3, 3);
        plan.ajouterIntersection(3, 4, 4);
        plan.ajouterIntersection(4, 5, 5);
        plan.ajouterIntersection(5, 6, 6);

        plan.ajouterTroncon(0, 1, "a", 2, 2);
        plan.ajouterTroncon(1, 2, "b", 2, 2);
        plan.ajouterTroncon(2, 3, "c", 2, 2);
        plan.ajouterTroncon(3, 4, "d", 2, 2);
        plan.ajouterTroncon(4, 5, "e", 2, 2);
        plan.ajouterTroncon(5, 0, "f", 2, 2);

        // creation des demandes de livraison.
        EnsembleLivraisons ensembleLivraisons = new EnsembleLivraisons();
        ensembleLivraisons.setEntrepot(plan.getIntersection(0));

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

        //FenetreLivraison fenetre1 = new FenetreLivraison(date1, date2);
        //FenetreLivraison fenetre2 = new FenetreLivraison(date2, date3);
        FenetreLivraison fenetre3 = new FenetreLivraison(date3, date4);
        FenetreLivraison fenetre4 = new FenetreLivraison(date4, date5);
        FenetreLivraison fenetre5 = new FenetreLivraison(date5, date6);

        //DemandeLivraison demande1 = new DemandeLivraison(plan.getIntersection(1), fenetre1);
        //DemandeLivraison demande2 = new DemandeLivraison(plan.getIntersection(2), fenetre2);
        DemandeLivraison demande3 = new DemandeLivraison(0, 25, plan.getIntersection(3), fenetre3);
        DemandeLivraison demande4 = new DemandeLivraison(0, 26, plan.getIntersection(4), fenetre4);
        DemandeLivraison demande5 = new DemandeLivraison(0, 52,plan.getIntersection(5), fenetre5);
        DemandeLivraison demande6 = new DemandeLivraison(0, 45,plan.getIntersection(0), fenetre5);

        Tournee tournee = new Tournee();

        Chemin chemin1 = new Chemin();
        Chemin chemin2 = new Chemin();
        Chemin chemin3 = new Chemin();
        Chemin chemin4 = new Chemin();
        Chemin chemin5 = new Chemin();
        Chemin chemin6 = new Chemin();

        chemin1.ajouterTronconDebut(plan.getIntersection(2).getTronconsSortants().next());
        chemin1.ajouterTronconDebut(plan.getIntersection(1).getTronconsSortants().next());
        chemin1.ajouterTronconDebut(plan.getIntersection(0).getTronconsSortants().next());
        chemin4.ajouterTronconDebut(plan.getIntersection(3).getTronconsSortants().next());
        chemin5.ajouterTronconDebut(plan.getIntersection(4).getTronconsSortants().next());
        chemin6.ajouterTronconDebut(plan.getIntersection(5).getTronconsSortants().next());

        chemin1.setLivraisonArrivee(demande3);
        chemin4.setLivraisonArrivee(demande4);
        chemin5.setLivraisonArrivee(demande5);
        chemin6.setLivraisonArrivee(demande6);

        tournee.AjouterChemin(chemin1);
        tournee.AjouterChemin(chemin4);
        tournee.AjouterChemin(chemin5);
        tournee.AjouterChemin(chemin6);
<<<<<<< HEAD

        /*try {
        SerialiseurXML.exporterTournee(tournee);
        } catch (IOException ex) {
        Logger.getLogger(SerialiseurXMLtest.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        
        
        showMessageDialog(null, "Indiquer au programme le fichier qui doit être testé puis le fichier TestSerialiseur.txt");
        //File file1 = SerialiseurXML.ouvreFichier();
        //File file2 = SerialiseurXML.ouvreFichier();
        //assertEquals("Files are not the same", (file1.compareTo(file2)),0);
=======
>>>>>>> 55e57d01338afeb4391f32c0240f2c78c7f76299
    }
}
