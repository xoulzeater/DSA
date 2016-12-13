/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.GregorianCalendar;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class Victim implements Comparable<Victim> {
    private int id;
    private String name;
    private GregorianCalendar dateOfBirth;
    private String address;
    private String status;
    private String gender;
    private int priority;
    
    public Victim(){}
    
    public Victim(int id,String name,GregorianCalendar gc, String address,
            String status,String gender){
        this.id = id;
        this.name = name;
        this.dateOfBirth = gc;
        this.address= address;
        this.status = status;
        this.gender = gender;
        setPriority();
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public GregorianCalendar getDateOfBirth(){
        return dateOfBirth;
    }
    
    public String getAddress(){
        return address;
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
    
    public void setDateOfBirth(GregorianCalendar gc){
        this.dateOfBirth = gc;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }

    public int getAge(){
        return MyConverter.getYears(dateOfBirth, new GregorianCalendar());
    }
    
    public String getPriority(){
        switch(priority){
            case 1:
                return "Adult";
            case 2:
                return "Old Folks";
            case 3:
                return "Child";
            default:
                return "Alien";
        }
    }
    
    public void setPriority(){
        if(getAge() < 18){
            this.priority = 3;
        }else if (getAge() > 55 ){
            this.priority=2;
        }else {
           this.priority = 1;
        }
    }
    
    @Override
    public int compareTo(Victim o) {
       
        if(priority > o.priority){
            return -1;
        }else if (priority == o.priority ){
            return 0;
        }else {
            return 1;
        }
    }
    
    
    
    
}
