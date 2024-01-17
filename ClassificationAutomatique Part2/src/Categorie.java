import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Categorie {
    // le nom de la catégorie p.ex : sport, politique,...
    private String nom;

    //le lexique de la catégorie
    private TreeMap<String, Integer> lexique;

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
    public TreeMap<String, Integer> getLexique() {
        return lexique;
    }


    /**
     * Initialise le lexique en lisant les données à partir d'un fichier spécifié.
     *
     * @param nomFichier Le chemin du fichier où lire les données pour initialiser le lexique.
     */
    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {

        lexique = new TreeMap<>();

        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            String ligne = scanner.nextLine();

            while (scanner.hasNextLine() && !ligne.equals("")) {

                String[] mot = ligne.split(":");

                lexique.put(mot[0], Integer.parseInt(mot[1]));

                ligne = scanner.nextLine();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
