import data.AdresDAO;
import data.AdresDAOPsql;
import data.ReizigerDAO;
import data.ReizigerDAOPsql;
import domain.Adres;
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
            var adao = new AdresDAOPsql(connection);
            var rdao = new ReizigerDAOPsql(connection, adao);
            testReizigerDAO(rdao);
            testAdresDAO(adao, rdao);

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
        var adres = new Adres(6, "1111AB", "15", "Heidelberglaan", "Utrecht", 77);
        var sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum), adres);

        System.out.println("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        rdao.delete(sietske);
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) {
        System.out.println("\n---------- Test AdresDAO -------------");
        var adres = new Adres(6, "1111AB", "15", "Heidelberglaan", "Utrecht", 1);
        var reiziger = new Reiziger(1, "G", "van", "Rijn", Date.valueOf("2002-09-17"), adres);

        // Alle adressen
        var adressen = adao.findAll();

        for(var a : adressen) {
            System.out.println(a);
        }

        System.out.println();

        // Adres opslaan
        System.out.println("[Test] Adres opslaan met id 7");
        var newAdres = new Adres(17, "1111AB", "15", "Heidelberglaan", "Utrecht", 88);
        var newReiziger = new Reiziger(88, "S", "", "Boers", Date.valueOf("2002-09-17"), newAdres);
        rdao.save(newReiziger); // rdao.save roept de dao van adres aan
        adressen = adao.findAll();
        for(var a : adressen) {
            System.out.println(a);
        }

        System.out.println();

        // Adres verwijderen
        System.out.println("[Test] Zojuist aangemaakte adres verwijderen");
        rdao.delete(newReiziger); // rdao delete roept de dao van adres aan
        adressen = adao.findAll();
        for(var a : adressen) {
            System.out.println(a);
        }

        System.out.println();

        // Adres updaten in database
        adao.update(adres);
        adressen = adao.findAll();
        System.out.println("[Test] Alle adressen na de update");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Adres zoeken met een id
        var adresById = adao.findById(2);
        System.out.println("[Test] Adres zoeken met id 2");
        System.out.println(adresById);
        System.out.println();

        // Adres zoeken met een reiziger
        var adresByReiziger = adao.findByReiziger(reiziger);
        System.out.println("[Test] Adres zoeken met een reiziger");
        System.out.println(adresByReiziger);
    }

}
