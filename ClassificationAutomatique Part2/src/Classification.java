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
        long startTime = System.currentTimeMillis();
        UtilitaireWrite.createFile(nomFichier);
        UtilitaireWrite.clear(nomFichier);

        for (Depeche depeche : depeches) {
            long depecheStartTime = System.currentTimeMillis();
            Categorie bestScoringCategorie = bestCategorie(depeche);
            System.out.println("bestScoringCategorie : " + (System.currentTimeMillis()-depecheStartTime) + "ms");
            UtilitaireWrite.write(nomFichier, depeche.getId() + ": " + bestScoringCategorie.getNom() + "\n");
            if (bestScoringCategorie.getNom().equals(depeche.getCategorie().getNom())) {
                bestScoringCategorie.score++;
            }
            depeche.getCategorie().nbDepeches++;
            System.out.println("Depeches : " + (System.currentTimeMillis()-depecheStartTime) + "ms");
        }

        UtilitaireWrite.write(nomFichier, "\n------------------\n");
        for (Categorie categorie : categories) {
            UtilitaireWrite.write(nomFichier, categorie.getNom() + ": " + (float) categorie.score / categorie.nbDepeches * 100 + "%\n");
        }
        UtilitaireWrite.write(nomFichier, "------------------");

        System.out.println("Classement : " + (System.currentTimeMillis()-startTime) + "ms");
    }


    /**
     * Initialise un dictionnaire contenant tout les mots pour une catégorie donné
     *
     * @param depeches listes de toutes les depeches
     * @param categorie le nom de la categorie pour laquelle on veut un dictionnaire
     * @return une map contenant tout les mots pour une categorie donne
     */
    public static Map<String, Integer> initDico(ArrayList<Depeche> depeches, String categorie) {
        TreeMap<String, Integer> dico = new TreeMap<>();

        for (Depeche depeche : depeches) {
            if(depeche.getCategorie().getNom().equals(categorie)){
            ArrayList<String> contenu = depeche.getMots();
            for (String mot : contenu) {
                if (!dico.containsKey(mot)) {
                    dico.put(mot, 0);
                }
            }
        }
        }
        return dico;
    }

    /**
     * Calcule les scores pour une catégorie spécifiée à partir d'une liste de dépeches et d'un dictionnaire.
     *
     * @param depeches La liste des dépeches à utiliser pour le calcul des scores.
     * @param categorie La catégorie pour laquelle calculer les scores.
     * @param dictionnaire Le dictionnaire contenant les mots et les scores associés.
     */
    public static void calculScores(ArrayList<Depeche> depeches, Categorie categorie, Map<String, Integer> dictionnaire) {
        for (Depeche depeche : depeches) {
            for (String mot : depeche.getMots()) {
                String key = UtilitairePaireChaineEntier.keyFromWord(dictionnaire, mot);
                if (!key.isEmpty()) {
                    int i = depeche.getCategorie().getNom().equals(categorie.getNom()) ? 1 : -1;
                    dictionnaire.put(key, dictionnaire.get(key) + i);
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
    public static void generationLexique(ArrayList<Depeche> depeches, Categorie categorie, String nomFichier){
        UtilitaireWrite.clear(nomFichier);
        Map<String, Integer> dicoCat = initDico(depeches, categorie.getNom());
        calculScores(depeches, categorie, dicoCat);
        for (Map.Entry<String, Integer> entry : dicoCat.entrySet()) {
            int score = entry.getValue();
            entry.setValue(poidsPourScore(score));
        }
        for (Map.Entry<String, Integer> entry : dicoCat.entrySet()) {
            // écrire tout les mots et poids associée pour des poids > 0
            if(entry.getValue() > 0){
                UtilitaireWrite.write(nomFichier, entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }
    }



    public static void main(String[] args) {
        //Chargement des dépêches en mémoire
        long startTime = System.currentTimeMillis();

        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        for (Categorie cat : categories) {
            String fileName = "./" + cat.getNom() + "Lexique.txt";
            System.out.println(fileName);
            UtilitaireWrite.createFile(fileName);
            generationLexique(depeches, cat, fileName);
            cat.initLexique(fileName);
        }
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        classementDepeches(depeches, "./output.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (endTime - startTime) + "ms");
    }

}

