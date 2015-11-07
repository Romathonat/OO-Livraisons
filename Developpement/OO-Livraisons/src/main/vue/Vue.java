/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.Plan;
import modele.Tournee;

/**
 * Vue réprésente la vue d'un état du modèle dans toutes ses composantes (graphique,
 * textuelle, légende, status). 
 * @author Nicolas
 */
public class Vue {
    
    /**
     * La fenêtre graphique dans laquelle s'inscrit la Vue.
     */
    protected Fenetre fenetre;
    
    /**
     * La vue graphique de la Vue.
     */
    protected VueGraphique vueGraphique;
    
    /**
     * La vue textuelle de la Vue.
     */
    protected VueTextuelle vueTextuelle;
    
    /**
     * La légende associée à la vue.
     */
    protected VueLegende vueLegende;
    
    /**
     * Le status associé à la vue. 
     */
    protected VueStatus vueStatus;
    
    /**
     * La vue de l'ensemble de livraisons associé à la vue.
     */
    private VueEnsembleLivraisons vueEnsembleLivraisons;
    
    /**
     * La vue de la tournée associé à la vue.
     */
    private VueTournee vueTournee;
    
    /**
     * La vue du plan associé à la vue.
     */
    private VuePlan vuePlan;
    
    /**
     * La liste des ids des intersections selectionnées. 
     */
    private List<Integer> intersectionSelectionnees;
    
    /**
     * Constructeur d'une Vue.
     * @param fenetre La fenêtre graphique dans laquelle s'inscrit la Vue.
     */
    public Vue(Fenetre fenetre){
        
        this.fenetre = fenetre;
        
        this.vueGraphique = new VueGraphique(this);
        
        this.vueTextuelle = new VueTextuelle(this);

        this.vueStatus = new VueStatus(this);
        
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(null);
        this.vueTournee = new VueTournee(this, null);
        this.vuePlan = new VuePlan(this, null);
        
        this.vueLegende = new VueLegende(this);
        
        intersectionSelectionnees = new ArrayList<>();
        
    }
    
    public VueGraphique getVueGraphique(){
        return this.vueGraphique;
    }
    
    /**
     * 
     * @return la première intersection selectionne
     */
    public Integer getPremiereInterSelectionnee(){
        return this.getIntersectionSelectionnees().get(0);
    }
    
    /**
     * Ajoute une intersection à la liste l'intersections selectionnées et, en indiquant
     * si l'intersection rajoutée est un point de livraison.
     * @param id L'id de l'intersection à ajouter. 
     * @param isPointLivraison True si l'intersection est un point de livraison,
     * false si elle n'en est pas un.
     */
    public void ajouterInterSelectionnee(int id, boolean isPointLivraison){
        this.intersectionSelectionnees.add(id);
        this.vueTextuelle.mettreAjourLivraisonsSelectionnees();
        this.vueGraphique.repaint();
    }
    
    /**
     * Vide la listes des intercections selectionées.
     */
    public void supprimerInterSelectionee(){
        this.intersectionSelectionnees.clear();
        this.vueTextuelle.mettreAjourLivraisonsSelectionnees();
        this.vueGraphique.repaint();
    }
    
    /**
     * Retourne un itérateur sur la liste des intercections sélectionnées.
     * @return Un itérateur sur la liste des intercections sélectionnées.
     */
    public Iterator<Integer> getInterSelectionne(){
        return this.intersectionSelectionnees.iterator();
    }
    
    /**
     * Remet à zéro la VuePlan de la vue, et par conséquent la 
     * VueTournee et la VueEnsembleLivraisons de la vue. La VuePlan, la 
     * VueEnsembleLivraisons et la VueTournee de la vue seront alors des 
     * nouveaux objets de même type, vides 
     */
    protected void resetPlan(){
        this.vuePlan = new VuePlan(this, null);
        this.resetEnsembleLivraisons();
    }
    
    /**
     * Remet à zéro la VueEnsembleLivraisons de la vue, et par conséquent la 
     * VueTournee de la vue. La VueEnsembleLivraisons et la VueTournee de la vue 
     * seront alors des nouveaux objets de même type, vides 
     */
    protected void resetEnsembleLivraisons(){
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(null);
        this.resetTournee();
                
    }
    
    /**
     * Remet à zéro la vue tournée de la vue. 
     * La VueTournee de la vue sera alors un nouvel objet VueTournee vide. 
     */
    protected void resetTournee(){
        this.vueTournee = new VueTournee(this, null);
    }
   
    /**
     * Si le plan passé en paramètre est différent du plan actuellement connu 
     * par la vuePlan de la vue, mets à jour le plan connu et l'ensemble des composans
     * de la vue. Sinon, ne fait rien. 
     * 
     * @param plan Le plan avec lequel on veut mettre à jour la vue. 
     */
    protected void updatePlan(Plan plan){
        if (plan == this.getVuePlan().getPlan()) { // en cas de problème de chargement.
            return;
        }
        this.vuePlan = new VuePlan(this, plan);
        this.resetEnsembleLivraisons();
            
        this.vueGraphique.repaint();

        // Mise à jour de la légende.
        this.vueLegende.updateLegende();
        this.vueStatus.updateStatusDroit("Plan chargé");
    }
    
    /**
     * Si l'ensemble de livraison passé en paramètre est différent de l'ensemble 
     * de livraison actuellement connu par la vueEnsembleLivraisons de la vue, 
     * mets à jour l'ensemble de livraison connu et l'ensemble des composans
     * de la vue. Sinon, ne fait rien. 
     * 
     * @param ensembleLivraisons L'ensemble de livraison avec lequel on veut mettre à jour la vue. 
     */
    protected void updateEnsembleLivraisons(EnsembleLivraisons ensembleLivraisons){
        if (ensembleLivraisons == this.getVueEnsembleLivraisons().getEnsembleLivraison()) { // en cas de problème de chargement.
            return;
        }
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(ensembleLivraisons);

        this.resetTournee();

        this.vueGraphique.repaint();

        this.vueLegende.updateLegende();

        this.vueTextuelle.mettreAJourListeDemandes();

        this.vueStatus.updateStatusDroit("Demandes de livraison chargée");
    }
    
    /**
     * Si la tournée passé en paramètre est différente de da tournée actuellement 
     * connue par la vueTournée de la vue, mets à jour la tournée connue et 
     * l'ensemble des composans de la vue. Sinon, ne fait rien. 
     * 
     * @param tournee La tournée avec lequel on veut mettre à jour la vue. 
     */
    public void updateTournee(Tournee tournee){
        if (tournee == this.getVueTournee().getTournee()) { // au cas ou le calcul de la tournee échouerai.
            return;
        }
        this.getVueEnsembleLivraisons().clearDemandeLivraisons(); //On les enlève pour les remmettre dans l'ordre    
        this.vueTournee = new VueTournee(this, tournee);
              
        this.vueTextuelle.mettreAJourListeDemandes();

        this.vueGraphique.repaint();

        this.vueLegende.updateLegende();
        this.vueStatus.updateStatusDroit("Tournée calculée");
    }
    
    /**
     * Retourne la vue de la fenêtre de livraison correspondante à la demande de
     * livraison passée en paramètre. 
     * @param demandeLivraison Une demande de livraison.
     * @return La vue de la fenêtre de livraison. 
     */
    protected VueFenetreLivraison getFenetreCorrespondante(DemandeLivraison demandeLivraison) {
        Iterator<VueFenetreLivraison> it_fenetreVue = getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext()) {

            VueFenetreLivraison fenetreVue = it_fenetreVue.next();
            if (demandeLivraison.getFenetreLivraison().getHeureDebut().compareTo(fenetreVue.getFenetreLivraison().getHeureDebut()) == 0) {
                return fenetreVue;
            }
        }
        // la fenetre n'a pas été trouvée.
        return null;
    }

    /**
     * Retourne la VueEnsembleLivraison de la vue
     * @return La VueEnsembleLivraison de la vue
     */
    protected VueEnsembleLivraisons getVueEnsembleLivraisons() {
        return vueEnsembleLivraisons;
    }

    /**
     * Retourne la VueTournee de la vue.
     * @return La VueTournee de la vue.
     */
    protected VueTournee getVueTournee() {
        return vueTournee;
    }

    /**
     * Retourne la VuePlan de la vue.
     * @return La VuePlan de la vue.
     */
    protected VuePlan getVuePlan() {
        return vuePlan;
    }

    /**
     * @return the intersectionSelectionnees
     */
    protected List<Integer> getIntersectionSelectionnees() {
        return intersectionSelectionnees;
    } 
    
}
