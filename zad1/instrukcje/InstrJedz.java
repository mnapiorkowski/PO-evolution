package zad1.instrukcje;

import zad1.parametry.*;
import zad1.roby.*;

public class InstrJedz extends Instrukcja {

    public void wykonaj(Rob rob, Parametry param) {
        int kierunek = rob.sprawdźPola(8, param.dajPlanszę());
        if (kierunek >= 0) {
            rob.zmieńKierunek(kierunek);
            rob.idźDoPrzodu(param.dajPlanszę(), param.dajIleDajeJedzenie());
        }
    }

    public String toString() {

        return "j";
    }
}
