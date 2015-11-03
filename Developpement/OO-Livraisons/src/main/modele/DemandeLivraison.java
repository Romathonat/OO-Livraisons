package modele;

import java.util.Date;

/**
 * Une demande de livraison est un point de livraison qui doit être désservi par
 * le livreur
 * @author tfavrot
 */
public class DemandeLivraison {
    
    
    /**
     * L'intersection qui doit etre livrée. 
     */
    private Intersection intersection;
    
    /**
     * L'heure de livraison à laquelle le livreur doit passer. 
     */
    private Date heureLivraison;
    
    /**
     * La fenêtre horaire de livraison dans laquelle la demande de livraison 
     * s'inscrit.
     */
    private FenetreLivraison fenetreLivraison;
    
    /**
     * Le temps d'arrêt qu'effectue le livreur à un point de livaison.
     */
    private static int tempsArret;
    
    /**
     * Constructeur d'une demande de livraison.
     * @param lieuLivraison L'intersection qui doit etre livrée. 
     * @param fenetreLivraison La fenêtre horaire de livraison dans laquelle 
     * la demande de livraison s'inscrit.
     */
    public DemandeLivraison(Intersection lieuLivraison, FenetreLivraison fenetreLivraison) {
        this.intersection = lieuLivraison;
        this.fenetreLivraison = fenetreLivraison;
    }

    /**
     * Retourne l'intersection de la demande de livraison.
     * @return L'intersection de la demande de livraison.
     */
    public Intersection getIntersection() {
        return intersection;
    }
    
    /**
     * Retourne le temps d'arrêt d'une demande de livraison.
     * @return Le temps d'arrêt d'une demande de livraison.
     */
    public static int getTempsArret() {
        return tempsArret;
    }
    
    /**
     * Retourne l'heure de livraison d'une demande de livraison.
     * @return L'heure de livraison d'une demande de livraison.
     */
    public Date getHeureLivraison(){
        return heureLivraison;
    }
    
    /**
     * Retourne la fenêtre de livraison de la demande de livraison.
     * @return La fenêtre de livraison de la demande de livraison.
     */
    public FenetreLivraison getFenetreLivraison(){
        return fenetreLivraison;
    }
    
    public Date setHeureLivraison(Date heureLivraison){
        return this.heureLivraison = heureLivraison;
    }
    
}
