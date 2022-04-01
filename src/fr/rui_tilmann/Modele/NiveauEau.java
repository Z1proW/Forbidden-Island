package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Difficulte;

public class NiveauEau {
    private final Difficulte difficulte;
    private  int current;

    public NiveauEau(Difficulte difficulte) {
        this.difficulte = difficulte;
        switch (difficulte) {
            case NOVICE:  current = 0;break;
            case NORMAL:  current = 1;break;
            case ELITE :  current = 2;break;
            case LEGENDAIRE :  current = 3;break;
        }
    }

    public void monteeEau(){
        current += 1;
    }

    public int getCurrent(){
        return  this.current;
    }
}
