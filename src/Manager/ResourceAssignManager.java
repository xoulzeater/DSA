/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.ListInterface;
import adt.SortedListInterface;
import da.ConnectDb;
import domain.Resource;
import domain.ResourceAssign;
import domain.Victim;
import java.util.GregorianCalendar;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class ResourceAssignManager {

    private Scanner sc;
    private VictimManager vicMgr;
    private ConnectDb db;

    public ResourceAssignManager() {
        sc = new Scanner(System.in);
        vicMgr = new VictimManager();
        db = new ConnectDb();
    }

    public void addResourceAssign() {
        System.out.println("----------------------------------");
        System.out.println("|\t Resource Assign \t|");
        System.out.println("----------------------------------");
        System.out.println("List of victim");
        System.out.println("----------------------");
        System.out.println("ID \t Name \t\t\t Age \t Food Assigned \t Supply Assigned \t ");
        SortedListInterface<Victim> list = db.getAllSortedVictim();
        for (Victim temp : list) {
            System.out.println(temp.getId() + "\t "
                    + String.format("%-20s", temp.getName())
                    + "\t" + temp.getAge() + "\t\t" + db.getVictimFoodCount(temp.getId(), "food") + "\t\t"
                    + db.getVictimFoodCount(temp.getId(), "supply"));
        }

        System.out.println("---------------------------------------------------------");
        System.out.println("List of supply and food");
        System.out.println("----------------------");
        ListInterface<Resource> resourceList = db.selectAllResource();
        System.out.println("ID \t Name \t\t\t Resource type \t Quantity \t  ");
        for (Resource temp : resourceList) {
            System.out.println(temp.getResId() + "\t" + String.format("%-20s", temp.getName())
                    + "\t\t" + temp.getType() + " \t " + temp.getQuantity());
        }
        System.out.println("---------------------------------------------------------");
        int victimId = 0;
        Victim victim = null;
        do {
            victimId = MyConverter.requestIntegerValue(
                    "Please enter your victim id:",
                    "Please enter the valid number format!!", false);
            victim = db.selectVictim(victimId);
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
            resource = db.selectResource(resourceId);
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
        ResourceAssign resourceAsgn = new ResourceAssign(resource, victim,
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
            int resourceRow = db.updateResource(resource);
            if (resourceRow > 1) {
                System.out.println("Resource information successfully updated");
            } else {
                System.out.println("Resource information fail to update!!");
            }
            int resAsgnRow = db.insertResourceAssign(resourceAsgn);
            if (resAsgnRow > 1) {
                System.out.println("Resource Assign information successfully updated in database!!");
            } else {
                System.out.println("Resource Assign information failed to udpate in the database!!");
            }
        } else {
            System.out.println(resource.getName() + " assigned to " + victim.getName() + " is cancelled!! ");
        }

    }

    public void printResourceAssign() {

    }

    public static void main(String[] args) {
        ResourceAssignManager rc = new ResourceAssignManager();
        rc.addResourceAssign();

    }
}
