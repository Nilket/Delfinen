package StævneTræningsTider;

import java.time.LocalDate;
import java.util.ArrayList;

public class TræningsTid {
    private double tid;
    private String medlemsNavn;
    private Disciplin disciplin;
    private LocalDate dato;
    private String aldersGruppe;



    public TræningsTid(String medlemsNavn, String aldersGruppe, double tid, Disciplin disciplin, int år, int måned, int dag) {
        this.medlemsNavn = medlemsNavn;
        this.tid = tid;
        this.disciplin = disciplin;
        this.dato = LocalDate.of(år,måned,dag);
        this.aldersGruppe = aldersGruppe;
    }



    // Getters
    public double getTid() {
        return this.tid;
    }
    public Disciplin getDisciplin() {
        return this.disciplin;
    }
    public String getMedlemsNavn() {
        return medlemsNavn;
    }
    public String getAldersGruppe(){
        return this.aldersGruppe;
    }


    //Metode til at konvertere datoen for et registreret trænings tid, fra typen LocalDate til en string
    //Så den kan blive gemt i en lokal fil
    public String getTræningDato() {
        String træningsDato = dato.toString();
        String [] træningtekstDato = træningsDato.split("-");
        String træningdone = "";
        træningdone += træningtekstDato[0];
        træningdone +=";" + træningtekstDato[1];
        træningdone +=";" + træningtekstDato[2];

        return træningdone;
    }



    @Override
    //En toString metode der override et træningstid objekts format og laver den til en læselig string
    //Som der kan printes ud
    public String toString() {
        return "Navn " + medlemsNavn + ", " + "Aldersgruppe: " + aldersGruppe +
                ", Træningstid: " + tid +
                ", disciplin; " + disciplin +
                ", dato: " + dato;
    }
}