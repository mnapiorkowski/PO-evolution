package zad1.parametry;

import zad1.instrukcje.*;
import zad1.pola.Plansza;

import java.util.ArrayList;

public class Parametry {
    private int ile_tur;
    private int pocz_ile_robów;
    private Program pocz_progr;
    private int pocz_energia;
    private int ile_daje_jedzenie;
    private int ile_rośnie_jedzenie;
    protected int koszt_tury;
    protected double pr_powielenia;
    protected double ułamek_energii_rodzica;
    protected int limit_powielania;
    private double pr_usunięcia_instr;
    private double pr_dodania_instr;
    private double pr_zmiany_instr;
    private Instrukcja[] spis_instr;
    private int co_ile_wypisz;
    private Plansza plansza;

    public Parametry(int[] param_i, double[] param_d, String pocz_progr, String spis_instr, ArrayList<String> plan_planszy) {
        this.ile_tur = param_i[0];
        this.pocz_ile_robów = param_i[1];
        this.pocz_energia = param_i[2];
        this.ile_daje_jedzenie = param_i[3];
        this.ile_rośnie_jedzenie = param_i[4];
        this.koszt_tury = param_i[5];
        this.limit_powielania = param_i[6];
        this.co_ile_wypisz = param_i[7];
        this.pr_powielenia = param_d[0];
        this.ułamek_energii_rodzica = param_d[1];
        this.pr_usunięcia_instr = param_d[2];
        this.pr_dodania_instr = param_d[3];
        this.pr_zmiany_instr = param_d[4];

        this.spis_instr = new Instrukcja[spis_instr.length()];
        for (int i = 0; i < spis_instr.length(); i++) {
            switch (spis_instr.charAt(i)) {
                case 'l':
                    this.spis_instr[i] = new InstrLewo(); break;
                case 'p':
                    this.spis_instr[i] = new InstrPrawo(); break;
                case 'i':
                    this.spis_instr[i] = new InstrIdz(); break;
                case 'w':
                    this.spis_instr[i] = new InstrWachaj(); break;
                case 'j':
                    this.spis_instr[i] = new InstrJedz(); break;
                default:
                    System.err.println("Niepoprawna instrukcja");
            }
        }

        ArrayList<Instrukcja> pocz_instr = new ArrayList<>();
        for (int i = 0; i < pocz_progr.length(); i++) {
            switch (pocz_progr.charAt(i)) {
                case 'l':
                    pocz_instr.add(new InstrLewo()); break;
                case 'p':
                    pocz_instr.add(new InstrPrawo()); break;
                case 'i':
                    pocz_instr.add(new InstrIdz()); break;
                case 'w':
                    pocz_instr.add(new InstrWachaj()); break;
                case 'j':
                    pocz_instr.add(new InstrJedz()); break;
                default:
                    System.err.println("Niepoprawna instrukcja");
            }
        }
        this.pocz_progr = new Program(pocz_instr);
        this.plansza = new Plansza(plan_planszy, ile_rośnie_jedzenie);
    }

    public Plansza dajPlanszę() {

        return plansza;
    }

    public double dajPrawdopodobieństwo(int x) {
        switch (x) {
            case 0:
                return pr_usunięcia_instr;
            case 1:
                return pr_dodania_instr;
            case 2:
                return pr_zmiany_instr;
            default:
                System.err.println("Niepoprawny argument");
        }
        return 0;
    }

    public int dajIleDajeJedzenie() {

        return ile_daje_jedzenie;
    }

    public int dajIleTur() {

        return ile_tur;
    }

    public int dajCoIleWypisz() {

        return co_ile_wypisz;
    }

    public Instrukcja[] dajSpisInstrukcji() {

        return spis_instr;
    }

    public int dajPoczIleRobów() {

        return pocz_ile_robów;
    }

    public int dajPoczEnergia() {

        return pocz_energia;
    }

    public Program dajPoczProgr() {

        return pocz_progr;
    }

    public double dajPrPowielenia() {

        return pr_powielenia;
    }

    public int dajLimitPowielania() {

        return limit_powielania;
    }

    public int dajKosztTury() {

        return koszt_tury;
    }

    public double dajUłamekEnergiiRodzica() {

        return ułamek_energii_rodzica;
    }
}
