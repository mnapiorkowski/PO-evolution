package zad1.instrukcje;

import zad1.parametry.*;
import zad1.roby.*;

public class InstrIdz extends Instrukcja {

    public void wykonaj(Rob rob, Parametry param) {

        rob.idźDoPrzodu(param.dajPlanszę(), param.dajIleDajeJedzenie());
    }

    public String toString() {

        return "i";
    }
}
