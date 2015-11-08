/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Une fenêtre de livraison contient les demandes de livraisons qui doivent être
 * effectuées dans l'horaire de la fenêtre. 
 * @author tfavrot
 */
public class FenetreLivraison {

    /**
     * L'heure de début d'une fenêtre horaire de livraison.
     */
    private Date heureDebut;
   
    /**
     * L'heure de fin d'une fenêtre temporelle de livraison.
     */
    private Date heureFin;
    
    /**
     * La liste des demandes de livraisons qui s'inscrivent dans la fenêtre 
     * de livraison.
     */
    private List<DemandeLivraison> listeDemandesLivraison;

    /**
     * Constructeur d'une demande de livraison.
     * @param heureDebut L'heure de début d'une fenêtre temporelle de livraison.
     * @param heureFin L'heure de fin d'une fenêtre temporelle de livraison.
     */
    public FenetreLivraison(Date heureDebut, Date heureFin) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.listeDemandesLivraison = new ArrayList<DemandeLivraison> ();
    }

    /**
     * Retourne l'heure de début de la fenêtre temporelle de livraison.
     */
    public Date getHeureDebut() {
        return heureDebut;
    }

    /**
     * Retourne l'heure de fin de la fenêtre temporelle de livraison.
     */
    public Date getHeureFin() {
        return heureFin;
    }

    /**
     * Ajoute un point de livraison à la fenêtre de livraison. Cette fonction
     * vérifie que l'intersection passée en paramètre est valide: En plus de ne
     * pas être "null", celle-ci ne doit pas être l'entrepôt.
     *
     * @param id L'id de la livraison.
     * @param client L'id du client concerné par cette livraison.
     * @param inter L'intersection à laquelle doit avoir lieu la livraison.
     * @return La demande de livraison, si les conditions d'ajout sont
     * respectées. Null sinon.
     */
    public DemandeLivraison ajouterDemandeLivraison(int id, int client, Intersection inter) {
        if (inter == null) {
            return null;
        }

        DemandeLivraison demande = new DemandeLivraison(id, client, inter, this);
        this.listeDemandesLivraison.add(demande);
        return demande;
    }

    /**
     * Retourne un itérateur sur les demandes de Livraison.
     *
     * @return un itérateur sur les demandes de Livraison.
     */
    public Iterator<DemandeLivraison> getDemandesLivraison() {
        Collection constCollection = Collections.unmodifiableCollection(listeDemandesLivraison);
        return constCollection.iterator();
    }
    
    /**
     * Retourne l'ensemble des id des intersections des demandes de livraisons
     * de la fenêtre de livraison.
     * @return L'ensemble des id des intersections des demandes de livraisons.
     */
    protected Set<Integer> getIdIntersectionsLivraisons() {
        Set<Integer> idIntersections = new HashSet<>();
        Iterator<DemandeLivraison> itDemande = listeDemandesLivraison.iterator();
        while(itDemande.hasNext()) {
            DemandeLivraison livraison = itDemande.next();
            idIntersections.add(livraison.getIntersection().getId());
        }
        return idIntersections;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat monFormat = new SimpleDateFormat("HH:mm:ss");
        return monFormat.format(this.heureDebut)+" à "+monFormat.format(this.heureFin);
    }

    /**
     * Supprime la demande de livraison de la fenetre correspondant à celle passée en paramètre.
     * @param demandeASupprimer la demande à supprimer.
     * @return true si la demande a été trouvée ET supprimée, false sinon.
     */
    boolean supprimerDemandeLivraison(DemandeLivraison demandeASupprimer) {
        
        return listeDemandesLivraison.remove(demandeASupprimer);
    }
}
