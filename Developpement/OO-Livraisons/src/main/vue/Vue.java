/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
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
    
    protected VueEnsembleLivraisons vueEnsembleLivraisons;
    protected VueTournee vueTournee;
    protected VuePlan vuePlan;
    
    
    protected List<Integer> intersectionSelectionnees;
    
    protected GenerateurCouleur generateurCouleur;
    
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
        this.intersectionSelectionnees.add(id);
        this.vueGraphique.repaint();
    }
    
    public void supprimerInterSelectionee(){
        this.intersectionSelectionnees.clear();
        this.vueGraphique.repaint();
    }
    
    public Iterator<Integer> getInterSelectionne(){
        return this.intersectionSelectionnees.iterator();
    }
    
    public void resetPlan(){
        this.vuePlan = new VuePlan(this, null);
        this.resetEnsembleLivraisons();
    }
    public void resetEnsembleLivraisons(){
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, null);
        this.resetTournee();
                
    }
    
    public void resetTournee(){
        this.vueTournee = new VueTournee(this, null);
    }
   
    
    protected void updatePlan(Plan plan){
        if (plan == this.vuePlan.plan) { // en cas de problème de chargement.
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
        if (ensembleLivraisons == this.vueEnsembleLivraisons.ensembleLivraison ) { // en cas de problème de chargement.
            return;
        }
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, ensembleLivraisons);

        this.resetTournee();

        this.vueGraphique.drawLivraisons();

        this.vueLegende.updateLegende();

        this.vueTextuelle.UpdateVueTextuelle(vueEnsembleLivraisons.listFenetresLivraisonVue.iterator());

        this.vueStatus.changerStatus("Demandes de livraison chargée");
    }
    
    protected void updateTournee(Tournee tournee){
        if (tournee == this.vueTournee.tournee) { // au cas ou le calcul de la tournee échouerai.
            return;
        }

        this.vueEnsembleLivraisons.clearDemandeLivraisons();
        
        this.vueTournee = new VueTournee(this, tournee);
              
        this.vueTextuelle.UpdateVueTextuelle(vueEnsembleLivraisons.listFenetresLivraisonVue.iterator());

        this.vueGraphique.drawTournee();

        this.vueLegende.updateLegende();
        this.vueStatus.changerStatus("Tournée calculée");
    }
    
    protected VueFenetreLivraison getFenetreCorrespondante(DemandeLivraison demandeLivraison) {
        Iterator<VueFenetreLivraison> it_fenetreVue = vueEnsembleLivraisons.listFenetresLivraisonVue.iterator();
        while (it_fenetreVue.hasNext()) {

            VueFenetreLivraison fenetreVue = it_fenetreVue.next();
            if (demandeLivraison.getFenetreLivraison().getHeureDebut().compareTo(fenetreVue.getFenetre().getHeureDebut()) == 0) {
                return fenetreVue;
            }
        }
        // la fenetre n'a pas été trouvée.
        return null;
    }
    
    
}
