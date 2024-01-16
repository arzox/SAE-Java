import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaireChaineEntier {
    private String chaine;
    private int entier;


    public PaireChaineEntier(String chaine, int entier){
        this.chaine = chaine;
        this.entier = entier;
    }

    public String getChaine(){
        return chaine;
    }

    public int getEntier(){
        return entier;
    }

    @Override
    public String toString() {
        return getChaine() + " poids: " + getEntier();
    }
}
