import StævneTræningsTider.Disciplin;
import StævneTræningsTider.StævneTid;
import StævneTræningsTider.TidsLister;
import StævneTræningsTider.TræningsTid;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class FileHandler {

    private MedlemsListe medlemsListe; // Medlemslisten, hvor medlemmerne bliver gemt
    private TidsLister tidsLister;
    private String medlemmersInformationerFil = "src/Filer/medlemmersInformationer";
    private String stævneTiderFil ="src/Filer/stævneTider";
    private String træningsTiderFil = "src/Filer/træningsTider";
    int highestID = 0; // Holder styr op det højeste ID i filen
    int linjeNummer = 0; // Tæller linjer i filen for at finde fejl
    int stævneLinjeNummer = 0;
    int træningLinjeNummer = 0;


    // Kontruktør, der modtager medlemslisten
    public FileHandler(MedlemsListe medlemsListe, TidsLister tidsLister) {
        this.medlemsListe = medlemsListe;
        this.tidsLister = tidsLister;

    }

    // Metode til at læse filen for medlemmers informationer
    public void medlemmersInformationerReadFile(){
        try{
            FileReader file = new FileReader(medlemmersInformationerFil); // Opretter en FileReader til at læse filen
            Scanner sc = new Scanner(file); // Scanner til at læse linjerne i filen

            // Læs hver linje i filen
            while(sc.hasNextLine()) {
                linjeNummer++; // Øger linjeNummeret for hver linje der læses
                String linje = sc.nextLine().trim(); // Trimmer filen
                if (linje.isEmpty()) {
                    continue; // Springer tomme linjer over
                }

                String[] data = linje.split(";");
                if (data.length >= 9) {
                    try {

                        // Hvis der er et ID i filen, skal det bruges, ellers få det næste ID
                        int memberID;
                        if (!data[0].isEmpty()) {
                            memberID = Integer.parseInt(data[0].trim());
                        } else {
                            memberID = Medlem.getNextID();
                        }

                        // Trim værdierne og parse data
                        String navn = data[1].trim();
                        int alder = Integer.parseInt(data[2].trim());
                        boolean medlemsType = Boolean.parseBoolean(data[3].trim());
                        String email = data[4].trim();
                        int år = Integer.parseInt(data[5].trim());
                        int måned = Integer.parseInt(data[6].trim());
                        int dag = Integer.parseInt(data[7].trim());
                        String typeSvømmer = data[8].trim();

                        // Opdater højeste ID, hvis dette medlem har et højere ID end tidligere
                        highestID = Math.max(highestID, memberID);

                        // Sender data til Medlemsliste
                        medlemsListe.addMedlem(navn, alder, medlemsType, email, år, måned, dag, typeSvømmer);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipper linje da der er formatteringsfejl på linje(" + linjeNummer + "): " + linje);
                    }

                }
            }
            // Efter filen er læst, opdater Medlem.nextID så næste medlem får det rette ID
            Medlem.setNextID(highestID + 1);
            sc.close();

        } catch(FileNotFoundException e) {
            System.out.println("Kunne ikke finde fil" + e.getMessage());
        }
    }

    // Metode til at skrive medlemmers informationer til filen
    public void medlemmersInformationerWriteToFile(){
        try {
            PrintStream filSkriver = new PrintStream(medlemmersInformationerFil);

            // Gennemgår alle medlemmer i medlemslisten og skriver dem til filen
            for (Medlem i : medlemsListe.getMedlemmer()) {
                filSkriver.println(i.getMemberID() + "; " + i.getName() + "; " + i.getAlder() + "; " + i.getAktiv() + "; " + i.getEmail() + "; " + i.getDatoForSidstBetaltKontigent() + "; " + i.getTypeSvømmer());
            }
            filSkriver.close();

        } catch(Exception e){
            System.out.println("Kunne ikke skrive til fil");
        }
    }






    //indlæser informationer fra den lokale fil, og instancere objekter af StævneTid i stævneTid arraylisten, som består af disse objekter
    public void stævneTidReadFile(){
        try{
            FileReader fileStævne = new FileReader(stævneTiderFil); // Opretter en FileReader til at læse filen
            Scanner scStævne = new Scanner(fileStævne); // Scanner til at læse linjerne i filen

            // Læs hver linje i filen
            while(scStævne.hasNextLine()) {
                stævneLinjeNummer++; // Øger linjeNummeret for hver linje der læses
                String linje = scStævne.nextLine().trim(); // Trimmer filen
                if (linje.isEmpty()) {
                    continue; // Springer tomme linjer over
                }

                String[] data = linje.split(";");
                if (data.length >= 9) {
                    try {

                        // Trim værdierne og parse data

                        String medlemNavn = data[0].trim();
                        String aldersGruppe = data[1].trim();
                        String stævneNavn = data[2].trim();
                        Disciplin stævneDisiplin = Disciplin.valueOf(data[3].trim().toUpperCase());
                        int stævnePlacering = Integer.parseInt(data[4].trim());
                        double stævneTid = Double.parseDouble(data[5].trim());
                        int stævneÅr = Integer.parseInt(data[6].trim());
                        int stævneMåned = Integer.parseInt(data[7].trim());
                        int stævneDag = Integer.parseInt(data[8].trim());


                        // Sender data til Medlemsliste
                        tidsLister.addStævneTid(medlemNavn, aldersGruppe, stævneNavn, stævneDisiplin, stævnePlacering, stævneTid, stævneÅr, stævneMåned, stævneDag);

                    } catch (NumberFormatException e) {
                        System.out.println("Skipper linje da der er formatteringsfejl på linje(" + stævneLinjeNummer + "): " + linje);
                    }

                }
            }

            scStævne.close();

        } catch(FileNotFoundException e) {
            System.out.println("Kunne ikke finde fil" + e.getMessage());
        }

    }
    //gemmer informationer i stævneTid Arraylisten i en lokal fil
    public void stævneTidWriteToFile(){
        try {
            PrintStream stævneFilSkriver = new PrintStream(stævneTiderFil);

            for(StævneTid i : tidsLister.getStævneTider()){
                stævneFilSkriver.println(i.getMedlemsNavn() + "; " + i.getAldersGruppe() + "; " + i.getStævneNavn() + "; " + i.getDisciplin() + "; " + i.getPlacering() + "; " + i.getTid() + "; " + i.getStævneDato());
            }
            stævneFilSkriver.close();

        } catch(FileNotFoundException e){
            System.out.println("Kunne ikke skrive til fillen");
        }
    }




    //indlæser informationer fra den lokale fil, og instancere objekter af TræningsTid i træningsTid arraylisten, som består af disse objekter
    public void træningsTidReadFile() {
        try{
            FileReader træningfile = new FileReader(træningsTiderFil); // Opretter en FileReader til at læse filen
            Scanner scTræning = new Scanner(træningfile); // Scanner til at læse linjerne i filen

            // Læs hver linje i filen
            while(scTræning.hasNextLine()) {
                træningLinjeNummer++; // Øger linjeNummeret for hver linje der læses
                String linje = scTræning.nextLine().trim(); // Trimmer filen
                if (linje.isEmpty()) {
                    continue; // Springer tomme linjer over
                }

                String[] data = linje.split(";");
                if (data.length >= 7) {
                    try {


                        String træningNavn = data[0].trim();
                        String aldersGruppe = data[1].trim();
                        double træningTid = Double.parseDouble(data[2].trim());
                        Disciplin træningDisiplin = Disciplin.valueOf(data[3].trim().toUpperCase());
                        int træningÅr = Integer.parseInt(data[4].trim());
                        int træningMåned = Integer.parseInt(data[5].trim());
                        int træningDag = Integer.parseInt(data[6].trim());

                        tidsLister.addTræningsTid(træningNavn, aldersGruppe, træningTid, træningDisiplin, træningÅr, træningMåned, træningDag);


                    } catch (NumberFormatException e) {
                        System.out.println("Skipper linje da der er formatteringsfejl på linje(" + træningLinjeNummer + "): " + linje);
                    }

                }
            }
            scTræning.close();

        } catch(FileNotFoundException e) {
            System.out.println("Kunne ikke finde fil" + e.getMessage());
        }
    }


    //gemmer informationer i træningsTid Arraylisten i en lokal fil
    public void træningsTidWriteToFile(){
        try{
            PrintStream træningsFilSkriver = new PrintStream(træningsTiderFil);
            for(TræningsTid i: tidsLister.getTræningsTider()){
                træningsFilSkriver.println(i.getMedlemsNavn() + "; " + i.getAldersGruppe() + "; " + i.getTid() + "; " + i.getDisciplin() + "; " + i.getTræningDato());
            }

        } catch (FileNotFoundException e) {
            System.out.println("kunne ikke skrive til filen");
        }
    }


}

