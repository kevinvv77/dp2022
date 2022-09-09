import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
        try {
            connection = getConnection();

            var query = "SELECT * FROM reiziger";
            var statement = connection.prepareStatement(query);

            var rs = statement.executeQuery();
            System.out.println("Alle reizigers:");
            while (rs.next()) {
                var voorletters = rs.getString("voorletters");
                var tussenvoegsel = rs.getString("tussenvoegsel") == null ? "" : rs.getString("tussenvoegsel");
                var achternaam = rs.getString("achternaam");
                var geboortedatum = rs.getString("geboortedatum");

                System.out.printf("\t %s. %s %s (%s)\n", voorletters, tussenvoegsel, achternaam, geboortedatum);
            }
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
}
