package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Carte;
import fr.rui_tilmann.Modele.Enums.Difficulte;

import java.util.Timer;
import java.util.TimerTask;

public class NiveauEau {

    private final Modele modele;
    private float niveau;

    public NiveauEau(Modele modele, Difficulte difficulte) {
        this.modele = modele;
        niveau = difficulte.ordinal();
    }

    public void monteeEau()
    {
        monteeEau((int)(niveau + 1));
    }

    private void monteeEau(int i)
    {
        if(niveau >= i) return;

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                niveau += 0.05;
                modele.notifyObservers();
                monteeEau(i);
            }
        }, 50);
    }

    public float getNiveau() {return this.niveau;}

    public int getNombreCartes()
    {
        if(niveau < 2) return 2;
        if(niveau < 5) return 3;
        if(niveau < 7) return 4;
        if(niveau < 9) return 5;
        return -1;
    }

}
