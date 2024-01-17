import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Categorie {
    // le nom de la catégorie p.ex : sport, politique,...
    private String nom;

    //le lexique de la catégorie
    private Map<String, Integer> lexique;

    // score des catégories
    public int score = 0;

    // nombre de depeches
    public int nbDepeches = 0;

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }

    // getter
    public String getNom() {
        return nom;
    }
    public Map<String, Integer> getLexique() {
        return lexique;
    }

    public void setLexique(Map<String, Integer> lexique) {
        this.lexique = lexique;
    }

    /**
     * Calcule le score d'une dépeche à partir de la somme des scores des mots dans le lexique.
     *
     * @param depeche La dépeche pour laquelle calculer le score.
     * @return Le score total de la dépeche basé sur les scores des mots dans le lexique.
     */
    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche depeche) {
        int score = 0;
        for (String mot : depeche.getMots()) {
            score += UtilitairePaireChaineEntier.valueFromWord(lexique, mot);
        }
        return score;
    }
}
