package zad1.roby;

import zad1.Symulacja;
import zad1.instrukcje.*;
import zad1.pola.*;

import java.util.Random;

public class Rob {
    private int numer_id;
    private int wiek;
    private Program program;
    private int energia;
    private int kierunek; // 0 -> góra, 1 -> gp, 2 -> prawo, 3 -> dp, 4 -> dół, 5 -> dl, 6 -> lewo, 7 -> gl
    private Pole pole;

    public Rob(int numer, Program program, int energia, int kierunek, Pole pole) {
        this.numer_id = numer;
        this.wiek = 0;
        this.program = program;
        this.energia = energia;
        this.kierunek = kierunek;
        this.pole = pole;
    }

    public int dajKierunek() {

        return kierunek;
    }

    public int dajEnergię() {

        return energia;
    }

    public int dajWiek() {

        return wiek;
    }

    public int długośćProgramu() {

        return program.dajListęInstrukcji().size();
    }

    public void zmieńKierunek(int nowy_kierunek) {

        kierunek = nowy_kierunek;
    }

    public void idźDoPrzodu(Plansza plansza, int ile_daje_jedzenie) {
        pole = plansza.sąsiad(pole, kierunek);
        if (pole.czyJestJedzenie()) {
            energia += ile_daje_jedzenie;
        }
        pole.zabierzJedzenie();
    }

    public int sprawdźPola(int ile, Plansza plansza) {
        for (int i = 0; i < 8; i += 2) {
            if (plansza.sąsiad(pole, i).czyJestJedzenie()) {
                return i;
            }
        }

        if (ile == 8) { // jeśli mamy sprawdzić też skosy
            for (int i = 1; i < 8; i += 2) {
                if (plansza.sąsiad(pole, i).czyJestJedzenie()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void powiel(Symulacja sym) {
        int nowy_numer = sym.dajNumerNastRoba();
        int nowa_energia = (int)Math.round(sym.dajParametry().dajUłamekEnergiiRodzica() * energia);
        int nowy_kierunek = (kierunek + 4) % 8;
        sym.dodajRoba(new Rob(nowy_numer, program.mutuj(sym), nowa_energia, nowy_kierunek, pole));
        energia -= nowa_energia;
    }

    public void rozegrajTurę(Symulacja sym) {
        // wykonuje instrukcje dopóki starczy mu energii
        for (int i = 0; energia >= 0 && i < program.dajListęInstrukcji().size(); i++) {
            program.dajListęInstrukcji().get(i).wykonaj(this, sym.dajParametry());
            energia--;
        }
        if (energia < 0) {
            sym.usuńRoba(this);
            return;
        }

        // powiela się z prawdopodobieństwem pr_powielenia
        Random r = new Random();
        boolean czy_powielać = r.nextDouble() < sym.dajParametry().dajPrPowielenia();
        if (czy_powielać && energia >= sym.dajParametry().dajLimitPowielania()) {
            powiel(sym);
        }

        // traci energię za rozegranie tury
        energia -= sym.dajParametry().dajKosztTury();
        if (energia < 0) {
            sym.usuńRoba(this);
            return;
        }
        wiek++;
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("rob #");
        sbuf.append(numer_id);
        sbuf.append(", wiek: ");
        sbuf.append(wiek);
        sbuf.append(", energ: ");
        sbuf.append(energia);
        sbuf.append(", progr: ");
        sbuf.append(program.toString());
        sbuf.append(", pole: [");
        sbuf.append(pole.dajX());
        sbuf.append(",");
        sbuf.append(pole.dajY());
        sbuf.append("]");
        return sbuf.toString();
    }
}
