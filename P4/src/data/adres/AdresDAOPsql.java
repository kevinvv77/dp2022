package data.adres;

import domain.Adres;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            var query = "INSERT INTO adres VALUES(?, ?, ?, ?, ?, ?)";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReizigerId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            var query = "UPDATE adres " +
                    "SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? " +
                    "WHERE adres_id = ?";
            var statement = connection.prepareStatement(query);

            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getReizigerId());
            statement.setInt(6, adres.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            var query = "DELETE FROM adres WHERE adres_id = ?";
            var statement = connection.prepareStatement(query);
            statement.setInt(1, adres.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            var query = "SELECT * FROM adres WHERE reiziger_id = ?";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, reiziger.getId());

            var rs = statement.executeQuery();

            rs.next();
            return buildAdresWithResultSet(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Adres findById(int id) {
        try {
            var query = "SELECT * FROM adres WHERE adres_id = ?";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, id);
            var rs = statement.executeQuery();
            rs.next();

            return buildAdresWithResultSet(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<>();

        try {
            var query = "SELECT * FROM adres";
            var statement = connection.prepareStatement(query);

            var rs = statement.executeQuery();

            while(rs.next()) {
                adressen.add(buildAdresWithResultSet(rs));
            }

            return adressen;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Adres buildAdresWithResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("adres_id");
        String postcode = rs.getString("postcode");
        String huisnummer = rs.getString("huisnummer");
        String straat = rs.getString("straat");
        String woonplaats = rs.getString("woonplaats");
        int reizigerId = rs.getInt("reiziger_id");

        return new Adres(id, postcode, huisnummer, straat, woonplaats, reizigerId);
    }
}
