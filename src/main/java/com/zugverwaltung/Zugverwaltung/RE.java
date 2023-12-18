package com.zugverwaltung.Zugverwaltung;


// RE class
class RE extends Zeuge {
    protected int schlafplaetze = 0;

    public RE(int fahrgestellnummer, String zugtyp, String zuglinie, int laufleistung, int baujahr,
            double preis, boolean unfallwagen, int schlafplaetze) {
        super(fahrgestellnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, unfallwagen);
        setSchlafplaetze(schlafplaetze);
    }

    public RE(int fahrgestellnummer, String zugtyp, String zuglinie, int laufleistung, int baujahr,
            double preis, int schlafplaetze) {
        this(fahrgestellnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, false, schlafplaetze);
    }

    public int getSchlafplaetze() {
        return this.schlafplaetze;
    }

    public void setSchlafplaetze(int schlafplaetze) {
        if (schlafplaetze < 0) {
            throw new IllegalArgumentException("Die Schlafplatz darf nicht kleiner als 0 sein");
        }
        this.schlafplaetze = schlafplaetze;
    }

    @Override
    protected String getInfoSpezielleEigenschaften() {
        return ", Schlafplätze: " + this.getSchlafplaetze();
    }
}
