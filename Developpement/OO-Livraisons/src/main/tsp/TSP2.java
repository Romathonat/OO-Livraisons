package tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author mgaillard
 */
public class TSP2 extends TSP1 {
    @Override
    protected double bound(Integer sommetCourant, Collection<Integer> nonVus) {
        //L'algorithme consiste a calculer la somme des arc de couts min sortant des sommets non vus.
        double sommeCoutsMin = coutMinSuccesseur(sommetCourant);
        Iterator<Integer> it = nonVus.iterator();
        while (it.hasNext()) {
            Integer s = it.next();
            sommeCoutsMin += coutMinSuccesseur(s);
        }
        return sommeCoutsMin;
    }
    
    private double coutMinSuccesseur(Integer sommet) {
        double coutMin = Double.MAX_VALUE;
        for (int i = 0;i < g.getNbSommets();i++) {
            double cout = g.getCout(sommet, i);
            if (cout >= 0.0) {
                coutMin = Math.min(coutMin, cout);
            }
        }
        return coutMin;
    }
}
