package domain;

import java.sql.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegels;
    private String achternaam;
    private Date geboorteDatum;
    private Adres adres;

    private List<OVChipkaart> ovChipkaartList;

    public Reiziger() {}

    public Reiziger(int id, String voorletters, String tussenvoegels, String achternaam, Date geboorteDatum, Adres adres, List<OVChipkaart> ovChipkaartList) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegels = tussenvoegels;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
        this.adres = adres;
        this.ovChipkaartList = ovChipkaartList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegels() {
        return tussenvoegels;
    }

    public void setTussenvoegels(String tussenvoegels) {
        this.tussenvoegels = tussenvoegels;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaartList() {
        return ovChipkaartList;
    }

    public void setOvChipkaartList(List<OVChipkaart> ovChipkaartList) {
        this.ovChipkaartList = ovChipkaartList;
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "id=" + id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegels='" + tussenvoegels + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboorteDatum=" + geboorteDatum +
                ", adres=" + adres +
                ", ovChipkaartList=" + ovChipkaartList +
                '}';
    }
}
