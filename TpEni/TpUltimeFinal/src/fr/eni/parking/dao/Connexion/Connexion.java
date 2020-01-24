package fr.eni.parking.dao.Connexion;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Connexion {

    /**
     * récupère la connection JDBC
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws  SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost;databasename=Parking;user=sa;password=Pa$$w0rd");
    }
}
