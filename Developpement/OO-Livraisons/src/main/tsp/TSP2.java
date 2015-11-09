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
        double sommeCoutsMin = coutMinSuccesseur(sommetCourant, nonVus, false);
        Iterator<Integer> it = nonVus.iterator();
        while (it.hasNext()) {
            Integer s = it.next();
            sommeCoutsMin += coutMinSuccesseur(s, nonVus, true);
        }
        return sommeCoutsMin;
    }
    
    private double coutMinSuccesseur(Integer sommet, Collection<Integer> nonVus, boolean prendreCompteEntrepot) {
        double coutMin = Double.MAX_VALUE;
        Iterator<Integer> it = nonVus.iterator();
        //On regarde le min vers les sommets non vus.
        while (it.hasNext()) {
            Integer s = it.next();
            double cout = g.getCout(sommet, s);
            if (cout >= 0.0) {
                coutMin = Math.min(coutMin, cout);
            }
        }
        //On regarde le cout min vers le point de depart.
        if (prendreCompteEntrepot) {
            double coutDepart = g.getCout(sommet, 0);
            if (coutDepart >= 0.0) {
                coutMin = Math.min(coutMin, coutDepart);
            }
        }
        return coutMin;
    }
}
