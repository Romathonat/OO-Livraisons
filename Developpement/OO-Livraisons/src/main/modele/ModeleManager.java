package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import tsp.GrapheCreux;
import tsp.TSP1;

/**
 * Le ModeleManager est le point d'entrée du contrôleur sur le modèle. Contient 
 * toutes les instances des objets métiers avec lequels le contrôleur a besoin 
 * d'interagir. 
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
     * 
     */
    private long tempsDerniereTourneeCalculee;

    /**
     * Contructeur du ModeleManager
     */
    public ModeleManager() {
        this.plan = new Plan();
        this.ensembleLivraisons = new EnsembleLivraisons();
        this.tournee = new Tournee();
        this.tempsDerniereTourneeCalculee = Long.MAX_VALUE;
    }

    /**
     * Retourne le plan du ModeleManager
     * @return Le plan du ModeleManager
     */
    public Plan getPlan() {
        return this.plan;
    }

    /**
     * Retourne l'ensemble de livraison du ModeleManger.
     * @return L'ensemble de livraison du ModeleManger.
     */
    public EnsembleLivraisons getEnsembleLivraisons() {
        return this.ensembleLivraisons;
    }

    /**
     * Retourne la tournée du ModeleManager
     * @return La tournée du ModeleManager
     */
    public Tournee getTournee() {
        return this.tournee;
    }
    
    /**
     * Remet à l'état initial le plan, l'ensemble de livraison, et la tournée(si elle a été calculée) du ModeleManager. 
     */
    public void resetPlan(){
        this.plan = new Plan();
        this.resetEnsembleLivraisons();
    }
    
    /**
     * Remet à l'état initial l'ensemble de livraison et la tournée (si elle a été calculée) du ModeleManager.
     * Le plan actuellement chargé sera conservé.
     */
    public void resetEnsembleLivraisons(){
        this.ensembleLivraisons = new EnsembleLivraisons();
        this.resetTournee();
    }
    
    /**
     * Remet à l'état initial la tournée (si elle a été calculée) du ModeleManager.
     * Le plan et l'ensemble de livraisons actuellement chargés seront conservé.
     */
    public void resetTournee(){
        this.tournee = new Tournee();
    }
    
    /**
     * Calcule la tournée à partir de l'ensemble de livraisons actuellement chargé.
     * @return 
     */
    public double calculerTournee() {
        if (ensembleLivraisons != null) {
            Intersection entrepot = ensembleLivraisons.getEntrepot();
            
            //Dans une collection, on retient tous les chemins entre les intersections.
            Map<DepartArriveeChemin, Chemin> chemins = new HashMap<>();
            //Dans un ensemble, on retient toutes les intersections visitées.
            Set<Integer> intersections = new TreeSet<>();
            intersections.add(entrepot.getId());
            
            //Pour chaque fenetre de livraisons.
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
                
                /*
                 * On calcule les plus courts chemins entre toutes les intersections
                 * de la fenetre de livraison precedente et toutes les intersections
                 * de la fenetre de livraison courante.
                 */
                Map<DepartArriveeChemin, Chemin> cheminsDepuisFenetrePrecedente = plan.calculerPlusCourtsChemins(idIntersectionsFenetrePrecedente, idIntersectionsFenetre);
                chemins.putAll(cheminsDepuisFenetrePrecedente);
                
                /*
                 * On calcule tous les plus courts chemins entre toutes les
                 * intersections de la fenetre de livraison courante.
                 */
                Map<DepartArriveeChemin, Chemin> cheminsFenetre = plan.calculerPlusCourtsChemins(idIntersectionsFenetre, idIntersectionsFenetre);
                chemins.putAll(cheminsFenetre);
                
                //Pour terminer on retient cette fenetre comme precedente fenetre.
                fenetrePrecedente = fenetre;
            }
            /*
             * Dans une dernière iterations, on calcule les plus courts chemins pour revenir de
             * la dernière fenetre de livraison vers l'entrepot.
             */
            Set<Integer> idIntersectionsFenetrePrecedente = fenetrePrecedente.getIdIntersectionsLivraisons();
            Set<Integer> idFenetreEntrepot = new HashSet<>(1);
            idFenetreEntrepot.add(entrepot.getId());
            Map<DepartArriveeChemin, Chemin> cheminsFenetreVersEntrepot = plan.calculerPlusCourtsChemins(idIntersectionsFenetrePrecedente, idFenetreEntrepot);
            chemins.putAll(cheminsFenetreVersEntrepot);
            
            /*
             * On modelise le probleme grace aux classes graphes.
             */
            GrapheCreux graphe = new GrapheCreux(intersections.size());
            //On etablit une correspondance entre les intersections et les sommets du graphe dans un tableau trié.
            ArrayList<Integer> correspondance = new ArrayList<>(intersections);
            //On parcourt les chemins calculés precedemment et on les ajoute au graphe transposé.
            Set<Map.Entry<DepartArriveeChemin, Chemin>> ensembleChemins = chemins.entrySet();
            Iterator<Map.Entry<DepartArriveeChemin, Chemin>> itChemins = ensembleChemins.iterator();
            while(itChemins.hasNext()) {
                //Pour chaque chemin on affiche 
                Map.Entry<DepartArriveeChemin, Chemin> entree = itChemins.next();
                DepartArriveeChemin itineraire = entree.getKey();
                Chemin chemin = entree.getValue();
                //On transpose les identifiants des intersections en identifiants du graphe.
                int sommetGrapheDepart = Collections.binarySearch(correspondance, itineraire.idDepart);
                int sommetGrapheArrivee = Collections.binarySearch(correspondance, itineraire.idArrivee);
                graphe.ajouterArc(sommetGrapheDepart, sommetGrapheArrivee, (int)chemin.getDuree()*10);
            }
            
            //On calcule la solution du problème.
            TSP1 tsp = new TSP1();
            tsp.chercheSolution(60000, graphe);
            
            //On interprete la solution du problème et on la transforme en tournee.
            tournee = new Tournee();
            //On part de l'entrepot.
            int sommetEntrepot = Collections.binarySearch(correspondance, entrepot.getId());
            int offsetEntrepot = tsp.trouverIndexSommet(sommetEntrepot);
            //On parcourt circulairement la solution depuis l'entrepot.
            int indexPrecedentSolution = offsetEntrepot;
            for (int i = 1;i < graphe.getNbSommets();i++) {
                int indexSolution = (i + offsetEntrepot) % graphe.getNbSommets();
                int sommetPrecedent = tsp.getSolution(indexPrecedentSolution);
                int sommet = tsp.getSolution(indexSolution);
                int intersectionPrecedente = correspondance.get(sommetPrecedent);
                int intersection = correspondance.get(sommet);
                //On ajoute ce chemin a la tournee.
                Chemin chemin = chemins.get(new DepartArriveeChemin(intersectionPrecedente, intersection));      
                //TODO : On ajoute la demande de livraison au chemin.
                tournee.AjouterChemin(chemin);
                
                indexPrecedentSolution = indexSolution;
            }
            //On ajoute un dernier chemin entre le dernier point de livraison et l'entrepot.
            int sommetPrecedent = tsp.getSolution(indexPrecedentSolution);
            int intersectionPrecedente = correspondance.get(sommetPrecedent);
            Chemin chemin = chemins.get(new DepartArriveeChemin(intersectionPrecedente, entrepot.getId()));
            tournee.AjouterChemin(chemin);
            return tournee.getTempsDeLivraison();
        }
        return -1;
    }

    public double getPremiereTournee() {
        /*
         * Calcul de la tournee selon le principe suivant:
         * On prend séquentiellement les points de livraison en respectant l'ordre
         * des fenetres de livraison.
         * On établit le plus court chemin entre deux points successifs
         * On somme.
         */

        /*
         * REMARQUE: cette méthode peut poser problème.
         * admettons qu'elle prenne un temps t1 sur 5 fenetre. mais ne respecte
         * pas la fenetre 4.
         * Peut-etre que par suite, on trouvera un itineraire qui prend un temps
         * t2 mais qui respecte la fenetre 4.
         * ... Bref on s'asseoit dessus pour le momment, on ajoutera une heuristique
         * qui veille à respecter les fenetres temporelle avant de s'inquieter du temps.
         */
        Iterator<FenetreLivraison> it_fenetre = ensembleLivraisons.getFenetresLivraison();

        // deux premières intersections : Entrepot et premiere demande de la premiere fenetre.
        Intersection interPrecedente = ensembleLivraisons.getEntrepot();
        Intersection interCourante = null;

        // premier chemin donc.
        Chemin chemin = null;

        /*
         * boucle centrale de "l'algorithme".
         * on récupère la prochaine fenetre.
         * on récupère la prochaine demande de la fenetre en cours.
         * tant qu'elle a un suivant (au sens de l'iterateur) on calcule les plus
         * courts chemin de la demande précédente à la demande en cours.
         * quand il n'y a plus de "demande suivante", on passe à la fenetre suivante
         * quand il n'y a plus de "fenetre suivante" on relie la derniere demande à l'entrepot.
         */
        while (it_fenetre.hasNext()) {

            FenetreLivraison fenetre = it_fenetre.next();

            // permet de verifier que la fenetre en cours a au moins une demande de livraison.
            if (!fenetre.getDemandesLivraison().hasNext()) {
                continue;
            }

            Iterator<DemandeLivraison> it_demande = fenetre.getDemandesLivraison();

            while (it_demande.hasNext()) {
                // récupération de l'intersection Courante.
                interCourante = it_demande.next().getIntersection();

                // calcul et stockage du plus court chemin entre les deux points.
                chemin = plan.calculerPlusCourtChemin(interPrecedente, interCourante);
                tournee.AjouterChemin(chemin);

                // l'intersection courante devient l'intersection précédente.
                interPrecedente = interCourante;
            }
        }

        // on retourne à l'entrepot
        chemin = plan.calculerPlusCourtChemin(interPrecedente, ensembleLivraisons.getEntrepot());
        tournee.AjouterChemin(chemin);

        return tournee.getTempsDeLivraison() ;
    }
}
