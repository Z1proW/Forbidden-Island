package fr.rui_tilmann.Modele.Enums;

import javax.swing.*;
import java.awt.*;

public enum Carte
{

    AIR("air.png"),
    EAU("eau.png"),
    TERRE("terre.png"),
    FEU("feu.png"),
    HELICOPTERE("helico.png"),
    SAC_DE_SABLE("sable.png"),
    MONTEE_DES_EAUX("eaux.png");

    public static final int MAX = 8;

    private final ImageIcon img;

    Carte(String fileName)
    {
        this.img = new ImageIcon("src/fr/rui_tilmann/images/carte/" + fileName);
    }

    public Image getImage() {return img.getImage();}

    public Artefact toArtefact()
    {
        Artefact artefact = null;

        switch(this)
        {
            case FEU:   artefact = Artefact.FEU;   break;
            case TERRE: artefact = Artefact.TERRE; break;
            case EAU:   artefact = Artefact.EAU;   break;
            case AIR:   artefact = Artefact.AIR;   break;
        }
        return artefact;
    }

}
