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
    
    
    protected List<Integer> intersectionSelectionnees;
    
    protected GenerateurCouleur generateurCouleur;
    
    protected Plan planCourant; 
    protected EnsembleLivraisons ensembleLivraisonsCourant;
    protected Tournee tourneeCourante;
    
    
    public Vue(Fenetre fenetre){
        
        this.fenetre = fenetre;
        
        this.planCourant = null;
        this.ensembleLivraisonsCourant = null;
        this.tourneeCourante = null;
        
        this.vueGraphique = new VueGraphique(this);
        
        this.vueTextuelle = new VueTextuelle();
        
        this.vueLegende = new VueLegende(this);
        this.vueLegende.updateLegende(0);
        
        this.vueStatus = new VueStatus();
        
        this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, ensembleLivraisonsCourant);
        this.vueTournee = new VueTournee(this, tourneeCourante);
        
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
    public void resetEnsembleLivraisons(){
        this.ensembleLivraisonsCourant = null;
        this.resetTournee();
    }
    
    public void resetTournee(){
        this.tourneeCourante = null;
        this.vueTournee = new VueTournee(this, tourneeCourante);
    }
    
    public void setPlanCourant(Plan plan){
        this.planCourant = plan;
    }
    
    public void setEnsembleLivraisonsCourant(EnsembleLivraisons ensembleLivraisons){
        this.ensembleLivraisonsCourant = ensembleLivraisons;
    }
    
    public void setTourneeCourante (Tournee tournee){
        this.tourneeCourante = tournee;
    }
    
    protected void updatePlan(Plan plan){
        
        this.planCourant = plan;
        this.resetEnsembleLivraisons();
        
        // RAZ des objets graphiques.
        vueGraphique.removeAll();
        vueTextuelle.removeAll();
            
        // Dessin du plan.
        vueGraphique.drawPlan();
        


        // Mise à jour de la légende.
        this.vueLegende.updateLegende(1);
        this.vueStatus.changerStatus("Plan chargé");
    }
    
    protected void updateEnsembleLivraisons(EnsembleLivraisons ensembleLivraisons){
                    
            this.ensembleLivraisonsCourant = ensembleLivraisons;
            this.vueEnsembleLivraisons = new VueEnsembleLivraisons(this, ensembleLivraisons);
            
            this.resetTournee();

            this.vueGraphique.drawLivraisons();

            this.vueLegende.updateLegende(2);
            
            this.vueTextuelle.UpdateVueTextuelle(vueEnsembleLivraisons.listFenetresLivraisonVue.iterator());

            this.vueStatus.changerStatus("Demandes de livraison chargée");
    }
    
    protected void updateTournee(Tournee tournee){
        
        this.vueEnsembleLivraisons.clearDemandeLivraisons();
        
        this.tourneeCourante = tournee;
        this.vueTournee = new VueTournee(this, tournee);
        
        
        
            
        vueTextuelle.UpdateVueTextuelle(vueEnsembleLivraisons.listFenetresLivraisonVue.iterator());

        vueGraphique.drawTournee();

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
