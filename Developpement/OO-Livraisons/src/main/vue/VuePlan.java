/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Plan;

/**
 *
 * @author Nicolas
 */
public class VuePlan {
    protected Plan plan;
    protected Vue vue;
    
    public VuePlan(Vue vue,Plan plan){
        this.vue = vue;
        this.plan = plan;
    }
}
