/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.GregorianCalendar;

/**
 *
 * @author Kah Hou
 */
public class TaskAssign {
    private int task_id;
    private int staff_id;
    private GregorianCalendar time;
    
    public TaskAssign() {
        
    }
    
    public TaskAssign(int task_id, int staff_id, GregorianCalendar time) {
        this.task_id = task_id;
        this.staff_id = staff_id;
        this.time = time;
    }
    
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
    
    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }
    
    public void setTime(GregorianCalendar time) {
        this.time = time;
    }
    
    public int getTask_id() {
        return task_id;
    }
    
    public int getStaff_id() {
        return staff_id;
    }
    
    public GregorianCalendar getTime() {
        return time;
    }
}
