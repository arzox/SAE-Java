import java.io.FileWriter;
import java.io.IOException;

public class UtilitaireWrite {
    public static void write(String nomFichier, String contenu) {
        try {
            FileWriter file = new FileWriter(nomFichier);
            file.write(contenu);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
