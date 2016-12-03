/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import adt.ListInterface;
import domain.Resource;
import domain.Victim;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import utility.DoublyList;

/**
 *
 * @author User
 */
public final class ConnectDb {

    public final String NAME = "nbuser";
    public final String PASSWORD = "nbuser";
    public final String LOCATIONS = "jdbc:derby://localhost:1527/disaster";
    public final String RESOURCE = "RESOURCE";
    public final String VICTIM = "VICTIM";

    public PreparedStatement stmt = null;
    private Statement st = null;
    private Connection con = null;

    public ConnectDb() {
        runConnection();
    }

    public void runConnection() {
        try {
            con = DriverManager.getConnection(LOCATIONS, NAME, PASSWORD);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ListInterface<Resource> selectAllResource() {
        runConnection();
        String query = "Select * FROM " + RESOURCE;
        Statement statement;
        ListInterface<Resource> resourceList = new DoublyList<>();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Resource resource = new Resource(rs.getInt("RES_ID"),
                        rs.getString("NAME"), rs.getString("TYPE"), rs.getInt("QUANTITY"), rs.getString("STATUS"));
                resourceList.add(resource);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resourceList;
    }

    public int insertResource(Resource resource) {
        runConnection();
        String query = "INSERT INTO " + RESOURCE
                + " (NAME,TYPE,QUANTITY,STATUS) VALUES (?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, resource.getName());
            stmt.setString(2, resource.getType());
            stmt.setInt(3, resource.getQuantity());
            stmt.setString(4, resource.getStatus());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;

    }

    public int deleteResource(int id) {
        runConnection();
        String deleteQuery = "DELETE FROM " + RESOURCE + " WHERE RES_ID = ?";
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

    public Resource selectResource(int code) {
        runConnection();
        String queryStr = "SELECT * FROM " + RESOURCE + " WHERE RES_ID = ?";
        Resource resource = null;
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resource = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getString(5));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return resource;
    }

    public int updateResource(Resource resource) {
        runConnection();
        int result = 0;
        String queryStr = "UPDATE " + RESOURCE + " SET NAME = ?, TYPE = ?,"
                + " QUANTITY=? ,STATUS = ? WHERE RES_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setString(1, resource.getName());
            stmt.setString(2, resource.getType());
            stmt.setInt(3, resource.getQuantity());
            stmt.setString(4, resource.getStatus());
            stmt.setInt(5, resource.getResId());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public ListInterface<Victim> selectAllVictim() {
        runConnection();
        String query = "Select * FROM " + VICTIM;
        Statement statement;
        ListInterface<Victim> victimList = new DoublyList<>();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                java.sql.Date tempDate = rs.getDate(3);
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(tempDate.getTime());
                Victim victim = new Victim(rs.getInt("VICTIM_ID"), rs.getString(2), gc, rs.getString(4), rs.getString(5), rs.getString(6));
                victimList.add(victim);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return victimList;
    }

    public int insertVictim(Victim victim) {
        runConnection();
        String query = "INSERT INTO " + VICTIM
                + " (NAME,DOB,ADDRESS,STATUS,GENDER) VALUES (?,?,?,?,?)";
        int result = 0;
        try {
            java.sql.Date tempDate = new java.sql.Date(victim.getDateOfBirth().getTimeInMillis());
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, victim.getName());
            stmt.setDate(2, tempDate);
            stmt.setString(3, victim.getAddress());
            stmt.setString(4, victim.getStatus());
            stmt.setString(5, victim.getGender());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public Victim selectVictim(int id) {
        runConnection();
        String queryStr = "SELECT * FROM " + VICTIM + " WHERE VICTIM_ID = ?";
        Victim victim = null;
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                GregorianCalendar temp = new GregorianCalendar();
                temp.setTimeInMillis(rs.getDate(3).getTime());
                victim = new Victim(rs.getInt(1),
                        rs.getString(2), temp, rs.getString(4),
                        rs.getString(5), rs.getString(6));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return victim;
    }

    public int deleteVictim(int id) {
        runConnection();
        String deleteQuery = "DELETE FROM " + VICTIM + " WHERE VICTIM_ID = ?";
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
    
    public int updateVictim(Victim victim){
         runConnection();
        int result = 0;
        String queryStr = "UPDATE " + VICTIM + " SET NAME = ?, DOB = ?,"
                + " ADDRESS=? ,STATUS = ?, GENDER = ? WHERE VICTIM_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setString(1, victim.getName());
            java.sql.Date date = new java.sql.Date(victim.getDateOfBirth().getTimeInMillis());
            stmt.setDate(2, date);
            stmt.setString(3, victim.getAddress());
            stmt.setString(4, victim.getStatus());
            stmt.setString(5, victim.getGender());
            stmt.setInt(6, victim.getId());
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
