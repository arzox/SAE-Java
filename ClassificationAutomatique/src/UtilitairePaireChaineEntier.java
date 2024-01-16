import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int debut = 0;
        int fin = listePaires.size() - 1;
        int milieu = (debut + fin) / 2;
        while (debut < fin) {
            boolean isTheWorldAlmostTheSame = chaine.startsWith(listePaires.get(milieu).getChaine());
            if (isTheWorldAlmostTheSame) {
                return milieu;
            } else if (listePaires.get(milieu).getChaine().toLowerCase().compareTo(chaine.toLowerCase()) < 0) {
                debut = milieu + 1;
            } else {
                fin = milieu;
            }
            milieu = (debut + fin) / 2;
        }
        return -1;
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int indice = indicePourChaine(listePaires, chaine);
        if (indice == -1) {
            return 0;
        } else {
            return listePaires.get(indice).getEntier();
        }
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        int max = listePaires.get(0).getEntier();
        String maxString = listePaires.get(0).getChaine();
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
