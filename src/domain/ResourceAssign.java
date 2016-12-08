/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.GregorianCalendar;

/**
 *
 * @author User
 */
public class ResourceAssign {
    
    private int res_id;
    private int victim_id;
    private GregorianCalendar assign_time;
    
    public ResourceAssign(){
    
    }
    
    public ResourceAssign(int res_id,int victim_id,GregorianCalendar assign_time){
        this.res_id=res_id;
        this.victim_id = victim_id;
        this.assign_time = assign_time;
        
    }
    
}
