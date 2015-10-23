package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// imports des modèles
import modele.Plan;
import modele.Intersection;
import modele.Troncon;
import modele.DemandeLivraison;
import modele.FenetreLivraison;
import modele.EnsembleLivraisons;

// imports relatifs à la serialisation XML
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DeserialiseurXML {

    /**
     * Ouvre un fichier xml et cree un plan à partir du contenu du fichier
     *
     * @param plan
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ExceptionXML
     */
    public static void chargerPlan(Plan plan) throws ParserConfigurationException, SAXException, IOException, ExceptionXML {
        File xml = OuvreurDeFichierXML.getInstance().ouvre(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
            construireAPartirDeDOMXML(racine, plan);
        } else {
            throw new ExceptionXML("Document non conforme");
        }
    }

    private static void construireAPartirDeDOMXML(Element noeudDOMRacine, Plan plan) throws ExceptionXML, NumberFormatException {
    	// TBD: Add security measures to protect core.

        NodeList listesNoeuds = noeudDOMRacine.getElementsByTagName("Noeud");
        for (int i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des Intersections
            //plan.ajouteIntersection(creeIntersection((Element) listesNoeuds.item(i)));
        }

        for (int i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des Troncon
            NodeList listeArcsSortants = ((Element) listesNoeuds.item(i)).getElementsByTagName("TronconSortant");
            for (int j = 0; j < listesNoeuds.getLength(); j++) {
                //plan.ajoute(creeTroncon((Element) listeArcsSortants.item(j), plan, i , j));
            }
        }
    }

    // Get infos from xml node "Noeud" : <Noeud id="1" x="88" y="171"/>
    private static Intersection creeIntersection(Element elt) throws ExceptionXML {
        // check with actual xml file.

        int Id = Integer.parseInt(elt.getAttribute("id"));
        int x = Integer.parseInt(elt.getAttribute("x"));
        int y = Integer.parseInt(elt.getAttribute("y"));

   		// TBD: Detection d'erreurs.
        return new Intersection(Id, x, y);
    }

    // Get infos from xml node "Troncon" : <LeTronconSortant nomRue="v0" vitesse="4,100000" longueur="602,100000" idNoeudDestination="0"/>
    private static Troncon creeTroncon(Element elt, Plan plan, int idDepart, int idArrivee) throws ExceptionXML {
        // check with actual xml file.
        String nomRue = elt.getAttribute("nomRue");
        float vitesse = Float.parseFloat(elt.getAttribute("vitesse"));
        float longueur = Float.parseFloat(elt.getAttribute("longueur"));

        Intersection interDepart = plan.getIntersection(idDepart);
        Intersection interArrivee = plan.getIntersection(idArrivee);

   		// TBD: Detection d'erreurs.
        return new Troncon(interDepart, interArrivee, nomRue, longueur, vitesse);
    }
}
