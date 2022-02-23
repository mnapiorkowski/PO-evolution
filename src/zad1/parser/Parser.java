package zad1.parser;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    private ArrayList<String> plan_planszy;
    private int[] param_i;
    private double[] param_d;
    private String spis_instr;
    private String pocz_progr;

    public Parser() {
        this.plan_planszy = new ArrayList<>();
        this.param_i = new int[8];
        this.param_d = new double[5];
    }

    public ArrayList<String> dajPlanPlanszy() {

        return plan_planszy;
    }

    public int[] dajParamI() {

        return param_i;
    }

    public double[] dajParamD() {

        return param_d;
    }

    public String dajSpisInstr() {

        return spis_instr;
    }

    public String dajPoczProgr() {

        return pocz_progr;
    }

    private static void wypiszBłąd(String str) {
        System.err.println(str);
        System.exit(1);
    }

    private void sprawdźWierszPlanszy(String wiersz, int dł_wiersza) {
        if (wiersz.length() != dł_wiersza)
            wypiszBłąd("Plansza nie jest prostokątem");

        for (int i = 0; i < wiersz.length(); i++) {
            if (wiersz.charAt(i) != ' ' && wiersz.charAt(i) != 'x')
                wypiszBłąd("Plansza zawiera niepoprawny znak");
        }
    }

    public void parsujPlanszę(Scanner sc) {
        int dł_wiersza = -1;
        if (!sc.hasNextLine())
            wypiszBłąd("Plansza jest pusta");

        while (sc.hasNextLine()) {
            String wiersz = sc.nextLine();
            if (dł_wiersza < 0)
                dł_wiersza = wiersz.length();
            else if (dł_wiersza == 0)
                wypiszBłąd("Plansza zawiera pusty wiersz");

            sprawdźWierszPlanszy(wiersz, dł_wiersza);
            plan_planszy.add(wiersz);
        }

    }

    private void sprawdźParametrI(int i, String param, boolean[] param_był, Scanner sc) {
        if (!param_był[i]) {
            if (sc.hasNextInt()) {
                param_i[i] = sc.nextInt();
                if (param_i[i] < 0)
                    wypiszBłąd("Wartość parametru " + param + " nie może być ujemna");
                param_był[i] = true;
            }
            else
                wypiszBłąd("Brak poprawnej wartości parametru " + param);
        }
        else
            wypiszBłąd("Parametr " + param + " wystąpił więcej niż 1 raz");
    }

    private void sprawdźParametrD(int i, String param, boolean[] param_był, Scanner sc) {
        if (!param_był[i]) {
            if (sc.hasNextDouble()) {
                param_d[i - 8] = sc.nextDouble();
                if (param_d[i - 8] < 0 || param_d[i - 8] > 1)
                    wypiszBłąd("Wartość parametru " + param + " musi mieścić się w przedziale [0,1]");
                param_był[i] = true;
            }
            else
                wypiszBłąd("Brak poprawnej wartości parametru " + param);
        }
        else
            wypiszBłąd("Parametr " + param + " wystąpił więcej niż 1 raz");
    }

    private String sprawdźParametrS(int i, String param, boolean[] param_był, Scanner sc) {
        if (!param_był[i]) {
            param_był[i] = true;

            if (sc.hasNext()) {
                String instrukcje = sc.findInLine("[lpwij]+");
                if (instrukcje == null) {
                    String reszta_wiersza = sc.findInLine(".*\\S.*");
                    if (reszta_wiersza == null) {
                        if (param.equals("spis_instr"))
                            wypiszBłąd("Spis instrukcji nie może być pusty");
                        else
                            return "";
                    }
                    else // reszta wiersza zawiera co najmniej jeden znak nie-biały
                        wypiszBłąd("Brak poprawnej wartości parametru " + param);
                }
                else if (param.equals("spis_instr")) {
                    boolean[] czy_wystąpiła = new boolean[5];
                    for (int j = 0; j < instrukcje.length(); j++) {
                        switch (instrukcje.charAt(j)) {
                            case 'l': {
                                if (czy_wystąpiła[0])
                                    wypiszBłąd("Instrukcje w spisie nie mogą się powtarzać");
                                else
                                    czy_wystąpiła[0] = true;
                                break;
                            }
                            case 'p': {
                                if (czy_wystąpiła[1])
                                    wypiszBłąd("Instrukcje w spisie nie mogą się powtarzać");
                                else
                                    czy_wystąpiła[1] = true;
                                break;
                            }
                            case 'w': {
                                if (czy_wystąpiła[2])
                                    wypiszBłąd("Instrukcje w spisie nie mogą się powtarzać");
                                else
                                    czy_wystąpiła[2] = true;
                                break;
                            }
                            case 'i': {
                                if (czy_wystąpiła[3])
                                    wypiszBłąd("Instrukcje w spisie nie mogą się powtarzać");
                                else
                                    czy_wystąpiła[3] = true;
                                break;
                            }
                            case 'j': {
                                if (czy_wystąpiła[4])
                                    wypiszBłąd("Instrukcje w spisie nie mogą się powtarzać");
                                else
                                    czy_wystąpiła[4] = true;
                                break;
                            }
                            default:
                                wypiszBłąd("Niepoprawna instrukcja");
                        }
                    }
                    return instrukcje;
                }
                else
                    return instrukcje;
            }
            else if (param.equals("spis_instr"))
                wypiszBłąd("Spis instrukcji nie może być pusty");
            else
                return "";
        }
        else
            wypiszBłąd("Parametr " + param + " wystąpił więcej niż 1 raz");

        return "";
    }

    public void parsujParametry(Scanner sc) {
        boolean[] param_był = new boolean[15];

        while (sc.hasNextLine()) {
            String param = sc.next();
            switch (param) {
                case "ile_tur":
                    sprawdźParametrI(0, param, param_był, sc);
                    break;
                case "pocz_ile_robów":
                    sprawdźParametrI(1, param, param_był, sc);
                    break;
                case "pocz_energia":
                    sprawdźParametrI(2, param, param_był, sc);
                    break;
                case "ile_daje_jedzenie":
                    sprawdźParametrI(3, param, param_był, sc);
                    break;
                case "ile_rośnie_jedzenie":
                    sprawdźParametrI(4, param, param_był, sc);
                    break;
                case "koszt_tury":
                    sprawdźParametrI(5, param, param_był, sc);
                    break;
                case "limit_powielania":
                    sprawdźParametrI(6, param, param_był, sc);
                    break;
                case "co_ile_wypisz":
                    sprawdźParametrI(7, param, param_był, sc);
                    break;
                case "pr_powielania":
                    sprawdźParametrD(8, param, param_był, sc);
                    break;
                case "ułamek_energii_rodzica":
                    sprawdźParametrD(9, param, param_był, sc);
                    break;
                case "pr_usunięcia_instr":
                    sprawdźParametrD(10, param, param_był, sc);
                    break;
                case "pr_dodania_instr":
                    sprawdźParametrD(11, param, param_był, sc);
                    break;
                case "pr_zmiany_instr":
                    sprawdźParametrD(12, param, param_był, sc);
                    break;
                case "pocz_progr":
                    pocz_progr = sprawdźParametrS(13, param, param_był, sc);
                    break;
                case "spis_instr":
                    spis_instr = sprawdźParametrS(14, param, param_był, sc);
                    break;
                default:
                    wypiszBłąd("Nieprawidłowa nazwa parametru: " + param);
            }
        }

        for (int i = 0; i < param_był.length; i++) {
            if (!param_był[i])
                wypiszBłąd("Nie podano wszystkich wymaganych parametrów");
        }

        if (!pocz_progr.matches("[" + spis_instr + "]*"))
            wypiszBłąd("W pocz_progr istnieje instrukcja spoza spis_instr");
    }
}
