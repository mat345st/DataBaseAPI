package com.mat345st.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by mat345st on 22.11.2019 / 15:51
 * SQLConnector / com.mat345st.sql
 *
 * @author mat345st
 */

public class DBConnectionOld {

    public static HashMap<String, ConnectionContainer> connections = new HashMap<>();

    public DBConnectionOld(String url, String username, String password, String alias) {
        if (!url.startsWith("jdbc:mysql://"))
            url = "jdbc:mysql://" + url;

        String start = url;

        //url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimeZone=Europe/Berlin";
        String [] login = new String[]{url, username, password};
        try {
            Connection con = createNewConnection(login);


            System.out.println("Connected to " + start.split("/")[url.split("/").length - 1] + " as " + con.getMetaData().getUserName().split("@")[0]);

            connections.put(alias, new ConnectionContainer(url,alias, con, login));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }


    public static void addConnection(String url, String username, String password, String alias){
        new DBConnectionOld(url,username,password,alias);
    }



    public static Statement getStatement(String alias){
        ConnectionContainer c = getContainer(alias);
        if (!isValidConnection(c.connection)){
            try {
                c.connection = createNewConnection(c.login);
                return c.connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }else {
            try {
                return c.connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public static Connection createNewConnection(String[] login) throws SQLException {
        return DriverManager.getConnection(login[0], login[1],login[2]);
    }

    public static boolean isValidConnection(Connection con){
        try {
            if (con.isClosed() && !con.isValid(2000)){
                return false;
            }
        } catch (SQLException e) {
            return true;
        }
        return true;
    }

    public static ConnectionContainer getContainer(String alias){
        if (!connections.containsKey(alias)){
            try {
                throw new Exception("This database does not exist");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return connections.get(alias);
    }



    public static class ConnectionContainer{
        public String[] login;
        public Connection connection;
        public String url;
        public String alias;

        public ConnectionContainer(String url, String alias, Connection con, String[] login){
            this.login = login;
            this.connection = con;

        }

    }

}
