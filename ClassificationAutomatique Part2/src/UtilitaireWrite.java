import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UtilitaireWrite {
    /**
     * Écrit le contenu en parametres dans le fichier spécifié
     *
     * @param nomFichier Le chemin du fichier dans lequel écrire le contenu
     * @param contenu Le contenu à écrire dans le fichier
     * @throws RuntimeException Si une erreur d'entrée/sortie survient lors de l'écriture dans le fichier
     */
    public static void write(String nomFichier, String contenu) {
        try {
            Files.writeString(Paths.get(nomFichier), contenu, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Supprime tout le contenu dans le fichier spécifié
     *
     * @param nomFichier Le chemin du fichier dans lequel supprimer le contenu
     * @throws RuntimeException Si une erreur d'entrée/sortie survient lors de l'écriture dans le fichier
     */
    public static void clear(String nomFichier) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(nomFichier);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.print("");
        writer.close();
    }

    /**
     * Permet de crée un fichier avec un nom spécifié
     *
     * @param nomFichier Le chemin où crée le fichier avec son nom
     */
    public static void createFile(String nomFichier){
        File file = new File(nomFichier);
    }
}
