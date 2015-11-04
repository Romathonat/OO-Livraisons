package tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class IteratorMinFirst implements Iterator<Integer> {
    
    private class CoutSommet implements Comparable<CoutSommet> {
        //Le cout pour aller vers le sommte.
        public double cout;
        //Le sommet
        public int sommet;
        
        public CoutSommet(double cout, int sommet) {
            this.cout = cout;
            this.sommet = sommet;
        }
        
        /**
         * Compare cette instance a une autre instance de CoutSommet.
         * @param autre Une autre instance de CoutSommet.
         * @return -1, 0 ou 1 selon si cette instance est plus petite, egale ou plus grande que l'autre.
         */
        @Override
        public int compareTo(CoutSommet autre) {
           int cmpCout = Double.compare(cout, autre.cout);
           return cmpCout == 0 ? Integer.compare(sommet, autre.sommet) : cmpCout;
        }
    }

    private Integer[] candidats;
    private int nbCandidats;

    /**
     * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus qui
     * sont successeurs de sommetCrt dans le graphe g, dans l'odre croissant du
     * cout des arcs.
     *
     * @param nonVus
     * @param sommetCrt
     * @param g
     */
    public IteratorMinFirst(Collection<Integer> nonVus, int sommetCrt, Graphe g) {   
        nbCandidats = 0;
        
        ArrayList<CoutSommet> sommets = new ArrayList<>(nonVus.size());
        Iterator<Integer> it = nonVus.iterator();
        while (it.hasNext()) {
            Integer s = it.next();
            double cout = g.getCout(sommetCrt, s);
            if (cout >= 0.0) {
                sommets.add(new CoutSommet(cout, s));
            }
        }
        Collections.sort(sommets, Collections.reverseOrder());
        candidats = new Integer[sommets.size()];
        Iterator<CoutSommet> itSommets = sommets.iterator();
        while (itSommets.hasNext()) {
            candidats[nbCandidats++] = itSommets.next().sommet;
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidats > 0;
    }

    @Override
    public Integer next() {
        nbCandidats--;
        return candidats[nbCandidats];
    }

    @Override
    public void remove() {
    }
}
