package com.example.restservice;

//import com.sun.org.apache.xpath.internal.objects.XString;
//import com.sun.org.apache.xpath.internal.operations.String;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Adatbazis implements Closeable {
    Connection conn = null;

    public Adatbazis(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url,
                    username, password);
            System.out.println("Sikerült kapcsolódni az adatbázishoz!");
        } catch (SQLException se) {
            System.out.println("Nem sikerült létrehozni a kapcsolatot! " + se.toString());
            throw new RuntimeException("Nem sikerült csatlakozni az adatbázishoz!");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (conn != null) {
            conn.close();
            System.out.println("Sikerült lebontani az adatbázis kapcsolatot a finalizeból!");
        }
        System.out.println("Nem is kellett lebontani az adatbázis kapcsolatot a finalizeból!");
    }

    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println("Hiba: " + se.toString());
            }
            System.out.println("Sikerült lebontani az adatbázis kapcsolatot a closeból!");
        }
        System.out.println("Nem is kellett lebontani az adatbázis kapcsolatot a closeból!");
    }

    public boolean dropTable(String table) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS " + table + ";");
            return true;
        } catch (SQLException se) {
            System.out.println("Nem sikerült eldobni a tanulók táblát! " + se.toString());
            return false;
        }
    }

    public int insertIntoUserTable(String email,
                                                String password, String nev) {
        try {
            Statement stmt = conn.createStatement();
            String command = "INSERT INTO users (username, password, name)" +
                    " VALUES ('" + email + "','" + password + "','" + nev +  "');";
            System.out.println(command);
            stmt.execute(command);
            return 0;
        } catch (SQLException se) {
            System.out.println("Nem sikerült beilleszteni a users táblába! " + se.toString());
            return se.getErrorCode();
        }
    }

    public String selectTable(String table) {
        try {
            Statement  stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + table + ";");
            ResultSetMetaData meta = result.getMetaData();

            String content = "<br/>";
            for(int i=1; i <= meta.getColumnCount(); ++i) {
                content += (meta.getColumnName(i) + "    ");
            }
            content += "<br/>";
            content += ("---------------------------------------------------------------------------------<br/>");

            while(result.next()) {
                for(int i=1; i <= meta.getColumnCount(); ++i) {
                    content += (result.getString(i) + "    ");
                }
                content += "<br/>";
            }
            return content;
        } catch (SQLException se) {
            String errormsg = ("Nem sikerült a select! " + se.toString() + "<br/>");
            return errormsg;
        }
    }

    public List<String> selectTableAsList(String table) {
        List<String> contents = new ArrayList<>();
        try {
            Statement  stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + table + ";");
            ResultSetMetaData meta = result.getMetaData();

            String content = "";
            for(int i=1; i <= meta.getColumnCount(); ++i) {
                content += (meta.getColumnName(i) + "    ");
            }
            contents.add(content);
            content = ("---------------------------------------------------------------------------------");
            contents.add(content);

            while(result.next()) {
                content = "";
                for(int i=1; i <= meta.getColumnCount(); ++i) {
                    content += (result.getString(i) + "    ");
                }
                contents.add(content);
            }
            return contents;
        } catch (SQLException se) {
            String errormsg = ("Nem sikerült a select! " + se.toString() + "<br/>");
            contents.add(errormsg);
            return contents;
        }
    }

    public Connection getConn() {
        return conn;
    }
}
