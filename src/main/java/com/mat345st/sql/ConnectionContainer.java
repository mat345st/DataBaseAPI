package com.mat345st.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mat345st on 23.02.2020 / 17:31
 * SQLConnector / com.mat345st.sql
 *
 * @author mat345st
 */

public class ConnectionContainer {

    public String[] login;
    public String alias;

    public Connection connection;
    public Statement last_statement;


    ConnectionContainer(String alias, String[] login){
        this.alias = alias;
        this.login = login;

    }


    public String getUrl(){
        return login[0];
    }
    public String getUsername(){
        return login[1];
    }
    public String getPassword(){
        return login[2];
    }

    public Connection createNewConnection() throws SQLException {
        this.connection = DriverManager.getConnection(login[0], login[1],login[2]);
        System.out.println("Connected to " + this.connection.getMetaData().getURL().split("/")[this.connection.getMetaData().getURL().split("/").length -1] /*login[0].split("/")[login[0].split("/").length - 1]*/ + " as " + this.connection.getMetaData().getUserName().split("@")[0]);
        return this.connection;
    }

    Statement createStatement() throws SQLException {
        last_statement = createStatement(this.connection);
        return last_statement;
    }

    static Statement createStatement(Connection con) throws SQLException {
        return con.createStatement();
    }


    boolean validStatement() throws SQLException {
        return validStatement(this.last_statement);
    }

    static boolean validStatement(Statement statement) throws SQLException {
        if (statement==null) return false;
        return !statement.isClosed();
    }

    boolean validConnection() throws SQLException {
        return validConnection(this.connection);
    }

    static boolean validConnection(Connection con) throws SQLException {
        if (con==null) return false;
        return (!con.isClosed() && con.isValid(2000));
    }


}
