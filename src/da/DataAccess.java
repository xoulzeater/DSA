/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DataAccess {

    public final String RESOURCE = "RESOURCE";
    public final String VICTIM = "VICTIM";
    public final String RESOURCEASSIGN = "RESOURCEASSIGN";
    public final String STAFF = "STAFF";
    public static final String TASK = "TASK";
    
    public final String NAME = "nbuser";
    public final String PASSWORD = "nbuser";
    public final String LOCATIONS = "jdbc:derby://localhost:1527/disaster";

    protected PreparedStatement stmt = null;
    protected Statement st = null;
    protected Connection con = null;

    protected void runConnection() {
        try {
            con = DriverManager.getConnection(LOCATIONS, NAME, PASSWORD);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void close() {
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
