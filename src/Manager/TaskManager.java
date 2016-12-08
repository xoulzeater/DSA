/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.ListInterface;
import da.ConnectDbStaff;
import domain.Task;
import java.util.GregorianCalendar;
import java.util.Scanner;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class TaskManager {

    private final Scanner sc;
    private final ConnectDbStaff db;

    public TaskManager() {
        db = new ConnectDbStaff();
        sc = new Scanner(System.in);
    }

    public void addTask() {
        System.out.println("---------------------------");
        System.out.println("| \t Add Task \t |");
        System.out.println("---------------------------");
        System.out.print("Please enter your new task name:");
        String name = sc.nextLine();
        System.out.print("Please enter your task description:");
        String desc = sc.nextLine();
        int manPower = MyConverter.requestIntegerValue(
                "Please enter your task man power:",
                "Please enter numeric value only!!",false);
        GregorianCalendar start;
        GregorianCalendar end;
        boolean checkDate = false;
        do {
            start = MyConverter.readStringCalendar(
                    "Please enter the start date:",
                    "Please enter the correct format of start date!!",false);
            end = MyConverter.readStringCalendar(
                    "Please enter the end date:",
                    "Please enter the correct format of end date!!",false);
            if (MyConverter.getDays(start, end) < 0) {
                System.out.println("Start date cannot larger than end date!!");
            } else {
                checkDate = true;
            }
        } while (!checkDate);

        Task newTask = new Task(0, name, desc, manPower, start, end);
        System.out.println("------------------------------------");
        printTask(newTask);
        System.out.println("------------------------------------");
        boolean validOption = MyConverter.validateOption(
                "Are you sure want to insert the information to database(Y/N)?");
        if (validOption) {
            int results = db.insertTask(newTask);
            if (results > 0) {
                int lastId = db.getLastId(ConnectDbStaff.TASK);
                System.out.println("Task successfully insert to database!! Your task id is :" + lastId);
            } else {
                System.out.println("Task failed to insert to database!!");
            }
        } else {
            System.out.println("Task insertion is cancelled!!");
        }
    }

    public void updateTask() {
        System.out.println("----------------------------");
        System.out.println("| \t Update task \t|");
        System.out.println("----------------------------");
        int taskId = MyConverter.requestIntegerValue(
                "Please enter the task id:",
                "Invalid task id, please try again!!",false);

        Task result = db.selectTask(taskId);
        if (result == null) {
            System.out.println("No search result, please try again!");
        } else {
            System.out.println("-------------------------------------");
            printTask(result);
            System.out.println("-------------------------------------");
            System.out.print("Please enter the new task name:");
            String name = sc.nextLine();
            System.out.print("Please enter the new description:");
            String desc = sc.nextLine();
            int manPower = MyConverter.requestIntegerValue(
                    "Please enter the new Man power:",
                    "Invalid input, please enter only numeric value!!",true);
            if(manPower == -1){
                manPower = result.getManPower();
            }
            GregorianCalendar start;
            GregorianCalendar end;
            boolean checkDate = false;
            do {
                start = MyConverter.readStringCalendar(
                        "Please enter the start date:",
                        "Please enter the correct format of start date!!",true);
                end = MyConverter.readStringCalendar(
                        "Please enter the end date:",
                        "Please enter the correct format of end date!!",true);
                if(start == null){
                    start = result.getStartDate();
                }
                if(end == null){
                    end = result.getEndDate();
                }
                if (MyConverter.getDays(start, end) < 0) {
                    System.out.println("Start date cannot larger than end date!!");
                } else {
                    checkDate = true;
                }
            } while (!checkDate);
            Task task = new Task(result.getId(), name, desc, manPower, start, end);
            printTask(task);
            boolean validate = MyConverter.validateOption("Are you sure want to update these information to database(y/n)?");
            if (validate) {
                int results = db.updateTask(task);
                if (results > 0) {
                    System.out.println("Task information successfully updated in the database!!");
                } else {
                    System.out.println("Task information failed to update in the database!!");
                }
            } else {
                System.out.println("Action update task is cancelled!!");
            }
        }
    }

    public void deleteTask() {
        System.out.println("----------------------------");
        System.out.println("| \t Delete Task \t |");
        System.out.println("----------------------------");

        int taskId = MyConverter.requestIntegerValue(
                "Please enter the task id:",
                "Invalid task id, please try again!!",false);

        Task result = db.selectTask(taskId);
        if (result == null) {
            System.out.println("No task result search in the database!!");
        } else {
            printTask(result);
            boolean validate = MyConverter.validateOption(
                    "Are you sure you want to delete the information in the database(Y/N)?");
            if (validate) {
                int results = db.deleteInformation(
                        result.getId(), ConnectDbStaff.TASK);
                if (results > 0) {
                    System.out.println("Task has been successfully deleted!!");
                } else {
                    System.out.println("Task failed to delete!!");
                }
            } else {
                System.out.println("Action delete task is cancelled!");
            }
        }
    }

    public void viewAllTask() {
        System.out.println("-----------------------------");
        System.out.println("| \t View All Task \t |");
        System.out.println("-----------------------------");
        ListInterface<Task> taskList = db.selectAllTask();
        for (Task temp : taskList) {
            String msg = String.format("|%-5d | %-20s | %-5d | %-10s | %-10s |", temp.getId(),
                    temp.getName(), temp.getManPower(),
                    MyConverter.displayDate(temp.getStartDate()),
                    MyConverter.displayDate(temp.getEndDate()));
            System.out.println(msg);
        }
        System.out.print("Press enter to continue...");
        sc.nextLine();

    }

    private void printTask(Task task) {
        System.out.println("----------------------------------");
        System.out.println("Task Id:" + task.getId());
        System.out.println("Task Name:" + task.getName());
        System.out.println("Task Description:" + task.getDescription());
        System.out.println("Task Man Power remaining:" + task.getManPower());
        System.out.println("Task issue time:"
                + MyConverter.displayDate(task.getStartDate()));
        System.out.println("Task end time:"
                + MyConverter.displayDate(task.getEndDate()));
        System.out.println("----------------------------------");

    }

}
