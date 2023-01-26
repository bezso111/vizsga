package com.example.restservice;

//import com.sun.org.apache.xpath.internal.objects.XString;
//import com.sun.org.apache.xpath.internal.operations.String;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Adatbazis implements Closeable {
    Connection conn = null;
    int errorcode = 0;

    public Adatbazis(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url,
                    username, password);
            System.out.println("Sikerült kapcsolódni az adatbázishoz!");
        } catch (SQLException se) {
            System.out.println("Nem sikerült létrehozni a kapcsolatot! " + se.getErrorCode());
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

    public int insertIntoUserTable(String email, String password, String nev) {
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

    public UserSelect selectLogin(String username, String password) {
        try {
            Statement  stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT id,name FROM users WHERE username='"+username+"' AND password='"+password+"';");

            if (result.next()) {
                errorcode=0;
                int id=result.getInt("id");
                String name=result.getString("name");
                return new UserSelect(id,name);
            } else {
                errorcode=0;
                return new UserSelect(0,"Nincs ilyen felhasználó jelszó páros!");
            }
        } catch (SQLException se) {
            String errormsg = ("Nem sikerült a select! " + se.toString() + "<br/>");
            errorcode = se.getErrorCode();
            return new UserSelect(0,errormsg);
        }
    }

    public UserSelect selectKamat() {
        try {
            Statement  stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT kamat FROM alapkamat;");

            if (result.next()) {
                errorcode=0;
                int id=result.getInt("kamat");
                String name="Sikerült!";
                return new UserSelect(id,name);
            } else {
                errorcode=0;
                return new UserSelect(0,"Nincs ilyen felhasználó jelszó páros!");
            }
        } catch (SQLException se) {
            String errormsg = ("Nem sikerült a select! " + se.toString() + "<br/>");
            errorcode = se.getErrorCode();
            return new UserSelect(0,errormsg);
        }
    }

    public boolean testuder(String userid,String username) {
        try {
            Statement stmnt = conn.createStatement();
            ResultSet result = stmnt.executeQuery("SELECT id FROM users WHERE id="+userid+" AND username='"+username+"';");
            if (result.next()) {
                errorcode=0;
                return true;
            } else {
                errorcode=0;
                return false;
            }
        } catch (SQLException se) {
            errorcode=1;
            return false;
        }
    }
    public String insertBefektetes(String osszeg, String futamido, boolean bef, String userid) {
        try {
            Statement stmnt = conn.createStatement();
            String query="SELECT befektetes.id FROM befektetes,alapkamat WHERE befektetes.osszeg="+osszeg+" AND befektetes.futamido+"+futamido+" AND befektetes.kamat=alapkamat.kamat AND befektetes.partner_id IS NULL AND befektetes.befektetes="+!bef+";";
            System.out.println(query);
            ResultSet result = stmnt.executeQuery(query);
            if (result.next()) {
                int partner_id = result.getInt(1);
                query = "INSERT INTO befektetes (befekteto_id,osszeg,futamido,kamat,befektetes,partner_id,oszerendeles_datum) SELECT "+userid+","+osszeg+","+futamido+",kamat,"+bef+","+partner_id+",curdate() FROM alapkamat LIMIT 1;";
                System.out.println(query);
                int count = stmnt.executeUpdate(query);
                query = "SELECT befektetes.id FROM befektetes,alapkamat WHERE befektetes.osszeg="+osszeg+" AND befektetes.futamido+"+futamido+" AND befektetes.kamat=alapkamat.kamat AND befektetes.befektetes="+bef+";";
                System.out.println(query);
                ResultSet result2 = stmnt.executeQuery(query);
                if (result2.next()) {
                    int befekteto_id = result2.getInt(1);
                    query = "UPDATE befektetes SET partner_id=" + befekteto_id + ",oszerendeles_datum=curdate() WHERE id=" + partner_id + ";";
                    System.out.println(query);
                    count = stmnt.executeUpdate(query);
                    System.out.println(count);
                    errorcode=0;
                    return "Sikeres összerendelés";
                } else {
                    errorcode=1;
                    return "Hibás felvitel!!";
                }
            } else {
                query = "INSERT INTO befektetes (befekteto_id,osszeg,futamido,kamat,befektetes) SELECT "+userid+","+osszeg+","+futamido+",kamat,"+bef+" FROM alapkamat LIMIT 1;";
                System.out.println(query);
                int count = stmnt.executeUpdate(query);
                errorcode=0;
                return "Nincs összerendelés";
            }
        } catch (SQLException se) {
            errorcode = se.getErrorCode();
            return "Nem a müvelet!  " + se.toString() + "<br/>";
        }
    }

    public Connection getConn() {
        return conn;
    }
}
