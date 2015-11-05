package tsp;

/**
 *
 * @author mgaillard
 */
public class GrapheDense implements Graphe {
    private int nbSommets;
    private double[][] cout;
    
    /**
     * Construit un graphe dense contenant un certain nombre de sommets.
     * @param nbSommets 
     */
    public GrapheDense(int nbSommets) {
        this.nbSommets = nbSommets;
        cout = new double[nbSommets][nbSommets];
        for (int i = 0;i < nbSommets;i++) {
            for (int j = 0;j < nbSommets;j++) {
                cout[i][j] = -1.0;
            }
        }
    }
    
    /**
     * Ajoute un arc au graphe entre les sommets i et j dont le cout est nouveauCout.
     * @param i Sommet de depart.
     * @param j Sommet d'arrivee.
     * @param nouveauCout Cout de l'arc.
     */
    public void ajouterArc(int i, int j, double nouveauCout) {
        //Si les deux sommets appartiennent au graphe.
        //Si le cout est strictement superieur a 0.
        if (i >= 0 && i < nbSommets && j >= 0 && j < nbSommets && nouveauCout > 0) {
            cout[i][j] = nouveauCout;
        }
    }

    @Override
    public int getNbSommets() {
        return nbSommets;
    }

    @Override
    public double getCout(int i, int j) {
        if (i < 0 || i >= nbSommets || j < 0 || j >= nbSommets) {
            return -1;
        }
        return cout[i][j];
    }

    @Override
    public boolean estArc(int i, int j) {
        if (i < 0 || i >= nbSommets || j < 0 || j >= nbSommets) {
            return false;
        }
        return (cout[i][j] >= 0.0);
    }
}
