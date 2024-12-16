import StævneTræningsTider.TidsLister;

public class Main {
    public static void main(String[] args) {
        MedlemsListe medlemsListe = new MedlemsListe();
        TidsLister tidsLister = new TidsLister();

        UI ui = new UI(medlemsListe, tidsLister);
        ui.menu();
    }
}
