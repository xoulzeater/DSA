/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.ListInterface;
import da.ConnectDbStaff;
import domain.Staff;
import java.util.GregorianCalendar;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class StaffManager {

    private final Scanner sc;
    private final ConnectDbStaff db;

    public StaffManager() {
        sc = new Scanner(System.in);
        db = new ConnectDbStaff();
    }

    public void addStaff() {
        System.out.println("============================");
        System.out.println("\t Add Staff \t");
        System.out.println("============================");
        String name = "";

        System.out.print("Please enter your Staff name:");
        name = sc.nextLine();

        GregorianCalendar dob = MyConverter.readStringCalendar(
                "Please enter your date of birth(dd/MM/yyyy):",
                "Invalid date format, please try again!",false,"dd/MM/yyyy");

        String gender = MyConverter.requestGender();
        Staff staff = new Staff(0, name, dob, "Available", gender);

        int results = db.insertStaff(staff);
        if (results > 0) {
            System.out.println("Staff information has been successfully added in database!!");
        } else {
            System.out.println("Staff failed to add in the database!!");
        }
    }

    public void viewAllStaff() {

        System.out.println("=============================");
        System.out.println("\t View All Staff \t");
        System.out.println("=============================");
        System.out.println("========================================================");
        System.out.println("|\t Staff Id \t| Name | Date of birth |\tAddress \t| Status | Gender |");
        System.out.println("========================================================");
        ListInterface<Staff> staffList = db.selectAllStaff();
        for (int index = 0; index < staffList.getNumberOfEntries(); index++) {
            Staff tempStaff = staffList.getEntry(index);
            String msg = String.format("| %-5d \t| %-20s | %-10s | %-10s | %-10s |",
                    tempStaff.getId(),
                    tempStaff.getName(),
                    MyConverter.displayDate(tempStaff.getDob()),
                    tempStaff.getGender(), tempStaff.getStatus());
            System.out.println(msg);
        }
        System.out.print("Press enter to continue...");
        sc.nextLine();

    }

    public void deleteStaff() {
        System.out.println("====================================");
        System.out.println("| \t !!! DELETE STAFF !!! \t|");
        System.out.println("====================================");
        int staffId = MyConverter.requestIntegerValue("Please enter the staff id:",
                "Invalid format of staff id, must contain only numeirc characters.",false);

        Staff result = db.selectStaff(staffId);
        if (result == null) {
            System.out.println("No such food name found in the list.");
        } else {
            System.out.println("---------------------------------------");
            printStaff(result);
            System.out.println("----------------------------------------");
            for (;;) {
                System.out.print("Are you sure you want to delete the staff information?(Y/N)");
                switch (sc.nextLine().toUpperCase()) {
                    case "Y":
                        /*remember uncommnent */
                        //int results = db.deleteStaff(result.getId());
                        int results = 0;
                        if (results > 0) {
                            System.out.println("Staff deleted successfully!");
                        } else {
                            System.out.println("Staff failed to delete!!");
                        }
                        return;
                    case "N":
                        System.out.println("Action delete staff is cancelled!");
                        return;
                    default:
                        System.out.println("Please enter the valid input!");
                        break;
                }
            }
        }
    }

    public void updateStaff() {
        System.out.println("==========================");
        System.out.println("| \t Update Staff \t | ");
        System.out.println("==========================");

        int staffId = MyConverter.requestIntegerValue(
                "Please enter your staff id:",
                "Invalid staff id, please try again!!",false);

        Staff result = db.selectStaff(staffId);
        if (result == null) {
            System.out.println("Staff information not found in the database!!");
        } else {
            System.out.println("-----------------------------------------------");
            printStaff(result);
            System.out.println("-----------------------------------------------");

            System.out.print("Please enter your new Staff name:");
            String name = sc.nextLine();

            GregorianCalendar dob = MyConverter.readStringCalendar(
                    "Please enter your date of birth(dd/MM/yyyy HH:mm:ss):",
                    "Invalid date format, please try again!",true,"dd/MM/yyyy");
            
            
            System.out.print("Please enter your date of birth:");
            String date = sc.nextLine();
            
            

            String gender = MyConverter.requestGender();
            String status = "";
            do {
                System.out.print("Please enter the status(A/NA):");;
                status = sc.nextLine().toUpperCase();
                switch (status) {
                    case "A":
                        status = "Available";
                        break;
                    case "NA":
                        status = "Not Available";
                    default:
                        System.out.println("Please enter a valid input.");
                }
            } while (status.equals("A") && status.equals("NA"));
            Staff staff = new Staff(0, name, dob, status, gender);
            printStaff(staff);
            boolean request = MyConverter.validateOption(
                    "Are you sure want to insert the information to database(Y/N)?");
            if (request) {
                int results = db.updateStaff(staff);
                if (results > 0) {
                    System.out.println("Staff information update successfully!");
                } else {
                    System.out.println("Staff information failed to update!");
                }
            } else {
                System.out.println("Update Staff cancel!");
            }
        }
    }

    public void printStaff(Staff staff) {
        System.out.println("Staff id:" + staff.getId());
        System.out.println("Staff name:" + staff.getName());
        System.out.println("Staff date of birth:" + MyConverter.displayDate(staff.getDob()));
        System.out.println("Staff Gender:" + staff.getGender());
        System.out.println("Staff status:" + staff.getStatus());

    }

}
