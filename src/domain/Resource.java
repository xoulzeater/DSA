/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author User
 */
public class Resource {
    private int resId;
    private String name;
    private String type;
    private int quantity;
    private String status;
    
    public Resource(){
        this(0,"","",0,"");
    }
    

    
    public Resource(String name){
         this(0,name,"",0,"");
    }
    
    public Resource(int res_id,String name, String type, int quantity, String status){
        this.resId=res_id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.status = status;
    }
    
    
    public int getResId(){
        return resId;
    }
    
    public String getName(){
        return name;
    }
    
    public String getType(){
        return type;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setResId(int resid){
        this.resId = resId;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    
}
