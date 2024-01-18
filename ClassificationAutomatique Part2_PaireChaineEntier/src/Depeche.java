import java.util.ArrayList;

public class Depeche {

    // Déclaration des variables qui définissent depeche
    private String id;
    private String date;
    private Categorie categorie;
    private String contenu;
    private ArrayList<String> mots;

    // Constructeur
    public Depeche(String id, String date, Categorie categorie, String contenu) {
        this.id = id;
        this.date = date;
        this.categorie = categorie;
        this.contenu = contenu;
        this.mots = decoupeEnMots(contenu);
    }


    /**
     * Découpe un String en mots et retourne une liste d'éléments sans la ponctuation.
     *
     * @param contenu Le String à découper en mots.
     * @return Une liste d'éléments représentant les mots extraits de la chaîne.
     */
    private ArrayList<String> decoupeEnMots(String contenu) {
        String chaine = contenu.toLowerCase();
        chaine = chaine.replace('\n', ' ');
        chaine = chaine.replace('.', ' ');
        chaine = chaine.replace(',', ' ');
        chaine = chaine.replace('\'', ' ');
        chaine = chaine.replace('\"', ' ');
        chaine = chaine.replace('(', ' ');
        chaine = chaine.replace(')', ' ');
        chaine = chaine.replace(':', ' ');
        String[] tabchaine = chaine.split(" ");
        ArrayList<String> resultat = new ArrayList<String>();
        for (int i = 0; i < tabchaine.length; i++) {
            if (!tabchaine[i].equals("")) {
                resultat.add(tabchaine[i]);
            }
        }
        return resultat;
    }

    // Getter
    public String getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public Categorie getCategorie() {
        return categorie;
    }
    public String getContenu() {
        return contenu;
    }
    public ArrayList<String> getMots() {
        return mots;
    }


    // Setter
    public void setId(String id) {
        this.id = id;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public void setMots(ArrayList<String> mots) {
        this.mots = mots;
    }

    /**
     * Affiche les caractéristiques de la dépeche, son ID, sa date, sa catégorie et son contenu.
     */
    public void afficher() {
        System.out.println("---------------------");
        System.out.println("depêche " + id);
        System.out.println("date:" + date);
        System.out.println("catégorie:" + categorie);
        System.out.println(contenu);
        System.out.println();
        System.out.println("---------------------");
    }

}

