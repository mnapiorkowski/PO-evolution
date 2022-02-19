package zad1.instrukcje;

import zad1.parametry.*;
import zad1.roby.*;

public class InstrPrawo extends Instrukcja {

    public void wykonaj(Rob rob, Parametry param) {
        int kierunek = rob.dajKierunek();
        rob.zmieńKierunek((kierunek + 2) % 8);
    }

    public String toString() {

        return "p";
    }
}
