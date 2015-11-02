package modele;

/**
 * Represente le depart et l'arrivee d'un Chemin.
 * @author mgaillard
 */
public class DepartArriveeChemin implements Comparable<DepartArriveeChemin>
{
    /**
     * L'identifiant de la livraison de depart.
     */
    public int idDepart;

    /**
     * L'identifiant de la livraison d'arrivee.
     */
    public int idArrivee;
    
    private static int ID_DEPART_MAX = 200;

    public DepartArriveeChemin(int idDepart, int idArrivee) {
        this.idDepart = idDepart;
        this.idArrivee = idArrivee;
    }
    /**
     * Compare cette instance a une autre instance de DepartArriveeChemin.
     * @param autre Une autre instance de DepartArriveeChemin.
     * @return -1, 0 ou 1 selon si cette instance est plus petite, egale ou plus grande que l'autre.
     */
    @Override
    public int compareTo(DepartArriveeChemin autre) {
        int cmpDepart = Integer.compare(idDepart, autre.idDepart);
        return cmpDepart == 0 ? Integer.compare(idArrivee, autre.idArrivee) : cmpDepart;
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
        DepartArriveeChemin other = (DepartArriveeChemin)obj;
        return (idDepart == other.idDepart && idArrivee == other.idArrivee);
    }
    
    @Override
    public int hashCode() {
        return idArrivee * ID_DEPART_MAX + idDepart;
    }
}