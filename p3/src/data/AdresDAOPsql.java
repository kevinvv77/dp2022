package data;

import domain.Adres;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public Adres findById(int id) {
        try {
            var query = "SELECT FROM adres WHERE adres_id = ?";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, id);
            var rs = statement.executeQuery();

            return buildAdresWithResultSet(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        return null;
    }

    private Adres buildAdresWithResultSet(ResultSet rs) throws SQLException {
        var id = rs.getInt("adres_id");
        var postcode = rs.getString("postcode");
        var huisnummer = rs.getString("huisnummer");
        var straat = rs.getString("straat");
        var woonplaats = rs.getString("woonplaats");
        var reizigerId = rs.getInt("reiziger_id");

        return new Adres(id, postcode, huisnummer, straat, woonplaats, reizigerId);
    }
}
