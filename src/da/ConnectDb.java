/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import adt.DataAccessInterface;
import adt.ListInterface;
import adt.SortedListInterface;
import domain.Resource;
import domain.ResourceAssign;
import domain.Victim;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import utility.DoublyList;
import utility.MyConverter;
import utility.SortedLists;

/**
 *
 * @author User
 */
public final class ConnectDb extends DataAccess {

   
    public ConnectDb() {
        super.runConnection();
    }

    public SortedListInterface getAllSortedVictim() {
        runConnection();
        String query = "Select * FROM " + VICTIM;
        Statement statement;
        SortedListInterface<Victim> victimList = new SortedLists<>();
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

    public int getVictimFoodCount(int id, String type) {
        runConnection();
        String queryStr = "SELECT COUNT(*) as total FROM " + RESOURCEASSIGN
                + " ress," + RESOURCE + " res WHERE ress.VICTIM_ID = ? AND res.RES_ID = ress.RES_ID AND type=? ";
        int count = 0;
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, id);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return count;
    }

    public int insertResourceAssign(ResourceAssign resAsgn) {
        runConnection();
        String query = "INSERT INTO " + RESOURCEASSIGN
                + " (RES_ID,VICTIM_ID,ASSIGN_TIME,QUANTITY) VALUES (?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, resAsgn.getResource().getResId());
            stmt.setInt(2, resAsgn.getVictim().getId());
            java.sql.Timestamp time
                    = new java.sql.Timestamp(resAsgn.getAssigntime().getTimeInMillis());
            stmt.setTimestamp(3, time);
            stmt.setInt(4, resAsgn.getQuantity());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return result;
    }

    public ListInterface<Victim> selectVictimDobRange(
            GregorianCalendar start, GregorianCalendar end) {
        DataAccessInterface<Victim> da = new VictimDataAccess();
        runConnection();
        String query = "Select distinct(resa.VICTIM_ID) FROM " + RESOURCEASSIGN + " resa," + VICTIM
                + " vic WHERE YEAR(vic.dob) BETWEEN ? AND ? AND resa.VICTIM_ID=vic.VICTIM_ID";
        ListInterface<Victim> list = new DoublyList<>();
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, end.get(Calendar.YEAR));
            stmt.setInt(2, start.get(Calendar.YEAR));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Victim victim = da.selectOneRecord(rs.getInt(1));
                list.add(victim);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return list;
    }

    public ListInterface<ResourceAssign> selectResourceAssignWithVictimId(int victimId) {
        DataAccessInterface<Resource> da = new ResourceDataAccess();
        runConnection();
        String queryStr = "SELECT * FROM " + RESOURCEASSIGN + " WHERE VICTIM_ID = ?";
        Victim victim = null;
        ListInterface<ResourceAssign> list = new DoublyList<>();
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, victimId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Resource resource = da.selectOneRecord(rs.getInt(2));
                ResourceAssign assign = new ResourceAssign(rs.getInt(1),
                        resource, victim,
                        MyConverter.convertDateToGregorian(rs.getTimestamp(4)),
                        rs.getInt(5));
                list.add(assign);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return list;
    }

    public boolean checkVictimInResourceAssign(int victimId) {
        runConnection();
        String queryStr = "SELECT * FROM " + RESOURCEASSIGN + " WHERE VICTIM_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, victimId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }
        return false;

    }
}
