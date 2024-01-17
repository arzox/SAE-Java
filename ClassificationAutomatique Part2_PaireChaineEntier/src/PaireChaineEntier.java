import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaireChaineEntier {

    // Déclaration des variables qui définissent PaireChaineEntier
    private String chaine;
    private int entier;

    // Constructeur
    public PaireChaineEntier(String chaine, int entier){
        this.chaine = chaine;
        this.entier = entier;
    }

    // Getters
    public String getChaine(){
        return chaine;
    }

    public int getEntier(){
        return entier;
    }

    // setter
    public void setEntier(int nouveauScore) {
        entier = nouveauScore;
    }

    /**
     * Renvoie l'objet PaireChaineEntier sous forme de chaîne (chaine, poids:)
     *
     * @return Une chaîne de la forme "chaine poids: entier".
     */
    @Override
    public String toString() {
        return getChaine() + " poids: " + getEntier();
    }

}
