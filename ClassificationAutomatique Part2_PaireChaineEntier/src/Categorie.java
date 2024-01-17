import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    // le nom de la catégorie p.ex : sport, politique,...
    private String nom;

    //le lexique de la catégorie
    private ArrayList<PaireChaineEntier> lexique;

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


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }

    public void setLexique(ArrayList<PaireChaineEntier> lexique) {
        this.lexique = lexique;
    }

    /**
     * Initialise le lexique en lisant les données à partir d'un fichier spécifié.
     *
     * @param nomFichier Le chemin du fichier où lire les données pour initialiser le lexique.
     */
    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {

        lexique = new ArrayList<>();

        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            String ligne = scanner.nextLine();

            while (scanner.hasNextLine() && !ligne.equals("")) {

                // récupérer le mot et son poids attribué
                String[] mot = ligne.split(":");

                PaireChaineEntier paireChaineEntier = new PaireChaineEntier(mot[0], Integer.parseInt(mot[1]));

                if(lexique.isEmpty()){
                    lexique.add(paireChaineEntier);
                }
                else{
                    insereTrie(paireChaineEntier);
                }

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
            score += UtilitairePaireChaineEntier.entierPourChaine(lexique, mot);
        }
        return score;
    }


    /**
     * Insère une PaireChaineEntier (chaine, entier) à la bonne position dans le lexique pour conserver le trie.
     *
     * @param paireChaineEntier l'objet PaireChaineEntier (chaine, entier) à insérer dans le lexique de manière triée.
     */
    private void insereTrie(PaireChaineEntier paireChaineEntier){
        int debut = 0;
        int fin = lexique.size() - 1;
        int milieu = (debut + fin) / 2;
        while (debut < fin) {
            if (lexique.get(milieu).getChaine().toLowerCase().compareTo(paireChaineEntier.getChaine().toLowerCase()) < 0) {
                debut = milieu + 1;
            } else {
                fin = milieu;
            }
            milieu = (debut + fin) / 2;
        }
        lexique.add(fin, paireChaineEntier);
    }
}
