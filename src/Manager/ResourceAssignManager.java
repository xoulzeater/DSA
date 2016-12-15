/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.DataAccessInterface;
import adt.ListInterface;
import adt.SortedListInterface;
import da.ConnectDb;
import da.ResourceDataAccess;
import da.VictimDataAccess;
import domain.Resource;
import domain.ResourceAssign;
import domain.Victim;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class ResourceAssignManager{

    private Scanner sc;
    private VictimManager vicMgr;
    private ConnectDb db;
    private DataAccessInterface<Victim> vicDa;
    private DataAccessInterface<Resource> resDa;

    public ResourceAssignManager() {
        sc = new Scanner(System.in);
        vicMgr = new VictimManager();
        db = new ConnectDb();
        vicDa = new VictimDataAccess();
        resDa = new ResourceDataAccess();
        
    }

    public void addResourceAssign() {
        System.out.println("--------------------------------");
        System.out.println("|\t Resource Assign \t|");
        System.out.println("--------------------------------");
        System.out.println("List of victim");
        System.out.println("----------------------");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("ID \t Name \t\t\t Age \t Food Assigned \t Supply Assigned \t Priority ");
        System.out.println("-----------------------------------------------------------------------------------------");
        SortedListInterface<Victim> list = db.getAllSortedVictim();
        for (Victim temp : list) {
            System.out.println(temp.getId() + "\t "
                    + String.format("%-20s", temp.getName())
                    + "\t" + temp.getAge() + "\t\t" + db.getVictimFoodCount(temp.getId(), "food") + "\t\t"
                    + db.getVictimFoodCount(temp.getId(), "supply") + "\t\t" + temp.getPriority());
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("List of supply and food");
        System.out.println("----------------------");
        ListInterface<Resource> resourceList = resDa.selectAllRecord();
        System.out.println("----------------------------------------------------------");
        System.out.println("ID  \t Name \t\t\t Resource type \t Quantity \t  ");
        System.out.println("----------------------------------------------------------");
        for (Resource temp : resourceList) {
            System.out.println(temp.getResId() + "\t" + String.format("%-20s", temp.getName())
                    + "\t\t" + temp.getType() + " \t " + temp.getQuantity());
        }
        System.out.println("----------------------------------------------------------");
        int victimId = 0;
        Victim victim = null;
        do {
            victimId = MyConverter.requestIntegerValue(
                    "Please enter your victim id:",
                    "Please enter the valid number format!!", false);
            victim = vicDa.selectOneRecord(victimId);
            if (victim == null) {
                System.out.println("Please enter the victim id given or valid one!!");
            }
        } while (victim == null);
        int resourceId = 0;
        Resource resource = null;
        do {
            resourceId = MyConverter.requestIntegerValue(
                    "Please enter your resource id:",
                    "Invalid resource id format, please try again!!", false);
            resource = resDa.selectOneRecord(resourceId);
            if (resource == null) {
                System.out.println("Please enter the resource id given or valid one!!");
            }
        } while (resource == null);
        int quantity = 0;
        do {
            quantity = MyConverter.requestIntegerValue("Please enter the quantity of the resource:",
                    "Invalid resource quantity, please contain number value only!!", false);
            if (quantity > resource.getQuantity()) {
                System.out.println("The quantity you entered cannot"
                        + " be larger than the quantity in the resource!!");
            }

        } while (quantity > resource.getQuantity());
        ResourceAssign resourceAsgn = new ResourceAssign(0, resource, victim,
                new GregorianCalendar(), quantity);

        System.out.println("--------------------------------------------");
        System.out.println("Resource information");
        System.out.println("----------------");
        System.out.println("Resource name\t:" + resourceAsgn.getResource().getName());
        System.out.println("Resource type\t:" + resourceAsgn.getResource().getType());
        System.out.println("Quantity assigned\t\t:" + resourceAsgn.getQuantity());
        System.out.println("After quantity assigned left\t:"
                + (resourceAsgn.getResource().getQuantity() - quantity));
        System.out.println("------------------");
        System.out.println("Victim information");
        System.out.println("------------------");
        System.out.println("Victim ID\t:" + resourceAsgn.getVictim().getId());
        System.out.println("Victim name\t:" + resourceAsgn.getVictim().getName());
        System.out.println("Victim age\t:" + resourceAsgn.getVictim().getAge());
        System.out.println("Gender\t\t:" + resourceAsgn.getVictim().getGender());
        boolean validate = MyConverter.validateOption("Are you sure you want to assign this resource(y/n)?");
        if (validate) {

            resource.setQuantity(resource.getQuantity() - quantity);
            int resourceRow = resDa.updateRecord(resource);
            if (resourceRow > 0) {
                System.out.println("Resource information successfully updated");
            } else {
                System.out.println("Resource information fail to update!!");
            }

            int resAsgnRow = db.insertResourceAssign(resourceAsgn);

            if (resAsgnRow > 0) {
                System.out.println("Resource Assign information successfully updated in database!!");
            } else {
                System.out.println("Resource Assign information failed to udpate in the database!!");
            }
        } else {
            System.out.println(resource.getName() + " assigned to " + victim.getName() + " is cancelled!! ");
        }

    }

    public void findResourceAssignByAge() {
        System.out.println("----------------------------------------------");
        System.out.println("|\t Print Resource Assignment \t|");
        System.out.println("----------------------------------------------");
        System.out.println("Please enter the range of age of the victim:");
        int startAge = 0;
        int endAge = 0;
        do {
            startAge = MyConverter.requestIntegerValue("Please enter the start age:",
                    "Please enter the valid age value!!", false);
            endAge = MyConverter.requestIntegerValue("Please enter the end age duration:",
                    "Please enter the valid age value!!", false);
            if (startAge >= endAge) {
                System.out.println("Your start age cannot larger than end age!!");
            }
        } while (startAge >= endAge);
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.set(Calendar.YEAR, gc1.get(Calendar.YEAR) - startAge);
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.set(Calendar.YEAR, gc2.get(Calendar.YEAR) - endAge);

        System.out.println("------------------------------------------------------");
        System.out.println("Display the victim from age " + startAge + " to " + endAge + ".");
        System.out.println("------------------------------------------------------");

        ListInterface<Victim> list = db.selectVictimDobRange(gc1, gc2);
        for (Victim temp : list) {
            printResourceAssign(temp);
        }

    }

    public void findResourceAssignByVictimId() {
        System.out.println("-----------------------------");
        System.out.println("|\t Print Resource Assignment \t|");
        System.out.println("-----------------------------");

        int victimId = MyConverter.requestIntegerValue("Please enter the victim id:", "Invalid input format of victim, please try again!!", false);
        
        Victim result = vicDa.selectOneRecord(victimId);
        if(result == null){
            System.out.println("No victim result in the database, please try again");
        }else{
            if(db.checkVictimInResourceAssign(victimId)){
                printResourceAssign(result);
            }else{
                System.out.println("Current victim has zero assignment!!");
            }
        }
        
    }

    private void printResourceAssign(Victim victim) {
        System.out.println("<<---------------------->> ");
        System.out.println("Victim ID:" + victim.getId());
        System.out.println("Name     :" + victim.getName());
        System.out.println("Age      :" + victim.getAge());
        ListInterface<ResourceAssign> asgnList
                = db.selectResourceAssignWithVictimId(victim.getId());
        System.out.println("Resource Assigned List");
        System.out.println("----------------------");
        System.out.println("\t-----------------------------------------------------------------------");
         String title = String.format("\t| %-15s | %-13s | %-7s | %-23s|",
                    "Name","Resource Type","Quantity","Time Assigned");
        System.out.println(title);
        System.out.println("\t-----------------------------------------------------------------------");
        for (ResourceAssign temp1 : asgnList) {
            String msg = String.format("\t| %-15s | %-13s | %-7d | %-23s |",
                    temp1.getResource().getName(),
                    temp1.getResource().getType(), temp1.getQuantity(),
                    MyConverter.getTime(temp1.getAssigntime()));
            System.out.println(msg);
        }
        System.out.println("\t-----------------------------------------------------------------------");

    }

    public static void main(String[] args) {
        ResourceAssignManager rc = new ResourceAssignManager();
        //rc.addResourceAssign();
       // rc.findResourceAssignAge();
        
    }
}
