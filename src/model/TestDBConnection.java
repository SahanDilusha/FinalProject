/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sahan
 */
public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://srv1201.hstgr.io:3306/u334158804_final_project?useSSL=false";
        String username = "u334158804_root";
        String password = "FinalProject@2024SE";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    }
}
