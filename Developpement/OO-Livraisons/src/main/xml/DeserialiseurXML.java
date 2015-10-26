package xml;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// imports des modèles
import modele.Plan;

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
    public static void chargerPlan(Plan plan) throws ParserConfigurationException, SAXException, IOException, ExceptionXML, ParseException {
        File xml = OuvreurDeFichierXML.getInstance().ouvre(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
            construirePlanAPartirDeDOMXML(racine, plan);
        } else {
            throw new ExceptionXML("Document non conforme");
        }
    }

    private static void construirePlanAPartirDeDOMXML(Element noeudDOMRacine, Plan plan) throws ExceptionXML, NumberFormatException, ParseException {
        // TBD: Add security measures to protect core.
        
        // Afin de convertir correctement les nombres décimaux (avec une virgule).
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        NodeList listesNoeuds = noeudDOMRacine.getElementsByTagName("Noeud");
        for (int i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des Intersections
            Element inter = (Element) listesNoeuds.item(i);
            int id = Integer.parseInt(inter.getAttribute("id"));
            int x = Integer.parseInt(inter.getAttribute("x"));
            int y = Integer.parseInt(inter.getAttribute("y"));

            if (plan.ajouterIntersection(id, x, y) == null)
                throw new ExceptionXML("Document non conforme, Intersection Invalide");
        }
        
        
        for (int i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des Troncon
            
            Element inter = (Element) listesNoeuds.item(i);
            int idDepart = Integer.parseInt(inter.getAttribute("id"));
            
            NodeList listeArcsSortants = ((Element) listesNoeuds.item(i)).getElementsByTagName("LeTronconSortant");
            for (int j = 0; j < listesNoeuds.getLength(); j++) {
                
                Element troncon = (Element) listeArcsSortants.item(j);
                
                String nomRue = troncon.getAttribute("nomRue");
                int idArrivee = Integer.parseInt(troncon.getAttribute("idNoeudDestination"));
                Number number = format.parse(troncon.getAttribute("vitesse"));
                double vitesse = number.doubleValue();
                number = format.parse(troncon.getAttribute("longueur"));
                double longueur = number.doubleValue();
                
                if (plan.ajouterTroncon(idDepart, idArrivee, nomRue, longueur, vitesse)== null)
                    throw new ExceptionXML("Document non conforme, Tronçon Invalide");
            }
        }
    }

}
