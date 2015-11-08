package xml;

import io.ExceptionXML;
import xmlModele.DeserialiseurXML;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import modele.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.xml.sax.SAXException;

/**
 *
 * @author Guillaume Kheng
 */
public class DeserialiseurXMLtest {

    @Test
    public void testChargementPlanEtChargementLivraison() {
        Plan plan = new Plan();
        EnsembleLivraisons ensembleLivraisons = new EnsembleLivraisons();

        showMessageDialog(null, "Indiquer au programme plan10x10Test_1_.xml");
        try {
            DeserialiseurXML.chargerPlan(new File(""), plan);
        } catch (ParserConfigurationException ex) {
            assertNotNull("Erreur de Configuration du parser", null);
        } catch (SAXException ex) {
            assertNotNull("Erreur SAX", null);
        } catch (IOException ex) {
            assertNotNull("Erreur IO", null);
        } catch (ExceptionXML ex) {
            assertNotNull(ex.getMessage(), null);
        } catch (ParseException ex) {
            assertNotNull("Erreur de parsing du document", null);
        }

        // Verification que l'ensemble des noeuds ont été crées.
        assertNotNull("Le noeud 0 n'a pas été crée correctement", plan.getIntersection(0));
        assertNotNull("Le noeud 1 n'a pas été crée correctement", plan.getIntersection(1));
        assertNotNull("Le noeud 2 n'a pas été crée correctement", plan.getIntersection(2));
        assertNotNull("Le noeud 3 n'a pas été crée correctement", plan.getIntersection(3));
        assertNull("Un noeud de trop", plan.getIntersection(4));

        // Verification que l'ensemble des tronçons ont été crées.
        Iterator<Troncon> it = plan.getIntersection(0).getTronconsSortants();
        assertNotNull("Le troncon sortant du noeud 0 n'a pas été crée correctement", it.next());
        assertNotNull("Le troncon sortant du noeud 0 n'a pas été crée correctement", it.next());
        assertFalse("Le noeud 0 possède un troncon de trop", it.hasNext());

        it = plan.getIntersection(1).getTronconsSortants();
        assertNotNull("Le troncon sortant du noeud 1 n'a pas été crée correctement", it.next());
        assertNotNull("Le troncon sortant du noeud 1 n'a pas été crée correctement", it.next());
        assertNotNull("Le troncon sortant du noeud 1 n'a pas été crée correctement", it.next());
        assertFalse("Le noeud 1 possède un troncon de trop", it.hasNext());

        it = plan.getIntersection(2).getTronconsSortants();
        assertNotNull("Le troncon sortant du noeud 2 n'a pas été crée correctement", it.next());
        assertNotNull("Le troncon sortant du noeud 2 n'a pas été crée correctement", it.next());
        assertNotNull("Le troncon sortant du noeud 2 n'a pas été crée correctement", it.next());
        assertFalse("Le noeud 2 possède un troncon de trop", it.hasNext());

        it = plan.getIntersection(3).getTronconsSortants();
        assertNotNull("Le troncon sortant du noeud 3 n'a pas été crée correctement", it.next());
        assertNotNull("Le troncon sortant du noeud 3 n'a pas été crée correctement", it.next());
        assertFalse("Le noeud 3 possède un troncon de trop", it.hasNext());


        // Test rechargement de plan.
        plan = new Plan();
        showMessageDialog(null, "Indiquer au programme plan10x10Test_2_.xml");
        try {
            DeserialiseurXML.chargerPlan(new File(""), plan);
        } catch (ParserConfigurationException ex) {
            assertNotNull("Erreur de Configuration du parser", null);
        } catch (SAXException ex) {
            assertNotNull("Erreur SAX", null);
        } catch (IOException ex) {
            assertNotNull("Erreur IO", null);
        } catch (ExceptionXML ex) {
            assertNotNull(ex.getMessage(), null);
        } catch (ParseException ex) {
            assertNotNull("Erreur de parsing du document", null);
        }

        // Test du chargement des livraisons.
        showMessageDialog(null, "Indiquer au programme LivraisonTest_2_.xml");
        try {
            DeserialiseurXML.chargerDemandesLivraisons(new File(""), plan, ensembleLivraisons);
        } catch (ParserConfigurationException ex) {
            assertNotNull("Erreur de Configuration du parser", null);
        } catch (SAXException ex) {
            assertNotNull("Erreur SAX", null);
        } catch (IOException ex) {
            assertNotNull("Erreur IO", null);
        } catch (ExceptionXML ex) {
            assertNotNull(ex.getMessage(), null);
        } catch (ParseException ex) {
            assertNotNull("Erreur de parsing du document", null);
        }

        // Verification de la cohérence des données chargées.
        Iterator<FenetreLivraison> itf = ensembleLivraisons.getFenetresLivraison();
        int i = 0;
        for (; itf.hasNext(); itf.next(), i++);
        assertEquals("Nombre de fenetre chargées incohérent", i, 3);

        itf = ensembleLivraisons.getFenetresLivraison();
        Iterator<DemandeLivraison> itd = itf.next().getDemandesLivraison();
        for (i = 0; itd.hasNext(); itd.next(), i++);
        assertEquals("Nombre de points de livraison pour la fenetre 1 incohérents", i, 3);

        itd = itf.next().getDemandesLivraison();
        for (i = 0; itd.hasNext(); itd.next(), i++);
        assertEquals("Nombre de points de livraison pour la fenetre 2 incohérents", i, 3);

        itd = itf.next().getDemandesLivraison();
        for (i = 0; itd.hasNext(); itd.next(), i++);
        assertEquals("Nombre de points de livraison pour la fenetre 3 incohérents", i, 2);
    }

}
