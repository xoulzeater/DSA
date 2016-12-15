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
public class Task implements Comparable<Task>{
    private int id;
    private String name;
    private String description;
    private int manPower;
    private String priority;
    private String status;
    
    public Task(){
    
    }
    
    public Task(int id,String name,String description, 
            int manPower, String priority, String status){
        this.id=id;
        this.name= name;
        this.description = description;
        this.manPower = manPower;
        this.priority = priority;
        this.status = status;
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
    
    public String getPriority() {
        return priority;
    }
    
    public String getStatus() {
        return status;
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
    
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    private int getPriorityInteger(){
        switch(priority.toLowerCase()){
            case "critical":
                return 3;
            case "normal":
                return 2;
            case "low":
                return 1;
            default:
                return -1;
        }
    }
    
    @Override
    public int compareTo(Task o) {
        if(getPriorityInteger() > o.getPriorityInteger()){
            return -1;
        }else if (getPriorityInteger() == o.getPriorityInteger()){
            return 0;
        }else{
            return 1;
        }
        
    }
}

