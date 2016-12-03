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
public class Staff {
    private int id;
    private String name;
    private GregorianCalendar dob;
    private String status;
    private String gender;
    
    public Staff(){
        this(0,"",new GregorianCalendar(),"","");
    }
    
    public Staff(int id,String name,GregorianCalendar gc, String status,String gender){
        this.id = id;
        this.name = name;
        this.dob = gc;
        this.status = status;
        this.gender = gender;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public GregorianCalendar getDob(){
        return dob;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getGender(){
        return gender;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDob(GregorianCalendar gc){
        this.dob = gc;
    }
    
    public void setGender (String gender){
        this.gender = gender;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
}
