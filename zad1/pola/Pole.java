package zad1.pola;

public class Pole {
    private boolean żywieniowe;
    protected int x;
    protected int y;
    private boolean jest_jedzenie;
    private int ile_rośnie_jedzenie;
    private int ile_do_odnowienia;

    public Pole(int x, int y, boolean czy_żywieniowe, int ile_rośnie_jedzenie) {
        this.x = x;
        this.y = y;
        this.żywieniowe = czy_żywieniowe;
        this.jest_jedzenie = czy_żywieniowe;
        this.ile_rośnie_jedzenie = ile_rośnie_jedzenie;
        this.ile_do_odnowienia = 0;
    }

    public int dajX() {

        return x;
    }

    public int dajY() {

        return y;
    }

    public boolean czyJestJedzenie() {
        return jest_jedzenie;
    }

    public void zabierzJedzenie() {
        jest_jedzenie = false;
        ile_do_odnowienia = ile_rośnie_jedzenie;
    }

    public void rośnieJedzenie() {
        if (żywieniowe && !jest_jedzenie) {
            if (ile_do_odnowienia == 0) {
                jest_jedzenie = true;
            }
            else ile_do_odnowienia--;
        }
    }
}
