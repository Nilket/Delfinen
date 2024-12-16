package StævneTræningsTider;

import java.util.ArrayList;
import java.util.Comparator;

public class TidsLister {
    private ArrayList<TræningsTid> træningsTider = new ArrayList<>();
    private ArrayList<StævneTid> stævneTider = new ArrayList<>();

    public void addTræningsTid(String medlemsNavn, String aldersGruppe, double tid, Disciplin disciplin, int år, int måned, int dag) {
        træningsTider.add(new TræningsTid(medlemsNavn, aldersGruppe, tid,disciplin,år, måned, dag));
    }

    public void addStævneTid(String medlemsNavn, String aldersGruppe, String stævneNavn, Disciplin disciplin, int placering, double tid, int år, int måned, int dag) {
        stævneTider.add(new StævneTid(medlemsNavn, aldersGruppe, stævneNavn,disciplin,placering,tid,år,måned,dag));
    }

    //Visser alle tider i systemet, både stævne og træning
    public void visTider() {
        System.out.println("\nStævne tider:");
        for(StævneTid stævneTid: stævneTider) {
            System.out.println(stævneTid);
        }
        System.out.println("\nTrænings tider:");
        for (TræningsTid træningsTid : træningsTider) {
            System.out.println(træningsTid);
        }
    }

    public ArrayList<StævneTid> getStævneTider() {
        return stævneTider;
    }

    public ArrayList<TræningsTid> getTræningsTider() {
        return træningsTider;
    }

    //Metode der modtager et input fra UI, hvis det input passer med en disciplin
    //Så retunerer den en enum disciplin, hvis den ikke matcher, så retunere den null
    public Disciplin hentDisciplin(String disciplinValg) {
        Disciplin disciplinInput = null;

        if (disciplinValg.equalsIgnoreCase("Crawl")) {
            disciplinInput=  Disciplin.CRAWL;

        } else if (disciplinValg.equalsIgnoreCase("Rygcrawl")) {
            disciplinInput = Disciplin.RYGCRAWL;

        } else if (disciplinValg.equalsIgnoreCase("Butterfly")) {
            disciplinInput = Disciplin.BUTTERFLY;

        } else if (disciplinValg.equalsIgnoreCase("Brystsvømning")) {
            disciplinInput = Disciplin.BRYSTSVØMNING;

        } else {
            disciplinInput = null;
        }

        return disciplinInput;
    }

    //Visser top 5 stævne tider, ud fra to paratmetre, disciplin og alder
    public void visTop5Stævnetider(Disciplin disciplin, String aldersValg) {
        //stævneTider listen bliver sorteret i forhold tid højest til lavest
        getStævneTider().sort(Comparator.comparing(StævneTid::getTid ));

        ArrayList<StævneTid> top5Senior = new ArrayList<>();
        ArrayList<String> tilføjedeMedlemmerSenior = new ArrayList<>();

        //først iterare den igennem stævneTid listen og tager kun dem, som har den disciplin, man ønsker at se
        for (StævneTid i : getStævneTider()) {
            if (i.getDisciplin().equals(disciplin)) {
                //nedenunder bliver medlemsNavnet gemt for den person, som har lavet en tid i den givne disciplin, dette gemmes i en string
                String medlemsNavn = i.getMedlemsNavn();

                //Nedenunder til
                if (!tilføjedeMedlemmerSenior.contains(i.getMedlemsNavn()) && i.getAldersGruppe().equalsIgnoreCase(aldersValg)) {
                    top5Senior.add(i);
                    tilføjedeMedlemmerSenior.add(medlemsNavn);
                    // her tilføjes stringen til en arraylist, så der kan tjekkes om medlemmet allerede er i top 5 Listen(top5Senior)
                }
            }
        }
        //Hvis der er blevet tilføjet mere end 5 personer til top5Senior arraylisten, så printer den kun de første 5 ud
        //hvis der er færre end 5, så bliver det antal medlemmer printet ud
        int a = Math.min(5, top5Senior.size());
        for (int i = 0; i < a; i++) {
            System.out.println(top5Senior.get(i));
        }
    }

    //Visser top 5 trænings tider, ud fra to paratmetre, disciplin og alder
    //Princippet er den samme som visTop5StævneTider
    public void visTop5Træningstider(Disciplin disciplin, String aldersValg) {
        getTræningsTider().sort(Comparator.comparing(TræningsTid::getTid ));

        ArrayList<TræningsTid> top5Junior = new ArrayList<>();
        ArrayList<String> tilføjedeMedlemmerJunior = new ArrayList<>();

        for (TræningsTid i : getTræningsTider()) {
            if (i.getDisciplin().equals(disciplin)) {
                String medlemsNavn = i.getMedlemsNavn();
                if (!tilføjedeMedlemmerJunior.contains(i.getMedlemsNavn()) && i.getAldersGruppe().equalsIgnoreCase(aldersValg)) {
                    top5Junior.add(i);
                    tilføjedeMedlemmerJunior.add(medlemsNavn);
                }
            }
        }
        int a = Math.min(5, top5Junior.size());
        for (int i = 0; i < a; i++) {
            System.out.println(top5Junior.get(i));
        }
    }
}
