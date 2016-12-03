/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.ListInterface;
import da.ConnectDb;
import domain.Resource;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class FoodManager {

    private Scanner sc;

    public FoodManager() {
        sc = new Scanner(System.in);

    }

    public void addFood() {
        System.out.println("============================");
        System.out.println("\t Add Resource \t");
        System.out.println("============================");

        ConnectDb db = new ConnectDb();
        ListInterface<Resource> listResource = db.selectAllResource();
        String name = "";
        do {
            System.out.print("Please enter your resource name:");
            name = sc.nextLine();
            if (listResource.contains(new Resource(name))) {
                System.out.println("Resource name cannot be duplicate in the list.");
            }
        } while (listResource.contains(new Resource(name)));

        String type = "";
        System.out.print("Please enter the resource type:");
        type = sc.nextLine();
        int amount = MyConverter.requestIntegerValue("Please enter the quantity:", "Please enter the valid quantity!");

        Resource resource = new Resource(0, name, type, amount, "Available");
        int result = db.insertResource(resource);
        if (result > 0) {
            System.out.println("Resource has been added successfully");
        } else {
            System.out.println("Resource failed to add in database.");
        }

    }

    public void viewAllFood() {
        ConnectDb db = new ConnectDb();

        ListInterface<Resource> resourceList = db.selectAllResource();
        System.out.println("=============================");
        System.out.println("\t View All Resource \t");
        System.out.println("=============================");
        System.out.println("========================================================");
        System.out.println("|\t ID \t|\t Food Name \t |\t Food category \t| Quantity |\t Status\t|");
        System.out.println("========================================================");
        for (int index = 0; index < resourceList.getNumberOfEntries(); index++) {
            Resource temp = resourceList.getEntry(index);
            String msg = String.format("| %-10d \t | %-15s\t| %-10s | %-5d | %-10s| ",
                    temp.getResId(), temp.getName(),
                    temp.getType(), temp.getQuantity(), temp.getStatus());
            System.out.println(msg);
        }
        System.out.print("Press enter to continue...");
        sc.nextLine();

    }

    public void deleteFood() {
        ConnectDb db = new ConnectDb();
        System.out.println("====================================");
        System.out.println("| \t !!! DELETE FOOD !!! \t|");
        System.out.println("====================================");

        int resCode = MyConverter.requestIntegerValue(
                "Please enter the resource code:",
                "Please enter the valid numeric id!");

        Resource result = db.selectResource(resCode);

        if (result == null) {
            System.out.println("No such resource found in the list.");
        } else {

            System.out.println("---------------------------------------");
            printResource(result);
            System.out.println("----------------------------------------");
            for (;;) {
                System.out.print("Are you sure you want to delete the food information?(Y/N)");
                switch (sc.nextLine().toUpperCase()) {
                    case "Y":

                        /*remember uncommnent */
                        //int results = db.deleteResource(result.getResId());
                        int results = 0;
                        if (results > 0) {
                            System.out.println("Food deleted successfully!");
                        } else {
                            System.out.println("Food failed to delete!!");
                        }
                        return;
                    case "N":
                        System.out.println("Action delete food is cancelled!");
                        return;
                    default:
                        System.out.println("Please enter the valid input!");
                        break;
                }
            }
        }
    }

    public void updateFood() {
        ConnectDb db = new ConnectDb();
        System.out.println("==========================");
        System.out.println("| \t Update Food \t | ");
        System.out.println("==========================");

        int resourceCode = MyConverter.requestIntegerValue(
                "Please enter the resource code:",
                "Please enter the valid numeric id!");

        Resource result = db.selectResource(resourceCode);

        if (result == null) {
            System.out.println("No such resource found in the list.");
        } else {

            System.out.println("---------------------------------------");
            printResource(result);
            System.out.println("----------------------------------------");
            ListInterface<Resource> listResource = db.selectAllResource();
            System.out.println("<< BLANK FOR REMAIN SAME >>");
            String name = "";
            do {
                System.out.print("Please enter your new resource name:");
                name = sc.nextLine();
                if (listResource.contains(new Resource(name))) {
                    System.out.println("Resource name cannot be duplicate in the ldatabase.");
                }
            } while (listResource.contains(new Resource(name)) && name == "");

            String type = "";
            System.out.print("Please enter the resource type:");
            type = sc.nextLine();

            String quantity = "";
            do {
                System.out.print("Please enter the quantity:");
                quantity = sc.nextLine();
                if (!quantity.matches("^[0-9]+$")) {
                    System.out.println("Please enter the valid quantity!");
                }
            } while (!quantity.matches("^[0-9]+$"));
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

            if ("".equals(name)) {
                name = result.getName();
            }
            if ("".equals(type)) {
                type = result.getType();
            }
            int amount = 0;
            if ("".equals(quantity)) {
                amount = result.getQuantity();
            } else {
                amount = Integer.parseInt(quantity);
            }
            Resource newResource = new Resource(result.getResId(), name, type, amount, status);
            int results = db.updateResource(newResource);
            if (results > 0) {
                System.out.println("Resource information update successfully!!");
            } else {
                System.out.println("Resource information update failed!!");
            }

        }

    }

    public void printResource(Resource resource) {
        System.out.println("Resource ID::" + resource.getResId());
        System.out.println("Resource Name:" + resource.getQuantity());
        System.out.println("Resource type:" + resource.getType());
        System.out.println("Resource Quantity::" + resource.getQuantity());
        System.out.println("Resource Status:" + resource.getStatus());
    }

}
