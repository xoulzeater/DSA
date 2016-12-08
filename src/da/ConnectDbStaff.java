/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import adt.ListInterface;
import domain.Staff;
import domain.Task;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import utility.DoublyList;
import utility.MyConverter;

/**
 *
 * @author User
 */
public class ConnectDbStaff {

    public final String NAME = "nbuser";
    public final String PASSWORD = "nbuser";
    public final String LOCATIONS = "jdbc:derby://localhost:1527/disaster";
    public final String STAFF = "STAFF";
    public static final String TASK = "TASK";

    public PreparedStatement stmt = null;
    private Statement st = null;
    private Connection con = null;
    private ResultSet rs = null;

    /*SELECT * FROM resource ORDER BY res_id DESC FETCH FIRST ROW ONLY
     */
    public void runConnection() {
        try {
            con = DriverManager.getConnection(LOCATIONS, NAME, PASSWORD);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ListInterface<Staff> selectAllStaff() {
        runConnection();
        String query = "Select * FROM " + STAFF;
        Statement statement;
        ListInterface<Staff> staffList = new DoublyList<>();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                Staff staff = new Staff(rs.getInt(1),
                        rs.getString(2),
                        MyConverter.convertDateToGregorian(rs.getDate(3)), rs.getString(4), rs.getString(5));
                staffList.add(staff);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return staffList;
    }

    public int insertStaff(Staff staff) {
        runConnection();
        String query = "INSERT INTO " + STAFF
                + " (NAME,DOB,STATUS,GENDER) VALUES (?,?,?,?)";
        int result = 0;
        try {

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, staff.getName());
            java.sql.Date tempDate = new java.sql.Date(staff.getDob().getTimeInMillis());
            stmt.setDate(2, tempDate);
            stmt.setString(3, staff.getStatus());
            stmt.setString(4, staff.getGender());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public Staff selectStaff(int id) {
        runConnection();
        String queryStr = "SELECT * FROM " + STAFF + " WHERE STAFF_ID = ?";
        Staff staff = null;
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                
                staff = new Staff(rs.getInt(1), rs.getString(2),
                        MyConverter.convertDateToGregorian(rs.getDate(3)),
                        rs.getString(4),
                        rs.getString(5));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return staff;
    }

    public int deleteStaff(int id) {
        runConnection();
        String deleteQuery = "DELETE FROM " + STAFF + " WHERE STAFF_ID = ?";
        int result = 0;
        try {
            stmt = con.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public int updateStaff(Staff staff) {
        runConnection();
        int result = 0;
        String queryStr = "UPDATE " + STAFF + " SET NAME = ?, DOB = ?,"
                + " STATUS = ?, GENDER = ? WHERE STAFF_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setString(1, staff.getName());
            java.sql.Date date = new java.sql.Date(
                    staff.getDob().getTimeInMillis());
            stmt.setDate(2, date);
            stmt.setString(3, staff.getStatus());
            stmt.setString(4, staff.getGender());
            stmt.setInt(5, staff.getId());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public ListInterface<Task> selectAllTask() {
        runConnection();
        String query = "Select * FROM " + TASK;
        Statement statement;
        ListInterface<Task> taskList = new DoublyList<>();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                Task task = new Task(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), MyConverter.convertDateToGregorian(rs.getTimestamp(5)),
                        MyConverter.convertDateToGregorian(rs.getTimestamp(6)));
                taskList.add(task);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return taskList;
    }

    public int getLastStaffId() {
        runConnection();
        String query = "SELECT * FROM resource ORDER BY res_id DESC FETCH FIRST ROW ONLY";
        int result = -1;
        try {
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public int insertTask(Task task) {
        runConnection();
        String query = "INSERT INTO " + TASK
                + " (NAME,DESCRIPTION,MANPOWER,START_TIME,END_TIME)"
                + " VALUES (?,?,?,?,?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getManPower());
            java.sql.Timestamp startDate
                    = new java.sql.Timestamp(task.getStartDate().getTimeInMillis());
            stmt.setTimestamp(4, startDate);
            java.sql.Timestamp endDate = 
                    new java.sql.Timestamp(task.getEndDate().getTimeInMillis());
            stmt.setTimestamp(5, endDate);
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public int getLastId(String table,String pk) {
        runConnection();
        String query = "SELECT * FROM " + table + " ORDER BY "+ pk +" DESC FETCH FIRST ROW ONLY";
        int result = -1;
        try {
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public Task selectTask(int id) {
        runConnection();
        String queryStr = "SELECT * FROM " + TASK + " WHERE TASK_ID = ?";
        Task task = null;
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                task = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
                        MyConverter.convertDateToGregorian(rs.getTimestamp(4)),
                        MyConverter.convertDateToGregorian(rs.getTimestamp(5)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return task;
    }
    
    public int deleteInformation(int id,String table){
         runConnection();
        String deleteQuery = "DELETE FROM " + table + " WHERE TASK_ID = ?";
        int result = 0;
        try {
            stmt = con.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }
    
   public int updateTask(Task task){
        runConnection();
        int result = 0;
        String queryStr = "UPDATE " + TASK + " SET NAME = ?, DESCRIPTION = ?,"
                + " MANPOWER = ?, START_TIME = ?, END_TIME = ? WHERE STAFF_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setString(1,task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getManPower());
            java.sql.Timestamp date = new java.sql.Timestamp(
                    task.getStartDate().getTimeInMillis());
            stmt.setTimestamp(4, date);
            java.sql.Timestamp date2 = new java.sql.Timestamp(
                    task.getEndDate().getTimeInMillis());
            stmt.setTimestamp(5, date2);
            stmt.setInt(6, task.getId());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
   }

    private void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
            }
        }
    }
}
