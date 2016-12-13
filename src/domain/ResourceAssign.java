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
    
    private int id;
    private Resource resource;
    private Victim victim;
    private GregorianCalendar assignTime;
    private int quantity;
    
    public ResourceAssign(){
    
    }
    
    public ResourceAssign(int id,Resource res_id,Victim victim,
            GregorianCalendar assignTime,int quantity){
        this.id = id;
        this.resource=res_id;
        this.victim = victim;
        this.assignTime = assignTime;
        this.quantity= quantity;
        
    }
    
    
    public Resource getResource(){
        return resource;
    }
    
    public Victim getVictim(){
        return victim;
    }
    
    public GregorianCalendar getAssigntime(){
        return assignTime;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setResource(Resource resource){
        this.resource = resource;
    }
    
    public void setVictim(Victim victim){
        this.victim = victim;
    }
    
    public void setAssignTime(GregorianCalendar assignTime){
        this.assignTime = assignTime;
    }
    
    
}
