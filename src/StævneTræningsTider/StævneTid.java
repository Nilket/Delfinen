package StævneTræningsTider;

import java.time.LocalDate;
import java.util.ArrayList;

public class StævneTid {
    private String stævneNavn;
    private String medlemsNavn;
    private Disciplin disciplin;
    private int placering;
    private double tid;
    private LocalDate dato;
    private String aldersGruppe;



    public StævneTid (String medlemsNavn, String aldersGruppe, String stævneNavn, Disciplin disciplin, int placering, double tid, int år, int måned, int dag) {
        this.medlemsNavn = medlemsNavn;
        this.stævneNavn = stævneNavn;
        this.disciplin = disciplin;
        this.placering = placering;
        this.tid = tid;
        this.dato = LocalDate.of(år,måned,dag);
        this.aldersGruppe = aldersGruppe;
    }
    // Getters
    public String getMedlemsNavn (){
        return this.medlemsNavn;
    }
    public String getStævneNavn() {
        return this.stævneNavn;
    }
    public Disciplin getDisciplin() {
        return disciplin;
    }
    public int getPlacering() {
        return this.placering;
    }
    public double getTid() {
        return this.tid;
    }
    public String getAldersGruppe(){
        return this.aldersGruppe;
    }



    //Metode til at konvertere datoen for et registreret stævne tid, fra typen LocalDate til en string
    //Så den kan blive gemt i en lokal fil
    public String getStævneDato() {
        String stævneDato = dato.toString();
        String [] stævneTekstDato = stævneDato.split("-");
        String stævneDone = "";
        stævneDone += stævneTekstDato[0];
        stævneDone +=";" + stævneTekstDato[1];
        stævneDone +=";" + stævneTekstDato[2];
        return stævneDone;
    }



    @Override
    public String toString() {
        return "Navn " + medlemsNavn + ", Aldersgruppe: " + aldersGruppe +
                ", stævnenavn: " + stævneNavn +
                ", placering: " + placering +
                ", disciplin: " + disciplin +
                ", tid: " + tid +
                ", dato: " + dato;
    }
}