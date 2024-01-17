import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UtilitairePaireChaineEntier {

    /**
     * Recherche une clé (mot) dans une Map et renvoie la clé correspondante
     * à partir d'une chaîne donnée, en utilisant une recherche binaire adaptée aux préfixes.
     *
     * @param treeMap Le TreeMap contenant les mots en tant que clés.
     * @param chaine La chaîne pour laquelle trouver la clé correspondante.
     * @return La clé correspondante à la chaîne spécifiée, ou une chaîne vide si aucune correspondance n'est trouvée.
     */
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


    /**
     * Obtient la valeur associée à une chaîne dans un TreeMap, en utilisant une recherche de clé.
     *
     * @param MapPaires La Map contenant les paires (clé, valeur).
     * @param chaine La chaîne pour laquelle obtenir la valeur associée.
     * @return La valeur associée à la chaîne spécifiée, ou 0 si aucune correspondance n'est trouvée.
     */
    public static int valueFromWord(Map<String, Integer> MapPaires, String chaine) {
        String key = keyFromWord(MapPaires, chaine);
        return key.isEmpty() ? 0 : MapPaires.get(key);
    }
}