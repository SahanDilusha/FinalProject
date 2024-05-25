package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    private static final String username = "u334158804_final_user";
    private static final String password = "FinalProject@2024SE";
    private static final String database = "u334158804_final_project";
    private static Connection connection = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create the initial connection
            connection = DriverManager.getConnection("jdbc:mysql://193.203.166.29:3306/" + database, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MySQL connection!", e);
        }
    }

    private MySQL() {
    }

    public static ResultSet execute(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Re-establish the connection if it's closed or null
            connection = DriverManager.getConnection("jdbc:mysql://193.203.166.29:3306/" + database, username, password);
        }

        Statement statement = connection.createStatement();
        if (query.startsWith("SELECT")) {
            return statement.executeQuery(query);
        } else {
            statement.executeUpdate(query);
            statement.close();
            return null;
        }
    }
}
