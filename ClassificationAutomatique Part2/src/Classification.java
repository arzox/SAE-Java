import java.io.*;
import java.util.*;

public class Classification {
    static Categorie sport = new Categorie("SPORTS");
    static Categorie culture = new Categorie("CULTURE");
    static Categorie economie = new Categorie("ECONOMIE");
    static Categorie politique = new Categorie("POLITIQUE");
    static Categorie envScience = new Categorie("ENVIRONNEMENT-SCIENCES");
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

    public static Categorie getCategorieFromName(String name) {
        for (Categorie categorie : categories) {
            if (categorie.getNom().equalsIgnoreCase(name)) {
                return categorie;
            }
        }
        return null;
    }

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

    public static Map<String, Integer> initDico(ArrayList<Depeche> depeches, String categorie) {
        TreeMap<String, Integer> dico = new TreeMap<>();

        for (Depeche depeche : depeches) {
            ArrayList<String> contenu = depeche.getMots();
            for (String mot : contenu) {
                if (!dico.containsKey(mot)) {
                    dico.put(mot, 0);
                }
            }
        }
        return dico;
    }


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

    public static void generationLexique(ArrayList<Depeche> depeches, Categorie categorie, String nomFichier){
        Map<String, Integer> dicoCat = initDico(depeches, categorie.getNom());
        calculScores(depeches, categorie, dicoCat);

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


        Map<String, Integer> dicoSport = initDico(depeches, sport.getNom());
    }

    public static void afficherHashMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Clé : " + entry.getKey() + " Valeur : " + entry.getValue());
        }
    }

}

