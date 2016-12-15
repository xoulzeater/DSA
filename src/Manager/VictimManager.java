/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.DataAccessInterface;
import adt.ListInterface;
import adt.ManagerInterface;
import da.ConnectDb;
import da.VictimDataAccess;
import domain.Victim;
import java.util.GregorianCalendar;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class VictimManager implements ManagerInterface{

    private final Scanner sc;
    private final DataAccessInterface<Victim> da;

    public VictimManager() {
        sc = new Scanner(System.in);
        da = new VictimDataAccess();
    }

    @Override
    public void displayInsertRecord() {
        System.out.println("=========================");
        System.out.println("|\t Add Victim \t|");
        System.out.println("=========================");

        String name = "";
        System.out.print("Please enter your name:");
        name = sc.nextLine();

        GregorianCalendar gc;
        do {
            gc = MyConverter.readStringCalendar(
                    "Please enter the date of birth(dd/mm/yyyy):",
                    "Please enter the valid date format!!", false, "dd/MM/yyyy");
            if (gc.after(new GregorianCalendar())) {
                System.out.println("Date of birth cannot be future!!!");
            }
        } while (gc.after(new GregorianCalendar()));

        System.out.print("Please enter the victim address:");
        String address = sc.nextLine();
        String genderOption = MyConverter.requestGender();
        System.out.println("----------------------------------------");
        Victim victim = new Victim(0, name, gc, address, "Available", genderOption);
        printVictim(victim);
        boolean validate = MyConverter.validateOption(
                "Are you sure you want to add victim information"
                + " in the database(Y/N)?");
        if (validate) {
            int result = da.addOneRecord(victim);
            if (result > 0) {
                System.out.println("Victim successful added in database!");
            } else {
                System.out.println("Victim failed to add into the database!!");
            }
        } else {
            System.out.println("Insert victim cancel!");
        }

    }


    @Override
    public void displayUpdateRecord() {
        System.out.println("=============================");
        System.out.println("|\t Update Victim Information \t|");
        System.out.println("=============================");

        int victimId = MyConverter.requestIntegerValue(
                "Please enter your victim id:",
                "Invalid Victim Id format, please try again!!", false);
        Victim result =  da.selectOneRecord(victimId);
        if (result == null) {
            System.out.println("No Victim information found in the database!");
        } else {
            System.out.println("----------------------------------");
            printVictim(result);
            System.out.println("----------------------------------");
            System.out.println("<< BLANK FOR REMAIN SAME >>");
            System.out.print("Please enter your new victim name:");
            String name = sc.nextLine();

            GregorianCalendar gc;
            do {
                gc = MyConverter.readStringCalendar(
                        "Please enter the date of birth(dd/mm/yyyy):",
                        "Please enter the valid date format!!", false, "dd/MM/yyyy");
                if (gc.after(new GregorianCalendar())) {
                    System.out.println("Date of birth cannot be future!!!");
                }
            } while (gc.after(new GregorianCalendar()));
            String gender = "";
            do {
                System.out.print("Please enter your new gender(M/F):");
                gender = sc.nextLine();
                if (!gender.isEmpty()) {
                    gender = getGender("");
                }
            } while (!gender.isEmpty() && !gender.equals("Male") && !gender.equals("Female"));
            System.out.print("Please enter the new address:");
            String address = sc.nextLine();
            System.out.print("Please enter the new status:");
            String status = sc.nextLine();
            if (name.isEmpty()) {
                name = result.getName();
            }
            if (address.isEmpty()) {
                address = result.getAddress();
            }
            if (status.isEmpty()) {
                status = result.getStatus();
            }
            if (gender.isEmpty()) {
                gender = result.getGender();
            }
            Victim newVictim = new Victim(result.getId(), name, gc, address, status, gender);
            int results = da.updateRecord(newVictim);
            if (results > 0) {
                System.out.println("Victim record successfully update in database.");
            } else {
                System.out.println("Victim record fail to update!!");
            }
        }
    }


    @Override
    public void displayAllRecord() {
        System.out.println("=========================================");
        System.out.println("|\t View All Victim Information \t|");
        System.out.println("=========================================");
        ListInterface<Victim> victimList =  da.selectAllRecord();
        String title = String.format("| %-5s | %-20s | %-10s | %-50s | %-10s | %-10s|",
                    "ID", "Name","Date","Address","Status", "Gender");
        System.out.println("===========================================================================================================================");
        System.out.println(title);
        System.out.println("===========================================================================================================================");
        for (Victim victim : victimList) {
            String msg = String.format("| %-5d | %-20s | %-10s | %-50s | %-10s | %-10s|",
                    victim.getId(), victim.getName(),
                    MyConverter.displayDate(victim.getDateOfBirth()),
                    victim.getAddress(), victim.getStatus(), victim.getGender());
            System.out.println(msg);
        }
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    public void printVictim(Victim victim) {
        System.out.println("Victim name:" + victim.getName());
        System.out.println("Date of birth:"
                + MyConverter.displayDate(victim.getDateOfBirth()));
        System.out.println("Address:" + victim.getAddress());
        System.out.println("Gender:" + victim.getGender());
        System.out.println("Status:" + victim.getStatus());
    }

    public String getGender(String requestMsg) {
        String gender = "";
        do {
            System.out.print(requestMsg);
            gender = sc.nextLine().toLowerCase();
            switch (gender) {
                case "m":
                    return "Male";
                case "f":
                    return "Female";
                default:
                    System.out.println("Invalid gender please enter again!");
            }
        } while (!"m".equals(gender) && !"f".equals(gender));
        return null;
    }

    @Override
    public void displayDeleteRecord() {
        ConnectDb db = new ConnectDb();
        System.out.println("=============================");
        System.out.println("|\t !! DELETE VICTIM INFORMATION !!\t|");
        System.out.println("=============================");

        int victimId = MyConverter.requestIntegerValue(
                "Please enter the victim id:",
                "Invalid format of victim id!!", false);
        Victim result = da.selectOneRecord(victimId);
        if (result == null) {
            System.out.println("No such resource found in the list.");
        } else {
            System.out.println("------------------------------------------");
            printVictim(result);
            System.out.println("------------------------------------------");
            for (;;) {
                System.out.print("Are you sure you want to delete the victim information?(Y/N)");
                switch (sc.nextLine().toUpperCase()) {
                    case "Y":
                        /*remember uncommnent */
                        //int results = db.deleteVictim(result.getId());
                        int results = 0;
                        if (results > 0) {
                            System.out.println("Victim deleted successfully!");
                        } else {
                            System.out.println("Victim failed to delete!!");
                        }
                        return;
                    case "N":
                        System.out.println("Action delete Victim is cancelled!");
                        return;
                    default:
                        System.out.println("Please enter the valid input!");
                        break;
                }
            }
        }

    }


}
