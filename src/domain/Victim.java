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
    
    public Victim(){}
    
    public Victim(int id,String name,GregorianCalendar gc, String address,
            String status,String gender){
        this.id = id;
        this.name = name;
        this.dateOfBirth = gc;
        this.address= address;
        this.status = status;
        this.gender = gender;
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
    
    @Override
    public int compareTo(Victim o) {
        int yearsDiff = MyConverter.getYears(dateOfBirth, new GregorianCalendar());
        if(yearsDiff < 18){
            return yearsDiff;
        }else if (yearsDiff > 55 ){
            return yearsDiff;
        }else {
            return 0;
        }
    }
    
    
    
    
}
