package com.zugverwaltung.Zugverwaltung;


import java.util.Scanner;

public class Programm {

    public static void main(String[] args) {
        Zeugeverwaltung zeugenVerwaltung = new Zeugeverwaltung();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Neue Zug hinzufügen");
                System.out.println("2. Zug löschen");
                System.out.println("3. Zug suchen");
                System.out.println("4. Alle Züge anzeigen");
                System.out.println("5. Der Anteil der UnfallZüge");
                System.out.println("6. Das durchschnittliche Alter der Züge");
                System.out.println("7. Der Anteil der ICE 3neo beträgt");
                System.out.println("8. Der beliebteste Zugtyp");
                System.out.println("9. Die Zuge aktualisieren ");
                System.out.println("0. Programm beenden");

                System.out.print("Bitte wählen Sie eine Option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:

                        // Neue Zug hinzufügen
                        while (true) {
                            System.out.print("Geben Sie die Zugnummer ein: ");
                            int zugNummer = scanner.nextInt();
                            scanner.nextLine();
                            if (zugNummer < 0) {
                                System.out.println(
                                        "Die Zugnummer darf nicht negativ sein. Bitte geben Sie eine gültige Zugnummer ein.");
                                continue;
                            }

                            System.out.print("Geben Sie den Zugtyp ein: ");
                            String zugtyp = scanner.nextLine();
                            if (!zugtyp.matches("[a-zA-Z]+")) {
                                System.out.println(
                                        "Ungültiger Zugtyp. Bitte geben Sie einen gültigen Zeichenfolgenwert ein.");
                                continue;
                            }

                            System.out.print("Geben Sie die zuglinie ein: ");
                            String zuglinie = scanner.nextLine();
                            if (!zuglinie.matches("[a-zA-Z]+")) {
                                System.out.println(
                                        "Ungültiger Zuglinie. Bitte geben Sie einen gültigen Zeichenfolgenwert ein.");
                                continue;
                            }
                            System.out.print("Geben Sie die Laufleistung ein: ");
                            int laufleistung = scanner.nextInt();
                            scanner.nextLine();
                            if (laufleistung < 0) {
                                System.out.println(
                                        "Die LeufLeistung darf nicht negativ sein. Bitte geben Sie eine gültige LeufLeistung ein.");
                                continue;
                            }

                            System.out.print("Geben Sie das Baujahr ein: ");
                            int baujahr = scanner.nextInt();
                            scanner.nextLine();
                            if (baujahr < 0) {
                                System.out.println(
                                        "Die Bau Baujahr darf nicht negativ sein. Bitte geben Sie eine gültige Baujahr ein.");
                                continue;
                            }

                            System.out.print("Geben Sie den Ticket Preis ein: ");
                            double preis = scanner.nextDouble();
                            scanner.nextLine();
                            if (preis < 0) {
                                System.out.println(
                                        "Die Preis darf nicht negativ sein. Bitte geben Sie eine gültige Preis ein.");
                                continue;
                            }

                            System.out.print("Ist es ein Unfallwagen? (ja/nein): ");
                            String unfall = scanner.next();
                            boolean unfallwagen;

                            scanner.nextLine();
                            if (unfall.equals("ja")) {
                                unfallwagen = true;
                            } else if (unfall.equals("nein")) {
                                unfallwagen = false;
                            } else {
                                System.out.println("Ungültige Eingabe. Bitte nur 'Ja' oder 'Nein' eingeben.");
                                continue;
                            }

                            while (true) {
                                System.out.print("Geben Sie den Fahrzeugtyp ein (ICE/RE): ");
                                String fahrzeugTyp = scanner.nextLine();

                                // Hier wird überprüft, ob es sich um einen ICE handelt
                                Zeuge neuerZug;
                                if ("RE".equalsIgnoreCase(fahrzeugTyp)) {
                                    System.out.print("Geben Sie die Anzahl der Schlafplätze ein: ");
                                    int schlafplaetze = scanner.nextInt();
                                    neuerZug = new RE(zugNummer, zugtyp, zuglinie, laufleistung, baujahr, preis,
                                            unfallwagen, schlafplaetze);
                                } else if ("ICE".equalsIgnoreCase(fahrzeugTyp)) {
                                    System.out.print("Geben Sie die Leander-Zahl ein: ");
                                    int leanderZahl = scanner.nextInt();
                                    neuerZug = new ICE(zugNummer, zugtyp, zuglinie, laufleistung, baujahr, preis,
                                            unfallwagen, leanderZahl);
                                } else {
                                    System.out.println("Ungültiger Zeugtyp. Bitte wählen Sie zwischen ICE und RE.");
                                    continue;
                                }

                                zeugenVerwaltung.aufnehmen(neuerZug);
                                System.out.println("Zug erfolgreich hinzugefügt!");
                                break;
                            }

                            break;
                        }
                        break;

                    case 2:
                        // Zug löschen
                        System.out.print("Geben Sie die Zugnummer zum Löschen ein: ");
                        int zuLoeschendeNummer = scanner.nextInt();
                        boolean loeschErfolg = zeugenVerwaltung.loeschen(zuLoeschendeNummer);
                        if (loeschErfolg) {
                            System.out.println("Zug erfolgreich gelöscht!");
                        } else {
                            System.out.println("Zug nicht gefunden oder konnte nicht gelöscht werden.");
                        }
                        break;

                    case 3:
                        // Zug suchen
                        System.out.print("Geben Sie die Zugnummer zum Suchen ein: ");
                        int zuSuchendeNummer = scanner.nextInt();
                        Zeuge gefundenerZug = zeugenVerwaltung.searchZug(zuSuchendeNummer);
                        if (gefundenerZug != null) {
                            System.out.println("Gefundener Zug: " + gefundenerZug.getInfo());
                        } else {
                            System.out.println("Zug nicht gefunden.");
                        }
                        break;
                    case 4:
                        // Alle Züge anzeigen
                        zeugenVerwaltung.ausgeben();
                        break;
                    case 5:
                        // Alle Züge anzeigen
                        System.out.println(
                                "Der Anteil der UnfallZüge beträgt " + zeugenVerwaltung.getAnteilUnfallwagen() + " %");
                        break;
                    case 6:
                        // Alle Züge anzeigen
                        System.out.println("Das durchschnittliche Alter der Züge beträgt "
                                + zeugenVerwaltung.getDurchschnittsalter() + " Jahre");
                        break;
                    case 7:
                        // Alle Züge anzeigen
                        System.out.println("Der Anteil der ICE 3neo beträgt " + zeugenVerwaltung.getAnteilICE() + " %");
                        break;
                    case 8:
                        // Alle Züge anzeigen
                        System.out.println("Der beliebteste Zugtyp ist " + zeugenVerwaltung.getBeliebtesterZugtyp());
                        break;
                    case 9:

                        // Benutzer nach der ID des Zeugs fragen
                        System.out.print("Geben Sie die ID des Zügs ein, das Sie aktualisieren möchten: ");
                        int zeugId = scanner.nextInt();
                        if (zeugenVerwaltung.searchZug(zeugId) != null) {
                            Zeuge zeug1 = zeugenVerwaltung.searchZug(zeugId);
                            System.out.println("die Verfügbara Zug: " + zeug1.getInfo());

                            // Benutzer nach den zu aktualisierenden Werten fragen
                            System.out.println("Welche Werte möchten Sie aktualisieren?");
                            System.out.println("1. Zuglinie");
                            System.out.println("2. Laufleistung");
                            System.out.println("3. Preis");
                            System.out.println("4. Unfallwagen");

                            System.out.print(
                                    "Geben Sie die Nummern der Werte ein, die Sie aktualisieren möchten : ");
                            int option1 = scanner.nextInt();
        

                            // Aktualisierungswerte setzen

                            switch (option1) {
                                case 1:
                                    System.out.print("Neue Zuglinie: ");
                                    zeugenVerwaltung.updateLinie(zeugId, scanner.next());
                                   
                                    break;
                                case 2:
                                    System.out.print("Neue Laufleistung: ");
                                    zeugenVerwaltung.updateLaufleistung(zeugId, scanner.nextInt());
                                    break;
                                case 3:
                                    System.out.print("Neuer Preis: ");
                                    zeugenVerwaltung.updatePreis(zeugId, scanner.nextDouble());
                                    break;
                                case 4:
                                    System.out.print("Ist es ein Unfallwagen? (true/false): ");
                                    zeugenVerwaltung.updateUnfall(zeugId, scanner.nextBoolean());
                                    break;
                                default:
                                    System.out.println("Ungültige Eingabe.");
                                    break;
                            }

                            Zeuge zeug = zeugenVerwaltung.searchZug(zeugId);
                            System.out.println("Zeug erfolgreich aktualisiert: " + zeug.getInfo());
                        } else {
                            System.out.println("Das Züg mit der Nummer " + zeugId + " ist nicht vorhanden");

                        }

                        break;

                    case 0:
                        // Programm beenden
                        System.out.println("Programm wird beendet.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Ungültige Option. Bitte wählen Sie erneut.");
                        break;
                }
            }
        }
        
    }
    
}
