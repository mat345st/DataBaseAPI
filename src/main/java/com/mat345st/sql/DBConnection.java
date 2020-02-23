package com.mat345st.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mat345st on 23.02.2020 / 17:39
 * SQLConnector / com.mat345st.sql
 *
 * @author mat345st
 */

public class DBConnection {

    private static Map<String, ConnectionContainer> connections = new HashMap<>();

    DBConnection() {}

    public static void addConnection(String url, String username, String password, String alias){
        if (!url.startsWith("jdbc:mysql://"))
            url = "jdbc:mysql://" + url;


        String [] login = new String[]{url, username, password};

        ConnectionContainer cc = new ConnectionContainer(alias, login);
        connections.put(alias, cc);
        try {
            cc.createNewConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Statement getStatement(String alias){
        ConnectionContainer cc;
        if ((cc=getConnectionContainer(alias))==null) {
            System.err.println("The database " + alias + " doesn't exist");
            return null;
        }

        try {
            if (!cc.validConnection())
                cc.createNewConnection();
            if (!cc.validStatement())
                cc.createStatement();
            return cc.last_statement;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public static Connection getConnection(String alias){
        ConnectionContainer cc;
        if ((cc=getConnectionContainer(alias))==null) {
            System.err.println("The database " + alias + " doesn't exist");
            return null;
        }

        try {
            if (!cc.validConnection())
                cc.createNewConnection();
            return cc.connection;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    private static ConnectionContainer getConnectionContainer(String alias){
        if (connections.containsKey(alias))
            return connections.get(alias);
        else
            return null;
    }
}
