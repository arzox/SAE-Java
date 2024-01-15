import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return 0;

    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return 0;
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
        return 0;
    }

}
