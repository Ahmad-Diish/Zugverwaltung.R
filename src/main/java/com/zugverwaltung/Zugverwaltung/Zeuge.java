package com.zugverwaltung.Zugverwaltung;

import java.util.*;

// Class Züge
abstract class Zeuge {
    protected int idnummer = 0;
    protected String zugtyp = "";
    protected String zuglinie = "";
    protected int laufleistung = 0;
    protected int baujahr = 0;
    protected double preis = 0.0;
    protected boolean unfallwagen = false;

    public Zeuge(int idnummer, String zugtyp, String zuglinie, int laufleistung, int baujahr, double preis, boolean unfallwagen) {
        setIdnummer(idnummer);
        setZugtyp(zugtyp);
        setLaufleistung(laufleistung);
        setBaujahr(baujahr);
        setPreis(preis);
        setUnfallwagen(unfallwagen);
        setZuglinie(zuglinie);
    }

    public Zeuge(int idnummer, String zugtyp, String zuglinie, int laufleistung, int baujahr, double preis) {
        this(idnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, false);
    }

    public String getZuglinie() {
        return this.zuglinie;
    }

    public void setZuglinie(String zuglinie) {
        if (zuglinie == null || zuglinie.trim().length() < 2) {
            throw new IllegalArgumentException("Ungültiges zuglinie");
        }
        this.zuglinie = zuglinie;
    }

    public int getIdnummer() {
        return this.idnummer;
    }

    public void setIdnummer(int idnummer) {
      if (idnummer < 0) { throw new IllegalArgumentException("Die Nummer darf nicht kleiner als 1 sein");}
        this.idnummer = idnummer;
    }

    public String getZugtyp() {
        return this.zugtyp;
    }

    public void setZugtyp(String zugtyp) {
        if (zugtyp == null || zugtyp.trim().isEmpty()) {
            throw new IllegalArgumentException("Ungültiger Zugtyp");
        }
        this.zugtyp = zugtyp;
    }

    public int getLaufleistung() {
        return this.laufleistung;
    }

    public void setLaufleistung(int laufleistung) {
        if (laufleistung < 0) {
            throw new IllegalArgumentException("Die Laufleistung darf nicht kleiner als 0 sein");
        }
        this.laufleistung = laufleistung;
    }

    public int getBaujahr() {
        return this.baujahr;
    }

    public void setBaujahr(int baujahr) {
        int aktuellesJahr = Calendar.getInstance().get(Calendar.YEAR);
        if (baujahr < 1950 || baujahr > aktuellesJahr) {
            System.out.println("Üngultiges Jahr");
        }
        this.baujahr = baujahr;
    }

    public double getPreis() {
        return this.preis;
    }

    public void setPreis(double preis) {
        if (preis < 0) {
            throw new IllegalArgumentException("Der Preis darf nicht kleiner als 0 sein");
        }
        this.preis = preis;
    }

    public boolean getUnfallwagen() {
        return this.unfallwagen;
    }

    public void setUnfallwagen(boolean unfallwagen) {
        this.unfallwagen = unfallwagen;
    }

    public String unfall() {
        String unfallfrei = ",!!! UNFALL !!!";
        if (!this.getUnfallwagen()) {
            unfallfrei = "";
        }
        return unfallfrei;
    }

    public String getInfo() {
        return "Das Züg " + this.getIdnummer() + ": Zugtyp: " + this.getZugtyp() +
                 ", Zugline: " + this.getZuglinie() + ", Laufleistung: " + this.getLaufleistung() + " Km, Baujahr: " +
                this.getBaujahr() + ", Ticket Preis: " + this.getPreis() + " Euro " + getInfoSpezielleEigenschaften() +"  "+unfall();
    }

    protected abstract String getInfoSpezielleEigenschaften();

}
