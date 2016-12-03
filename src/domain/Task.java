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
public class Task {
    private int id;
    private String name;
    private String description;
    private int manPower;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    
    public Task(){
    
    }
    
    public Task(int id,String name,String description, 
            int manPower, GregorianCalendar gc, GregorianCalendar gc2){
        this.id=id;
        this.name= name;
        this.description = description;
        this.manPower = manPower;
        this.startTime=gc;
        this.endTime = gc2;
        
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getManPower(){
        return manPower;
    }
    
    public GregorianCalendar getStartDate(){
        return startTime;
    }
    
    public GregorianCalendar getEndDate(){
        return endTime;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDescription (String decription){
        this.description = description;
    }
    
    public void setManPower(int manPower){
        this.manPower = manPower;
    }
    
    public void setStartDate(GregorianCalendar gc ){
        this.startTime  = gc;
    }
    
    public void setEndDate(GregorianCalendar gc){
        this.endTime = gc;
    }
}

