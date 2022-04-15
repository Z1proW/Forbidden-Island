package fr.rui_tilmann.Modele.Enums;

import javax.swing.*;
import java.awt.*;

public enum Tresor
{
    AIR("air.png"),
    EAU("eau.png"),
    TERRE("terre.png"),
    FEU("feu.png"),
    HELICOPTERE("helico.png"),
    SAC_DE_SABLE("sable.png"),
    MONTEE_DES_EAUX("eaux.png");

    private final ImageIcon img;

    Tresor(String fileName)
    {
        this.img = new ImageIcon("src/fr/rui_tilmann/images/carte/" + fileName);
    }

    public Image getImage() {return img.getImage();}

}
