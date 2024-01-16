import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UtilitaireWrite {
    public static void write(String nomFichier, String contenu) {
        try {
            Files.writeString(Paths.get(nomFichier), contenu, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void createFile(String nomFichier){
        File file = new File(nomFichier);
    }
}
