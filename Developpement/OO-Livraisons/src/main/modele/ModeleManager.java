package modele;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tsp.Graphe;
import tsp.TemplateTSP;
import xmlModele.DeserialiseurXML;
import io.ExceptionXML;
import tsp.GrapheDense;
import tsp.TSP3;

/**
 * Le ModeleManager est le point d'entrée du contrôleur sur le modèle. Contient
 * toutes les instances des objets métiers avec lequels le contrôleur a besoin
 * d'interagir.
 *
 * @author Kilian
 */
public class ModeleManager {

    /**
     * Le plan chargé.
     */
    private Plan plan;

    /**
     * L'ensemble de livraison chargé.
     */
    private EnsembleLivraisons ensembleLivraisons;

    /**
     * La tournée.
     */
    private Tournee tournee;

    /**
     * La livraison en attente d'être ajoutée.
     */
    private DemandeLivraison bufferLivraison;

    /**
     * Contructeur du ModeleManager
     */
    public ModeleManager() {
        this.plan = new Plan();
        this.ensembleLivraisons = new EnsembleLivraisons();
        this.tournee = new Tournee();
    }

    /**
     * Retourne le plan du ModeleManager
     *
     * @return Le plan du ModeleManager
     */
    public Plan getPlan() {
        return this.plan;
    }

    /**
     * Retourne l'ensemble de livraison du ModeleManger.
     *
     * @return L'ensemble de livraison du ModeleManger.
     */
    public EnsembleLivraisons getEnsembleLivraisons() {
        return this.ensembleLivraisons;
    }

    /**
     * Retourne la tournée du ModeleManager
     *
     * @return La tournée du ModeleManager
     */
    public Tournee getTournee() {
        return this.tournee;
    }

    /**
     * Remet à l'état initial le plan, l'ensemble de livraison, et la tournée(si
     * elle a été calculée) du ModeleManager.
     */
    public void resetPlan() {
        this.plan = null;
        this.resetEnsembleLivraisons();
    }

    /**
     * Remet à l'état initial l'ensemble de livraison et la tournée (si elle a
     * été calculée) du ModeleManager. Le plan actuellement chargé sera
     * conservé.
     */
    public void resetEnsembleLivraisons() {
        this.ensembleLivraisons = null;
        this.resetTournee();
    }

    /**
     * Remet à l'état initial la tournée (si elle a été calculée) du
     * ModeleManager. Le plan et l'ensemble de livraisons actuellement chargés
     * seront conservé.
     */
    public void resetTournee() {
        this.tournee = null;
    }

    /**
     * Charge un plan depuis un fichier xml.
     *
     * @param file Le fichier xml qui contient le plan à charger.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ExceptionXML
     * @throws ParseException
     */
    public void chargerPlan(File file) throws ParserConfigurationException, SAXException, IOException, ExceptionXML, ParseException {
        Plan planIntermediaire = new Plan();
        DeserialiseurXML.chargerPlan(file, planIntermediaire);
        this.plan = planIntermediaire;
        this.resetEnsembleLivraisons();
    }

    /**
     * Charge un ensemble de livraison depuis un fichier xml.
     *
     * @param file Le fichier xml qui contient l'ensemble de livraison à
     * charger.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ExceptionXML
     * @throws ParseException
     */
    public void chargerEnsembleLivraisons(File file) throws ParserConfigurationException, SAXException, IOException, ExceptionXML, ParseException {
        EnsembleLivraisons ensembleLivraisonsIntermediaire = new EnsembleLivraisons();
        DeserialiseurXML.chargerDemandesLivraisons(file, this.plan, ensembleLivraisonsIntermediaire);
        this.ensembleLivraisons = ensembleLivraisonsIntermediaire;
        this.resetTournee();
    }

    /**
     * Ajoute au modèle la demande de livraison présente dans le buffer d'ajout.
     * La demande de livraison est ajoutée à sa fenêtre, et,dans la tournée,
     * elle est placée avant la demande de livraison spécifiée. Pour le bon
     * fonctionnement de cette méthode, la tournée doit déjà avoir été calculée.
     *
     * @param demandeLivraisonArrivee La livraison après laquelle la nouvelle
     * demande de livraison doit être insérée dans la tournée.
     * @return La nouvelle demande de livraison qui a été ajoutée.
     */
    public DemandeLivraison ajouterNouvelleLivraison(DemandeLivraison demandeLivraisonArrivee) {

        DemandeLivraison demandeLivraison = this.getBufferLivraison().getFenetreLivraison().ajouterDemandeLivraison(getBufferLivraison());

        Intersection interDepart = null;
        Intersection interArrive = demandeLivraisonArrivee.getIntersection();

        //on trouve l'interDepart en trouvant la demande de livraison qui precede interArrive
        Iterator<Chemin> itChemin = this.tournee.getChemins();

        Chemin chemin = null;
        while (itChemin.hasNext()) {
            chemin = itChemin.next();
            if (chemin.getLivraisonArrivee().getIntersection() == interArrive) {
                interDepart = chemin.getIntersectionDepart();
                //on enlève ce chemin, puisqu'il va être remplacé par deux nouveaux

                break;
            }
        }

        Chemin cheminDepart = this.plan.calculerPlusCourtChemin(interDepart, demandeLivraison.getIntersection());
        Chemin cheminArrive = this.plan.calculerPlusCourtChemin(demandeLivraison.getIntersection(), interArrive);

        cheminDepart.setLivraisonArrivee(demandeLivraison);
        cheminArrive.setLivraisonArrivee(demandeLivraisonArrivee);

        this.tournee.AjouterChemin(cheminDepart);
        this.tournee.AjouterChemin(cheminArrive);
        this.tournee.supprimerChemin(chemin);

        this.tournee.CalculerHeuresDemandesLivraisons();

        return demandeLivraison;
    }

    /**
     * Supprime la demande de livraison passée en paramètre de la tournee. La
     * demande est supprimée de la tournée et de la liste interne de demande de
     * livraison.
     *
     * @param demandeASupprimer La livraison à supprimer.
     */
    public void supprimerDemandeLivraison(DemandeLivraison demandeASupprimer) {

        Intersection interDepart = null;
        Intersection interArrivee = null;
        Chemin premierCheminASupprimer = null;
        Chemin deuxiemeCheminASupprimer = null;
        Intersection interLivraison = demandeASupprimer.getIntersection();
        DemandeLivraison demandeSuivante = null;

        Iterator<Chemin> itChemin = this.tournee.getChemins();

        while (itChemin.hasNext()) {
            Chemin cheminDepart = itChemin.next();
            if (cheminDepart.getIntersectionArrivee() == interLivraison) {

                interDepart = cheminDepart.getIntersectionDepart();

                if (!itChemin.hasNext()) {
                    this.tournee.supprimerChemin(cheminDepart);
                    this.ensembleLivraisons.supprimerDemandeLivraison(demandeASupprimer);
                    return;
                }

                Chemin cheminArrivee = itChemin.next();

                interArrivee = cheminArrivee.getIntersectionArrivee();
                demandeSuivante = cheminArrivee.getLivraisonArrivee();

                premierCheminASupprimer = cheminDepart;
                deuxiemeCheminASupprimer = cheminArrivee;
                break;
            }
        }

        if (interDepart.getId() != interArrivee.getId()) {
            Chemin chemin = this.plan.calculerPlusCourtChemin(interDepart, interArrivee);
            chemin.setLivraisonArrivee(demandeSuivante);
            tournee.AjouterChemin(chemin);
        }

        this.tournee.supprimerChemin(premierCheminASupprimer);
        this.tournee.supprimerChemin(deuxiemeCheminASupprimer);

        this.tournee.CalculerHeuresDemandesLivraisons();

        this.ensembleLivraisons.supprimerDemandeLivraison(demandeASupprimer);

    }

    /**
     * echange l'ordre de deux livraisons passées en paramètre. Les demandes
     * sont échangées si elles ne sont pas nulles.
     *
     * @param demande une des deux demandes. (la première a été chargé
     * préalablement dans le buffer du modeleManager
     */
    public void echangerDeuxLivraisons(DemandeLivraison demande) {

        if (demande == null || this.getBufferLivraison() == null) {
            return;
        }

        DemandeLivraison premiereDemande = this.getBufferLivraison();
        DemandeLivraison secondeDemande = demande;

        if (premiereDemande.getHeureLivraison().after(secondeDemande.getHeureLivraison())) {
            DemandeLivraison temp = premiereDemande;
            premiereDemande = secondeDemande;
            secondeDemande = temp;
        }

        DemandeLivraison demandeSuivantPremiere = null;
        Iterator<Chemin> it_chemin = this.tournee.getChemins();
        while (it_chemin.hasNext()) {
            if (it_chemin.next().getLivraisonArrivee().getIntersection().getId() == premiereDemande.getIntersection().getId()) {
                demandeSuivantPremiere = it_chemin.next().getLivraisonArrivee();
                break;
            }
        }

        DemandeLivraison demandeSuivanteSeconde = null;
        it_chemin = this.tournee.getChemins();
        while (it_chemin.hasNext()) {
            if (it_chemin.next().getLivraisonArrivee().getIntersection().getId() == secondeDemande.getIntersection().getId()) {
                demandeSuivanteSeconde = it_chemin.next().getLivraisonArrivee();
                break;
            }
        }

        if (demandeSuivantPremiere.getIntersection().getId() == secondeDemande.getIntersection().getId()) {
            demandeSuivantPremiere = premiereDemande;
        }

        this.supprimerDemandeLivraison(secondeDemande);
        this.supprimerDemandeLivraison(premiereDemande);
        this.setBufferLivraison(premiereDemande);
        this.ajouterNouvelleLivraison(demandeSuivanteSeconde);
        this.setBufferLivraison(secondeDemande);
        this.ajouterNouvelleLivraison(demandeSuivantPremiere);

    }

    /**
     * Calcule tous les plus courts chemins entre les intersections de
     * l'ensemble des livraisons. La map retournee contient : Tous les chemins
     * partant de l'entrepot et allant vers toutes les intersections de la
     * premiere fenetre de livraison. Tous les chemins entre toutes les
     * intersections des fenetres de livraisons. Tous les chemins entre toutes
     * les intersections d'une fenetre de livraison vers toutes les toutes les
     * intersections de la fenetre de livraison suivante. Tous les chemins entre
     * toutes les intersections de la dernière fenetre de livraison vers
     * l'entrepot. Ajoute a l'ensemble intersections, tous les identifiants des
     * intersections de l'ensemble de livraison.
     *
     * @param intersections Un ensemble qui a l'issue d'exécution de la methode
     * va contenir toutes les id des intersections de l'ensemble de livraison.
     * @return La map contenant tous les chemins.
     */
    private Map<DepartArriveeChemin, Chemin> calculerChemins(Set<Integer> intersections) {
        Intersection entrepot = ensembleLivraisons.getEntrepot();
        Map<DepartArriveeChemin, Chemin> chemins = new HashMap<>();
        Iterator<FenetreLivraison> itFenetre = ensembleLivraisons.getFenetresLivraison();
        //Pour simuler le fait qu'une livraison demarre de l'entrepot,
        // on cree une fausse fenetre de livraison seulement pour l'entrepot.
        FenetreLivraison fenetrePrecedente = new FenetreLivraison(null, null);
        fenetrePrecedente.ajouterDemandeLivraison(-1, -1, entrepot);
        while (itFenetre.hasNext()) {
            FenetreLivraison fenetre = itFenetre.next();

            //On retient tous les id des intersections des fenetres de livraison dans un ensemble.
            Set<Integer> idIntersectionsFenetrePrecedente = fenetrePrecedente.getIdIntersectionsLivraisons();
            Set<Integer> idIntersectionsFenetre = fenetre.getIdIntersectionsLivraisons();
            //On met a jour l'ensemble de toutes les intersections utilisees.
            intersections.addAll(idIntersectionsFenetre);

            //Plus courts chemins entre fenetre precedente et fenetre courante.
            Map<DepartArriveeChemin, Chemin> cheminsDepuisFenetrePrecedente = plan.calculerPlusCourtsChemins(idIntersectionsFenetrePrecedente, idIntersectionsFenetre);
            chemins.putAll(cheminsDepuisFenetrePrecedente);

            //Plus courts chemins dans la fenetre courante.
            Map<DepartArriveeChemin, Chemin> cheminsFenetre = plan.calculerPlusCourtsChemins(idIntersectionsFenetre, idIntersectionsFenetre);
            chemins.putAll(cheminsFenetre);

            fenetrePrecedente = fenetre;
        }
        //Plus courts chemins pour revenir vers l'entrepot.
        Set<Integer> idIntersectionsFenetrePrecedente = fenetrePrecedente.getIdIntersectionsLivraisons();
        Set<Integer> idFenetreEntrepot = new HashSet<>(1);
        idFenetreEntrepot.add(entrepot.getId());
        Map<DepartArriveeChemin, Chemin> cheminsFenetreVersEntrepot = plan.calculerPlusCourtsChemins(idIntersectionsFenetrePrecedente, idFenetreEntrepot);
        chemins.putAll(cheminsFenetreVersEntrepot);

        return chemins;
    }

    /**
     * Retourne un Graphe du package tsp modelisant le necessaire pour calculer
     * la tournee avec la classe TSP.
     *
     * @param correspondance Un tableau des correspondances entre les
     * identifiants de sommets du graphe et les identifiants des Intersections.
     * @param chemins La map contenant tous les chemins possibles entre les
     * intersections.
     * @return Un Graphe modelisant le problème a resoudre.
     */
    private Graphe modeliserGrapheTsp(ArrayList<Integer> correspondance, Map<DepartArriveeChemin, Chemin> chemins) {
        GrapheDense graphe = new GrapheDense(correspondance.size());
        //On parcourt les chemins calculés precedemment et on les ajoute au graphe transposé.
        Set<Map.Entry<DepartArriveeChemin, Chemin>> ensembleChemins = chemins.entrySet();
        Iterator<Map.Entry<DepartArriveeChemin, Chemin>> itChemins = ensembleChemins.iterator();
        while (itChemins.hasNext()) {
            //Pour chaque chemin on affiche 
            Map.Entry<DepartArriveeChemin, Chemin> entree = itChemins.next();
            DepartArriveeChemin itineraire = entree.getKey();
            Chemin chemin = entree.getValue();
            //On transpose les identifiants des intersections en identifiants du graphe.
            int sommetGrapheDepart = Collections.binarySearch(correspondance, itineraire.idDepart);
            int sommetGrapheArrivee = Collections.binarySearch(correspondance, itineraire.idArrivee);
            graphe.ajouterArc(sommetGrapheDepart, sommetGrapheArrivee, chemin.getDuree());
        }
        return graphe;
    }

    /**
     * Transforme la solution trouvee par la classe TSP en une Tournee.
     *
     * @param tsp Une instance de la classe TSP ayant deja calculee la tournee
     * minimale.
     * @param correspondance Un tableau des correspondances entre les
     * identifiants de sommets du graphe et les identifiants des Intersections.
     * @param chemins La map contenant tous les chemins possibles entre les
     * intersections.
     * @return La tournee minimale trouvee par la classe TSP.
     */
    private Tournee transformerSolutionTspEnTournee(TemplateTSP tsp, ArrayList<Integer> correspondance, Map<DepartArriveeChemin, Chemin> chemins) {
        Tournee nouvelleTournee = new Tournee();
        //On part de l'entrepot.
        Intersection entrepot = this.ensembleLivraisons.getEntrepot();
        int sommetEntrepot = Collections.binarySearch(correspondance, entrepot.getId());
        int offsetEntrepot = tsp.trouverIndexSommet(sommetEntrepot);
        //On parcourt circulairement la solution depuis l'entrepot.
        int indexPrecedentSolution = offsetEntrepot;
        for (int i = 1; i < correspondance.size(); i++) {
            int indexSolution = (i + offsetEntrepot) % correspondance.size();
            int sommetPrecedent = tsp.getSolution(indexPrecedentSolution);
            int sommet = tsp.getSolution(indexSolution);
            int intersectionPrecedente = correspondance.get(sommetPrecedent);
            int intersection = correspondance.get(sommet);
            //On ajoute ce chemin a la tournee.
            Chemin chemin = chemins.get(new DepartArriveeChemin(intersectionPrecedente, intersection));
            //On definit la demande de livraison a l'arrivee du chemin.
            DemandeLivraison livraison = this.ensembleLivraisons.getDemandeLivraison(intersection);
            chemin.setLivraisonArrivee(livraison);
            nouvelleTournee.AjouterChemin(chemin);

            indexPrecedentSolution = indexSolution;
        }
        //On ajoute un dernier chemin entre le dernier point de livraison et l'entrepot.
        int sommetPrecedent = tsp.getSolution(indexPrecedentSolution);
        int intersectionPrecedente = correspondance.get(sommetPrecedent);
        Chemin chemin = chemins.get(new DepartArriveeChemin(intersectionPrecedente, entrepot.getId()));
        //On ajoute une demande de livraison fictive au chemin contenant l'entrepot comme lieu.
        FenetreLivraison fenetreEntrepot = new FenetreLivraison(new Date(0), new Date(Long.MAX_VALUE));
        DemandeLivraison livraisonEntrepot = new DemandeLivraison(-1, -1, entrepot, fenetreEntrepot);
        livraisonEntrepot.setTempsArret(0);
        chemin.setLivraisonArrivee(livraisonEntrepot);
        nouvelleTournee.AjouterChemin(chemin);

        return nouvelleTournee;
    }

    /**
     * Calcule la Tournee optimale pour satisfaire les demandes de livraisons.
     *
     * @return La duree de la tournee.
     */
    public double calculerTournee() {
        long tempsDebut = System.currentTimeMillis();
        if (ensembleLivraisons != null) {
            Intersection entrepot = ensembleLivraisons.getEntrepot();

            //Dans un ensemble, on retient toutes les intersections visitées.
            Set<Integer> intersections = new TreeSet<>();
            intersections.add(entrepot.getId());
            //collection contenant tous les chemins entre les demandes de livraisons.
            Map<DepartArriveeChemin, Chemin> chemins = calculerChemins(intersections);

            //On etablit une correspondance entre les intersections et les sommets du graphe dans un tableau trié.
            ArrayList<Integer> correspondance = new ArrayList<>(intersections);
            //On modelise le probleme avec la classe Graphe du package tsp.
            Graphe graphe = modeliserGrapheTsp(correspondance, chemins);

            //On calcule la solution du problème.
            TemplateTSP tsp = new TSP3();
            long tempsDebutTsp = System.currentTimeMillis();
            tsp.chercheSolution(60000, graphe);
            long tempsTotalTsp = System.currentTimeMillis() - tempsDebutTsp;

            //On interprete la solution du problème et on la transforme en tournee.
            this.tournee = transformerSolutionTspEnTournee(tsp, correspondance, chemins);
            this.tournee.CalculerHeuresDemandesLivraisons();
            long tempsTotal = System.currentTimeMillis() - tempsDebut;
            System.out.print("Solution trouvee en " + tempsTotal + "ms. TSP en " + tempsTotalTsp + "ms.");
            return tournee.getTempsDeLivraison();
        }
        return -1;
    }

    /**
     * @return the bufferLivraison
     */
    public DemandeLivraison getBufferLivraison() {
        return bufferLivraison;
    }

    /**
     * @param bufferLivraison the bufferLivraison to set
     */
    public void setBufferLivraison(DemandeLivraison bufferLivraison) {
        this.bufferLivraison = bufferLivraison;
    }
}
