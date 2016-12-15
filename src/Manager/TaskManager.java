/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import adt.ListInterface;
import adt.SortedListInterface;
import da.ConnectDbStaff;
import domain.Staff;
import domain.Task;
import domain.TaskAssign;
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
    private final StaffManager sm;

    public TaskManager() {
        db = new ConnectDbStaff();
        sc = new Scanner(System.in);
        sm = new StaffManager();
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
                "Please enter numeric value only!!", false);

        System.out.print("Please enter your task priority:");
        String prio = sc.nextLine();

        Task newTask = new Task(0, name, desc, manPower, prio, "Incomplete");
        System.out.println("------------------------------------");
        printTask(newTask);
        System.out.println("------------------------------------");
        boolean validOption = MyConverter.validateOption(
                "Are you sure want to insert the information to database(Y/N)?");
        if (validOption) {
            int results = db.insertTask(newTask);
            if (results > 0) {
                int lastId = db.getLastId(ConnectDbStaff.TASK, "TASK_ID");
                System.out.println("Task successfully insert to database!! Your task id is :" + lastId);
            } else {
                System.out.println("Task failed to insert to database!!");
            }
        } else {
            System.out.println("Task insertion is cancelled!!");
        }
    }

    public void assignTask() {
        System.out.println("---------------------------");
        System.out.println("| \t Assign Task \t |");
        System.out.println("---------------------------");
        SortedListInterface<Task> sortedTaskList = db.getAllSortedTask();
        for (Task temp : sortedTaskList) {
            System.out.println(String.format("|%-5d | %-20s | %-30s | %-5d | %-10s | %-10s |", temp.getId(),
                    temp.getName(), temp.getDescription(), temp.getManPower(), temp.getPriority(), temp.getStatus()));
        }
        System.out.println("---------------------------------------------------------");
        int taskId = 0;
        Task task = null;
        do {
            taskId = MyConverter.requestIntegerValue(
                    "Please enter the task id to be assign:",
                    "Please enter the valid number format!!", false);
            task = db.selectTask(taskId);
            if (task == null) {
                System.out.println("Please enter the task id given or valid one!!");
            }
        } while (task == null);
        ListInterface<Staff> staffList = db.getAllAvailableStaff();
        if (task.getManPower() > staffList.getNumberOfEntries()) {
            System.out.println("The number of staff is not sufficient to be assign to this task");
            System.out.println("The number of man power needed for this task is : " + task.getManPower());
            System.out.println("The number of staff available is : " + staffList.getNumberOfEntries());
            boolean validOption = MyConverter.validateOption(
                    "Do you want to add more staff to continue?");
            if (validOption) {
                for (int index = 0; index < (task.getManPower() - staffList.getNumberOfEntries()); index++) {
                    sm.addStaff();
                }
            } else {
                System.out.println("Task assigning is failed!!");
            }
        } else {
            staffList = db.getAllAvailableStaff();
            System.out.println("The number of staff is sufficient");
            boolean validOption = MyConverter.validateOption(
                    "Are you sure want to insert the information to database(Y/N)?");
            if (validOption) {
                for (int index = 0; index < task.getManPower(); index++) {
                    staffList.getEntry(index).setStatus("Unavailable");
                    db.updateStaff(staffList.getEntry(index));
                    TaskAssign taskAssign = new TaskAssign(task.getId(), staffList.getEntry(index).getId(), new GregorianCalendar());
                    db.insertAssignTask(taskAssign);
                }
                task.setStatus("Completing");
                db.updateTask(task);
                System.out.println("Task successfully assigned!!");
                System.out.print("Press enter to continue...");
                sc.nextLine();
            } else {
                System.out.println("Task assigning is cancelled!!");
            }
        }
    }

    public void updateTask() {
        System.out.println("----------------------------");
        System.out.println("| \t Update task \t|");
        System.out.println("----------------------------");
        int taskId = MyConverter.requestIntegerValue(
                "Please enter the task id:",
                "Invalid task id, please try again!!", false);

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
                    "Invalid input, please enter only numeric value!!", true);
            if (manPower == -1) {
                manPower = result.getManPower();
            }
            System.out.print("Please enter the new task priority:");
            String prio = sc.nextLine();

            Task task = new Task(result.getId(), name, desc, manPower, prio, result.getStatus());
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

    public void updateTaskAssign() {
        System.out.println("----------------------------");
        System.out.println("| \t Update task assign \t|");
        System.out.println("----------------------------");
        ListInterface<Task> taskList = db.selectAllCompletingTask();
        if (taskList.isEmpty()) {
            System.out.println("No task is in completing state");
            System.out.print("Press enter to continue...");
            sc.nextLine();
        } else {
            for (Task temp : taskList) {
                String msg = String.format("|%-5d | %-20s | %-20s | %-5d | %-10s |", temp.getId(),
                        temp.getName(), temp.getDescription(), temp.getManPower(), temp.getPriority(), temp.getStatus());
                System.out.println(msg);
            }
            int taskId = 0;
            Task task = null;
            do {
                taskId = MyConverter.requestIntegerValue(
                        "Please enter the task id to be completed:",
                        "Please enter the valid number format!!", false);
                task = db.selectTask(taskId);
                if (task == null) {
                    System.out.println("Please enter the task id given or valid one!!");
                }
            } while (task == null);
            printTask(task);
            boolean validate = MyConverter.validateOption("Are you sure want to complete this task(y/n)?");
            if (validate) {
                task.setStatus("Completed");
                ListInterface<TaskAssign> taskAssignList = db.selectTaskAssign(taskId);
                for(int index = 0; index < taskAssignList.getNumberOfEntries(); index++) {
                    int tempID = taskAssignList.getEntry(index).getStaff_id();
                    db.updateStaffStatus(tempID);
                }
                int results = db.updateTask(task);
                if (results > 0) {
                    System.out.println("Task assign information successfully updated in the database!!");
                } else {
                    System.out.println("Task assign information failed to update in the database!!");
                }
            } else {
                System.out.println("Action update task assign is cancelled!!");
            }
        }
    }

    public void deleteTask() {
        System.out.println("----------------------------");
        System.out.println("| \t Delete Task \t |");
        System.out.println("----------------------------");

        int taskId = MyConverter.requestIntegerValue(
                "Please enter the task id:",
                "Invalid task id, please try again!!", false);

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
            String msg = String.format("|%-5d | %-20s | %-20s | %-5d | %-10s | %-10s |", temp.getId(),
                    temp.getName(), temp.getDescription(), temp.getManPower(), temp.getPriority(), temp.getStatus());
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
        System.out.println("Task Priority:" + task.getPriority());
        System.out.println("Task Status:" + task.getStatus());
        System.out.println("----------------------------------");

    }

}
