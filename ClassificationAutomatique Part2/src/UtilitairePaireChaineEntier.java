import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UtilitairePaireChaineEntier {


    public static String keyFromWord(Map<String, Integer> treeMap, String chaine) {
        ArrayList<String> listePaires = new ArrayList<>(treeMap.keySet());
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

    public static int valueFromWord(Map<String, Integer> MapPaires, String chaine) {
        String key = keyFromWord(MapPaires, chaine);
        return key.isEmpty() ? 0 : MapPaires.get(key);
    }
}