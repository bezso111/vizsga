package com.example.restservice;

import java.sql.*;

public class DbConnect {
    static final String dbUrl = "jdbc:mysql://localhost:3306/vizsga?serverTimezone=UTC";
    static final String username = "root";
    static final String password = "";
    static Connection dbConnection;
    static Statement dbStatement;

    public static ResultSet sendQuery(String query) throws SQLException {

        if ( DbConnect.dbConnection == null || DbConnect.dbStatement == null ) {
            DbConnect.dbConnection = DriverManager.getConnection(DbConnect.dbUrl, DbConnect.username, DbConnect.password);
            DbConnect.dbStatement = dbConnection.createStatement();
        }
        return DbConnect.dbStatement.executeQuery(query);
    }

    public static int sendUpdate(String query) throws SQLException {
        if ( DbConnect.dbConnection == null || DbConnect.dbStatement == null ) {
            DbConnect.dbConnection = DriverManager.getConnection(DbConnect.dbUrl, DbConnect.username, DbConnect.password);
            DbConnect.dbStatement = dbConnection.createStatement();
        }
        return DbConnect.dbStatement.executeUpdate(query);

    }
}
