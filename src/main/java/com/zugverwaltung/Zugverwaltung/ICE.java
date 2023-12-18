package com.zugverwaltung.Zugverwaltung;


// ICE Class
class ICE extends Zeuge {
    protected int leanderZahl = 0;

    public ICE(int fahrgestellnummer, String zugtyp, String zuglinie, int laufleistung, int baujahr,
            double preis, boolean unfallwagen, int leanderZahl) {
        super(fahrgestellnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, unfallwagen);
        setLeanderZahl(leanderZahl);
    }

    public ICE(int fahrgestellnummer, String zugtyp, String zuglinie, int laufleistung, int baujahr,
            double preis, int leanderZahl) {
        this(fahrgestellnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, false, leanderZahl);
    }

    public double getLeanderZahl() {
        return this.leanderZahl;
    }

    public void setLeanderZahl(int leanderzahl) {
        if (leanderzahl < 0) {
            throw new IllegalArgumentException("Die Leanderzhal darf nicht kleiner als 0 sein");
        }
        this.leanderZahl = leanderzahl;
    }

    @Override
    protected String getInfoSpezielleEigenschaften() {
        return ", Länderzahl:" + this.getLeanderZahl();
    }
}