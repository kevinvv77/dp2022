package data.ovchipkaart;

import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private final Connection connection;

    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            var query = "INSERT INTO ov_chipkaart VALUES(?, ?, ?, ?, ?)";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, ovChipkaart.getKaartNummer());
            statement.setDate(2, ovChipkaart.getGeldigTot());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReizigerId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            var query = "UPDATE ov_chipkaart " +
                    "SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ?" +
                    "WHERE kaart_nummer = ?";
            var statement = connection.prepareStatement(query);

            statement.setDate(1, ovChipkaart.getGeldigTot());
            statement.setInt(2, ovChipkaart.getKlasse());
            statement.setDouble(3, ovChipkaart.getSaldo());
            statement.setInt(4, ovChipkaart.getReizigerId());
            statement.setInt(5, ovChipkaart.getKaartNummer());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            var query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, ovChipkaart.getKaartNummer());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public OVChipkaart findById(int id) {
        try {
            var query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            var rs = statement.executeQuery();
            rs.next();

            return buildOVChipkaartWithResultSet(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        var ovchipkaarten = new ArrayList<OVChipkaart>();

        try {
            var query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            var statement = connection.prepareStatement(query);

            statement.setInt(1, reiziger.getId());

            var rs = statement.executeQuery();
            while (rs.next())
                ovchipkaarten.add(buildOVChipkaartWithResultSet(rs));

            return ovchipkaarten;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        var ovchipkaarten = new ArrayList<OVChipkaart>();

        try {
            var query = "SELECT * FROM ov_chipkaart";
            var statement = connection.prepareStatement(query);

            var rs = statement.executeQuery();
            while(rs.next())
                ovchipkaarten.add(buildOVChipkaartWithResultSet(rs));

            return ovchipkaarten;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private OVChipkaart buildOVChipkaartWithResultSet(ResultSet rs) throws SQLException {
        var kaartNummer = rs.getInt("kaart_nummer");
        var geldigTot = rs.getDate("geldig_tot");
        var klasse = rs.getInt("klasse");
        var saldo = rs.getDouble("saldo");
        var reizigerId = rs.getInt("reiziger_id");

        return new OVChipkaart(kaartNummer, geldigTot, klasse, saldo, reizigerId);
    }
}
