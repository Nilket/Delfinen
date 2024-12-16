import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Medlem {
    private static int nextID = 1;
    private int memberID;
    private String navn;
    private int alder;
    private boolean aktiv;
    private String typeSvømmer;
    private String email;
    private double prisKontigent;
    private LocalDate datoForSidstBetaltKontigent;
    private boolean restance;
    private String restanceFarve;
    private String aldersGruppe;

    //Kontruktøren for medlems klassen
    public Medlem(String navn, int alder, boolean aktiv, String email, int år, int måned, int dag, String typeSvømmer) {
        this.memberID = nextID;
        nextID ++;
        this.navn = navn;
        this.alder = alder;
        this.email = email;
        this.aktiv = aktiv;
        this.typeSvømmer = typeSvømmer;
        this.prisKontigent = beregnPrisKontigent();
        this.datoForSidstBetaltKontigent = LocalDate.of(år, måned, dag);
        this.restance = beregnRestance();
        this.restanceFarve = restanceChecker();
        this.aldersGruppe = calcAlder();
    }

    // Getters
    public int getMemberID() {
        return memberID;
    }
    public String getName() {
        return navn;
    }
    public int getAlder() {
        return alder;
    }
    public String getEmail() {
        return email;
    }
    public boolean getAktiv() {
        return aktiv;
    }
    public static int getNextID() {
        return nextID;
    }
    public boolean getRestance() {
        return restance;
    }
    public double getPrisKontigent() {
        return prisKontigent;
    }
    public String getDatoForSidstBetaltKontigent() { //metode der laver den sidst betalte dato om til en string
        // så den kan skrives til en lokal fil
        String dato = datoForSidstBetaltKontigent.toString();
        String [] tekstDato = dato.split("-");
        String done = "";
        done += tekstDato[0];
        done +=";" + tekstDato[1];
        done +=";" + tekstDato[2];
        return done;
    }
    public String getTypeSvømmer(){
        return typeSvømmer;
    }
    public String getAldersGruppe(){
        return aldersGruppe;
    }

    // Setters
    public void setNavn(String nyNavn) {
        this.navn = nyNavn;
    }
    public void setAlder(int nyAlder) {
        this.alder=nyAlder;
    }
    public void setEmail(String nyEmail) {
        this.email = nyEmail;
    }
    public void setAktiv(boolean nyAktiv) {
        this.aktiv= nyAktiv;
    }
    public static void setNextID(int nextID) {
        Medlem.nextID = nextID;
    }
    public void setPrisKontigent(int alder, boolean aktiv){ // metoder der bliver kaldt, når et medlems informationer bliver redigeret,
        // fx går de fra ikke at være aktiv til aktiv, og de skal derfor betale mere i kontigent.
        double kontigent;
        if (aktiv) {
            if (alder < 18){
                kontigent = 1000;
            } else if (alder >= 18 && alder < 60) {
                kontigent = 1600;
            } else {
                kontigent = (1600 * 0.75);
            }
        } else {
            kontigent = 500;
        }
        this.prisKontigent = kontigent;
    }
    public void setTypeSvømmer(String typeSvømmer){
        this.typeSvømmer = typeSvømmer;
    }

    public double beregnPrisKontigent() { // metode der beregner, hvor meget et medlem skal betale i kontigent, tager højde for alder og aktivtets status
        double kontigent;
        if (aktiv) {
            if (alder < 18){
                kontigent = 1000;
            } else if (alder >= 18 && alder < 60) {
                kontigent = 1600;
            } else {
                kontigent = (1600 * 0.75);
            }
        } else {
            kontigent = 500;
        }
        return kontigent;
    }

    public boolean beregnRestance() { // metode der beregner om en person er i restance ud fra, om der er gået 365 fra deres sidste betalingsdato til i dag
        if (ChronoUnit.DAYS.between(datoForSidstBetaltKontigent, LocalDate.now()) >= 365) {
            restance = true;
        } else {
            restance = false;
        }
        return restance;
    }

    public String restanceChecker() { // metode der tjekker om en person er i restance(skylder penge til klubben), hvis de er, så bliver de markeret med rød.
        String farve = "";
        if (restance) {
            farve = "\u001B[31m";
        } else {
            farve = "\u001B[32m";
        }
        return farve;
    }
    public String calcAlder(){ //metode til at udregne om et medlem er junior eller senior baseret på om de er under eller over 18
        String aldersGruppe;
        if(this.alder<18){
            aldersGruppe = "Junior";
        } else {
            aldersGruppe = "Senior";
        }
        return aldersGruppe;
    }

    @Override
    public String toString() { //toString der override et medlems objekt, og printer dets informationer ud "pænt"..
        return restanceFarve + "Medlems ID: " + memberID + "\nNavn: " + navn + "\nAlder: " + alder +
                "\nAktiv: " + aktiv + "\nType af svømmer: " + typeSvømmer + "\nEmail: " + email +
                "\nÅrlig pris kontigent: " + prisKontigent + " kr" + "\nSidst betalt dato: " + datoForSidstBetaltKontigent +
                "\nEr personen i restance: " + restance + "\n";
    }
}
