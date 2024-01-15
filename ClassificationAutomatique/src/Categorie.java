import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique; //le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }


    public String getNom() {
        return nom;
    }


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {
        lexique = new ArrayList<>();

        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            String chaine = "";

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                int length = ligne.length();

                char nombre = ligne.charAt(length - 1);
                int nb = Integer.parseInt(String.valueOf(nombre));

                ligne = scanner.nextLine();
                int separatorIndex = ligne.indexOf(':');
                if (separatorIndex != -1) {
                    chaine = ligne.substring(0, separatorIndex);
                }

                while (scanner.hasNextLine() && separatorIndex != -1) {
                    ligne = scanner.nextLine();
                    if (separatorIndex != -1) {
                        chaine = ligne.substring(0, separatorIndex);
                    }
                }
                PaireChaineEntier unlexique = new PaireChaineEntier(chaine, nb);
                lexique.add(unlexique);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        return 0;
    }

}
