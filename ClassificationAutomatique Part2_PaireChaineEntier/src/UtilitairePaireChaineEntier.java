import java.util.ArrayList;

public class UtilitairePaireChaineEntier {

    /**
     * Recherche l'indice d'une chaîne dans une liste triée de paires (chaine, entier)
     * en utilisant une recherche binaire adaptée aux préfixes.
     *
     * @param listePaires La liste triée de paires (chaine, entier) à rechercher.
     * @param chaine La chaîne à trouver dans la liste.
     * @return L'indice de la première paire dont la chaîne est un préfixe de la chaîne spécifiée,
     *         ou -1 si la chaîne n'est pas trouvée.
     */
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


    /**
     * Récupère l'entier associé à une chaîne dans une liste triée de PaireChaineEntier.
     *
     * @param listePaires La liste triée de PaireChaineEntier à rechercher.
     * @param chaine La chaîne pour laquelle obtenir l'entier associé.
     * @return L'entier associé à la chaîne spécifiée, ou 0 si la chaîne n'est pas trouvée.
     */
    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int indice = indicePourChaine(listePaires, chaine);
        if (indice == -1) {
            return 0;
        } else {
            return listePaires.get(indice).getEntier();
        }
    }


    /**
     * Recherche la plus grande chaine de caractères dans une liste triée de PaireChaineEntier.
     *
     * @param listePaires La liste triée de PaireChaineEntier où rechercher la plus grande chaine.
     * @return la chaine de caractères ayant le plus grand nombre de caractères.
     */
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

    /**
     * Calcul la moyenne des entiers dans une liste de PaireChaineEntier.
     *
     * @param listePaires La liste de PaireChaineEntier où calculer la moyenne des entiers.
     * @return la moyenne des entiers porter par chaque objet PaireChaineEntier.
     */
    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int somme = 0;
        for (PaireChaineEntier paireChaineEntier : listePaires) {
            somme += paireChaineEntier.getEntier();
        }
        return (float) somme / listePaires.size();
    }

}
