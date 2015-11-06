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
 *
 * @author Nicolas
 */
public class Vue {
    
    protected Fenetre fenetre;
    
    protected VueGraphique vueGraphique;
    protected VueTextuelle vueTextuelle;
    protected VueLegende vueLegende;
    protected VueStatus vueStatus;
    
    private VueEnsembleLivraisons vueEnsembleLivraisons;
    private VueTournee vueTournee;
    private VuePlan vuePlan;
    
    private List<Integer> intersectionSelectionnees;
    
    private GenerateurCouleur generateurCouleur;
    
    public Vue(Fenetre fenetre){
        
        this.fenetre = fenetre;
        
        this.vueGraphique = new VueGraphique(this);
        
        this.vueTextuelle = new VueTextuelle();

        this.vueStatus = new VueStatus();
        
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, null);
        this.vueTournee = new VueTournee(this, null);
        this.vuePlan = new VuePlan(this, null);
        
        this.vueLegende = new VueLegende(this);
        
        intersectionSelectionnees = new ArrayList<>();
        
        generateurCouleur = new GenerateurCouleur();
    }
    
    public VueGraphique getVueGraphique(){
        return this.vueGraphique;
    }
    
    /**
     * indique quelle intersection a été selectionnée et si elle est un point de livraison
     * @param id
     * @param isPointLivraison 
     */
    public void ajouterInterSelectionnee(int id, boolean isPointLivraison){
        this.getIntersectionSelectionnees().add(id);
        this.vueGraphique.repaint();
    }
    
    public void supprimerInterSelectionee(){
        this.getIntersectionSelectionnees().clear();
        this.vueGraphique.repaint();
    }
    
    public Iterator<Integer> getInterSelectionne(){
        return this.getIntersectionSelectionnees().iterator();
    }
    
    protected void resetPlan(){
        this.vuePlan = new VuePlan(this, null);
        this.resetEnsembleLivraisons();
    }
    
    protected void resetEnsembleLivraisons(){
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, null);
        this.resetTournee();
                
    }
    
    protected void resetTournee(){
        this.vueTournee = new VueTournee(this, null);
    }
   
    protected void updatePlan(Plan plan){
        if (plan == this.getVuePlan().getPlan()) { // en cas de problème de chargement.
            return;
        }
        this.vuePlan = new VuePlan(this, plan);
        this.resetEnsembleLivraisons();
            
        // Dessin du plan.
        vueGraphique.drawPlan();

        // Mise à jour de la légende.
        this.vueLegende.updateLegende();
        this.vueStatus.changerStatus("Plan chargé");
    }
    
    protected void updateEnsembleLivraisons(EnsembleLivraisons ensembleLivraisons){
        if (ensembleLivraisons == this.getVueEnsembleLivraisons().getEnsembleLivraison()) { // en cas de problème de chargement.
            return;
        }
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, ensembleLivraisons);

        this.resetTournee();

        this.vueGraphique.drawLivraisons();

        this.vueLegende.updateLegende();

        this.vueTextuelle.UpdateVueTextuelle(getVueEnsembleLivraisons().getListVueFenetresLivraison());

        this.vueStatus.changerStatus("Demandes de livraison chargée");
    }
    
    protected void updateTournee(Tournee tournee){
        if (tournee == this.getVueTournee().getTournee()) { // au cas ou le calcul de la tournee échouerai.
            return;
        }
        this.getVueEnsembleLivraisons().clearDemandeLivraisons(); //On les enlève pour les remmettre dans l'ordre    
        this.vueTournee = new VueTournee(this, tournee);
              
        this.vueTextuelle.UpdateVueTextuelle(getVueEnsembleLivraisons().getListVueFenetresLivraison());

        this.vueGraphique.drawTournee();

        this.vueLegende.updateLegende();
        this.vueStatus.changerStatus("Tournée calculée");
    }
    
    protected VueFenetreLivraison getFenetreCorrespondante(DemandeLivraison demandeLivraison) {
        Iterator<VueFenetreLivraison> it_fenetreVue = getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext()) {

            VueFenetreLivraison fenetreVue = it_fenetreVue.next();
            if (demandeLivraison.getFenetreLivraison().getHeureDebut().compareTo(fenetreVue.getFenetre().getHeureDebut()) == 0) {
                return fenetreVue;
            }
        }
        // la fenetre n'a pas été trouvée.
        return null;
    }

    /**
     * @return the vueEnsembleLivraisons
     */
    protected VueEnsembleLivraisons getVueEnsembleLivraisons() {
        return vueEnsembleLivraisons;
    }

    /**
     * @return the vueTournee
     */
    protected VueTournee getVueTournee() {
        return vueTournee;
    }

    /**
     * @return the vuePlan
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

    /**
     * @return the generateurCouleur
     */
    protected GenerateurCouleur getGenerateurCouleur() {
        return generateurCouleur;
    }
    
    
}
