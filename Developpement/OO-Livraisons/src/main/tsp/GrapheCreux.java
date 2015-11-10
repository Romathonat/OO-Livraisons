package tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mgaillard
 */
public class GrapheCreux implements Graphe {

    /**
     * Une classe representant une arete du Graphe a laquelle on associe un
     * cout.
     */
    private class Arc {

        public int arrivee;
        public double cout;

        public Arc(int arrivee, double cout) {
            this.arrivee = arrivee;
            this.cout = cout;
        }
    }

    private int nbSommets;
    private List<Arc>[] listeAdjacence;

    /**
     * Construit un graphe creux contenant un certain nombre de sommets.
     *
     * @param nbSommets
     */
    public GrapheCreux(int nbSommets) {
        this.nbSommets = nbSommets;
        listeAdjacence = new ArrayList[nbSommets];
        //On initialise chaque ArrayList avec le bon type.
        for (int i = 0; i < nbSommets; i++) {
            listeAdjacence[i] = new ArrayList<>();
        }
    }

    /**
     * Ajoute un arc au graphe entre les sommets i et j dont le cout est cout.
     *
     * @param i Sommet de depart.
     * @param j Sommet d'arrivee.
     * @param cout Cout de l'arc.
     */
    public void ajouterArc(int i, int j, double cout) {
        //Si les deux sommets appartiennent au graphe.
        //Si le cout est strictement superieur a 0.
        if (i >= 0 && i < nbSommets && j >= 0 && j < nbSommets && cout > 0) {
            //On ajoute l'arete au graphe.
            Arc arete = new Arc(j, cout);
            listeAdjacence[i].add(arete);
        }
    }

    /**
     * @return le nombre de sommets de <code>this</code>
     */
    @Override
    public int getNbSommets() {
        return nbSommets;
    }

    /**
     * @param i
     * @param j
     * @return le cout de l'arc (i,j) si (i,j) est un arc ; -1 sinon
     */
    @Override
    public double getCout(int i, int j) {
        if (i >= 0 && i < nbSommets && j >= 0 && j < nbSommets) {
            //On parcourt la liste d'adjacence.
            Iterator<Arc> itArete = listeAdjacence[i].iterator();
            while (itArete.hasNext()) {
                Arc arete = itArete.next();
                if (arete.arrivee == j) {
                    return arete.cout;
                }
            }
        }
        //Si on n'a pas trouve d'arete, on retourne -1.
        return -1;
    }

    /**
     * @param i
     * @param j
     * @return true si <code>(i,j)</code> est un arc de <code>this</code>
     */
    @Override
    public boolean estArc(int i, int j) {
        if (i >= 0 && i < nbSommets && j >= 0 && j < nbSommets) {
            //On parcourt la liste d'adjacence.
            Iterator<Arc> itArete = listeAdjacence[i].iterator();
            while (itArete.hasNext()) {
                Arc arete = itArete.next();
                if (arete.arrivee == j) {
                    return true;
                }
            }
        }
        //Si on n'a pas trouve d'arete, on retourne false.
        return false;
    }
}
