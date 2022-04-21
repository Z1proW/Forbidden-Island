package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Carte;
import fr.rui_tilmann.Modele.Enums.Difficulte;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
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
        jouerSon();

        new Timer().schedule(new TimerTask()
        {
            int i = 0;

            @Override
            public void run()
            {
                niveau += 0.1;
                modele.notifyObservers();

                i++;
                if(i >= 10) cancel();
            }
        }, 0, 50);
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

    private void jouerSon()
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/fr/rui_tilmann/audio/water.wav")));
            clip.start();
        }
        catch(Exception e) {e.getMessage();}

    }

}
