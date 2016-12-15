/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import adt.DataAccessInterface;
import adt.ListInterface;
import domain.Victim;
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
public class VictimDataAccess extends DataAccess implements DataAccessInterface<Victim> {

    public final String VICTIM = "VICTIM";

    @Override
    public ListInterface<Victim> selectAllRecord() {
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

    @Override
    public Victim selectOneRecord(int id) {
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

    @Override
    public int addOneRecord(Victim newEntry) {
        runConnection();
        String query = "INSERT INTO " + VICTIM
                + " (NAME,DOB,ADDRESS,STATUS,GENDER) VALUES (?,?,?,?,?)";
        int result = 0;
        try {
            java.sql.Date tempDate = new java.sql.Date(newEntry.getDateOfBirth().getTimeInMillis());
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, newEntry.getName());
            stmt.setDate(2, tempDate);
            stmt.setString(3, newEntry.getAddress());
            stmt.setString(4, newEntry.getStatus());
            stmt.setString(5, newEntry.getGender());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    @Override
    public int deleteRecord(int id) {
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

    @Override
    public int updateRecord(Victim anEntry) {
        runConnection();
        int result = 0;
        String queryStr = "UPDATE " + VICTIM + " SET NAME = ?, DOB = ?,"
                + " ADDRESS=? ,STATUS = ?, GENDER = ? WHERE VICTIM_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setString(1, anEntry.getName());
            java.sql.Date date = new java.sql.Date(anEntry.getDateOfBirth().getTimeInMillis());
            stmt.setDate(2, date);
            stmt.setString(3, anEntry.getAddress());
            stmt.setString(4, anEntry.getStatus());
            stmt.setString(5, anEntry.getGender());
            stmt.setInt(6, anEntry.getId());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

}
