import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilitairePaireChaineEntier {


    public static String keyFromWord(Map<String, Integer> hashMapPaires, String chaine) {
        ArrayList<String> listePaires = new ArrayList<>(hashMapPaires.keySet());
        int debut = 0;
        int fin = listePaires.size() - 1;
        int milieu = (debut + fin) / 2;
        while (debut < fin) {
            boolean isTheWorldAlmostTheSame = chaine.startsWith(listePaires.get(milieu));
            if (isTheWorldAlmostTheSame) {
                return listePaires.get(milieu);
            } else if (listePaires.get(milieu).toLowerCase().compareTo(chaine.toLowerCase()) < 0) {
                debut = milieu + 1;
            } else {
                fin = milieu;
            }
            milieu = (debut + fin) / 2;
        }
        return "";
    }
}