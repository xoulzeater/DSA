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
import da.ResourceDataAccess;
import domain.Resource;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class FoodManager implements ManagerInterface{

    private final Scanner sc;
    private final DataAccessInterface<Resource> da;

    public FoodManager() {
        sc = new Scanner(System.in);
        da = new ResourceDataAccess();

    }

    @Override
    public void displayInsertRecord() {
        System.out.println("============================");
        System.out.println("\t Add Resource \t");
        System.out.println("============================");
        ListInterface<Resource> listResource = da.selectAllRecord();
        String name = "";
        do {
            System.out.print("Please enter your resource name:");
            name = sc.nextLine();
            if (listResource.contains(new Resource(name))) {
                System.out.println("Resource name cannot be duplicate in the list.");
            }
        } while (listResource.contains(new Resource(name)));

        String type = requestResourceType(false);

        int amount = MyConverter.requestIntegerValue("Please enter the quantity:",
                "Please enter the valid quantity!", false);

        Resource resource = new Resource(-1, name, type, amount, "Available");
        boolean confirm = MyConverter.validateOption(
                "Are you confirm to insert the following data in the database(y/n)?");
        if (confirm) {
            int result = da.addOneRecord(resource);
            if (result > 0) {
                System.out.println("Resource has been added successfully");
            } else {
                System.out.println("Resource failed to add in database.");
            }
        } else {
            System.out.println("Insert record is cancelled!!");
        }

    }

    @Override
    public void displayAllRecord() {

        ListInterface<Resource> resourceList = da.selectAllRecord();
        System.out.println("=============================");
        System.out.println("\t View All Resource \t");
        System.out.println("=============================");
        System.out.println("=================================================================================");
        System.out.println("|\t ID \t|\t Food Name \t |Food category| Quantity |\t Status\t|");
        System.out.println("=================================================================================");
        for (int index = 0; index < resourceList.getNumberOfEntries(); index++) {
            Resource temp = resourceList.getEntry(index);
            String msg = String.format("| %-10d \t | %-15s\t| %-12s | %-8d | %-12s| ",
                    temp.getResId(), temp.getName(),
                    temp.getType(), temp.getQuantity(), temp.getStatus());
            System.out.println(msg);
        }
        System.out.println("=================================================================================");
        System.out.print("Press enter to continue...");
        sc.nextLine();

    }


    @Override
    public void displayDeleteRecord() {
        System.out.println("=========================================");
        System.out.println("| \t !!! DELETE Resource !!! \t|");
        System.out.println("=========================================");

        int resCode = MyConverter.requestIntegerValue(
                "Please enter the resource code:",
                "Please enter the valid numeric id!", false);

        Resource result = da.selectOneRecord(resCode);

        if (result == null) {
            System.out.println("No such resource found in the list.");
        } else {

            System.out.println("---------------------------------------");
            printResource(result);
            System.out.println("----------------------------------------");
            boolean option = MyConverter.validateOption("Are you sure you want to delete the food information?(Y/N):");
            if (option) {
                //int results = db.deleteResource(result.getResId());
                int results = 0;
                if (results > 0) {
                    System.out.println("Resource deleted successfully!");
                } else {
                    System.out.println("Resource failed to delete!!");
                }
            } else {
                System.out.println("Resource is cancelled to be deleted!!!");
            }
        }
    }

    @Override
    public void displayUpdateRecord() {
        System.out.println("==================================");
        System.out.println("| \t Update Resource \t | ");
        System.out.println("==================================");

        int resourceCode = MyConverter.requestIntegerValue(
                "Please enter the resource code:",
                "Please enter the valid numeric id!", false);

        Resource result = da.selectOneRecord(resourceCode);

        if (result == null) {
            System.out.println("No such resource found in the list.");
        } else {
            System.out.println("---------------------------------------");
            printResource(result);
            System.out.println("----------------------------------------");
            ListInterface<Resource> listResource = da.selectAllRecord();
            System.out.println("<< BLANK FOR REMAIN SAME >>");
            String name = "";
            do {
                System.out.print("Please enter your new resource name:");
                name = sc.nextLine();
                if (listResource.contains(new Resource(name))) {
                    System.out.println("Resource name cannot be duplicate in the ldatabase.");
                }

            } while (listResource.contains(new Resource(name)) && name == "");
            if ("".equals(name)) {
                name = result.getName();
            }
            String type = requestResourceType(true);
            if (type.isEmpty()) {
                type = result.getType();
            }
            int amount = MyConverter.requestIntegerValue(
                    "Please enter the new quantity:",
                    "Invalid quantity input, only can contain number!!", true);
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
                        break;
                    default:
                        System.out.println("Please enter a valid input.");
                }
            } while (status.equals("A") && status.equals("NA"));

            boolean option = MyConverter.validateOption("Are you sure you want to update these information in the database(y/n)?:");
            if (option) {
                Resource newResource = new Resource(result.getResId(), name, type, amount, status);
                int results = da.updateRecord(newResource);
                if (results > 0) {
                    System.out.println("Resource information update successfully!!");
                } else {
                    System.out.println("Resource information update failed!!");
                }
            } else {
                System.out.println("Resource information update is cancelled!!");
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

    private String requestResourceType(boolean allowEmpty) {
        String type = "";
        do {
            int resourceChoice = MyConverter.requestIntegerValue(
                    "Please enter the resource type(1 for food, 2 for supply):",
                    "Invalid input, please enter number only!!", allowEmpty);
            switch (resourceChoice) {
                case 1:
                    type = "food";
                    break;
                case 2:
                    type = "supply";
                    break;
                case -1:
                    return type;
                default:
                    System.out.println("Please enter the valid choice!!");
            }
        } while (!type.equals("food") && !type.equals("supply"));
        return type;
    }

}
