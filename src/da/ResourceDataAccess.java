/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import adt.DataAccessInterface;
import adt.ListInterface;
import domain.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import utility.DoublyList;

/**
 *
 * @author User
 */
public class ResourceDataAccess extends DataAccess implements DataAccessInterface<Resource> {

    @Override
    public ListInterface<Resource> selectAllRecord() {
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

    @Override
    public Resource selectOneRecord(int id) {
            runConnection();
        String queryStr = "SELECT * FROM " + RESOURCE + " WHERE RES_ID = ?";
        Resource resource = null;
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setInt(1, id);
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

    @Override
    public int addOneRecord(Resource newEntry) {
        runConnection();
        String query = "INSERT INTO " + RESOURCE
                + " (NAME,TYPE,QUANTITY,STATUS) VALUES (?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, newEntry.getName());
            stmt.setString(2, newEntry.getType());
            stmt.setInt(3, newEntry.getQuantity());
            stmt.setString(4, newEntry.getStatus());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            super.close();
        }
        return result;
    }

    @Override
    public int deleteRecord(int id) {
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

    @Override
    public int updateRecord(Resource anEntry) {
         runConnection();
        int result = 0;
        String queryStr = "UPDATE " + RESOURCE + " SET NAME = ?, TYPE = ?,"
                + " QUANTITY=? ,STATUS = ? WHERE RES_ID = ?";
        try {
            stmt = con.prepareStatement(queryStr);
            stmt.setString(1, anEntry.getName());
            stmt.setString(2, anEntry.getType());
            stmt.setInt(3, anEntry.getQuantity());
            stmt.setString(4, anEntry.getStatus());
            stmt.setInt(5, anEntry.getResId());
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
