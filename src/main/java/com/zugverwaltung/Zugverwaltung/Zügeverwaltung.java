package com.zugverwaltung.Zugverwaltung;

import java.sql.*;
import java.util.*;
import java.util.List;

class Zeugeverwaltung {

    // Datenbank Verbindung
    private static final String DB_URL = "jdbc:mysql://localhost:3306/zeuge";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "13031303";

    private Connection connection;

    public Zeugeverwaltung() {
        
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

    }

    public void aufnehmen(Zeuge zeug) {
        if (searchZugN(zeug.getIdnummer()) == -1) {
            try {
                
                String insertSQL = "INSERT INTO info VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL)) {
                    preparedStatement.setInt(1, zeug.getIdnummer());
                    preparedStatement.setString(2, zeug.getZugtyp());
                    preparedStatement.setString(3, zeug.getZuglinie());
                    preparedStatement.setInt(4, zeug.getLaufleistung());
                    preparedStatement.setInt(5, zeug.getBaujahr());
                    preparedStatement.setDouble(6, zeug.getPreis());
                    preparedStatement.setBoolean(7, zeug.getUnfallwagen());

                    if (zeug instanceof RE) {
                        preparedStatement.setInt(8, ((RE) zeug).getSchlafplaetze());
                        preparedStatement.setInt(9, -1);
                    } else if (zeug instanceof ICE) {
                        preparedStatement.setInt(8, -1);
                        preparedStatement.setInt(9, (int) ((ICE) zeug).getLeanderZahl());
                    }

                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException( "Das Züg mit der Nummer " + zeug.getIdnummer() + " ist schon Vorhanden");
        }
    }

    public List<Zeuge> getAllZeugen() {
        List<Zeuge> zeugenList = new ArrayList<>();

        try {
            
            String selectSQL = "SELECT * FROM info";

            try (Statement statement = this.connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(selectSQL)) {

                while (resultSet.next()) {
                    int idnummer = resultSet.getInt(1);
                    String zugtyp = resultSet.getString(2);
                    String zuglinie = resultSet.getString(3);
                    int laufleistung = resultSet.getInt(4);
                    int baujahr = resultSet.getInt(5);
                    double preis = resultSet.getDouble(6);
                    boolean unfallwagen = resultSet.getBoolean(7);

                    int schlafplaetze = resultSet.getInt(8);
                    int leanderZahl = resultSet.getInt(9);

                    if (schlafplaetze >= 0) {
                        zeugenList.add(new RE(idnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, unfallwagen, schlafplaetze));
                    } else if (leanderZahl >= 0) {
                        zeugenList.add(new ICE(idnummer, zugtyp, zuglinie, laufleistung, baujahr, preis, unfallwagen, leanderZahl));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zeugenList;
    }

    public void ausgeben() {
        System.out.println("Unser Lager enthält " + getAllZeugen().size() + " Züge");
        System.out.println("Unser Lager enthält folgende Züge:");
        for (int i = 0; i < getAllZeugen().size(); i++) {
            Zeuge fahrzeug = getAllZeugen().get(i);
            System.out.println(fahrzeug.getInfo());
        }
    }

    public boolean loeschen(int nummer) {
        try {
           
            int index = searchZugN(nummer);
            if (index != -1) {
                
                String deleteSQL = "DELETE FROM info WHERE ID = ?";
                try (PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL)) {
                    preparedStatement.setInt(1, nummer);
                    preparedStatement.executeUpdate();
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // Die Methode

    public double getAnteilUnfallwagen() {
        int anzahlUnfall = 0;
        int anzahlGesamt = getAllZeugen().size();

        for (Zeuge fahrzeug : getAllZeugen()) {
            if (fahrzeug.getUnfallwagen()) {
                anzahlUnfall++;
            }
        }
        return ((double) anzahlUnfall / anzahlGesamt) * 100;
    }

    public double getAnteilICE() {
        int anzahlICE = 0;
        int anzahlGesamt = getAllZeugen().size();

        for (Zeuge fahrzeug : getAllZeugen()) {
            if (fahrzeug.getZugtyp().matches("ICE 3neo")) {
                anzahlICE++;
            }
        }
        return ((double) anzahlICE / anzahlGesamt) * 100;
    }

    public String getBeliebtesterZugtyp() {
        Map<String, Integer> zugtypMap = new HashMap<>();

        for (Zeuge fahrzeug : getAllZeugen()) {
            String fahrzeugzugtyp = fahrzeug.getZugtyp();
            boolean zugtypBekannt = false;
            for (String hst : zugtypMap.keySet()) {
                if (hst.equals(fahrzeugzugtyp)) {
                    zugtypBekannt = true;
                    break;
                }
            }
            if (zugtypBekannt) {
                zugtypMap.put(fahrzeugzugtyp, zugtypMap.get(fahrzeugzugtyp) + 1);
            } else {
                zugtypMap.put(fahrzeugzugtyp, 1);
            }
        }

        int maxAnzahl = -1;
        String maxAnzahlzugtyp = "";
        for (Map.Entry<String, Integer> entry : zugtypMap.entrySet()) {
            if (entry.getValue() > maxAnzahl) {
                maxAnzahl = entry.getValue();
                maxAnzahlzugtyp = entry.getKey();
            }
        }
        return maxAnzahlzugtyp;
    }

    public double getDurchschnittsalter() {
        int summeAlter = 0;
        int anzahlGesamt = getAllZeugen().size();
        int aktuellesJahr = Calendar.getInstance().get(Calendar.YEAR);

        for (Zeuge fahrzeug : getAllZeugen()) {
            int alter = aktuellesJahr - fahrzeug.getBaujahr();
            summeAlter += alter;
        }
        return ((double) summeAlter / anzahlGesamt);
    }
    
    
    
    
    
    // Die Suchen Methode
    public int searchZugN(int nummer) {
        try {
            
            String selectSQL = "SELECT * FROM info WHERE ID = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL)) {
                preparedStatement.setInt(1, nummer);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return nummer;

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }

    public Zeuge searchZug(int nummer) {
        try {
            
            String selectSQL = "SELECT * FROM info WHERE ID = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL)) {
                preparedStatement.setInt(1, nummer);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        
                        int idnummer = resultSet.getInt(1);
                        String zugtyp = resultSet.getString(2);
                        String modell = resultSet.getString(3);
                        int laufleistung = resultSet.getInt(4);
                        int baujahr = resultSet.getInt(5);
                        double preis = resultSet.getDouble(6);
                        boolean unfallwagen = resultSet.getBoolean(7);

                        int schlafplaetze = resultSet.getInt(8);
                        int leanderZahl = resultSet.getInt(9);

                        if (schlafplaetze >= 0) {
                            return new RE(idnummer, zugtyp, modell, laufleistung, baujahr, preis, unfallwagen,schlafplaetze);
                        } else if (leanderZahl >= 0) {
                            return new ICE(idnummer, zugtyp, modell, laufleistung, baujahr, preis, unfallwagen, leanderZahl);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    
    
    
    
    
    
    // Die Zuge aktualisieren
    public void updatePreis(int nummer, double newPreis) {
        if (searchZugN(nummer) != -1) {
            try {
                
                String updateSQL = "UPDATE info SET Preis = ? WHERE ID = ?";

                try (PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL)) {
                    preparedStatement.setDouble(1, newPreis);
                    preparedStatement.setInt(2, nummer);
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Das Züg mit der Nummer " + nummer + " ist nicht vorhanden");
        }
    }

    public void updateLinie(int nummer, String newLinie) {
        if (searchZugN(nummer) != -1) {
            try {
                
                String updateSQL = "UPDATE info SET Zuglinie = ? WHERE ID = ?";

                try (PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL)) {
                    preparedStatement.setString(1, newLinie);
                    preparedStatement.setInt(2, nummer);
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException( "Das Züg mit der Nummer " + nummer + " ist nicht vorhanden");
        }
    }

    public void updateLaufleistung(int nummer, int newLauf) {
        if (searchZugN(nummer) != -1) {
            try {
                
                String updateSQL = "UPDATE info SET Laufleistung = ? WHERE ID = ?";

                try (PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL)) {
                    preparedStatement.setInt(1, newLauf);
                    preparedStatement.setInt(2, nummer);
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Das Züg mit der Nummer " + nummer + " ist nicht vorhanden");
        }
    }

    public void updateUnfall(int nummer, boolean newUnfall) {
        if (searchZugN(nummer) != -1) {
            try {
                
                String updateSQL = "UPDATE info SET Unfall = ? WHERE ID = ?";

                try (PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL)) {
                    preparedStatement.setBoolean(1, newUnfall);
                    preparedStatement.setInt(2, nummer);
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException( "Das Züg mit der Nummer " + nummer + " ist nicht vorhanden");
        }
    }

}
