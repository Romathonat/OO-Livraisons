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
    protected List<FenetreLivraisonVue> listFenetresLivraisonVue;
    protected List<DemandeLivraisonVue> listDemandesLivraisonVue;
    protected List<CheminVue> listCheminVue;
    
    protected GenerateurCouleur generateurCouleur;
    
    protected Plan planCourant; //il nous faut garder ces references pour redessiner le plan quand repaint est appelle
    protected EnsembleLivraisons ensembleLivraisonsCourant;//attention doivent être mise à null si on recharge juste le plan !
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
        
        vueStatus = new VueStatus();
        
        listFenetresLivraisonVue = new ArrayList<>();
        listDemandesLivraisonVue = new ArrayList<>();
        listCheminVue = new ArrayList<>();
        
        generateurCouleur = new GenerateurCouleur();
    }
    
    public void resetEnsembleLivraisons(){
        this.ensembleLivraisonsCourant = null;
        this.resetTournee();
    }
    
    public void resetTournee(){
        this.tourneeCourante = null;
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
            this.resetTournee();

            // mise à jour des objets visuels
            this.listFenetresLivraisonVue.clear();
            this.listDemandesLivraisonVue.clear();

            Iterator<FenetreLivraison> it_fenetre = this.ensembleLivraisonsCourant.getFenetresLivraison();
            Iterator<DemandeLivraison> it_demande = null;

            while (it_fenetre.hasNext()) {
                FenetreLivraison fenetreLivraison = it_fenetre.next();
                FenetreLivraisonVue fenetreLivraisonVue = new FenetreLivraisonVue(fenetreLivraison, generateurCouleur.getCouleurSuivante());
                listFenetresLivraisonVue.add(fenetreLivraisonVue);

                it_demande = fenetreLivraison.getDemandesLivraison();
                while (it_demande.hasNext()) {
                    listDemandesLivraisonVue.add(new DemandeLivraisonVue(fenetre, fenetreLivraisonVue, it_demande.next()));
                }
            }


            vueGraphique.drawLivraisons();

            this.vueLegende.updateLegende(2);
            
            vueTextuelle.UpdateVueTextuelle(listDemandesLivraisonVue.iterator());

            this.vueStatus.changerStatus("Demandes de livraison chargée");
    }
    
    protected void updateTournee(Tournee tournee){
        this.tourneeCourante = tournee;

            // Mise à jour des elements graphiques.
            listDemandesLivraisonVue.clear();
            Iterator<Chemin> it_chemin = this.fenetre.vue.tourneeCourante.getChemins();
            Iterator<DemandeLivraison> it_demande = null;

            while (it_chemin.hasNext()) {
                Chemin chemin = it_chemin.next();
                FenetreLivraisonVue fenetreLivraisonVue = getFenetreCorrespondante(chemin.getLivraisonArrivee());

                if (fenetreLivraisonVue == null) {
                    continue;
                }

                listCheminVue.add(new CheminVue(fenetreLivraisonVue, chemin));
                listDemandesLivraisonVue.add(new DemandeLivraisonVue(this.fenetre, fenetreLivraisonVue, chemin.getLivraisonArrivee()));
            }

            // mise à jour de la vue textuelle.
            vueTextuelle.UpdateVueTextuelle(listDemandesLivraisonVue.iterator());

            // mise àjour de la vue graphique.
            vueGraphique.drawTournee();
            
            this.vueStatus.changerStatus("Tournée calculée");
    }
    
    private FenetreLivraisonVue getFenetreCorrespondante(DemandeLivraison demandeLivraison) {
        Iterator<FenetreLivraisonVue> it_fenetreVue = listFenetresLivraisonVue.iterator();
        while (it_fenetreVue.hasNext()) {

            FenetreLivraisonVue fenetreVue = it_fenetreVue.next();
            if (demandeLivraison.getFenetreLivraison().getHeureDebut().compareTo(fenetreVue.getFenetre().getHeureDebut()) == 0) {
                return fenetreVue;
            }
        }
        // la fenetre n'a pas été trouvée.
        return null;
    }
    
    
}
