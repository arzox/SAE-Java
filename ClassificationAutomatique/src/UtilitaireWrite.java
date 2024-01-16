import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
}
