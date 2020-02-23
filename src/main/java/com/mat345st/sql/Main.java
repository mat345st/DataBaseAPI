package com.mat345st.sql;

import java.sql.Statement;

/**
 * Created by mat345st on 23.02.2020 / 19:15
 * SQLConnector / com.mat345st.sql
 *
 * @author mat345st
 */

public class Main {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection("mysql.battlemc.de/BattleBot", "BattleBot", "U2mfUcShdmXmx1fE", "BattleBot");


        Statement st = DBConnection.getStatement("BattleBot");


    }
}
