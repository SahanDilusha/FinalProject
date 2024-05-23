package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {

    private static Connection connection;

    private static final String username = "u334158804_final_user";
    private static final String password = "FinalProject@2024SE";
    private static final String database = "u334158804_final_project";

//    private static final String username = "root";
//    private static final String password = "Sahan@200212010";
//    private static final String database = "final_project";
    static {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://193.203.166.29:3306/" + database, username, password);

//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execute(String query) throws Exception {

        Statement statement = connection.createStatement();

        if (query.startsWith("SELECT")) {
            return statement.executeQuery(query);
        } else {
            statement.executeUpdate(query);
            return null;
        }

    }

}
