package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Difficulte;

public class NiveauEau {

    private int niveau;

    public NiveauEau(Difficulte difficulte) {
        niveau = difficulte.ordinal();
    }

    public void monteeEau() {niveau++;}

    public int getNiveau() {return this.niveau;}

}
