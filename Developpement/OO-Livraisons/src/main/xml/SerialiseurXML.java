package xml;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import modele.Chemin;
import modele.Tournee;
import modele.Troncon;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SerialiseurXML {// Singleton

    /**
     * Ouvre un fichier xml et ecrit dans ce fichier une description xml de plan
     *
     * @param tournee
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     * @throws ExceptionXML
     */
    public static void exporterTournee(Tournee tournee) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, ExceptionXML {
        // récupération du document.
        File txtFile = OuvreurDeFichierXML.getInstance().ouvre(false);
        StreamResult result = new StreamResult(txtFile);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        // création de l'arborescence
        Element racine = sauverTournee (document, tournee);

        // sauvegarde du fichier.
        document.appendChild(racine);
        DOMSource source = new DOMSource(document);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.transform(source, result);
    }

    private static Element sauverTournee(Document document, Tournee tournee) {
        
        // sauvegarde des infos de la tournée.
        Element racine = document.createElement("Carnet_de_Route");
        creerAttribut(document, racine, "Date_Debut", "ADD method Here");
        creerAttribut(document, racine, "Date_Fin", "ADD method Here");
        
        // on sauvegarde chaque chemin dans l'ordre.
        Iterator<Chemin> it_chemin = tournee.getChemins();
        while (it_chemin.hasNext()) {
            Chemin chemin = it_chemin.next();
            Element elt_chemin = sauverChemin(document, chemin);
            racine.appendChild(elt_chemin);
        }
        
        return racine;
    }

    private static Element sauverChemin(Document document, Chemin chemin) {
        
        // formateur de dates.
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        
        Element elt_chemin = document.createElement("livraison");
            creerAttribut(document, elt_chemin, "heureDeLivraison", df.format(chemin.getLivraisonArrivee().getHeureLivraison()));
            creerAttribut(document, elt_chemin, "IntersectionDepart", Integer.toString(chemin.getIntersectionDepart().getId()));
            creerAttribut(document, elt_chemin, "IntersectionArrivee", Integer.toString(chemin.getIntersectionArrivee().getId()));
            
            Iterator<Troncon> it_troncon = chemin.getTroncons();
        while (it_troncon.hasNext()) {
            Troncon troncon = it_troncon.next();
            Element elt_Troncon = sauverTroncon(document, troncon);
            elt_chemin.appendChild(elt_Troncon);
        }
            
        return elt_chemin;
    }

    private static Element sauverTroncon(Document document, Troncon troncon) {
        
        Element elt_troncon = document.createElement("troncon");
            creerAttribut(document, elt_troncon, "IntersectionDepart", Integer.toString(troncon.getIntersectionDepart().getId()));
            creerAttribut(document, elt_troncon, "IntersectionArrivee", Integer.toString(troncon.getIntersectionArrivee().getId()));
            
        return elt_troncon;
    }
    
    private static void creerAttribut(Document document, Element racine, String nom, String valeur) {
        Attr attribut = document.createAttribute(nom);
        racine.setAttributeNode(attribut);
        attribut.setValue(valeur);
    }
}
