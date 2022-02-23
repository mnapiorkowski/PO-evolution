/*
 * PO 2021
 * zad 1 - ewolucja
 *
 * Michał Napiórkowski
 *
 * Rozwiązanie nie zawiera rozszerzeń ani modyfikacji względem oryginalnej treści.
 */
package zad1;

import zad1.parametry.*;
import zad1.roby.*;
import zad1.pola.*;
import zad1.instrukcje.*;
import zad1.parser.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Symulacja {
    private Parametry parametry;
    private int numer_nast_roba;
    private ArrayList<Rob> roby;
    private ArrayList<Rob> roby_nowe;
    private ArrayList<Rob> roby_martwe;
    private int ile_urodzonych;
    private int ile_zmarłych;

    public Symulacja(Parametry parametry) {
        this.parametry = parametry;
        this.numer_nast_roba = parametry.dajPoczIleRobów();
        this.roby = new ArrayList<>();
        for (int i = 0; i < parametry.dajPoczIleRobów(); i++) {
            int kierunek = losujKierunek();
            Pole pole = parametry.dajPlanszę().losujPole();
            roby.add(new Rob(i, parametry.dajPoczProgr(), parametry.dajPoczEnergia(), kierunek, pole));
        }
        this.roby_martwe = new ArrayList<>();
        this.roby_nowe = new ArrayList<>();
        ile_urodzonych = parametry.dajPoczIleRobów();
        ile_zmarłych = 0;
    }

    public Parametry dajParametry() {

        return parametry;
    }

    public int dajNumerNastRoba() {

        return numer_nast_roba;
    }

    private double[] daneProgr() {
        double[] dane = new double[3];
        dane[0] = Integer.MAX_VALUE;
        for (int i = 0; i < roby.size(); i++) {
            int dl = roby.get(i).długośćProgramu();
            if (dl < dane[0]) {
                dane[0] = dl;
            }
            if (dl > dane[2]) {
                dane[2] = dl;
            }
            dane[1] = (i * dane[1] + dl) / (i + 1);
        }
        if (dane[0] == Integer.MAX_VALUE)
            dane[0] = 0;
        return dane;
    }

    private double[] daneEnerg() {
        double[] dane = new double[3];
        dane[0] = Integer.MAX_VALUE;
        for (int i = 0; i < roby.size(); i++) {
            int en = roby.get(i).dajEnergię();
            if (en < dane[0]) {
                dane[0] = en;
            }
            if (en > dane[2]) {
                dane[2] = en;
            }
            dane[1] = (i * dane[1] + en) / (i + 1);
        }
        if (dane[0] == Integer.MAX_VALUE)
            dane[0] = 0;
        return dane;
    }

    private double[] daneWiek() {
        double[] dane = new double[3];
        dane[0] = Integer.MAX_VALUE;
        for (int i = 0; i < roby.size(); i++) {
            int w = roby.get(i).dajWiek();
            if (w < dane[0]) {
                dane[0] = w;
            }
            if (w > dane[2]) {
                dane[2] = w;
            }
            dane[1] = (i * dane[1] + w) / (i + 1);
        }
        if (dane[0] == Integer.MAX_VALUE)
            dane[0] = 0;
        return dane;
    }

    public void wypiszDane(int nr_tury) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("tura #");
        sbuf.append(nr_tury);
        sbuf.append(", roby: ");
        sbuf.append(roby.size());
        sbuf.append(", żywność: ");
        sbuf.append(parametry.dajPlanszę().zliczŻywnościowe());
        sbuf.append(", wiek: ");
        double dane[] = daneWiek();
        sbuf.append((int)dane[0]);
        sbuf.append("/");
        sbuf.append(String.format("%.1f",dane[1]));
        sbuf.append("/");
        sbuf.append((int)dane[2]);
        sbuf.append(", energ: ");
        dane = daneEnerg();
        sbuf.append((int)dane[0]);
        sbuf.append("/");
        sbuf.append(String.format("%.1f",dane[1]));
        sbuf.append("/");
        sbuf.append((int)dane[2]);
        sbuf.append(", progr: ");
        dane = daneProgr();
        sbuf.append((int)dane[0]);
        sbuf.append("/");
        sbuf.append(String.format("%.1f",dane[1]));
        sbuf.append("/");
        sbuf.append((int)dane[2]);
        System.out.println(sbuf.toString());
    }

    public void raportuj(int nr_tury) {
        if (nr_tury == 0) {
            System.out.println("===== roby rozpoczynające symulację ======");
        }
        else if (nr_tury == parametry.dajIleTur() + 1) {
            System.out.println("\n===== żyjące roby po zakończeniu symulacji ======");
        }
        else {
            System.out.println("\n===== żyjące roby po turze " + nr_tury + " ======");
        }
        for (int i = 0; i < roby.size(); i++) {
            System.out.println(roby.get(i).toString());
        }

        System.out.println("\n===== aktualny stan planszy ======");
        System.out.println(parametry.dajPlanszę().toString());

        System.out.println("liczba robów urodzonych od początku symulacji: " + ile_urodzonych);
        System.out.println("liczba robów zmarłych od początku symulacji:  " + ile_zmarłych);
    }

    public void dodajRoba(Rob rob) {
        roby_nowe.add(rob);
        numer_nast_roba++;
        ile_urodzonych++;
    }

    private void dodajNowe() {
        roby.addAll(roby_nowe);
        roby_nowe.clear();
    }

    public void usuńRoba(Rob rob) {
        roby_martwe.add(rob);
        ile_zmarłych++;
    }

    private void usuńMartwe() {
        roby.removeAll(roby_martwe);
        roby_martwe.clear();
    }

    private int losujKierunek() {
        Random r = new Random();
        return r.nextInt(4);
    }

    public Instrukcja losujInstrukcję() {
        Random r = new Random();
        int indeks = r.nextInt(parametry.dajSpisInstrukcji().length);
        return parametry.dajSpisInstrukcji()[indeks];
    }

    public static void main(String[] args) throws FileNotFoundException {
        File plansza_txt = new File(args[0]);
        Scanner sc = new Scanner(plansza_txt);
        Parser parser = new Parser();
        parser.parsujPlanszę(sc);
        ArrayList<String> plan_planszy = parser.dajPlanPlanszy();

        File parametry_txt = new File(args[1]);
        sc = new Scanner(parametry_txt);
        parser.parsujParametry(sc);
        int[] param_i = parser.dajParamI();
        double[] param_d = parser.dajParamD();
        String spis_instr = parser.dajSpisInstr();
        String pocz_progr = parser.dajPoczProgr();

        Parametry parametry = new Parametry(param_i, param_d, pocz_progr, spis_instr, plan_planszy);
        Symulacja sym = new Symulacja(parametry);

        sym.raportuj(0);
        int do_wypisania = sym.parametry.dajCoIleWypisz();
        for (int i = 0; i < sym.parametry.dajIleTur(); i++) {
            for (int j = 0; j < sym.roby.size(); j++) {
                sym.roby.get(j).rozegrajTurę(sym);
            }
            sym.usuńMartwe();
            sym.dodajNowe();
            sym.parametry.dajPlanszę().regeneruj();
            sym.wypiszDane(i + 1);
            do_wypisania--;
            if (do_wypisania == 0) {
                sym.raportuj(i + 1);
                do_wypisania = sym.parametry.dajCoIleWypisz();
            }
        }
        sym.raportuj(sym.parametry.dajIleTur() + 1);
    }
}
