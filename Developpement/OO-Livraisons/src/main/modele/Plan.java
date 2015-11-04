package modele;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author mgaillard
 */
public class Plan{

    // Attributs

    //private Intersection entrepot;
    private Map<Integer, Intersection> intersections;
    /**
     * Le plus grand identifiant donne a une intersection.
     */
    private int intersectionsMaxId;
    private Collection<Troncon> troncons;

    /**
     * Valeur maximale de X sur le plan.
     */
    private int Xmax;
    /**
     * Valeur maximale de Y sur le plan.
     */
    private int Ymax;

    // Methodes
    public Plan() {
        this.intersections = new HashMap<>();
        this.intersectionsMaxId = 0;
        this.troncons = new LinkedList<>();
        this.Xmax = 0;
        this.Ymax = 0;
    }

    /**
     * Ajoute une intersection au plan. L'identifiant de l'intersection doit
     * etre positif ou nul. Retourne l'Intersection ajoutee.
     *
     * @param id L'identifiant unique positif de l'intersection.
     * @param x La coordonnée X de l'intersection.
     * @param y La coordonnée Y de l'intersection.
     * @return L'Intersection ajoutee, ou bien null.
     */
    public Intersection ajouterIntersection(int id, int x, int y) {
        if (id >= 0 && x >= 0 && y >= 0) {
            Intersection intersection = new Intersection(id, x, y);
            intersections.put(id, intersection);
            intersectionsMaxId = Math.max(intersectionsMaxId, id);
            Xmax = Math.max(Xmax, x);
            Ymax = Math.max(Ymax, y);
            return intersection;
        }
        return null;
    }
  
    /**
     * Getter sur le Xmax
     * @return 
     */
    public int getXmax(){
        return Xmax;
    }
    
    /**
     * Getter sur le Ymax
     * @return 
     */
    public int getYMax(){
        return Ymax;
    }
    /**
     * Ajoute un troncon au plan. Pour l'intersection de depart du troncon, la
     * liste des successeurs est mise a jour. Si une des intersections de depart
     * ou d'arrivee n'existe pas, le troncon n'est pas ajouté.
     *
     * @param idIntersectionDepart L'identifiant de l'intersection de départ.
     * @param idIntersectionArrivee L'identifiant de l'intersection d'arrivee.
     * @param nom Le nom du troncon.
     * @param longueur La longueur du troncon.
     * @param vitesse La vitesse moyenne sur le troncon.
     * @return true si le troncon a bien pu etre ajouté, false sinon.
     */
    public Troncon ajouterTroncon(int idIntersectionDepart, int idIntersectionArrivee, String nom, double longueur, double vitesse) {
        //On cherche les deux intersections.
        Intersection depart = getIntersection(idIntersectionDepart);
        Intersection arrivee = getIntersection(idIntersectionArrivee);
        //Si les deux intersections existent, on peut ajouter le troncon.
        if (depart != null && arrivee != null) {
            //On peut ajouter le troncon.
            Troncon nouveauTroncon = new Troncon(depart, arrivee, nom, longueur, vitesse);
            //On ajoute le troncon a la collection.
            troncons.add(nouveauTroncon);
            //On ajoute le troncon comme successeur de l'intersection de depart.
            depart.ajouterTronconSortant(nouveauTroncon);
            //On retourne true pour signaler que le troncon a bien été ajouté.
            return nouveauTroncon;
        } else {
            //On retourne null pour signaler que le troncon n'a pas été ajouté.
            return null;
        }
    }

    /**
     * Retourne une Intersection par son identifiant. Si l'Intersection n'existe
     * pas, retourne null.
     *
     * @param idIntersection L'identifiant de l'Intersection à retourner.
     * @return Une Intersection, ou bien null.
     */
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
    }

    /**
     * Retourne un iterator sur les troncons
     *
     * @return
     */
    public Iterator<Troncon> getTroncons() {
        Collection constCollection = Collections.unmodifiableCollection(troncons);
        return constCollection.iterator();
    }

    /**
     * Retourne un iterator sur les intersections
     *
     * @return
     */
    public Iterator<Entry<Integer, Intersection>> getIntersections() {
        return this.intersections.entrySet().iterator();
    }
    
    /**
     * Représente une duree pour aller vers une intersection.
     */
    private class DistanceIntersection {

        // Attributs

        public double duree;
        public Intersection intersection;

        // Methodes

        public DistanceIntersection(double distance, Intersection intersection) {
            this.duree = distance;
            this.intersection = intersection;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            DistanceIntersection other = (DistanceIntersection) obj;
            return (duree == other.duree && intersection.getId() == other.intersection.getId());
        }
    }

    private class DistanceIntersectionComparator implements Comparator<DistanceIntersection> {

        /**
         * Compare deux DistanceIntersection.
         *
         * @return -1, 0 ou 1 selon si a est plus petite, egale ou plus grande que b.
         */
        @Override
        public int compare(DistanceIntersection a, DistanceIntersection b) {
            int cmpDistance = Double.compare(a.duree, b.duree);
            return cmpDistance == 0 ? Integer.compare(a.intersection.getId(), b.intersection.getId()) : cmpDistance;
        }

        @Override
        public boolean equals(Object obj) {
            return !(obj == null || getClass() != obj.getClass());
        }
    }

    /**
     * Utilise l'algorithme Dijkstra pour retourner les plus courts chemins vers un ensemble d'intersection.
     * @param idDepart L'id de l'intersection de départ du Chemin.
     * @param idArrivees Un ensemble d'id des intersections vers lesquelles calculer les plus courts chemins.
     * @return Une Map indexant tous les chemins entre le point de depart et les points d'arrives.
     */
    protected Map<DepartArriveeChemin, Chemin> calculerPlusCourtsChemins(int idDepart, Set<Integer> idArrivees) {
        //Tableau des precedences et des distances des noeuds.
        Troncon[] precedent = new Troncon[intersectionsMaxId + 1];
        //Contient les distances par rapport au depart des intersections.
        double[] duree = new double[intersectionsMaxId + 1];
        //File a priorite contenant toutes les intersections et leur distance par rapport au depart.
        PriorityQueue<DistanceIntersection> intersectionPasEncoreVues = new PriorityQueue<>(intersections.size(), new DistanceIntersectionComparator());
        //Ensemble des intersections à visiter avant d'arrêter le calcul.
        Set<Integer> idIntersectionsAVisiter = new HashSet(idArrivees);
        
        //On initialise les precedence à null, et les distances a l'infini.
        for (int i = 0; i < precedent.length; i++) {
            precedent[i] = null;
            duree[i] = Double.MAX_VALUE;
        }

        //La distance a l'intersection de depart est nulle.
        duree[idDepart] = 0.0;

        //On rempli le tableau avec tous les noeuds qui n'ont pas encore ete vu.
        Iterator<Intersection> itIntersection = intersections.values().iterator();
        while (itIntersection.hasNext()) {
            Intersection intersection = itIntersection.next();
            intersectionPasEncoreVues.add(new DistanceIntersection(duree[intersection.getId()], intersection));
        }

        //Boucle principale de l'algorithme.
        while (intersectionPasEncoreVues.size() > 0) {
            Intersection intersection = intersectionPasEncoreVues.poll().intersection;
            //On retire cette intersection de la liste des intersections d'arrivees.
            idIntersectionsAVisiter.remove(intersection.getId());
            //Si tous les plus courts chemins vers les intersections d'arrivées ont été calculé, on peut arrêter le calcul.
            if (idIntersectionsAVisiter.isEmpty()) {
                //Pas besoin de calculer d'autres choses, on peut arreter.
                intersectionPasEncoreVues.clear();
            } else {
                //Pour tous les successeurs de l'intersection.
                Iterator<Troncon> itTroncon = intersection.getTronconsSortants();
                while (itTroncon.hasNext()) {
                    Troncon troncon = itTroncon.next();
                    int idIntersectionArrivee = troncon.getIntersectionArrivee().getId();
                    if (duree[idIntersectionArrivee] > duree[intersection.getId()] + troncon.getDuree()) {
                        //On retire l'intersection de la file a priorite.
                        intersectionPasEncoreVues.remove(new DistanceIntersection(duree[idIntersectionArrivee], troncon.getIntersectionArrivee()));
                        //On met a jour sa duree dans le tableau.
                        duree[idIntersectionArrivee] = duree[intersection.getId()] + troncon.getDuree();
                        //On l'ajoute a nouveau dans la file de priorite.
                        intersectionPasEncoreVues.add(new DistanceIntersection(duree[idIntersectionArrivee], troncon.getIntersectionArrivee()));
                        //On met a jour les precedences pour retrouver le chemin.
                        precedent[idIntersectionArrivee] = troncon;
                    }
                }
            }
        }
        
        //On ne retourne les chemins que pour les Intersections atteintes lors du Dijsktra.
        idArrivees.removeAll(idIntersectionsAVisiter);
        
        //On calcule les plus courts chemin vers toutes les intersections d'arrivées.
        //On initialise le HashMap avec la capacité de depart.
        Map<DepartArriveeChemin, Chemin> chemins = new HashMap<>(idArrivees.size());
        
        //Pour chaque intersection d'arrivee.
        Iterator<Integer> itIdArrivee = idArrivees.iterator();
        while (itIdArrivee.hasNext()) {
            int idIntersectionArrivee = itIdArrivee.next();
            int idIntersection = idIntersectionArrivee;
            //On remonte dans l'arbre des precedents.
            Chemin chemin = new Chemin();
            while (precedent[idIntersection].getIntersectionDepart().getId() != idDepart) {
                //On ajoute le troncon avant les autres troncons.
                chemin.ajouterTronconDebut(precedent[idIntersection]);
                //On passe a l'intersection d'avant.
                idIntersection = precedent[idIntersection].getIntersectionDepart().getId();
            }
            //On ajoute le troncon du debut.
            chemin.ajouterTronconDebut(precedent[idIntersection]);
            //On ajoute ce chemin aux chemins.
            chemins.put(new DepartArriveeChemin(idDepart, idIntersectionArrivee), chemin);
        }
        
        return chemins;
    }
    
    /**
     * Calcule tous les plus courts chemin entre deux ensembles d'id d'intersections.
     * @param idDeparts L'ensemble des id des intersections de depart.
     * @param idArrivees L'ensemble des id des intersections d'arrivee.
     * @return Tous les plus courts chemins enter les intersections de depart et d'arrivee.
     */
    protected Map<DepartArriveeChemin, Chemin> calculerPlusCourtsChemins(Set<Integer> idDeparts, Set<Integer> idArrivees) {
        //On reserve une map pour contenir tous les chemins. Il y en a n²-n quand il y a n intersections.
        Map<DepartArriveeChemin, Chemin> chemins = new HashMap<>(idDeparts.size() * idArrivees.size());
        //Pour chaque intersection de la fenetre.
        Iterator<Integer> itIntersectionsDepart = idDeparts.iterator();
        while(itIntersectionsDepart.hasNext()) {
            //On calcule les chemins partant de cette intersection vers toutes les autres intersections.
            int idDepart = itIntersectionsDepart.next();
            Set<Integer> idIntersectionsArrivees = new HashSet<>(idArrivees);
            //On retire des intersections d'arrivees l'intersection de depart.
            idIntersectionsArrivees.remove(idDepart);
            //Si il y a au moins un chemin a calculer, on lance le calcul.
            if (idIntersectionsArrivees.size() > 0) {
                Map<DepartArriveeChemin, Chemin> cheminsFenetre = calculerPlusCourtsChemins(idDepart, idIntersectionsArrivees);
                chemins.putAll(cheminsFenetre);
            }
        }
        return chemins;
    }
    
    /**
     * Calcule le plus court chemin entre deux Intersections.
     * @deprecated 
     * @param depart L'intersection de depart du chemin.
     * @param arrivee L'intersection d'arrivee du chemin.
     * @return Le plus court chemin entre ces deux intersections.
     */
    protected Chemin calculerPlusCourtChemin(Intersection depart, Intersection arrivee) {
        Set<Integer> arrivees = new HashSet<>();
        arrivees.add(arrivee.getId());
        Map<DepartArriveeChemin, Chemin> chemins = calculerPlusCourtsChemins(depart.getId(), arrivees);
        return chemins.get(new DepartArriveeChemin(depart.getId(), arrivee.getId()));
    }
}
