import StævneTræningsTider.Disciplin;
import StævneTræningsTider.TidsLister;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private MedlemsListe medlemsListe;
    private TidsLister tidsLister;
    Scanner sc = new Scanner(System.in);
    FileHandler fileHandler;
    LocalDate iDag = LocalDate.now();



    public UI(MedlemsListe medlemsListe, TidsLister tidsLister) {
        this.medlemsListe = medlemsListe;
        this.tidsLister = tidsLister;
        this.fileHandler = new FileHandler(medlemsListe, tidsLister);

    }

    public void menu() {
        String valg;
        //når programmet starter, så bliver de tre lokale filer indlæst og instanceret i programmet
        fileHandler.medlemmersInformationerReadFile();
        fileHandler.stævneTidReadFile();
        fileHandler.træningsTidReadFile();

        do {
            System.out.println("\u001B[38m");
            System.out.println("1. Opret nyt medlem");
            System.out.println("2. Vis medlemmer");
            System.out.println("3. Beregn samlet kontingent");
            System.out.println("4. Registrer svømmetid");
            System.out.println("5. Vis træning/stævne tider");
            System.out.println("6. Rediger medlem");
            System.out.println("7. Afslut program");
            System.out.println("Vælg en funktion");
            valg = sc.nextLine();

            switch (valg) {
                case "1":
                    opretMedlem();
                    break;
                case "2":
                    visForskelligeMedlemmer();
                    break;
                case "3":
                    beregnSamletKontingent();
                    break;
                case "4":
                    registrerTid();
                    break;
                case "5":
                    visTider();
                    break;
                case "6":
                    redigerMedlem();
                    break;
                case "7":
                    System.out.println("Systemet afsluttes");
                    break;
                default:
                    System.out.println("Ugyldigt valg. Vælg igen");
            }
        } while (!valg.equals("7"));

        sc.close();
    }
    private void visMedlemsMuligheder() {
        System.out.println("\u001B[38m");
        System.out.println("\nHvilken type medlemmer ønsker du at se:" +
                "\n1.Vis alle medlemmer" +
                "\n2.Vis alle medlemmer i restance" +
                "\n3.Vis alle medlemmer der ikke er i restance" +
                "\n4.Vis alle senior konkurrencesvømmere" +
                "\n5.Vis alle junior konkurrencesvømmere" +
                "\n6.Gå tilbage til hovedmenuen");
    }

    //metode til at visse alle eller kun en gruppe af medlemmer, såsom dem der i restance
    private void visForskelligeMedlemmer() {
        boolean medlemLoop = true;
        while (medlemLoop) {
            visMedlemsMuligheder();
            Scanner scMedlem = new Scanner(System.in);
            String scMedlemInput = scMedlem.nextLine();
            if (scMedlemInput.equalsIgnoreCase("1")) {
                medlemsListe.visMedlemmer();
            } else if (scMedlemInput.equalsIgnoreCase("2")) {
                medlemsListe.printRestanceMedlemmer();
            } else if (scMedlemInput.equalsIgnoreCase("3")) {
                medlemsListe.printIkkeRestanceMedlemmer();
            } else if (scMedlemInput.equalsIgnoreCase("4")) {
                medlemsListe.printSeniorKonkurrencesvømmere();
            } else if (scMedlemInput.equalsIgnoreCase("5")) {
                medlemsListe.printJuniorKonkurrencesvømmere();
            } else if (scMedlemInput.equalsIgnoreCase("6")) {
                medlemLoop = false;
            } else {
                System.out.println("Du skulle vælge et tal mellem 1-6!");
            }
        }
    }

    //metode der modtager inputs fra brugeren og efterfølgende instancere er medlem i "MedlemsListe" klassen
    private void opretMedlem() {
        System.out.println("\nHvad er navnet?");
        //sc.nextLine(); //Fjerner inputBuffer
        String navn = sc.nextLine();

        System.out.println("\nHvor gammel er personen? ");
        int alder = sc.nextInt();

        System.out.println("Er personen aktiv eller ej? (Ja/Nej)");
        boolean medlemsType = false; // default værdi

        // Læser brugers input og konvertere til boolean
        boolean validInput = true;
        while (validInput) {
            String input = sc.next().toLowerCase();
            if (input.equals("ja")) {
                medlemsType = true;
                validInput = false;
            } else if (input.equals("nej")) {
                medlemsType = false;
                validInput = false;
            } else {
                System.out.println("Ugyldigt input. Skriv venlist 'Ja' eller 'Nej'.");
            }
        }

        sc.nextLine(); //Fjerner inputBuffer
        System.out.println("Hvad er personens email?");
        String email = sc.nextLine();


        boolean typeLoop = true;
        String typeSvømmer = "";
        while(typeLoop) {
            System.out.println("\nEr personen motionist eller konkurrence svømmer?");
            System.out.println("Skriv 1 for for motionist eller 2 for konkurrence svømmer");

            String medlemsTypeInput = sc.nextLine();
            if (medlemsTypeInput.equalsIgnoreCase("1")) {
                typeSvømmer = "Motionist";
                typeLoop = false;
            } else if (medlemsTypeInput.equalsIgnoreCase("2")) {
                typeSvømmer = "Konkurrencesvømmer";
                typeLoop = false;
            } else {
                System.out.println("Du skulle skrive 1 eller 2!");
            }
        }

        int år = iDag.getYear();
        int måned = iDag.getMonthValue();
        int dag = iDag.getDayOfMonth();

        //Medlemmet bliver tilføjet til systemet samt gemt i en lokal fil
        medlemsListe.addMedlem(navn, alder, medlemsType, email, år, måned, dag, typeSvømmer);
        fileHandler.medlemmersInformationerWriteToFile(); // Opdatere filen med ny data
        System.out.println(navn + " er blevet tilføjet til medlemslisten :)");
    }


    //Finder et eller flere medlemmer ud fra et navn angivet af brugeren
    private Medlem findMedlem() {
        if(medlemsListe.getMedlemmer().isEmpty()){
            System.out.println("Der er ingen medlemmer i systemet");
        }
        System.out.println("\nIndtast navnet eller en del af navnet på det medlem, du leder efter:");
        String søgNavn = sc.nextLine().toLowerCase();

        // Finder medlemmer med det indtastede navn eller del af navnet
        ArrayList<Medlem> medlemmer = medlemsListe.søgMedlemmerVedDelvistNavn(søgNavn);

        if (medlemmer.isEmpty()) {
            System.out.println("Ingen medlemmer med navnet: " + søgNavn + " blev fundet.");
            return null;

            // Hvis der kun er et medlem med navnet
        } else if (medlemmer.size() == 1) {
            System.out.println("Medlem fundet: ");
            System.out.println(medlemmer.get(0).toString());
            return medlemmer.get(0);

        } else {
            System.out.println("Flere medlemmer med navnet: " + søgNavn + " blev fundet.");
            for (Medlem medlem : medlemmer) {
                System.out.println(medlem.toString());
            }
            System.out.println("\u001B[38m"); // Gør så den får hvid som farve

            System.out.println("Indtast ID nummeret på det medlem du vil redigere.");

            int valgID = Integer.parseInt(sc.nextLine());
            Medlem valgtMedlem = medlemsListe.søgMedlemVedID(valgID);

            if (valgtMedlem != null) {
                System.out.println("Du har valgt dette medlem");
                System.out.println(valgtMedlem);
                return valgtMedlem;

            } else {
                System.out.println("Intet medlem med ID: " + valgID + " blev fundet.");
                return null;
            }
        }
    }

    //Metode til at kunne redigere et medlems informationer, inputs fra brugeren bliver gemt i UI og
    // sendt til "MedlemsListe" klassen, hvor ændringerne sker
    private void redigerMedlem() {
        Medlem medlem = findMedlem();
        System.out.println("\u001B[38m"); // Gør så den får hvid som farve

        String nytNavn = medlem.getName();
        int nyAlder = medlem.getAlder();
        boolean nytMedlemsType = medlem.getAktiv();
        String nyEmail = medlem.getEmail();
        String nyTypeSvømmer = medlem.getTypeSvømmer();

        int valg;

        do {
            System.out.println("Rediger medlemsoplysninger for: " + medlem.getName());
            System.out.println("Vælg, hvad du vil ændre:");
            System.out.println("1. Navn");
            System.out.println("2. Alder");
            System.out.println("3. Medlemskabstype (True/False)");
            System.out.println("4. Email");
            System.out.println("5. Type af svømmer");
            System.out.println("6. Afslut redigering");
            valg = Integer.parseInt(sc.nextLine());


            // Spørg brugeren om de nye oplysninger
            switch (valg) {
                case 1:
                    System.out.println("Indtast nyt navn:");
                    nytNavn = sc.nextLine().trim();
                    break;

                case 2:
                    System.out.println("Indtast ny alder:");
                    try {
                        nyAlder = Integer.parseInt(sc.nextLine());


                    } catch (InputMismatchException e) {
                        System.out.println("Ugyldigt input. Indtast et heltal.");
                        sc.nextLine();
                    }
                    break;

                case 3:
                    System.out.println("Indtast nyt medlemskabstype (true/false):");
                    try {
                        nytMedlemsType = Boolean.parseBoolean(sc.nextLine());


                    } catch (InputMismatchException e) {
                        System.out.println("Ugyldigt input. Indtast enten true eller false.");
                        sc.nextLine();
                    }
                    break;

                case 4:
                    System.out.println("Indtast ny email:");
                    nyEmail = sc.nextLine();
                    break;

                case 5:
                    boolean typeLoop = true;
                    while(typeLoop) {
                        System.out.println("Skriv 1 for for motionist eller 2 for konkurrence svømmer");

                        String medlemsTypeInput = sc.nextLine();
                        if (medlemsTypeInput.equalsIgnoreCase("1")) {
                            nyTypeSvømmer = "Motionist";
                            typeLoop = false;
                        } else if (medlemsTypeInput.equalsIgnoreCase("2")) {
                            nyTypeSvømmer = "Konkurrencesvømmer";
                            typeLoop = false;
                        } else {
                            System.out.println("Du skulle skrive 1 eller 2!");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Afslutter redigering.");
                    break;

                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }

            // Sender data til metoden redigerMedlem i MedlemsListen
            medlem.setPrisKontigent(medlem.getAlder(), medlem.getAktiv()); //beregner den nye pris for medlemmet, ud fra alder og aktiv status
            medlemsListe.redigerMedlem(medlem.getMemberID(), nytNavn, nyAlder, nytMedlemsType, nyEmail, nyTypeSvømmer);
            fileHandler.medlemmersInformationerWriteToFile();
            System.out.println("\u001B[38m"); // Gør så den får hvid som farve
            System.out.println("Ændringer gemt.");

        } while (valg != 5);
        fileHandler.medlemmersInformationerWriteToFile();
    }


    private void beregnSamletKontingent() {
        medlemsListe.beregnSamletKontigent();
    }

    private void visTider() {
        System.out.println("1.Top 5 tider" +
                "\n2.Alle tider");
        String tiderInput = sc.nextLine();
        if(tiderInput.equalsIgnoreCase("1")){
            visTop5();
        } else if(tiderInput.equalsIgnoreCase("2")){
            tidsLister.visTider();
        } else {
            System.out.println("Du skulle skrive 1 eller 2!");
        }
    }


    private void registrerTid() {
        Medlem medlem = findMedlem();
        System.out.println("\u001B[38m"); // Gør så den får hvid som farve

        int valg;
        if (medlem != null) {
            System.out.println("Er det en træning eller stævne?");
            System.out.println("1. Træning");
            System.out.println("2. Stævne");
            valg = Integer.parseInt(sc.nextLine());

            switch (valg) {
                case 1:
                    indtastTræningsTid(medlem.getName(), medlem.getAldersGruppe());
                    break;
                case 2:
                    indtastStævneTid(medlem.getName(), medlem.getAldersGruppe());
                    break;
                default:
                    System.out.println("Ugyldigt valg.");
            }
        }
    }
    private void indtastTræningsTid(String medlemsNavn, String aldersGruppe) {
        Disciplin disciplin = null;
        boolean valg = true;

        while (valg) {
            System.out.println("Hvilken disciplin? (Crawl, rygcrawl, butterfly, brystsvømning)");
            String disciplinValg = sc.nextLine();

            if (disciplinValg.equalsIgnoreCase("Crawl")) {
                disciplin = Disciplin.CRAWL;
                valg = false;
            } else if (disciplinValg.equalsIgnoreCase("Rygcrawl")) {
                disciplin = Disciplin.RYGCRAWL;
                valg = false;
            } else if (disciplinValg.equalsIgnoreCase("Butterfly")) {
                disciplin = Disciplin.BUTTERFLY;
                valg = false;
            } else if (disciplinValg.equalsIgnoreCase("Brystsvømning")) {
                disciplin = Disciplin.BRYSTSVØMNING;
                valg = false;
            } else {
                System.out.println("Ugyldigt input. Prøv igen.");
            }
        }

        System.out.println("Indtast tid");
        double tid = Double.parseDouble(sc.nextLine());

        System.out.println("Skriv år for træning");
        int år = Integer.parseInt(sc.nextLine());

        System.out.println("Skriv måned for træning");
        int måned = Integer.parseInt(sc.nextLine());

        System.out.println("Skriv dato for træning");
        int dag = Integer.parseInt(sc.nextLine());

        // Tilføj træningstid til listen
        tidsLister.addTræningsTid(medlemsNavn, aldersGruppe, tid, disciplin, år, måned, dag);
        fileHandler.træningsTidWriteToFile();
    }

    private void indtastStævneTid(String medlemsNavn, String aldersGruppe) {
        System.out.println("Indtast stævnets navn:");
        String stævneNavn = sc.nextLine();

        Disciplin disciplin = null;
        boolean valg = true;

        while (valg) {
            System.out.println("Hvilken disciplin? (Crawl, rygcrawl, butterfly, brystsvømning)");
            String disciplinValg = sc.nextLine();

            if (disciplinValg.equalsIgnoreCase("Crawl")) {
                disciplin = Disciplin.CRAWL;
                valg = false;
            } else if (disciplinValg.equalsIgnoreCase("Rygcrawl")) {
                disciplin = Disciplin.RYGCRAWL;
                valg = false;
            } else if (disciplinValg.equalsIgnoreCase("Butterfly")) {
                disciplin = Disciplin.BUTTERFLY;
                valg = false;
            } else if (disciplinValg.equalsIgnoreCase("Brystsvømning")) {
                disciplin = Disciplin.BRYSTSVØMNING;
                valg = false;
            } else {
                System.out.println("Ugyldigt input. Prøv igen.");
            }
        }


        System.out.println("Indtast placering");
        int placering = Integer.parseInt(sc.nextLine());

        System.out.println("Indtast tid");
        double tid = Double.parseDouble(sc.nextLine());

        System.out.println("Skriv år for stævne");
        int år = Integer.parseInt(sc.nextLine());

        System.out.println("Skriv måned for stævne");
        int måned = Integer.parseInt(sc.nextLine());

        System.out.println("Skriv dato for stævne");
        int dag = Integer.parseInt(sc.nextLine());

        // Tilføj stævnetid til listen
        tidsLister.addStævneTid(medlemsNavn, aldersGruppe, stævneNavn, disciplin, placering, tid, år, måned, dag);
        fileHandler.stævneTidWriteToFile();
    }


    private void visTop5() { //metoden visser top 5 hurtigste svømmere, sorteret ud fra senior/junior, træning/stævne og disciplin.
        boolean junSenLoop = true;
        boolean trænStævneLoop = true;
        String aldersValg = "";
        while(junSenLoop) {
            System.out.println("Vil du se top 5 svømmere for junior eller senior svømmere?");
            System.out.println("1.for junior" +
                    "\n2.for senior");
            String junSenValg = sc.nextLine();
            if (junSenValg.equalsIgnoreCase("1")) {
                aldersValg = "Junior";
                junSenLoop = false;
            } else if (junSenValg.equalsIgnoreCase("2")) {
                aldersValg = "Senior";
                junSenLoop = false;
            } else {
                System.out.println("Du skulle skrive 1 eller 2!");
            }
        }
        System.out.println("Vil du set trænings tider eller stævne tider?");
        System.out.println("1.for stævne" +
                "\n2.for træning");
        String træningStævneValg = sc.nextLine();

        while(trænStævneLoop) {
            if (træningStævneValg.equalsIgnoreCase("1")) {
                System.out.println("Vælg en discplin (crawl, rygcrawl, butterfly, brystsvømning):");
                String scDisciplinInput = sc.nextLine();

                Disciplin disciplin = tidsLister.hentDisciplin(scDisciplinInput);
                if(disciplin == null){
                    System.out.println("Du skrev ikke en af de fire muligheder!");
                } else {
                    tidsLister.visTop5Stævnetider(disciplin, aldersValg);
                    trænStævneLoop = false;
                }

            } else if (træningStævneValg.equalsIgnoreCase("2")) {
                System.out.println("Vælg en discplin (crawl, rygcrawl, butterfly, brystsvømning):");
                String scDisciplinInput = sc.nextLine();

                Disciplin disciplin = tidsLister.hentDisciplin(scDisciplinInput);
                if(disciplin == null){
                    System.out.println("Du skrev ikke en af de fire muligheder!");
                } else {
                    tidsLister.visTop5Træningstider(disciplin, aldersValg);
                    trænStævneLoop = false;
                }

            } else {
                System.out.println("Du skulle skrive 1 eller 2!");
            }
        }
    }
}
