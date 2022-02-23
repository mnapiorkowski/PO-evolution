package zad1.pola;

import java.util.ArrayList;
import java.util.Random;

public class Plansza {
    private int rozmiar_planszy_x;
    private int rozmiar_planszy_y;
    private Pole[][] pola;

    public Plansza(ArrayList<String> plan_planszy, int ile_rośnie_jedzenie) {
        this.rozmiar_planszy_x = plan_planszy.get(0).length();
        this.rozmiar_planszy_y = plan_planszy.size();
        this.pola = new Pole[rozmiar_planszy_x][rozmiar_planszy_y];
        for (int i = 0; i < rozmiar_planszy_x; i++) {
            for (int j = 0; j < rozmiar_planszy_y; j++) {
                boolean czy_żywieniowe;
                if (plan_planszy.get(j).charAt(i) == 'x') {
                    czy_żywieniowe = true;
                }
                else czy_żywieniowe = false;
                pola[i][j] = new Pole(i, j, czy_żywieniowe, ile_rośnie_jedzenie);
            }
        }
    }

    private static int mod(int a, int b) {
        int c = a % b;
        return (c < 0) ? c + b : c;
    }

    public Pole sąsiad(Pole pole, int kierunek) {
        int x = pole.x;
        int y = pole.y;

        switch (kierunek) {
            case 0: // góra
                y = mod(y - 1, rozmiar_planszy_y); break;
            case 1: // gp
                y = mod(y - 1, rozmiar_planszy_y);
                x = mod(x + 1, rozmiar_planszy_x); break;
            case 2: // prawo
                x = mod(x + 1, rozmiar_planszy_x); break;
            case 3: // dp
                y = mod(y + 1, rozmiar_planszy_y);
                x = mod(x + 1, rozmiar_planszy_x); break;
            case 4: // dół
                y = mod(y + 1, rozmiar_planszy_y); break;
            case 5: // dl
                y = mod(y + 1, rozmiar_planszy_y);
                x = mod(x - 1, rozmiar_planszy_x); break;
            case 6: // lewo
                x = mod(x - 1, rozmiar_planszy_x); break;
            case 7: // gl
                y = mod(y - 1, rozmiar_planszy_y);
                x = mod(x - 1, rozmiar_planszy_x); break;
            default:
                System.err.println("Niepoprawny kierunek");
        }
        return pola[x][y];
    }

    public void regeneruj() {
        for (int i = 0; i < rozmiar_planszy_x; i++) {
            for (int j = 0; j < rozmiar_planszy_y; j++) {
                pola[i][j].rośnieJedzenie();
            }
        }
    }

    public int zliczŻywnościowe() {
        int zlicz = 0;
        for (int i = 0; i < rozmiar_planszy_x; i++) {
            for (int j = 0; j < rozmiar_planszy_y; j++) {
                if (pola[i][j].czyJestJedzenie()) {
                    zlicz++;
                }
            }
        }
        return zlicz;
    }

    public Pole losujPole() {
        Random r = new Random();
        int x = r.nextInt(rozmiar_planszy_x);
        int y = r.nextInt(rozmiar_planszy_y);
        return pola[x][y];
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < rozmiar_planszy_y; i++) {
            for (int j = 0; j < rozmiar_planszy_x; j++) {
                if (pola[j][i].czyJestJedzenie())
                    sbuf.append("x");
                else
                    sbuf.append(" ");
            }
            sbuf.append("\n");
        }
        return sbuf.toString();
    }
}
