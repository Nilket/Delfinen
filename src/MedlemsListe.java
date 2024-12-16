import java.util.ArrayList;

public class MedlemsListe {
    //en Object ArrayListe af typen Medlem, som består af medlemmer
    private ArrayList<Medlem> medlemmer = new ArrayList<>(); // et


    // metode til at tilføje et medlem til medlemmer listen, dens inputs kommer fra UI klassen
    public void addMedlem(String navn, int alder, boolean medlemsType, String email, int år, int måned, int dag, String typeSvømmer) {
        medlemmer.add(new Medlem(navn, alder, medlemsType, email, år, måned, dag, typeSvømmer));
    }

    //metode til at redigere et medlems informationer, dens input kommer fra UI klassen
    public void redigerMedlem(int id, String nytNavn, int nyAlder, boolean nytMedlemsType, String nyEmail, String nyTypeSvømmer) {
        Medlem medlem = søgMedlemVedID(id);
        if (medlem != null) {
            medlem.setNavn(nytNavn);
            medlem.setAlder(nyAlder);
            medlem.setAktiv(nytMedlemsType);
            medlem.setEmail(nyEmail);
            medlem.setTypeSvømmer(nyTypeSvømmer);
            System.out.println("Medlemmet er opdateret.");
            System.out.println(medlem);
        } else {
            System.out.println("Intet medlem med ID: " + id + " blev fundet.");
        }
    }

    // Getter
    public ArrayList<Medlem> getMedlemmer() {
        return medlemmer;
    }

    //printer alle medlemmer i medlemmer arraylisten ud
    public void visMedlemmer() {
        for(Medlem medlem: medlemmer){
            System.out.println(medlem);
        }
    }

    //iterare igennem medlemmer listen og finder et eller flere navne basseret på paramereten "delvistNavn"
    public ArrayList<Medlem> søgMedlemmerVedDelvistNavn(String delvistNavn) {
        ArrayList<Medlem> fundneMedlemmer = new ArrayList<>();

        for (Medlem medlem : medlemmer) {
            if (medlem.getName().toLowerCase().contains(delvistNavn)) {
                fundneMedlemmer.add(medlem);
            }
        }
        return fundneMedlemmer;
    }



    // Søgning i listen
    public Medlem søgMedlemVedID(int ID) {
        for (Medlem medlem : medlemmer) {
            if (medlem.getMemberID() == ID) {
                return medlem;
            }
        }
        System.out.println("Intet medlem med ID: " + ID + " blev fundet.");
        return null;
    }


    //iterare igennem medlemmer listen, og beregner den årlige kontigent for medlemmer der ikke er i restance
    public void beregnSamletKontigent() {
        double samletKontigent = 0;
        for (Medlem medlem : medlemmer) {
            if (!medlem.getRestance()) {
                samletKontigent += medlem.getPrisKontigent();
            }
        }
        System.out.println("Det samlede kontigent er: " + samletKontigent + " kr.");
    }

    public void printRestanceMedlemmer(){
        System.out.println("\nDisse mennesker er i restance:");
        for(Medlem i : medlemmer){
            if(i.getRestance()== true){
                System.out.println(i);
            }
        }
    }

    public void printIkkeRestanceMedlemmer(){
        System.out.println("\nDisse mennesker er ikke i restance:");
        for(Medlem i : medlemmer){
            if(i.getRestance()== false){
                System.out.println(i);
            }
        }
    }

    public void printSeniorKonkurrencesvømmere(){
        System.out.println("\nListe over senior konkurrencesvømmer: ");
        for(Medlem i : medlemmer){
            if(i.getAlder() >= 18 && i.getTypeSvømmer().equalsIgnoreCase("Konkurrencesvømmer")){
                System.out.println(i);
            }
        }
    }

    public void printJuniorKonkurrencesvømmere(){
        System.out.println("\nListe over junior konkurrencesvømmer: ");
        for(Medlem i : medlemmer){
            if(i.getAlder() < 18 && i.getTypeSvømmer().equalsIgnoreCase("Konkurrencesvømmer")){
                System.out.println(i);
            }
        }
    }

}
