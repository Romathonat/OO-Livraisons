package tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author mgaillard
 */
public class TSP3 extends TSP2 {

    @Override
    protected Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g) {
        return new IteratorMinFirst(nonVus, sommetCrt, g);
    }
}
