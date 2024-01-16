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
            String ligne = scanner.nextLine();

            while (scanner.hasNextLine() && !ligne.equals("")) {

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


    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche depeche) {
        int score = 0;
        for (String mot : depeche.getMots()) {
            score += UtilitairePaireChaineEntier.entierPourChaine(lexique, mot);
        }
        return score;
    }

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