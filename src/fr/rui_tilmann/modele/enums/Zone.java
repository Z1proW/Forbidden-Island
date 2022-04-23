package fr.rui_tilmann.modele.enums;

import javax.swing.*;
import java.awt.*;

public enum Zone
{

    NORMALE(""),
    AIR("air.png"),
    EAU("eau.png"),
    TERRE("terre.png"),
    FEU("feu.png"),
    HELIPORT("heliport.png");

    private final ImageIcon img;

    Zone(String fileName)
    {
        this.img = new ImageIcon("src/fr/rui_tilmann/images/zone/" + fileName);
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
