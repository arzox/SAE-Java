import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int debut = 0;
        int fin = listePaires.size() - 1;
        int milieu = (debut + fin) / 2;
        while (debut <= fin) {
            if (listePaires.get(milieu).getChaine().equals(chaine)) {
                return milieu;
            } else if (listePaires.get(milieu).getChaine().compareTo(chaine) < 0) {
                debut = milieu + 1;
            } else {
                fin = milieu - 1;
            }
            milieu = (debut + fin) / 2;
        }
        return -1;
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return listePaires.get(indicePourChaine(listePaires, chaine)).getEntier();
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        int max = 0;
        String maxString = "";
        for (PaireChaineEntier hashMapDubled : listePaires) {
            if (hashMapDubled.getEntier() > max) {
                max = hashMapDubled.getEntier();
                maxString = hashMapDubled.getChaine();
            }
        }
        return maxString;
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int somme = 0;
        for (PaireChaineEntier paireChaineEntier : listePaires) {
            somme += paireChaineEntier.getEntier();
        }
        return (float) somme / listePaires.size();
    }

}
