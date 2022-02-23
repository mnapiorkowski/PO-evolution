package zad1.instrukcje;

import zad1.parametry.*;
import zad1.roby.*;

public abstract class Instrukcja {

    public abstract void wykonaj(Rob rob, Parametry param);

    public abstract String toString();

}
