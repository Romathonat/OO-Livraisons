package xmlModele;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modele.Plan;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import io.ExceptionXML;

public class DeserialiseurXML {

    /**
     * Ouvre un fichier xml et cree un plan à partir du contenu du fichier
     *
     * @param file
     * @param plan Le plan auquel on va ajouter les données sérialisée dans le
     * document XML
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ExceptionXML
     * @throws java.text.ParseException
     */
    public static void chargerPlan(File file, Plan plan) throws ParserConfigurationException,
            SAXException, IOException, ExceptionXML, ParseException {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(file);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
            construirePlan_APartirDeDOMXML(racine, plan);
        } else {
            throw new ExceptionXML("Document non conforme");
        }

    }

    /**
     * Ouvre un fichier xml et charge un ensemble de livraisons à partir du
     * contenu du fichier
     *
     * @param file
     * @param plan Le plan auquel on va ajouter les données sérialisée dans le
     * document XML
     * @param ensembleLivraisons L'ensemble de demandes de livraison auquel on
     * va ajouter les données sérialisée dans le document xml.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ExceptionXML
     * @throws java.text.ParseException
     */
    public static void chargerDemandesLivraisons(File file, Plan plan, EnsembleLivraisons ensembleLivraisons) throws ParserConfigurationException,
            SAXException, IOException, ExceptionXML, ParseException {

        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(file);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("JourneeType")) {
            RecupererDemandesLivraison_APartirDeDOMXML(racine, plan, ensembleLivraisons);
        } else {
            throw new ExceptionXML("Document non conforme");
        }

    }

    /**
     * Parse un document xml à partir de sa racine du document XML, et renseigne
     * les informations du plan avec les informations contenues dans le fichier.
     *
     * @param noeudDOMRacine La racine du document xml.
     * @param plan Le plan auquel on doit ajouter les informations.
     * @throws ExceptionXML
     * @throws NumberFormatException
     * @throws ParseException
     */
    private static void construirePlan_APartirDeDOMXML(Element noeudDOMRacine, Plan plan) throws ExceptionXML, NumberFormatException, ParseException {

        // Afin de convertir correctement les nombres décimaux (avec une virgule).
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        NodeList listesNoeuds = noeudDOMRacine.getElementsByTagName("Noeud");
        int i;
        for (i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des données de l'intersections.
            Element inter = (Element) listesNoeuds.item(i);
            int id = Integer.parseInt(inter.getAttribute("id"));
            int x = Integer.parseInt(inter.getAttribute("x"));
            int y = Integer.parseInt(inter.getAttribute("y"));

            // on ajoute l'intersection au plan.
            if (plan.ajouterIntersection(id, x, y) == null) {
                throw new ExceptionXML("Document non conforme, Intersection Invalide");
            }
        }
        if (i == 0) {
            throw new ExceptionXML("Document non conforme, le plan ne contient aucune intersection");
        }

        for (i = 0; i < listesNoeuds.getLength(); i++) {
            // Récuperation des Troncon

            Element inter = (Element) listesNoeuds.item(i);
            int idDepart = Integer.parseInt(inter.getAttribute("id"));

            NodeList listeArcsSortants = ((Element) listesNoeuds.item(i)).getElementsByTagName("LeTronconSortant");
            for (int j = 0; j < listeArcsSortants.getLength(); j++) {

                Element troncon = (Element) listeArcsSortants.item(j);

                String nomRue = troncon.getAttribute("nomRue");
                int idArrivee = Integer.parseInt(troncon.getAttribute("idNoeudDestination"));
                Number number = format.parse(troncon.getAttribute("vitesse"));
                double vitesse = number.doubleValue();
                number = format.parse(troncon.getAttribute("longueur"));
                double longueur = number.doubleValue();

                if (plan.ajouterTroncon(idDepart, idArrivee, nomRue, longueur, vitesse) == null) {
                    throw new ExceptionXML("Document non conforme, Tronçon Invalide");
                }
            }
        }
        if (i == 0) {
            throw new ExceptionXML("Document non conforme, le plan ne contient aucun tronçon");
        }
    }

    /**
     * Parse un document xml à partir de sa racine du document XML, et renseigne
     * les informations de l'ensemble de livraisons avec les informations
     * contenues dans le fichier.
     *
     * @param noeudDOMRacine La racine du document xml.
     * @param plan Le plan concerné par l'ensemble de livraison considéré.
     * @param ensembleLivraisons L'ensemble de demande de livraison auquels les
     * informations doivent être ajoutées.
     * @throws ExceptionXML
     * @throws NumberFormatException
     * @throws ParseException
     */
    private static void RecupererDemandesLivraison_APartirDeDOMXML(Element noeudDOMRacine, Plan plan, EnsembleLivraisons ensembleLivraisons) throws ExceptionXML, NumberFormatException, ParseException {

        // Utilitaire pour parser la date.
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        // Récupération de l'Entrepot.
        Element eltEntrepot = (Element) noeudDOMRacine.getElementsByTagName("Entrepot").item(0);
        if (eltEntrepot == null) {
            throw new ExceptionXML("Document non conforme, Entrepot Absent.");
        }
        int idEntrepot = Integer.parseInt(eltEntrepot.getAttribute("adresse"));

        if (ensembleLivraisons.setEntrepot(plan.getIntersection(idEntrepot)) == null) {
            throw new ExceptionXML("Document non conforme, l'id de l'entrepot ne correspond à aucune intersection.");
        }

        // Intégration des plages horaires.
        NodeList listeFenetres = noeudDOMRacine.getElementsByTagName("Plage");
        int countFenetres = 0, countDemandes = 0;
        for (int i = 0; i < listeFenetres.getLength(); i++) {
            // Récuperation des données de la fenetre.
            Element nodeFenetre = (Element) listeFenetres.item(i);

            Date heureDebut = sdf.parse(nodeFenetre.getAttribute("heureDebut"));
            Date heureFin = sdf.parse(nodeFenetre.getAttribute("heureFin"));

            // on ajoute la fenetre horaire à l'ensemble.
            FenetreLivraison fenetre = ensembleLivraisons.ajouteFenetreDeLivraison(heureDebut, heureFin);
            if (fenetre == null) {
                throw new ExceptionXML("Document non conforme, Plage Horaire invalide");
            }

            NodeList listePointsLivraison = nodeFenetre.getElementsByTagName("Livraison");
            for (int j = 0; j < listePointsLivraison.getLength(); j++) {
                //Récupération des données du point de livraison.
                Element pointDeLivraison = (Element) listePointsLivraison.item(j);

                int id = Integer.parseInt(pointDeLivraison.getAttribute("id"));
                int client = Integer.parseInt(pointDeLivraison.getAttribute("client"));
                int adresse = Integer.parseInt(pointDeLivraison.getAttribute("adresse"));

                if (fenetre.ajouterDemandeLivraison(id, client, plan.getIntersection(adresse)) == null) {
                    throw new ExceptionXML("Document non conforme, Point de livraison invalide");
                }
                countDemandes++;
            }
            countFenetres++;
        }

        if (countFenetres == 0) {
            throw new ExceptionXML("Document non conforme, Absence de fenetre de livraison");
        }
        if (countDemandes == 0) {
            throw new ExceptionXML("Document non conforme, Absence de point de livraison");
        }

    }
}
