package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Difficulte;

public class NiveauEau {

    private int niveau;

    public NiveauEau(Difficulte difficulte) {
        niveau = difficulte.ordinal();
    }

    public void monteeEau() {niveau++;}

    public int getNiveau() {return this.niveau;}

    public int getNombreCartes()
    {
        if(niveau < 2) return 2;
        if(niveau < 5) return 3;
        if(niveau < 7) return 4;
        if(niveau < 9) return 5;
        return -1;
    }


}
