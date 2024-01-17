import java.io.*;
import java.util.*;

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

    /**
     * Initialise un dictionnaire contenant tout les mots pour une catégorie donné
     *
     * @param depeches listes de toutes les depeches
     * @param categorie le nom de la categorie pour laquelle on veut un dictionnaire
     * @return une liste de PaireChaineEntier contenant tout les mots pour une categorie donne
     */
    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> dico = new ArrayList<>();

        for (Depeche depeche : depeches) {
            ArrayList<String> contenu = depeche.getMots();
            for (String mot : contenu) {
                int indice = UtilitairePaireChaineEntier.indicePourChaine(dico, mot);
                if (depeche.getCategorie().getNom().equals(categorie)) {
                    if(indice == -1) {
                        insereTrie(new PaireChaineEntier(mot, 0), dico);
                    }
                    else{
                        int value = dico.get(indice).getEntier() + 1;
                        dico.get(indice).setEntier(value);
                    }
                }
                else{
                    if(indice != -1){
                        int value = dico.get(indice).getEntier() - 1;
                        dico.get(indice).setEntier(value);
                    }
                }
            }
        }
        return dico;
    }

    private static void insereTrie(PaireChaineEntier paireChaineEntier, ArrayList<PaireChaineEntier> dico){
        if(dico.isEmpty()){
            dico.add(paireChaineEntier);
            return;
        }
        int debut = 0;
        int fin = dico.size() - 1;
        int milieu = (debut + fin) / 2;
        while (debut < fin) {
            if (dico.get(milieu).getChaine().toLowerCase().compareTo(paireChaineEntier.getChaine().toLowerCase()) < 0) {
                debut = milieu + 1;
            } else {
                fin = milieu;
            }
            milieu = (debut + fin) / 2;
        }
        dico.add(fin, paireChaineEntier);
    }


    /**
     * Calcule les scores pour une catégorie spécifiée à partir d'une liste de dépeches
     * et d'une liste d'objets PaireChaineEntier représentant le dictionnaire.
     *
     * @param depeches La liste des dépeches à utiliser pour le calcul des scores.
     * @param categorie La catégorie pour laquelle calculer les scores.
     * @param dictionnaire La liste d'objets PaireChaineEntier représentant le dictionnaire.
     */
    public static void calculScores(ArrayList<Depeche> depeches, Categorie categorie, ArrayList<PaireChaineEntier> dictionnaire) {
        for (Depeche depeche : depeches) {
            for (String mot : depeche.getMots()) {
                int indice = UtilitairePaireChaineEntier.indicePourChaine(dictionnaire, mot);
                if (indice != -1) {
                    int i = depeche.getCategorie().getNom().equals(categorie.getNom()) ? 1 : -1;
                    int nouveauScore = dictionnaire.get(indice).getEntier() + i;
                    dictionnaire.get(indice).setEntier(nouveauScore);
                    System.out.println(dictionnaire.get(indice).getEntier());
                }
            }
        }
    }

    /**
     * Calcule le poids associé à un score donné en fonction de plages de scores spécifiées.
     *
     * @param score Le score pour lequel calculer le poids.
     * @return Le poids associé au score, selon des plages prédéfinies.
     */
    public static int poidsPourScore(int score) {
        if (score < 0) {
            return 0;
        } else if (score <= 5) {
            return 1;
        } else if (score <= 10) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Génère un lexique pour une catégorie spécifiée à partir d'une liste de dépeches,
     * en calculant les scores, appliquant des poids et enregistrant les mots et poids dans un fichier.
     *
     * @param depeches La liste des dépeches à utiliser pour la génération du lexique.
     * @param categorie La catégorie pour laquelle générer le lexique.
     */
    public static void generationLexique(ArrayList<Depeche> depeches, Categorie categorie){
        ArrayList<PaireChaineEntier> dicoCat = initDico(depeches, categorie.getNom());

        for (int i = 0; i < dicoCat.size(); i++) {
            int score = dicoCat.get(i).getEntier();
            int poidsScore = poidsPourScore(score);
            if (poidsScore < 0) {
                dicoCat.remove(i);
            }
        }
        categorie.setLexique(dicoCat);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        //Chargement des dépêches en mémoire
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        for (Categorie cat : categories) {
            generationLexique(depeches, cat);
            System.out.println(cat.getLexique());
        }

        classementDepeches(depeches, "./output.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (endTime - startTime) + "ms");

    }
}

