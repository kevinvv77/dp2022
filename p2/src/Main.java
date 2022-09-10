import data.ReizigerDAO;
import data.ReizigerDAOPsql;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
        try {
            var connection = getConnection();
            var rdao = new ReizigerDAOPsql(connection);
            testReizigerDAO(rdao);

            closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection() throws SQLException {
        if (connection ==  null) {
            var url = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=yxzy777";
            connection = DriverManager.getConnection(url);
        }

        return connection;
    }

    private static void closeConnection() throws SQLException {
        if(connection != null) connection.close();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        var reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (var r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        var gbdatum = "1981-03-14";
        var sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.println("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }
}
