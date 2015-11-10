/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Plan;

/**
 * Représente graphiquement le plan.
 *
 * @author Nicolas
 */
public class VuePlan {

    /**
     * Le plan associé à la VuePlan.
     */
    private Plan plan;

    /**
     * La vue dans laquelle s'inscrit la VuePlan.
     */
    private Vue vue;

    /**
     * Constructeur de VuePlan.
     *
     * @param vue La vue dans laquelle s'inscrit la VuePlan.
     * @param plan Le plan associé à la VuePlan.
     */
    protected VuePlan(Vue vue, Plan plan) {
        this.vue = vue;
        this.plan = plan;
    }

    /**
     * Retourne le plan associé à la VuePlan.
     *
     * @return Le plan associé à la VuePlan.
     */
    protected Plan getPlan() {
        return plan;
    }
}
