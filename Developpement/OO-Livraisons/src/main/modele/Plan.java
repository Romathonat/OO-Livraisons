package modele;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author mgaillard
 */
public class Plan {
    // Attributs
    private Intersection entrepot;
    private Map<Integer, Intersection> intersections;
    /**
     * Le plus grand identifiant donne a une intersection.
     */
    private int intersectionsMaxId;
    private Collection<Troncon> troncons;
    
    // Methodes
    public Plan() {
        this.entrepot = null;
        this.intersections = new HashMap<>();
        this.intersectionsMaxId = 0;
        this.troncons = new LinkedList<>();
    }
    
    /**
     * Ajoute une intersection au plan.
     * L'identifiant de l'intersection doit etre positif ou nul.
     * Retourne l'Intersection ajoutee.
     * @param id L'identifiant unique positif de l'intersection.
     * @param x La coordonnée X de l'intersection.
     * @param y La coordonnée Y de l'intersection.
     * @return L'Intersection ajoutee, ou bien null.
     */
    public Intersection ajouterIntersection(int id, int x, int y) {
        if (id >= 0) {
            Intersection intersection = new Intersection(id, x, y);
            intersections.put(id, intersection);
            intersectionsMaxId = Math.max(intersectionsMaxId, id);
            return intersection;
        }
        return null;
    }
    
    /**
     * Ajoute un troncon au plan.
     * Pour l'intersection de depart du troncon, la liste des successeurs est mise a jour.
     * Si une des intersections de depart ou d'arrivee n'existe pas, le troncon n'est pas ajouté.
     * @param idIntersectionDepart L'identifiant de l'intersection de départ.
     * @param idIntersectionArrivee L'identifiant de l'intersection d'arrivee.
     * @param nom Le nom du troncon.
     * @param longueur La longueur du troncon.
     * @param vitesse La vitesse moyenne sur le troncon.
     * @return True si le troncon a bien pu etre ajouté, false sinon.
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
            //On retourne false pour signaler que le troncon n'a pas été ajouté.
            return null;
        }
    }
    
    /**
     * Retourne une Intersection par son identifiant.
     * Si l'Intersection n'existe pas, retourne null.
     * @param idIntersection L'identifiant de l'Intersection à retourner.
     * @return Une Intersection, ou bien null.
     */
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
    }
    
    /**
     * Définis l'Intersection où est situé l'entrepôt par son identifiant.
     * Si l'identifiant de l'intersection n'existe pas, l'entrepot sera definit à null.
     * @param idIntersectionEntrepot L'identifiant où est situé l'entrepôt.
     * @return L'intersection où est situé l'entrepot, ou bien null si cette intersection n'existe pas.
     */
    protected Intersection setEntrepot(int idIntersectionEntrepot) {
        this.entrepot = getIntersection(idIntersectionEntrepot);
        return this.entrepot;
    }
    
    /**
     * Retourne l'Intersection où est situé l'entrepôt.
     * @return l'Intersection où est situé l'entrepôt.
     */
    public Intersection getEntrepot() {
        return entrepot;
    }
    
    
    private class DistanceIntersection
    {
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
            DistanceIntersection other = (DistanceIntersection)obj;
            return (duree == other.duree && intersection.getId() == other.intersection.getId());
        }
    }
    
    private class DistanceIntersectionComparator implements Comparator<DistanceIntersection> {
        /**
         * Compare deux DistanceIntersection.
         * @return -1, 0 ou 1 selon si a est plus petite, egale ou plus grande b.
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
     * Utilise l'algorithme Dijkstra.
     * @param depart
     * @param arrivee
     * @return 
     */
    public Chemin calculerPlusCourtChemin(Intersection depart, Intersection arrivee) {
        //Tableau des precedences et des distances des noeuds.
        Troncon[] precedent = new Troncon[intersectionsMaxId + 1];
        //Contient les distances par rapport au depart des intersections.
        double[] duree = new double[intersectionsMaxId + 1];
        //File a priorite contenant toutes les intersections et leur distance par rapport au depart.
        PriorityQueue<DistanceIntersection> intersectionPasEncoreVues = new PriorityQueue<>(intersections.size(), new DistanceIntersectionComparator());
        
        //On initialise les precedence à null, et les distances a l'infini.
        for (int i = 0;i < precedent.length;i++) {
            precedent[i] = null;
            duree[i] = Double.MAX_VALUE;
        }
        
        //La distance a l'intersection de depart est nulle.
        duree[depart.getId()] = 0.0;
        
        //On rempli le tableau avec tous les noeuds qui n'ont pas encore ete vu.
        Iterator<Intersection> itIntersection = intersections.values().iterator();
        while (itIntersection.hasNext()) {
            Intersection intersection = itIntersection.next();
            intersectionPasEncoreVues.add(new DistanceIntersection(duree[intersection.getId()], intersection));
        }
        
        //Boucle principale de l'algorithme.
        while (intersectionPasEncoreVues.size() > 0) {
            Intersection intersection = intersectionPasEncoreVues.poll().intersection;
            System.out.println(intersection.getId());
            //Si l'intersection que l'on considere est la fin, on a donc deja calcule le plus court chemin vers la fin.
            if (intersection.equals(arrivee)) {
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
        
        Chemin chemin = new Chemin();
        //On remonte dans l'arbre des precedents.
        int idIntersection = arrivee.getId();
        while (precedent[idIntersection].getIntersectionDepart().getId() != depart.getId()) {
            //On ajoute le troncon avant les autres troncons.
            chemin.ajouterTronconDebut(precedent[idIntersection]);
            //On passe a l'intersection d'avant.
            idIntersection = precedent[idIntersection].getIntersectionDepart().getId();
        }
        //On ajoute le troncon du debut.
        chemin.ajouterTronconDebut(precedent[idIntersection]);
        
        return chemin;
    }
}
