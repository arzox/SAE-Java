import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification {

    // Initialisation de toutes les categories
    static Categorie sport = new Categorie("SPORTS");
    static Categorie culture = new Categorie("CULTURE");
    static Categorie economie = new Categorie("ECONOMIE");
    static Categorie politique = new Categorie("POLITIQUE");
    static Categorie envScience = new Categorie("ENVIRONNEMENT-SCIENCES");

    // Initialisation d'un Vecteur contenant toutes les categories
    static ArrayList<Categorie> categories = new ArrayList<>(Arrays.asList(sport, culture, economie, politique, envScience));
    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes += '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, getCategorieFromName(categorie), lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    /**
     * Renvoie une instance de la classe Categorie correspondant au nom spécifié.
     *
     * @param name Le nom de la catégorie à rechercher.
     * @return Une instance de la classe Categorie correspondant au nom, ou null si aucune catégorie n'est trouvée.
     */
    public static Categorie getCategorieFromName(String name) {
        for (Categorie categorie : categories) {
            if (categorie.getNom().equalsIgnoreCase(name)) {
                return categorie;
            }
        }
        return null;
    }


    /**
     * Identifie la meilleure catégorie pour une dépeche en évaluant les scores de chaque catégorie.
     *
     * @param depeche La dépeche pour où on recherche la meilleure catégorie.
     * @return La meilleure catégorie identifiée pour la dépeche, basée sur les scores calculés.
     */
    private static Categorie bestCategorie(Depeche depeche) {
        int max = 0;
        Categorie bestCategorie = categories.get(0);
        for (Categorie categorie : categories) {
            int currentScore = categorie.score(depeche);
            if (currentScore > max) {
                max = currentScore;
                bestCategorie = categorie;
            }
        }
        return bestCategorie;
    }


    /**
     * Classe  les depeches en fonction des catégories et écrit le résultat dans le fichier spécifié
     * Donne le pourcentage des résultats pour chaque catégories
     *
     * @param depeches La liste des dépeches à classer.
     * @param nomFichier Le nom du fichier dans lequel enregistrer les résultats.
     */
    public static void classementDepeches(ArrayList<Depeche> depeches, String nomFichier) {
        UtilitaireWrite.clear(nomFichier);

        for (Depeche depeche : depeches) {
            Categorie bestScoringCategorie = bestCategorie(depeche);
            UtilitaireWrite.write(nomFichier, depeche.getId() + ": " + bestScoringCategorie.getNom() + "\n");
            if (bestScoringCategorie.getNom().equals(depeche.getCategorie().getNom())) {
                bestScoringCategorie.score++;
            }
            depeche.getCategorie().nbDepeches++;
        }

        UtilitaireWrite.write(nomFichier, "\n------------------\n");
        for (Categorie categorie : categories) {
            UtilitaireWrite.write(nomFichier, categorie.getNom() + ": " + (float) categorie.score / categorie.nbDepeches * 100 + "%\n");
        }
        UtilitaireWrite.write(nomFichier, "------------------");
    }

    public static void main(String[] args) {

        sport.initLexique("./SPORTS.txt");
        economie.initLexique("./ECONOMIE.txt");
        politique.initLexique("./POLITIQUE.txt");
        envScience.initLexique("./ENVIRONNEMENT-SCIENCES.txt");
        culture.initLexique("./CULTURE.txt");

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        classementDepeches(depeches, "./output.txt");
    }
}

