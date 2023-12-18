package com.zugverwaltung.Zugverwaltung.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class info {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int ID;
public int getID() {
    return ID;
}
public void setID(int iD) {
    ID = iD;
}
private String Zugtyp;
public String getZugtyp() {
    return Zugtyp;
}
public void setZugtyp(String zugtyp) {
    Zugtyp = zugtyp;
}
private String Zuglinie;
public String getZuglinie() {
    return Zuglinie;
}
public void setZuglinie(String zuglinie) {
    Zuglinie = zuglinie;
}
private int Laufleistung;
public int getLaufleistung() {
    return Laufleistung;
}
public void setLaufleistung(int laufleistung) {
    Laufleistung = laufleistung;
}
private int Baujahr;
public int getBaujahr() {
    return Baujahr;
}
public void setBaujahr(int baujahr) {
    Baujahr = baujahr;
}
private double Preis;
public double getPreis() {
    return Preis;
}
public void setPreis(double preis) {
    Preis = preis;
}

private int Schlafplestze;
public int getSchlafplestze() {
    return Schlafplestze;
}
public void setSchlafplestze(int schlafplestze) {
    Schlafplestze = schlafplestze;
}
private int Länderzahl;
public int getLänderzahl() {
    return Länderzahl;
}
public void setLänderzahl(int länderzahl) {
    Länderzahl = länderzahl;
}

    
}
