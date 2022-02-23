package zad1.instrukcje;

import zad1.parametry.*;
import zad1.roby.*;

public class InstrLewo extends Instrukcja {

    public void wykonaj(Rob rob, Parametry param) {
        int kierunek = rob.dajKierunek();
        kierunek = (kierunek - 2) % 8;
        if (kierunek < 0) kierunek += 8;
        rob.zmieńKierunek(kierunek);
    }

    public String toString() {

        return "l";
    }
}
