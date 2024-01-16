import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private HashMap<String, Integer> lexique; //le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }


    public String getNom() {
        return nom;
    }


    public HashMap<String, Integer> getLexique() {
        return lexique;
    }


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {

        lexique = new HashMap<>();

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


    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche depeche) {
        int score = 0;
        for (String mot : depeche.getMots()) {
            score += UtilitairePaireChaineEntier.entierPourChaine(lexique, mot);
        }
        return score;
    }
}
