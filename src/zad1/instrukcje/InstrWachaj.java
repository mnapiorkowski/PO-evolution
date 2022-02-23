package zad1.instrukcje;

import zad1.parametry.*;
import zad1.roby.*;

public class InstrWachaj extends Instrukcja {

    public void wykonaj(Rob rob, Parametry param) {
        int kierunek = rob.sprawdźPola(4, param.dajPlanszę());
        if (kierunek >= 0) {
            rob.zmieńKierunek(kierunek);
        }
    }

    public String toString() {

        return "w";
    }
}
