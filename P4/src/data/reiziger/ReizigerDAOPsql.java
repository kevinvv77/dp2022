package data.reiziger;

import data.adres.AdresDAO;
import data.ovchipkaart.OVChipkaartDAO;
import data.ovchipkaart.OVChipkaartDAOPsql;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private final Connection connection;
    private AdresDAO adresDAO;
    private OVChipkaartDAO ovChipkaartDAO;

    public ReizigerDAOPsql(Connection connection, AdresDAO adresDAO, OVChipkaartDAO ovChipkaartDAO) {
        this.connection = connection;
        this.adresDAO = adresDAO;
        this.ovChipkaartDAO = ovChipkaartDAO;
    }

    public void setAdresDAO(AdresDAO adresDAO) {
        this.adresDAO = adresDAO;
    }

    public void setOvChipkaartDAO(OVChipkaartDAO ovChipkaartDAO) {
        this.ovChipkaartDAO = ovChipkaartDAO;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            var saveQuery = "INSERT INTO reiziger VALUES(?, ?, ?, ?, ?)";
            var saveReiziger = connection.prepareStatement(saveQuery);

            saveReiziger.setInt(1, reiziger.getId());
            saveReiziger.setString(2, reiziger.getVoorletters());
            saveReiziger.setString(3, reiziger.getTussenvoegels());
            saveReiziger.setString(4, reiziger.getAchternaam());
            saveReiziger.setDate(5, reiziger.getGeboorteDatum());

            saveReiziger.executeUpdate();
            saveReiziger.close();
            adresDAO.save(reiziger.getAdres());

            for(var ov : reiziger.getOvChipkaartList())
                ovChipkaartDAO.save(ov);

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            var updateQuery = "UPDATE reiziger SET voorletters = ?, " +
                    "tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            var updateReiziger = connection.prepareStatement(updateQuery);

            updateReiziger.setString(1, reiziger.getVoorletters());
            updateReiziger.setString(2, reiziger.getTussenvoegels());
            updateReiziger.setString(3, reiziger.getAchternaam());
            updateReiziger.setDate(4, reiziger.getGeboorteDatum());
            updateReiziger.setInt(5, reiziger.getId());

            updateReiziger.executeUpdate();
            updateReiziger.close();

            adresDAO.update(reiziger.getAdres());

            for(var ov : reiziger.getOvChipkaartList())
                ovChipkaartDAO.update(ov);

            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            adresDAO.delete(reiziger.getAdres());
            for(var ov: reiziger.getOvChipkaartList())
                ovChipkaartDAO.delete(ov);

            var deleteQuery = "DELETE FROM reiziger WHERE reiziger_id = ?";
            var deleteReiziger = connection.prepareStatement(deleteQuery);

            deleteReiziger.setInt(1, reiziger.getId());
            deleteReiziger.executeUpdate();
            deleteReiziger.close();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            var query = "SELECT FROM reiziger WHERE reiziger_id = ?";
            var findReizigerById = connection.prepareStatement(query);

            findReizigerById.setInt(1, id);
            var rs = findReizigerById.executeQuery();

            return buildReiziger(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Reiziger> findByGeboorteDatum(String datum) {
        try {
            var query = "SELECT FROM reiziger WHERE geboortedatum = ?";
            var findByGbDatum = connection.prepareStatement(query);
            findByGbDatum.setDate(1, Date.valueOf(datum));

            var rs = findByGbDatum.executeQuery();
            return voegReizigerToeAanLijst(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            var query = "SELECT * FROM reiziger";
            var findALl = connection.prepareStatement(query);

            var rs = findALl.executeQuery();
            return voegReizigerToeAanLijst(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private List<Reiziger> voegReizigerToeAanLijst(ResultSet rs) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();

        while(rs.next()) {
            reizigers.add(buildReiziger(rs));
        }

        return reizigers;
    }

    private Reiziger buildReiziger(ResultSet rs) throws SQLException {
        var id = rs.getInt("reiziger_id");
        var voorletters = rs.getString("voorletters");
        var tussenvoegsel = rs.getString("tussenvoegsel") == null ? "" : rs.getString("tussenvoegsel");
        var achternaam = rs.getString("achternaam");
        var geboortedatum = rs.getDate("geboortedatum");

        var reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum, null, null);
        var adres = adresDAO.findByReiziger(reiziger);
        var ovchipkaartList = ovChipkaartDAO.findByReiziger(reiziger);
        reiziger.setOvChipkaartList(ovchipkaartList);
        reiziger.setAdres(adres);

        return reiziger;
    }
}
