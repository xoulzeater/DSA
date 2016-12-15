/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsa;

import Manager.FoodManager;
import Manager.ResourceAssignManager;
import Manager.StaffManager;
import Manager.TaskManager;
import Manager.VictimManager;
import adt.ManagerInterface;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class DSA {

    static Scanner sc = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        for (;;) {
            System.out.println("=======================================");
            System.out.println("\t Main Menu\t");
            System.out.println("=======================================");
            System.out.println("Please enter your option:");
            System.out.println("1. Maintain resource.");
            System.out.println("2. Maintain Victim information.");
            System.out.println("3. Maintain Staff Information.");
            System.out.println("4. Maintain Task information.");
            System.out.println("5. Maintain requests and task assignments.");
            System.out.println("6. Maintain medical services."); 
            System.out.println("0. Exit");
            System.out.print("Your option:");
            String ans = sc.nextLine();
            switch (ans) {
                case "1":
                    resourceMenu();
                    break;
                case "2":
                    victimMenu();
                    break;
                case "3":
                    staffMenu();
                    break;
                case "4":
                    taskMenu();
                    break;
                case "5":
                    break;
                case "0":
                    System.out.println("Thank you for using the system!!");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public static void resourceMenu() {
        ManagerInterface mgr = new FoodManager();
        ResourceAssignManager resMgr = new ResourceAssignManager();
        for (;;) {
            System.out.println("=======================================");
            System.out.println("\t Resource Menu\t");
            System.out.println("=======================================");
            System.out.println("Please select an option:");
            System.out.println("1. Add Resource");
            System.out.println("2. Update Resource");
            System.out.println("3. Delete Resource");
            System.out.println("4. View all resource.");
            System.out.println("5. Assign resource");
            System.out.println("6. View all task assigned according to age.");
            System.out.println("7. View task according to victim id");
            System.out.println("0. Return to main menu.");
            System.out.print("Your option:");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    mgr.displayInsertRecord();
                    break;
                case "2":
                    mgr.displayUpdateRecord();
                    break;
                case "3":
                    mgr.displayDeleteRecord();
                    break;
                case "4":
                    mgr.displayAllRecord();
                    break;
                case "5":
                    resMgr.addResourceAssign();
                    break;
                case "6":
                    resMgr.findResourceAssignByAge();
                    break;
                case "7":
                    resMgr.findResourceAssignByVictimId();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid optionn please try again!");

            }
        }
    }

    public static void staffMenu(){
        StaffManager mgr = new StaffManager();
        for (;;) {
            System.out.println("=======================================");
            System.out.println("\t Staff Menu\t");
            System.out.println("=======================================");
            System.out.println("Please select an option:");
            System.out.println("1. Add new staff");
            System.out.println("2. Update staff information.");
            System.out.println("3. Delete staff information.");
            System.out.println("4. View All Staff information");
            System.out.println("0. Return to main menu.");
            System.out.print("Your option:");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                   mgr.addStaff();
                    break;
                case "2":
                    mgr.updateStaff();
                    break;
                case "3":
                    mgr.deleteStaff();
                    break;
                case "4":
                    mgr.viewAllStaff();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid optionn please try again!");

            }
        }
    }
    
    public static void victimMenu(){
        ManagerInterface mgr = new VictimManager();
        for (;;) {
            System.out.println("=======================================");
            System.out.println("\t Victim Manager Menu\t");
            System.out.println("=======================================");
            System.out.println("Please select an option:");
            System.out.println("1. Add victim");
            System.out.println("2. Update victim information.");
            System.out.println("3. Delete victim information");
            System.out.println("4. View all victim information.");
            System.out.println("0. Return to main menu.");
            System.out.print("Your option:");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    mgr.displayInsertRecord();
                    break;
                case "2":
                    mgr.displayUpdateRecord();
                    break;
                case "3":
                    mgr.displayDeleteRecord();
                    break;
                case "4":
                    mgr.displayAllRecord();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid optionn please try again!");

            }
        }
    }
    
    public static void taskMenu(){
        TaskManager mgr = new TaskManager();
           for (;;) {
            System.out.println("=======================================");
            System.out.println("\t Task Menu\t");
            System.out.println("=======================================");
            System.out.println("Please select an option:");
            System.out.println("1. Add task");
            System.out.println("2. Update task information.");
            System.out.println("3. Delete task information.");
            System.out.println("4. View All task information");
            System.out.println("0. Return to main menu.");
            System.out.print("Your option:");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    mgr.addTask();
                    break;
                case "2":
                    mgr.deleteTask();
                    break;
                case "3":
                    mgr.updateTask();
                    break;
                case "4":
                    mgr.viewAllTask();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid optionn please try again!");

            }
        }
    }


}
