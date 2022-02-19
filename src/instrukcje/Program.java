package zad1.instrukcje;

import zad1.*;
import java.util.ArrayList;
import java.util.Random;

public class Program {
    private ArrayList<Instrukcja> lista_instrukcji;

    public Program(ArrayList<Instrukcja> instrukcje) {

        this.lista_instrukcji = instrukcje;
    }

    public ArrayList<Instrukcja> dajListęInstrukcji() {

        return lista_instrukcji;
    }

    public Program mutuj(Symulacja sym) {
        ArrayList<Instrukcja> nowa_lista = new ArrayList<>();
        nowa_lista.addAll(lista_instrukcji);

        Random r = new Random();
        double ułamek = r.nextDouble();
        if (ułamek < sym.dajParametry().dajPrawdopodobieństwo(0) && !nowa_lista.isEmpty()) {
            nowa_lista.remove(nowa_lista.size() - 1);
        }
        ułamek = r.nextDouble();
        if (ułamek < sym.dajParametry().dajPrawdopodobieństwo(1)) {
            Instrukcja losowa = sym.losujInstrukcję();
            nowa_lista.add(losowa);
        }
        ułamek = r.nextDouble();
        if (ułamek < sym.dajParametry().dajPrawdopodobieństwo(2) && !nowa_lista.isEmpty()) {
            int indeks = r.nextInt(nowa_lista.size());
            Instrukcja nowa = sym.losujInstrukcję();
            nowa_lista.set(indeks, nowa);
        }

        return new Program(nowa_lista);
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        for (Instrukcja instrukcja : lista_instrukcji) {
            sbuf.append(instrukcja.toString());
        }
        return sbuf.toString();
    }

}
