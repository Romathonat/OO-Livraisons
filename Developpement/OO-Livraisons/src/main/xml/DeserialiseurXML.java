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
	 * @param plan
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ExceptionXML
	 */
	public static void chargerPlan(Plan plan) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		File xml = OuvreurDeFichierXML.getInstance().ouvre(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
           construireAPartirDeDOMXML(racine, plan);
        }
        else
        	throw new ExceptionXML("Document non conforme");
	}
        
        
        

    private static void construireAPartirDeDOMXML(Element noeudDOMRacine, Plan plan) throws ExceptionXML, NumberFormatException{
    	// TBD: Add security measures to protect core.
        
       	NodeList listesNoeuds = noeudDOMRacine.getElementsByTagName("Noeud");
       	for (int i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des Intersections
            //plan.ajoute(creeIntersection((Element) listesNoeuds.item(i)));
            
            NodeList listeArcsSortants = ((Element) listesNoeuds.item(i)).getElementsByTagName("TronconSortant");
            
            for (int j = 0; j < listesNoeuds.getLength();j++) {
                // Récupération des Tronçon sortant du noeud courant.
                //plan.ajoute(creeTroncon((Element) listeArcsSortants.item(i)));
            }
       	}
    }
    /*
    private static Troncon creeTroncon(Element elt) throws ExceptionXML{
        // check with actual xml file.
   		String idTronçon = elt.getAttribute("nom");
   		float longueur = Float.parseFloat(elt.getAttribute("longueur"));
                float vitesse = Float.parseFloat(elt.getAttribute("vitesse"));
   		//Troncon t = TronconFactory.creeTroncon(idTroncon, vitesse);
   		if (t == null)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Coordonnees d'un point en dehors du plan");
   		if (rayon <= 0)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Cercle de rayon negatif ou nul");
   		return new Troncon();
    }*/
    /*
    private static Intersection creeIntersection(Element elt) throws ExceptionXML{
        // check with actual xml file.
   		int x = Integer.parseInt(elt.getAttribute("x"));
   		int y = Integer.parseInt(elt.getAttribute("y"));
   		Point p = PointFactory.creePoint(x, y);
   		if (p == null)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Coordonnees d'un point en dehors du plan");
      	int largeurRectangle = Integer.parseInt(elt.getAttribute("largeur"));
   		if (largeurRectangle <= 0)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Rectangle de largeur negative ou nulle");
      	int hauteurRectangle = Integer.parseInt(elt.getAttribute("hauteur"));
   		if (hauteurRectangle <= 0)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Rectangle de hauteur negative ou nulle");
   		return new Rectangle(p, largeurRectangle, hauteurRectangle);
    }*/
}
